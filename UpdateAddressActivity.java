package com.prism.pickany247;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Helper.PrefManager;
import com.prism.pickany247.Singleton.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateAddressActivity extends AppCompatActivity implements View.OnClickListener {
    AppController appController;
    private PrefManager pref;
    Button applyButton;
    ProgressDialog pDialog;
    EditText etName,etState,etPincode,etPhone,etAddress;
    CheckBox checkBox;
    String userId,checkId,addressid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Address");

        pref = new PrefManager(getApplicationContext());
        // Displaying user information from shared preferences
        HashMap<String, String> profile = pref.getUserDetails();
        userId=profile.get("id");


        addressid = getIntent().getStringExtra("addressid");
        String city = getIntent().getStringExtra("city");
        String customername = getIntent().getStringExtra("customername");
        String state = getIntent().getStringExtra("state");
        String mobile = getIntent().getStringExtra("mobile");
        String address = getIntent().getStringExtra("address");
        String zipcode = getIntent().getStringExtra("zipcode");


        appController = (AppController) getApplication();

        etName=(EditText)findViewById(R.id.etName);
        etName.setText(customername);

        etState=(EditText)findViewById(R.id.etState);
        etState.setText(state);

        etPincode=(EditText)findViewById(R.id.etPincode);
        etPincode.setText(zipcode);

        etPhone=(EditText)findViewById(R.id.etPhoneNo);
        etPhone.setText(mobile);

        etAddress=(EditText)findViewById(R.id.etAddress);
        etAddress.setText(address);

        checkBox=(CheckBox)findViewById(R.id.checkboxDefault);
        applyButton=(Button)findViewById(R.id.btnApply);
        applyButton.setOnClickListener(this);

        checkId="0";
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    checkId="1";
                }
                else {

                    checkId="0";
                }
            }
        });


        if (appController.isConnection()) {

            // Showing progress dialog before making http request
            // Showing progress dialog before making http request
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("submitting...");



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

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnApply:

                // check internet connection
                if (appController.isConnection()) {
                    validateForm();
                } else {
                    setContentView(R.layout.internet);
                    // app.internetDilogue(AddAddressActivity.this);
                }

                break;


        }
    }

    //validate inputs...
    private void validateForm(){

        String name =etName.getText().toString();
        String state =etState.getText().toString();
        String pincode =etPincode.getText().toString().trim();
        String phoneno =etPhone.getText().toString().trim();
        String address =etAddress.getText().toString();


        if (!name.isEmpty() && !state.isEmpty()  && !pincode.isEmpty() && !phoneno.isEmpty() && !address.isEmpty()){

            if (!isValidPhoneNumber(phoneno)) {
                //Toast.makeText(getApplicationContext(),"Invalid Aadhar no",Toast.LENGTH_SHORT).show();
                etPhone.setError("Invalid mobile no");
            }

            else if (!isPostalCodeValid(pincode)){
                etPincode.setError("Invalid Pincode");
            }
            else {
                addAddress(name,state,pincode,phoneno,address);
            }

        }else {

            Toast.makeText(getApplicationContext(),"Enter All fields !",Toast.LENGTH_SHORT).show();

        }
    }

    // submit details
    private void addAddress(final String name, final String state, final String pincode, final String mobile, final String address){

        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Api.ADD_ADDRESS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ", "" + response);
                        // hideDialog();

                        pDialog.hide();

                        Intent intent = new Intent(UpdateAddressActivity.this, AddressListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        //Toast.makeText(OtpVerificationActivity.this,"Registration Successfully",Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_ERROR: ", "" + error);
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
                }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("id",addressid);
                params.put("user_id",userId);
                params.put("name",name);
                params.put("city","vizag");
                params.put("state",state);
                params.put("zip",pincode);
                params.put("phone",mobile);
                params.put("address",address );
                params.put("set_as_default",checkId );


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

    /**
     * Regex to validate the mobile number
     * mobile number should be of 10 digits length
     *
     * @param mobile
     * @return
     */
    private static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }

    private static boolean isPostalCodeValid (String postalcode) {
        return postalcode.matches("^\\w\\d\\w\\d\\w\\d|\\w\\d\\w\\s\\d\\w\\d$");
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

