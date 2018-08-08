package com.prism.pickany247.Singleton;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Helper.PrefManager;
import com.prism.pickany247.Response.CartResponse;

import java.util.HashMap;

public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static AppController mInstance;

    private PrefManager pref;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
       /* HashMap<String, String> profile = pref.getUserDetails();
        String userId=profile.get("id");*/

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    // check internet connection
    public boolean isConnection(){

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo !=null && networkInfo.isConnected();
    }

    public void cartCount(String userId){
        // Displaying user information from shared preferences

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.CART_URL + userId, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Response", "" + response);

                Gson gson = new Gson();
                CartResponse cartResponse = gson.fromJson(response, CartResponse.class);



                if (cartResponse.getCart_items().size()!=0) {

                    int cartindex =cartResponse.getCart_items().size();
                    Log.e("CARTCOUNT",""+cartindex);

                    SharedPreferences preferences = getSharedPreferences("CARTCOUNT", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("itemCount", cartindex);
                    editor.apply();
                }
                else{

                    SharedPreferences preferences =getSharedPreferences("CARTCOUNT",0);
                    SharedPreferences.Editor editor =preferences.edit();
                    editor.putInt("itemCount",0);
                    editor.apply();
                }

                }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //hidePDialog();
                SharedPreferences preferences =getSharedPreferences("CARTCOUNT",0);
                SharedPreferences.Editor editor =preferences.edit();
                editor.putInt("itemCount",0);
                editor.apply();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}
