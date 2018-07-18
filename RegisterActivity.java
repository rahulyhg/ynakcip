package com.prism.pickany247;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Singleton.AppController;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity implements View.OnClickListener {
    AppController appController;
    ProgressDialog pDialog;
    TextView txtSignin;
    CheckBox checkBox;
    Button btnRegister;
    EditText etMobile,etUsername,etEmail,etPassword;
    String agree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        appController= (AppController) getApplication();

        txtSignin=(TextView)findViewById(R.id.txtSignin);
        String next = "Already Registered ? <font color='#1db674'>Login</font> me";
        txtSignin.setText(Html.fromHtml(next));

        txtSignin.setOnClickListener(this);

        checkBox=(CheckBox)findViewById(R.id.checkbox);
        String checktext = "I agree to the Pickany24x7 ? <font color='#1db674'>Terms & conditions</font> and <font color='#1db674'>Privacy Policy</font>";
        checkBox.setText(Html.fromHtml(checktext));

        agree="0";
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    agree = "1";
                }else {
                    agree= "0";
                }
            }
        });

        etUsername=(EditText)findViewById(R.id.etUsername);
        etMobile=(EditText)findViewById(R.id.etPhone);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etPassword=(EditText)findViewById(R.id.etPassword);

        btnRegister=(Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnRegister:
                if (appController.isConnection()){

                    // Showing progress dialog before making http request
                    pDialog = new ProgressDialog(this);
                    pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    pDialog.setIndeterminate(true);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    pDialog.setContentView(R.layout.my_progress);


                      validateForm();

                  //  Toast.makeText(getApplicationContext(),"good connection",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"no Internet connection",Toast.LENGTH_SHORT).show();

                }

                break;



            case R.id.txtSignin:
                Intent intent1 =new Intent(RegisterActivity.this,LoginActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;


        }
    }


    //validate inputs...
    private void validateForm(){

        String name =etUsername.getText().toString();
        String email =etEmail.getText().toString();
        String password =etPassword.getText().toString();
        String phoneno =etMobile.getText().toString().trim();



        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phoneno.isEmpty()){

            if (!isValidPhoneNumber(phoneno)) {
                //Toast.makeText(getApplicationContext(),"Invalid Aadhar no",Toast.LENGTH_SHORT).show();
                etMobile.setError("Invalid mobile no");
            }

            else if(!isValidEmaillId(email)){
                Toast.makeText(getApplicationContext(), "InValid Email Address.", Toast.LENGTH_SHORT).show();
            }
            else if (agree.equalsIgnoreCase("0")){

                Toast.makeText(getApplicationContext(), "Please Accept Terms&Conditions.", Toast.LENGTH_SHORT).show();

            }
            else {
                addAddress(name,email,password,phoneno);
            }

        }else {

            Toast.makeText(getApplicationContext(),"Enter All fields !",Toast.LENGTH_SHORT).show();

        }


    }

    // submit details
    private void addAddress(final String name, final String email, final String password, final String mobile){

        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.REGISTRATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ",""+response);
                        // hideDialog();
                        pDialog.hide();

                        Intent intent =new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        // intent.putExtra("addressId",addressId);
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
                params.put("username",name);
                params.put("email",email);
                params.put("mobile",mobile);
                params.put("password",password);
                params.put("i_agreed",agree);

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

    private boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent =new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        // intent.putExtra("addressId",addressId);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }



}
