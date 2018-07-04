package com.prism.pickany247;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.prism.pickany247.Adapters.CartAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Helper.PrefManager;
import com.prism.pickany247.Interface.CartProductClickListener;
import com.prism.pickany247.Interface.CartDataSet;
import com.prism.pickany247.Response.CartResponse;
import com.prism.pickany247.Response.Product;
import com.prism.pickany247.Singleton.AppController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    AppController appController;
    private ProgressDialog pDialog;
    private PrefManager pref;
    private static Products products;
    private BaseAdapter productsAdapter;
    private int j = 1;
    String userId;
    CartResponse cartResponse = new CartResponse();
    Gson gson;
    public TextView txtSubTotal;
    public Button btnCheckOut;
    public String length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cart");

        appController = (AppController) getApplication();

        pref = new PrefManager(getApplicationContext());
        // Displaying user information from shared preferences
        HashMap<String, String> profile = pref.getUserDetails();
        userId=profile.get("id");

        txtSubTotal=(TextView)findViewById(R.id.txtSubTotal);
        btnCheckOut=(Button)findViewById(R.id.btnCheckOut);


        if (appController.isConnection()) {

            // Showing progress dialog before making http request
            pDialog = new ProgressDialog(this);

            pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.setContentView(R.layout.my_progress);

            cartData();


        } else {

            setContentView(R.layout.internet);

            Button tryButton = (Button) findViewById(R.id.btnTryagain);
            tryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            // app.internetDilogue(KitchenitemListActivity.this);

        }


    }


    private void cartData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.CART_URL + userId, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response", "" + response);

                hidePDialog();
                gson = new Gson();
                cartResponse = gson.fromJson(response, CartResponse.class);

                final ArrayList<Product> productArrayList = new ArrayList<>();

                for (CartResponse.CartItemsBean cartItemsBean : cartResponse.getCart_items()) {

                    productArrayList.add(new Product(cartItemsBean.getProduct_name(), Integer.parseInt(cartItemsBean.getQuantity()), Double.parseDouble(cartItemsBean.getUnit_price_incl_tax()), cartItemsBean.getImage(),
                            cartItemsBean.getModule(),cartItemsBean.getUser_id(),cartItemsBean.getItem_id(),cartItemsBean.getId(),cartItemsBean.getProduct_id()));

                    products = new Products(productArrayList);

                    Log.e("LENGTH",""+products.size());

                    length= String.valueOf(products.size());

                }

                productsAdapter = new CartAdapter(products, productClickListener, getLayoutInflater(), getApplicationContext());

                ListView productsListView = (ListView) findViewById(R.id.products_list);
                productsListView.setAdapter(productsAdapter);



                // calculate total amount
                calculateMealTotal();

                btnCheckOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (cartResponse.getMessage().equalsIgnoreCase("No address found")){

                            Intent intent =new Intent(CartActivity.this, AddAddressActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        }else if (cartResponse.getMessage().equalsIgnoreCase("List of items in cart")){

                            for (CartResponse.UserAddressBean addressInfoBean:cartResponse.getUser_address()){

                                Intent intent =new Intent(CartActivity.this, CheckoutActivity.class);
                                intent.putExtra("addressId",addressInfoBean.getId());
                                intent.putExtra("itemslength",length);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }


                        }
                    }
                });


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePDialog();

                //  Toast.makeText(getApplicationContext(),"toast",Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(CartActivity.this);
        requestQueue.add(stringRequest);


    }


    private final CartProductClickListener productClickListener = new CartProductClickListener() {
        @Override
        public void onMinusClick(Product product) {

            if (j >= 1) {
                products.removeOneFrom(product);
               // updateQuantity(product);
                productsAdapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onPlusClick(Product product) {
            products.addOneTo(product);
           // updateQuantity(product)1231231;
            productsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onSaveClick(Product product) {

            saveWishList(product);
            productsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onRemoveClick(Product product) {

            products.delete(product);
            productsAdapter.notifyDataSetChanged();
        }


    };


    private class Products implements CartDataSet {

        private List<Product> productList;

        Products(List<Product> productList) {
            this.productList = productList;
        }


        @Override
        public int size() {
            return productList.size();
        }

        @Override
        public Product get(int position) {
            return productList.get(position);
        }

        @Override
        public long getId(int position) {
            return position;
        }

        public void removeOneFrom(Product product) {
            int i = productList.indexOf(product);

            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }

            if (product.quantity > 1) {

                Product updatedProduct = new Product(product.name, (product.quantity - 1), product.price, product.thumbnail,product.module,product.user_id,product.item_id,product.cartId,product.productId);
                productList.remove(product);
                productList.add(i, updatedProduct);

                updateQuantity(updatedProduct);

                calculateMealTotal();
            }

        }

        public void addOneTo(Product product) {
            int i = productList.indexOf(product);
            if (i == -1) {
                throw new IndexOutOfBoundsException();
            }
            Product updatedProduct = new Product(product.name, (product.quantity + 1), product.price, product.thumbnail,product.module,product.user_id,product.item_id,product.cartId,product.productId);
            productList.remove(product);
            productList.add(i, updatedProduct);

            Log.e("QUNATITY",""+updatedProduct.quantity);

            updateQuantity(updatedProduct);

            calculateMealTotal();
        }


        // Remove from Cart
        public void delete(final Product product){
            // Showing progress dialog before making http request
           final ProgressDialog pDialog = new ProgressDialog(CartActivity.this);

            pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.setContentView(R.layout.my_progress);

            StringRequest dr = new StringRequest(Request.Method.DELETE, Api.REMOVE_CART_URL+product.cartId,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {

                            pDialog.dismiss();
                            // response
                            Log.e("DELETE",""+response);

                            int i = productList.indexOf(product);
                            if (i == -1) {
                                throw new IndexOutOfBoundsException();
                            }
                            productList.remove(productList.get(i));
                            calculateMealTotal();
                            productsAdapter.notifyDataSetChanged();

                            /*Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();*/
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error.
                            pDialog.dismiss();

                        }
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(dr);
        }


    }

    // total Amount
    public void calculateMealTotal() {
        int mealTotal = 0;
        for (Product order : products.productList) {
            mealTotal += order.price * order.quantity;

        }

        Log.e("TOTAL", "" +products.size()+"Items | "+ mealTotal);

        txtSubTotal.setText(products.size()+" Items | Rs "+ mealTotal);

        if(products.size()==0){

            setContentView(R.layout.emptycart);
            Button btnGotohome =(Button)findViewById(R.id.btnGotohome);
            btnGotohome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent =new Intent(CartActivity.this,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);}
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    // Save Wishlist
    public void updateQuantity(final Product product){

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Api.QUANTITY_CART_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ",""+response);
                        // hideDialog();
                       /* Intent intent = new Intent(mContext, CartActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        mContext.startActivity(intent);
*/

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_ERROR: ",""+error);
                        //hideDialog();
                        // Toast.makeText(AddAddsActivity.this,"Email Already Exist",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();

                params.put("user_id", product.user_id);
                params.put("product_id", product.productId);
                params.put("category", product.module);
                params.put("quantity", String.valueOf(product.quantity));
                params.put("unit_price_incl_tax", String.valueOf(product.price));

                Log.e("RESPONSE_Parasms: ",""+params);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    // Save Wishlist
    private void saveWishList(final Product product){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.ADD_WISHLIST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ",""+response);
                        // hideDialog();
                       /* Intent intent = new Intent(mContext, CartActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        mContext.startActivity(intent);
*/

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_ERROR: ",""+error);
                        //hideDialog();
                        // Toast.makeText(AddAddsActivity.this,"Email Already Exist",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();

                params.put("module", product.module);
                params.put("user_id", product.user_id);
                params.put("item_id", product.item_id);

                Log.e("RESPONSE_Parasms: ",""+params);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

