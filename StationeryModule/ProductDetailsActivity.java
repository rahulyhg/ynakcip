package com.prism.pickany247.StationeryModule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
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
import com.prism.pickany247.Adapters.ViewPagerAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.CartActivity;
import com.prism.pickany247.Helper.PrefManager;
import com.prism.pickany247.HomeActivity;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.StationeryResponse;
import com.prism.pickany247.Response.ViewPagerItem;
import com.prism.pickany247.Singleton.AppController;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    AppController appController;
    Gson gson;
    StationeryResponse stationeryResponse =new StationeryResponse();
    List<ViewPagerItem> viewPagerItemslist=new ArrayList<>();
    TextView txtName,txtPrice;
    RatingBar ratingBar;
    Button btnAddToCart,btnBuyNow;
    private PrefManager pref;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String id = getIntent().getStringExtra("productId");
        Log.e("PRODUCTID",""+id);
        String title =getIntent().getStringExtra("productName");

        getSupportActionBar().setTitle(title);
        appController=(AppController)getApplicationContext();

        pref = new PrefManager(getApplicationContext());

        // Displaying user information from shared preferences
        HashMap<String, String> profile = pref.getUserDetails();
        userid =profile.get("id");

        if (appController.isConnection()) {

            // Showing progress dialog before making http request
            pDialog = new ProgressDialog(this);

            pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.setContentView(R.layout.my_progress);

            prepareProductDetailsData(id);


        } else {

            setContentView(R.layout.internet);

            Button tryButton =(Button)findViewById(R.id.btnTryagain);
            tryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            // app.internetDilogue(KitchenitemListActivity.this);

        }

    }

    private void prepareProductDetailsData(String id){

         pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.PRODUCT_DETAILS_URL+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pDialog.dismiss();

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                for (final StationeryResponse.FilteredProductsBean filteredProductsBean:stationeryResponse.getFiltered_products()){

                    String allImages =filteredProductsBean.getAllImages();

                    String[] namesList = allImages.split(",");

                    for(String name : namesList){

                        System.out.println(name);

                        viewPagerItemslist.add(new ViewPagerItem(filteredProductsBean.getImagePath()+name));
                    }


                    txtName=(TextView)findViewById(R.id.txtProductName);
                    txtPrice=(TextView)findViewById(R.id.txtPrice);
                    ratingBar=(RatingBar)findViewById(R.id.ratingBar);

                    txtName.setText(filteredProductsBean.getProduct_name());
                    txtPrice.setText(filteredProductsBean.getUnit_price());
                    ratingBar.setRating(Float.parseFloat(filteredProductsBean.getRating()));

                    btnAddToCart=(Button)findViewById(R.id.btnAddtocart);
                    btnBuyNow=(Button)findViewById(R.id.btnbuynow);

                    btnAddToCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String cart ="ADD TO CART";
                          addtocartData(userid,filteredProductsBean.getProduct_id(),"",filteredProductsBean.getModule(),"1",filteredProductsBean.getUnit_price(),
                          "","","","","","","","","",cart);
                        }
                    });

                    btnBuyNow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String cart ="BUY NOW";
                            addtocartData(userid,filteredProductsBean.getProduct_id(),"",filteredProductsBean.getModule(),"1",filteredProductsBean.getUnit_price(),
                                    "","","","","","","","","",cart);
                        }
                    });

                }

                final PageIndicatorView pageIndicatorView = findViewById(R.id.pageIndicatorView);
                ViewPagerAdapter adapter =new ViewPagerAdapter(getApplicationContext(),viewPagerItemslist);
                ViewPager viewPager =(ViewPager)findViewById(R.id.viewPager);
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        pageIndicatorView.setSelection(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });


                //dynamic carAdapter
                // carAdapter.registerDataSetObserver(indicator.getDataSetObserver());


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              pDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    // submit details
    private void addtocartData(final String userid, final String itemid, final String requestCode, final String cartType
    , final String quantity, final String unitPrice, final String txtAmount, final String discount, final String color, final String eggless,
                               final String egglessAmount, final String heartShape, final String heartShapeAmount,
                               final String flavour, final String message,final String btnValue){

        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.ADD_TO_CART_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ",""+response);
                        // hideDialog();

                        pDialog.hide();

                        if(btnValue.equals("ADD TO CART")){

                            if (btnAddToCart.getText().equals("GO TO CART")) {
                                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }else {
                                btnAddToCart.setText("GO TO CART");
                                Toast.makeText(getApplicationContext(),"Item Added to cart",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (btnValue.equalsIgnoreCase("BUY NOW"))
                        {
                            Intent intent =new Intent(getApplicationContext(),CartActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_ERROR: ",""+error);
                        pDialog.hide();
                        // Toast.makeText(AddAddsActivity.this,"Email Already Exist",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("item_id", itemid);
                params.put("request_code", requestCode);
                params.put("category", "stationery");
                params.put("cart_type", cartType);
                params.put("quantity", quantity);
                params.put("unit_price_incl_tax", unitPrice);
                params.put("tax_rate", "0");
                params.put("tax_amt", txtAmount);
                params.put("discount", discount);
                params.put("color", color);
                params.put("eggless", eggless);
                params.put("eggless_amt", egglessAmount);
                params.put("heart_shape", heartShape);
                params.put("heart_shape_amt", heartShapeAmount);
                params.put("flavour", flavour);
                params.put("message", message);
                Log.e("RESPONSE_Parasms: ",""+params);
                return params;
            }

        };

        // Adding request to request queue


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
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
