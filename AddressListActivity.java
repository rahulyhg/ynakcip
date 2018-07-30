package com.prism.pickany247;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.prism.pickany247.Adapters.AddressAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Helper.PrefManager;
import com.prism.pickany247.Response.AddressItem;
import com.prism.pickany247.Singleton.AppController;

import java.util.HashMap;

public class AddressListActivity extends AppCompatActivity {
    private PrefManager pref;
    private ProgressDialog pDialog;
    private AddressAdapter cartAdapter;
    private ListView recyclerViewCart;
    AddressItem cartItem = new AddressItem();
    Gson gson;
    Button btnDelivery,btnAddAddress;
    String userId,itemslength;
    int checked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Address");



        AppController app = (AppController) getApplication();
        pref = new PrefManager(getApplicationContext());
        // Displaying user information from shared preferences
        HashMap<String, String> profile = pref.getUserDetails();
        userId=profile.get("id");
        itemslength=getIntent().getStringExtra("itemslength");


        recyclerViewCart = (ListView) findViewById(R.id.addressList);
        btnDelivery=(Button)findViewById(R.id.btnDelivery);
        btnAddAddress=(Button)findViewById(R.id.btnAddAddress);
        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(AddressListActivity.this, AddAddressActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        if (app.isConnection()) {

            // Showing progress dialog before making http request
            pDialog = new ProgressDialog(this);
            pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.setContentView(R.layout.my_progress);

            prepareAddressData();


        } else {

            setContentView(R.layout.internet);

          //  app.internetDilogue(AddressListActivity.this);

        }
    }

    private void prepareAddressData(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.ADDRESS_LIST_URL + userId, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response",""+response+""+Api.ADDRESS_LIST_URL+userId);
                hidePDialog();
                gson = new Gson();
                cartItem = gson.fromJson(response, AddressItem.class);

                cartAdapter = new AddressAdapter(AddressListActivity.this, cartItem.getModules());
                recyclerViewCart.setAdapter(cartAdapter);
                // cartAdapter.notifyDataSetChanged();

                cartAdapter.setSelectedIndex(checked);
                recyclerViewCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                        cartAdapter.setSelectedIndex(i);
                        cartAdapter.notifyDataSetChanged();

                        final AddressItem.ModulesBean addressItem=(AddressItem.ModulesBean)cartAdapter.getItem(i);
                        //  Toast.makeText(AddressListActivity.this,"cl"+addressItem.getCustmerId().toString(),Toast.LENGTH_SHORT).show();

                        btnDelivery.setVisibility(View.VISIBLE);
                        btnDelivery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent intent =new Intent(AddressListActivity.this, CheckoutActivity.class);
                                intent.putExtra("addressId",addressItem.getId());
                                intent.putExtra("itemslength",itemslength);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                // save the radio button value
                                SharedPreferences preferences=getSharedPreferences("PICKANY",0);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("checked", i);
                                editor.apply();

                                // Toast.makeText(AddressListActivity.this,""+i,Toast.LENGTH_SHORT).show();

                                // Toast.makeText(AddressListActivity.this,"cl"+addressItem.getCustmerId(),Toast.LENGTH_SHORT).show();

                            }
                        });



                    }
                });


                //dynamic carAdapter
                // carAdapter.registerDataSetObserver(indicator.getDataSetObserver());


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
        RequestQueue requestQueue = Volley.newRequestQueue(AddressListActivity.this);
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
                /*Intent intent =new Intent(CartActivity.this, SmsActivity.class);
                intent.putExtra("link",carBikeItem.getLink());
                intent.putExtra("id",carBikeItem.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);*/
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
