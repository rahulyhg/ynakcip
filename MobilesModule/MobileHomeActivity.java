package com.prism.pickany247.MobilesModule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.prism.pickany247.Adapters.ArtAdapter;
import com.prism.pickany247.Adapters.DeskAdapter;
import com.prism.pickany247.Adapters.OfficeAdpter;
import com.prism.pickany247.Adapters.SchoolAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.HomeActivity;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.StationeryHomeResponse;
import com.prism.pickany247.Response.StationeryResponse;
import com.prism.pickany247.Singleton.AppController;
import com.prism.pickany247.StationeryModule.ProductListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MobileHomeActivity extends AppCompatActivity {
    AppController appController;
    Gson gson;
    StationeryResponse stationeryResponse =new StationeryResponse();

    @BindView(R.id.txtNewMobile)
    TextView txtNewMobile;
    @BindView(R.id.txtNewView)
    TextView txtNewView;
    @BindView(R.id.recyclerNew)
    RecyclerView recyclerNew;
    @BindView(R.id.txtUsedMobile)
    TextView txtUsedMobile;
    @BindView(R.id.txtUsedView)
    TextView txtUsedView;
    @BindView(R.id.recyclerUsed)
    RecyclerView recyclerUsed;
    @BindView(R.id.txtTablet)
    TextView txtTablet;
    @BindView(R.id.txtTabletView)
    TextView txtTabletView;
    @BindView(R.id.recyclerTablet)
    RecyclerView recyclerTablet;
    @BindView(R.id.txtAccessories)
    TextView txtAccessories;
    @BindView(R.id.txtAccessoriesView)
    TextView txtAccessoriesView;
    @BindView(R.id.recyclerAccessories)
    RecyclerView recyclerAccessories;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;

    ArtAdapter artAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbile_home);
        ButterKnife.bind(this);

        simpleSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorBlue,R.color.colorPrimary);
        appController=(AppController)getApplicationContext();
        if (appController.isConnection()) {

            prepareCategoeriesData();

            simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    prepareCategoeriesData();
                    simpleSwipeRefreshLayout.setRefreshing(false);
                }
            });


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

    private void prepareCategoeriesData(){

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_CATEGORIES_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                StationeryHomeResponse stationeryhomeResponse = gson.fromJson(response, StationeryHomeResponse.class);

                for (StationeryHomeResponse.MainCategoriesBean mainCategoriesBean:stationeryhomeResponse.getMainCategories()){

                    if (mainCategoriesBean.getId().equals("1")){

                        prepareArtData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name());

                        txtNewMobile.setText(mainCategoriesBean.getCategory_name());

                    }
                    else if (mainCategoriesBean.getId().equals("2")){

                        prepareDeskData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name());
                        txtUsedMobile.setText(mainCategoriesBean.getCategory_name());
                    }
                    else if (mainCategoriesBean.getId().equals("4")){

                        prepareSchoolData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name());
                        txtTablet.setText(mainCategoriesBean.getCategory_name());
                    }
                    else if (mainCategoriesBean.getId().equals("3")){

                        prepareOfficeData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name());
                        txtAccessories.setText(mainCategoriesBean.getCategory_name());
                    }

                }


                //dynamic carAdapter
                // carAdapter.registerDataSetObserver(indicator.getDataSetObserver());


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleSwipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }


    private void prepareArtData(final String id, final String catname){

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_URL+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerNew.setLayoutManager(mLayoutManager);
                recyclerNew.setItemAnimator(new DefaultItemAnimator());

                artAdapter = new ArtAdapter(getApplicationContext(), stationeryResponse.getFiltered_products());
                recyclerNew.setAdapter(artAdapter);
                artAdapter.notifyDataSetChanged();

                txtNewView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent1 = new Intent(getApplicationContext(), ProductListActivity.class);
                        intent1.putExtra("catId",id);
                        intent1.putExtra("title",catname);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });


                //dynamic carAdapter
                // carAdapter.registerDataSetObserver(indicator.getDataSetObserver());


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleSwipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareDeskData(final String id, final String catname){

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_URL+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerUsed.setLayoutManager(mLayoutManager);
                recyclerUsed.setItemAnimator(new DefaultItemAnimator());

                artAdapter = new ArtAdapter(getApplicationContext(), stationeryResponse.getFiltered_products());
                recyclerUsed.setAdapter(artAdapter);
                artAdapter.notifyDataSetChanged();

                txtUsedView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent1 = new Intent(getApplicationContext(), ProductListActivity.class);
                        intent1.putExtra("catId",id);
                        intent1.putExtra("title",catname);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });
                //dynamic carAdapter
                // carAdapter.registerDataSetObserver(indicator.getDataSetObserver());


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleSwipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareSchoolData(final String id, final String catname){

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_URL+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerTablet.setLayoutManager(mLayoutManager);
                recyclerTablet.setItemAnimator(new DefaultItemAnimator());

                artAdapter = new ArtAdapter(getApplicationContext(), stationeryResponse.getFiltered_products());
                recyclerTablet.setAdapter(artAdapter);
                artAdapter.notifyDataSetChanged();


                txtTabletView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent1 = new Intent(getApplicationContext(), ProductListActivity.class);
                        intent1.putExtra("catId",id);
                        intent1.putExtra("title",catname);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });
                //dynamic carAdapter
                // carAdapter.registerDataSetObserver(indicator.getDataSetObserver());


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleSwipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareOfficeData(final String id, final String catname){

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_URL+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerAccessories.setLayoutManager(mLayoutManager);
                recyclerAccessories.setItemAnimator(new DefaultItemAnimator());

                artAdapter = new ArtAdapter(getApplicationContext(), stationeryResponse.getFiltered_products());
                recyclerAccessories.setAdapter(artAdapter);
                artAdapter.notifyDataSetChanged();

                txtAccessoriesView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent1 = new Intent(getApplicationContext(), ProductListActivity.class);
                        intent1.putExtra("catId",id);
                        intent1.putExtra("title",catname);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });
                //dynamic carAdapter
                // carAdapter.registerDataSetObserver(indicator.getDataSetObserver());


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleSwipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    @OnClick({R.id.txtNewView, R.id.txtUsedView, R.id.txtTabletView, R.id.txtAccessoriesView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtNewView:

                break;
            case R.id.txtUsedView:
                break;
            case R.id.txtTabletView:
                break;
            case R.id.txtAccessoriesView:
                break;
        }
    }
}
