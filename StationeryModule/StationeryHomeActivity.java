package com.prism.pickany247.StationeryModule;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
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
import com.prism.pickany247.Adapters.ExtraAdapter;
import com.prism.pickany247.Adapters.OfficeAdpter;
import com.prism.pickany247.Adapters.SchoolAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.HomeActivity;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.StationeryHomeResponse;
import com.prism.pickany247.Response.StationeryResponse;
import com.prism.pickany247.Singleton.AppController;

public class StationeryHomeActivity extends AppCompatActivity  {
    SwipeRefreshLayout swipeRefreshLayout;
    AppController appController;
    private ArtAdapter artAdapter;
    private DeskAdapter deskAdapter;
    private SchoolAdapter schoolAdapter;
    private OfficeAdpter officeAdpter;
    private ExtraAdapter extraAdapter;
    private RecyclerView rcArt,rcDesk,rcSchool,rcOffice,rcExtra;
    private TextView txtArt,txtDesk,txtSchool,txtOffice,txtExtra;
    private TextView txtArtName,txtDeskName,txtSchoolName,txtOfficeName,txtExtraName;
    Gson gson;
    StationeryResponse stationeryResponse =new StationeryResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stationery_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Stationery");

        appController=(AppController)getApplicationContext();


        // init SwipeRefreshLayout and ListView
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.simpleSwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorBlue,R.color.colorPrimary);

        rcArt = (RecyclerView) findViewById(R.id.recyclerArt);
        rcDesk = (RecyclerView) findViewById(R.id.recyclerdesk);
        rcSchool = (RecyclerView) findViewById(R.id.recyclerSchool);
        rcOffice = (RecyclerView) findViewById(R.id.recyclerOffice);
        rcExtra = (RecyclerView) findViewById(R.id.recyclerExtra);

        txtArtName=(TextView)findViewById(R.id.txtArtName);
        txtDeskName=(TextView)findViewById(R.id.txtDeskName);
        txtSchoolName=(TextView)findViewById(R.id.txtSchoolName);
        txtOfficeName=(TextView)findViewById(R.id.txtOfficeName);
        txtExtraName=(TextView)findViewById(R.id.txtExtraName);

        txtArt=(TextView)findViewById(R.id.txtArtCrafts);
        txtDesk=(TextView)findViewById(R.id.txtDesk);
        txtSchool=(TextView)findViewById(R.id.txtSchool);
        txtOffice=(TextView)findViewById(R.id.txtOffice);
        txtExtra=(TextView)findViewById(R.id.txtExtra);

       /* txtArt.setOnClickListener(this);
        txtDesk.setOnClickListener(this);
        txtSchool.setOnClickListener(this);
        txtOffice.setOnClickListener(this);
        txtExtra.setOnClickListener(this);*/


        if (appController.isConnection()) {

            prepareCategoeriesData();


            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    prepareCategoeriesData();
                    swipeRefreshLayout.setRefreshing(false);
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

        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_CATEGORIES_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                swipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
               StationeryHomeResponse stationeryhomeResponse = gson.fromJson(response, StationeryHomeResponse.class);

               for (StationeryHomeResponse.MainCategoriesBean mainCategoriesBean:stationeryhomeResponse.getMainCategories()){

                   if (mainCategoriesBean.getId().equals("1")){

                       prepareArtData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name());

                       txtArtName.setText(mainCategoriesBean.getCategory_name());

                   }
                   else if (mainCategoriesBean.getId().equals("2")){

                       prepareDeskData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name());
                       txtDeskName.setText(mainCategoriesBean.getCategory_name());
                   }
                   else if (mainCategoriesBean.getId().equals("4")){

                       prepareSchoolData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name());
                       txtSchoolName.setText(mainCategoriesBean.getCategory_name());
                   }
                   else if (mainCategoriesBean.getId().equals("3")){

                       prepareOfficeData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name());
                       txtOfficeName.setText(mainCategoriesBean.getCategory_name());
                   }
                   else if (mainCategoriesBean.getId().equals("5")){

                       prepareExtraData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name());
                       txtExtraName.setText(mainCategoriesBean.getCategory_name());
                   }
               }


                //dynamic carAdapter
                // carAdapter.registerDataSetObserver(indicator.getDataSetObserver());


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }


    private void prepareArtData(final String id, final String catname){

        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_URL+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                swipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                rcArt.setLayoutManager(mLayoutManager);
                rcArt.setItemAnimator(new DefaultItemAnimator());

                artAdapter = new ArtAdapter(getApplicationContext(), stationeryResponse.getFiltered_products());
                rcArt.setAdapter(artAdapter);
                artAdapter.notifyDataSetChanged();

                txtArt.setOnClickListener(new View.OnClickListener() {
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
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareDeskData(final String id, final String catname){

        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_URL+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                swipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                rcDesk.setLayoutManager(mLayoutManager);
                rcDesk.setItemAnimator(new DefaultItemAnimator());

                deskAdapter = new DeskAdapter(getApplicationContext(), stationeryResponse.getFiltered_products());
                rcDesk.setAdapter(deskAdapter);
                deskAdapter.notifyDataSetChanged();

                txtDesk.setOnClickListener(new View.OnClickListener() {
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
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareSchoolData(final String id, final String catname){

        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_URL+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                swipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                rcSchool.setLayoutManager(mLayoutManager);
                rcSchool.setItemAnimator(new DefaultItemAnimator());

                schoolAdapter = new SchoolAdapter(getApplicationContext(), stationeryResponse.getFiltered_products());
                rcSchool.setAdapter(schoolAdapter);
                schoolAdapter.notifyDataSetChanged();


                txtSchool.setOnClickListener(new View.OnClickListener() {
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
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareOfficeData(final String id, final String catname){

        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_URL+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                swipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                rcOffice.setLayoutManager(mLayoutManager);
                rcOffice.setItemAnimator(new DefaultItemAnimator());

                officeAdpter = new OfficeAdpter(getApplicationContext(), stationeryResponse.getFiltered_products());
                rcOffice.setAdapter(officeAdpter);
                officeAdpter.notifyDataSetChanged();

                txtOffice.setOnClickListener(new View.OnClickListener() {
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
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareExtraData(final String id, final String catname){

        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_URL+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                swipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                rcExtra.setLayoutManager(mLayoutManager);
                rcExtra.setItemAnimator(new DefaultItemAnimator());

                extraAdapter = new ExtraAdapter(getApplicationContext(), stationeryResponse.getFiltered_products());
                rcExtra.setAdapter(extraAdapter);
                extraAdapter.notifyDataSetChanged();


                txtExtra.setOnClickListener(new View.OnClickListener() {
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
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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
/*
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.txtArtCrafts:

                Intent intent1 = new Intent(getApplicationContext(), ProductListActivity.class);
                intent1.putExtra("catId","1");
                intent1.putExtra("title","Art & Crafts");
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;

            case R.id.txtDesk:

                Intent intent2 = new Intent(getApplicationContext(), ProductListActivity.class);
                intent2.putExtra("catId","2");
                intent2.putExtra("title","Desk Organizers");
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;
            case R.id.txtSchool:

                Intent intent3 = new Intent(getApplicationContext(), ProductListActivity.class);
                intent3.putExtra("catId","3");
                intent3.putExtra("title","School & Colleges ");
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;

            case R.id.txtOffice:

                Intent intent4 = new Intent(getApplicationContext(), ProductListActivity.class);
                intent4.putExtra("catId","4");
                intent4.putExtra("title","Office Stationery");
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent4);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;

            case R.id.txtExtra:

                Intent intent5 = new Intent(getApplicationContext(), ProductListActivity.class);
                intent5.putExtra("catId","5");
                intent5.putExtra("title","Extra Stationery");
                intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent5);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;
        }
    }*/
}
