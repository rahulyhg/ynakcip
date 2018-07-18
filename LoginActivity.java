package com.prism.pickany247;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.gson.Gson;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Helper.PrefManager;
import com.prism.pickany247.Response.LoginItem;
import com.prism.pickany247.Singleton.AppController;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity implements View.OnClickListener {
    AppController appController;
    PrefManager session;
    LoginItem loginItem =new LoginItem();
    Gson gson;
    private ProgressDialog pDialog;
    TextView txtForgotPass,txtSignUp;
    Button btnLogin;
    EditText etMobile,etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appController= (AppController) getApplication();

        session=new PrefManager(this);
        if(session.isLoggedIn()){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
        }

        txtSignUp=(TextView)findViewById(R.id.txtSignup);
        String next = "Not a member ? <font color='#1db674'>SignUp</font> now";
        txtSignUp.setText(Html.fromHtml(next));
        txtSignUp.setOnClickListener(this);

        txtForgotPass=(TextView)findViewById(R.id.txtForgotPassword);
        txtForgotPass.setOnClickListener(this);

        etMobile=(EditText)findViewById(R.id.etPhone);
        etPassword=(EditText)findViewById(R.id.etPassword);

        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnLogin:


                if (appController.isConnection()){

                    // Showing progress dialog before making http request
                    pDialog = new ProgressDialog(this);

                    pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    pDialog.setIndeterminate(true);
                    pDialog.setCancelable(false);
                    pDialog.show();
                    pDialog.setContentView(R.layout.my_progress);

                    String mobileno=etMobile.getText().toString().trim();
                    String password =etPassword.getText().toString().trim();

                    if (!mobileno.isEmpty() && !password.isEmpty()) {

                        prepareLoginData(mobileno, password);
                    }

                   // Toast.makeText(getApplicationContext(),"good connection",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"no Internet connection",Toast.LENGTH_SHORT).show();

                }

                break;

            case R.id.txtSignup:
                Intent intent1 =new Intent(LoginActivity.this,RegisterActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.txtForgotPassword:
               /* Intent intent2 =new Intent(LoginActivity.this,RegisterActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                // startActivity(intent2);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);*/
                break;
        }
    }

    // submit details
    private void prepareLoginData(final String mobile, final String password){

        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ",""+response);
                        // hideDialog();

                        pDialog.hide();

                        gson = new Gson();
                        loginItem = gson.fromJson(response, LoginItem.class);

                        String id ="";
                        String reg_user_name ="";
                        String reg_mobile_number ="";
                        String reg_email ="";

                        for (LoginItem.UserDetailsBean userLoginBean:loginItem.getUserDetails()){

                            id=userLoginBean.getId();
                            reg_user_name=userLoginBean.getUsername();
                            reg_mobile_number=userLoginBean.getMobile();
                            reg_email=userLoginBean.getEmail();

                        }

                        if (loginItem.getMessage().equalsIgnoreCase("Login Successfull")){

                            Intent intent =new Intent(LoginActivity.this,HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                            session.createLogin(id,reg_user_name,reg_email,reg_mobile_number);

                            Toast.makeText(LoginActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();


                        }
                        else {

                            Toast.makeText(LoginActivity.this,"Invalid mobile/password",Toast.LENGTH_SHORT).show();
                        }


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
                params.put("mobile",mobile);
                params.put("password",password);
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




}
