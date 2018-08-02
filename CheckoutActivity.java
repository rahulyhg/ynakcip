package com.prism.pickany247;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Helper.PrefManager;
import com.prism.pickany247.Response.CartResponse;
import com.prism.pickany247.Singleton.AppController;

import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    AppController appController;
    private ProgressDialog pDialog;
    private PrefManager pref;
    String userId,addressid,itemsCount;
    CartResponse cartResponse = new CartResponse();
    Gson gson;
    public TextView txtTotal,txtItems,txtpriceInclTax,txtGST,txtSubTotal,txtDeliveryCharges,txtgrandTotal,txtName,txtAddress,txtChangeAddress;
    public Button btnOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Checkout");

        appController = (AppController) getApplication();

        pref = new PrefManager(getApplicationContext());
        // Displaying user information from shared preferences
        HashMap<String, String> profile = pref.getUserDetails();
        userId=profile.get("id");

        addressid=getIntent().getStringExtra("addressId");
        itemsCount=getIntent().getStringExtra("itemslength");

      //  txtTotal=(TextView)findViewById(R.id.txtTotal);
        txtItems=(TextView)findViewById(R.id.txtItems);
        txtpriceInclTax=(TextView)findViewById(R.id.txtIncludeTax);
        txtGST=(TextView)findViewById(R.id.txtGst);
        txtSubTotal=(TextView)findViewById(R.id.txtSubTotal);
        txtDeliveryCharges=(TextView)findViewById(R.id.txtDeliver);
        txtName=(TextView)findViewById(R.id.txtUserName) ;
        txtAddress=(TextView)findViewById(R.id.txtAddress);
        txtChangeAddress=(TextView) findViewById(R.id.txtChangeAddress);
        txtgrandTotal=(TextView)findViewById(R.id.txtGrandTotal);

        btnOrder=(Button)findViewById(R.id.btnOrder);



        if (appController.isConnection()) {

            // Showing progress dialog before making http request
            pDialog = new ProgressDialog(this);

            pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.setContentView(R.layout.my_progress);

            checkoutData();


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

    private void checkoutData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.CART_URL + userId+"&address_id="+addressid, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response", "" + response);

                hidePDialog();
                gson = new Gson();
                cartResponse = gson.fromJson(response, CartResponse.class);

                for (CartResponse.TotalBillBean cartItemsBean : cartResponse.getTotal_bill()) {

                   //  txtTotal.setText(" \u20B9"+cartItemsBean.getGrandTotal());
                     txtItems.setText(""+itemsCount);
                     txtpriceInclTax.setText("\u20B9"+cartItemsBean.getPriceInclTax());
                     txtGST.setText("\u20B9"+cartItemsBean.getGST());
                     txtSubTotal.setText("\u20B9"+cartItemsBean.getSubTotal());
                     txtDeliveryCharges.setText("\u20B9"+cartItemsBean.getDeliveryCharges());
                     txtgrandTotal.setText("\u20B9"+cartItemsBean.getGrandTotal());

                    txtChangeAddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), AddressListActivity.class);
                            intent.putExtra("itemslength",itemsCount);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    });

                }


                for (final CartResponse.UserAddressBean addressBean:cartResponse.getUser_address()){

                    txtName.setText(addressBean.getName());
                    txtAddress.setText("Mobile : "+addressBean.getPhone()+"\n"+addressBean.getAddress()+","+addressBean.getCity()+","+addressBean.getState()+","+addressBean.getZip());

                    btnOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            postOrder(addressBean.getId());
                        }
                    });



                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePDialog();

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Oops. Server error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Oops. AuthFailure error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Oops. Parse error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Oops. Connection error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                }

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }


    private void postOrder( final String addressid){
// Showing progress dialog before making http request
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("your order is Placing..");
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.CHECKOUT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ",""+response);
                        // hideDialog();

                        pDialog.hide();

                        Intent intent =new Intent(getApplicationContext(), InvoiceActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        //Toast.makeText(OtpVerificationActivity.this,"Registration Successfully",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_ERROR: ",""+error);
                        pDialog.hide();
                        if (error instanceof NetworkError) {
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "Oops. Server error!", Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "Oops. AuthFailure error!", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Oops. Parse error!", Toast.LENGTH_LONG).show();
                        } else if (error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "Oops. Connection error!", Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError) {
                            Toast.makeText(getApplicationContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id",userId);
                params.put("address_id",addressid);

                Log.e("RESPONSE_Parasms: ",""+params);
                return params;
            }

        };

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
