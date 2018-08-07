package com.prism.pickany247;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.prism.pickany247.Adapters.ProductAadpter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Fragments.BottomSheet3DialogFragment;
import com.prism.pickany247.Modules.Grocery.GroceryFilterActivity;
import com.prism.pickany247.Modules.Mobiles.MobileFilterActivity;
import com.prism.pickany247.Modules.Stationery.StationeryFilterActivity;
import com.prism.pickany247.Response.ProductResponse;
import com.prism.pickany247.Singleton.AppController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    AppController appController;
    @BindView(R.id.filterLayout)
    CardView filterLayout;
    private ProductAadpter adapter;
    private RecyclerView rcProduct;
    Gson gson;
    ProductResponse productResponse = new ProductResponse();
    LinearLayout sortLinear, filterLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String id = getIntent().getStringExtra("catId");
        final String title = getIntent().getStringExtra("title");
        final String module = getIntent().getStringExtra("module");

        if (module.equalsIgnoreCase("celebrations")){

            filterLayout.setVisibility(View.GONE);
        }


        filterLinear = (LinearLayout) findViewById(R.id.linearFilter);
        sortLinear = (LinearLayout) findViewById(R.id.linearSort);
        sortLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheet3DialogFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

            }
        });

        filterLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (module.equalsIgnoreCase("grocery")) {

                    Intent intent = new Intent(getApplicationContext(), GroceryFilterActivity.class);
                    intent.putExtra("catId", id);
                    intent.putExtra("title", title);
                    intent.putExtra("module", module);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else if (module.equalsIgnoreCase("mobiles")) {

                    Intent intent = new Intent(getApplicationContext(), MobileFilterActivity.class);
                    intent.putExtra("catId", id);
                    intent.putExtra("title", title);
                    intent.putExtra("module", module);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else {

                    Intent intent = new Intent(getApplicationContext(), StationeryFilterActivity.class);
                    intent.putExtra("catId", id);
                    intent.putExtra("title", title);
                    intent.putExtra("module", module);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });


        getSupportActionBar().setTitle(title);
        appController = (AppController) getApplicationContext();


        // init SwipeRefreshLayout and ListView
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.simpleSwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorBlue, R.color.colorPrimary);

        rcProduct = (RecyclerView) findViewById(R.id.rcProduct);


        if (appController.isConnection()) {

            prepareProductData(id, title, module);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    prepareProductData(id, title, module);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });


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

    private void prepareProductData(final String id, String title, final String module) {

        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.PRODUCTS_URL + module + "&maincatId=" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("PRODUCTLISTURL", "" + Api.PRODUCTS_URL + module + "&maincatId=" + id);

                swipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                productResponse = gson.fromJson(response, ProductResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                rcProduct.setLayoutManager(mLayoutManager);
                rcProduct.setItemAnimator(new DefaultItemAnimator());

                adapter = new ProductAadpter(getApplicationContext(), productResponse.getFiltered_products());
                rcProduct.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                //dynamic carAdapter
                // carAdapter.registerDataSetObserver(indicator.getDataSetObserver());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_cart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.action_search:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
