package com.prism.pickany247.Modules.Grocery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.prism.pickany247.Adapters.MyCheckBoxAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.ProductListActivity;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.CatResponse;
import com.prism.pickany247.Response.CheckBoxItem;
import com.prism.pickany247.Response.GroceryFilterResponse;
import com.prism.pickany247.Singleton.AppController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroceryFilterActivity extends AppCompatActivity implements View.OnClickListener {
    List<CheckBoxItem> catItems = new ArrayList<>();
    List<CheckBoxItem> subCatItems = new ArrayList<>();
    List<CheckBoxItem> productTypeItems = new ArrayList<>();
    List<CheckBoxItem> colorItems = new ArrayList<>();
    List<CheckBoxItem> brandItems = new ArrayList<>();
    List<CheckBoxItem> ratingItems = new ArrayList<>();
    MyCheckBoxAdapter myMyCheckBoxAdapter;
    CatResponse homeResponse = new CatResponse();
    GroceryFilterResponse filterResponse = new GroceryFilterResponse();
    Gson gson;
    String strCat = "";

    String catId, title, module;
    @BindView(R.id.rbCat)
    RadioButton rbCat;
    @BindView(R.id.rbSubCat)
    RadioButton rbSubCat;
    @BindView(R.id.rbBrand)
    RadioButton rbBrand;
    @BindView(R.id.rbProductType)
    RadioButton rbProductType;
    @BindView(R.id.rbColor)
    RadioButton rbColor;
    @BindView(R.id.rbRatings)
    RadioButton rbRatings;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.catList)
    ListView catList;
    @BindView(R.id.catLayout)
    LinearLayout catLayout;
    @BindView(R.id.subcatList)
    ListView subcatList;
    @BindView(R.id.subcatLayout)
    LinearLayout subcatLayout;
    @BindView(R.id.brandList)
    ListView brandList;
    @BindView(R.id.brandLayout)
    LinearLayout brandLayout;
    @BindView(R.id.productTypecatList)
    ListView productTypecatList;
    @BindView(R.id.productTypeLayout)
    LinearLayout productTypeLayout;
    @BindView(R.id.colorList)
    ListView colorList;
    @BindView(R.id.colorLayout)
    LinearLayout colorLayout;
    @BindView(R.id.ratingsList)
    ListView ratingsList;
    @BindView(R.id.ratingsLayout)
    LinearLayout ratingsLayout;
    @BindView(R.id.btnApply)
    Button btnApply;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filter");
        ButterKnife.bind(this);

        catId = getIntent().getStringExtra("catId");
        title = getIntent().getStringExtra("title");
        module = getIntent().getStringExtra("module");

        prepareCatData();

    }

    private void prepareCatData() {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_HOME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("CAT_RESPONSE", "" + response);
                progressBar.setVisibility(View.GONE);
                gson = new Gson();
                homeResponse = gson.fromJson(response, CatResponse.class);

                catItems.clear();
                boolean value;

                for (CatResponse.CategoriesBean mainCategoriesBean : homeResponse.getCategories()) {

                    if (mainCategoriesBean.getId().equals(catId)) {

                        value = true;
                    } else {
                        value = false;
                    }

                    catItems.add(new CheckBoxItem(value, mainCategoriesBean.getId(), mainCategoriesBean.getCategory_name()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(GroceryFilterActivity.this, catItems);
                // Assign adapter to ListView
                catList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


                rbSubCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        catLayout.setVisibility(View.GONE);
                        subcatLayout.setVisibility(View.VISIBLE);
                        brandLayout.setVisibility(View.GONE);
                        productTypeLayout.setVisibility(View.GONE);
                        colorLayout.setVisibility(View.GONE);
                        ratingsLayout.setVisibility(View.GONE);

                        String catStr = "";
                        for (int i = 0; i < catItems.size(); i++) {
                            if (catItems.get(i).isChecked()) {
                                catStr += catItems.get(i).getId() + ",";

                            }
                        }

                        if (catStr.length() > 0) {
                            strCat = catStr.substring(0, catStr.length() - 1);
                            Log.e("CHECKBOXES", "" + strCat);
                            //  Toast.makeText(StationeryFilterActivity.this, strone, Toast.LENGTH_LONG).show();
                            prepareSubCatData(strCat);
                            prepareBrandData(strCat);
                            prepareColorData(strCat);
                            prepareProductTypeData(strCat);
                            prepareRatingData(strCat);

                        } else {
                            System.out.println(catStr);
                        }
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

    private void prepareSubCatData(String catId) {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("SUB_CAT_RESPONSE", "" + response);
                progressBar.setVisibility(View.GONE);

                gson = new Gson();
                filterResponse = gson.fromJson(response, GroceryFilterResponse.class);

                subCatItems.clear();
                boolean value;

                for (GroceryFilterResponse.SubCategoryBean subCatListBean : filterResponse.getSub_Category()) {

                    subCatItems.add(new CheckBoxItem(false, subCatListBean.getSub_category_id(), subCatListBean.getSub_category_name()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(GroceryFilterActivity.this, subCatItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                subcatList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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
                }}
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

    private void prepareBrandData(String catId) {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                progressBar.setVisibility(View.GONE);

                gson = new Gson();
                filterResponse = gson.fromJson(response, GroceryFilterResponse.class);
                brandItems = new ArrayList<>();
                brandItems.clear();
                boolean value;

                for (GroceryFilterResponse.BrandsBean brandsBean : filterResponse.getBrands()) {

                    brandItems.add(new CheckBoxItem(false, brandsBean.getBrand_id(), brandsBean.getName()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(GroceryFilterActivity.this, brandItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                brandList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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
                }}
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

    private void prepareProductTypeData(String catId) {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                progressBar.setVisibility(View.GONE);

                gson = new Gson();
                filterResponse = gson.fromJson(response, GroceryFilterResponse.class);
                productTypeItems = new ArrayList<>();
                productTypeItems.clear();
                boolean value;

                for (GroceryFilterResponse.ProductTypesBean productTypesBean : filterResponse.getProduct_Types()) {

                    productTypeItems.add(new CheckBoxItem(false, "", productTypesBean.getProduct_type()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(GroceryFilterActivity.this, productTypeItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                productTypecatList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

    private void prepareColorData(String catId) {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                progressBar.setVisibility(View.GONE);
                gson = new Gson();
                filterResponse = gson.fromJson(response, GroceryFilterResponse.class);
                colorItems = new ArrayList<>();
                colorItems.clear();
                boolean value;

                for (GroceryFilterResponse.MeasurementsBean colorsBean : filterResponse.getMeasurements()) {

                    colorItems.add(new CheckBoxItem(false, colorsBean.getId(), colorsBean.getCapacity()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(GroceryFilterActivity.this, colorItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                colorList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

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
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

    private void prepareRatingData(String catId) {

        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                progressBar.setVisibility(View.GONE);
                gson = new Gson();
                filterResponse = gson.fromJson(response, GroceryFilterResponse.class);
                ratingItems = new ArrayList<>();
                catItems.clear();


                for (GroceryFilterResponse.RatingBean ratingBean : filterResponse.getRating()) {

                    ratingItems.add(new CheckBoxItem(false, "1", "1.0 & above  (" + ratingBean.getRating1() + " )"));
                    ratingItems.add(new CheckBoxItem(false, "2", "2.0 & above  (" + ratingBean.getRating2() + " )"));
                    ratingItems.add(new CheckBoxItem(false, "3", "3.0 & above  (" + ratingBean.getRating3() + " )"));
                    ratingItems.add(new CheckBoxItem(false, "4", "4.0 & above  (" + ratingBean.getRating4() + " )"));
                    ratingItems.add(new CheckBoxItem(false, "5", "5.0 & above  (" + ratingBean.getRating5() + " )"));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(GroceryFilterActivity.this, ratingItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                ratingsList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

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
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


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


    @OnClick({R.id.rbCat, R.id.rbSubCat, R.id.rbBrand, R.id.rbProductType, R.id.rbColor, R.id.rbRatings, R.id.btnApply})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.rbCat:

                prepareCatData();

                catLayout.setVisibility(View.VISIBLE);
                subcatLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                productTypeLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;
           /* case R.id.rbSubCat:

                priceLayout.setVisibility(View.GONE);
                catLayout.setVisibility(View.GONE);
                subcatLayout.setVisibility(View.VISIBLE);
                brandLayout.setVisibility(View.GONE);
                productTypeLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;*/
            case R.id.rbBrand:

                // prepareBrandData();

                catLayout.setVisibility(View.GONE);
                subcatLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.VISIBLE);
                productTypeLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;
            case R.id.rbProductType:

                //  prepareProductTypeData();

                catLayout.setVisibility(View.GONE);
                subcatLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                productTypeLayout.setVisibility(View.VISIBLE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;
            case R.id.rbColor:

                //  prepareColorData();

                catLayout.setVisibility(View.GONE);
                subcatLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                productTypeLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.VISIBLE);
                ratingsLayout.setVisibility(View.GONE);


                break;
            case R.id.rbRatings:

                //  prepareRatingData();

                catLayout.setVisibility(View.GONE);
                subcatLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                productTypeLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.VISIBLE);


                break;
            case R.id.btnApply:

                Log.e("CHECKBOXESONE", "" + strCat);


                // subcat data
                String subCat = "";
                String strSubCat = "";
                for (CheckBoxItem checkBox : subCatItems) {

                    if (checkBox.checked) {
                        subCat += checkBox.getId() + ",";
                    }
                }
                if (subCat.length() > 0) {
                    strSubCat = subCat.substring(0, subCat.length() - 1);
                    Log.e("strSubCat", "" + strSubCat);
                }

                // brand data
                String brand = "";
                String strBrand = "";
                for (CheckBoxItem checkBox : brandItems) {

                    if (checkBox.checked) {
                        brand += checkBox.getId() + ",";
                    }
                }
                if (brand.length() > 0) {
                    strBrand = brand.substring(0, brand.length() - 1);
                    Log.e("strBrand", "" + strBrand);
                }

                // product data
                String productType = "";
                String strProductType = "";
                for (CheckBoxItem checkBox : productTypeItems) {

                    if (checkBox.checked) {
                        productType += checkBox.getId() + ",";
                    }
                }
                if (productType.length() > 0) {
                    strProductType = productType.substring(0, productType.length() - 1);
                    Log.e("strProductType", "" + strProductType);
                }

                // capacity data
                String capacity = "";
                String strcapacity = "";
                for (CheckBoxItem checkBox : colorItems) {

                    if (checkBox.checked) {
                        capacity += checkBox.getItemString() + ",";
                    }
                }
                if (capacity.length() > 0) {
                    strcapacity = capacity.substring(0, capacity.length() - 1);
                    Log.e("strColor", "" + strcapacity);
                }


                // rating data
                String rating = "";
                String strRating = "";
                for (CheckBoxItem checkBox : ratingItems) {

                    if (checkBox.checked) {
                        rating += checkBox.getId() + ",";
                    }
                }
                if (rating.length() > 0) {
                    strRating = rating.substring(0, rating.length() - 1);
                    Log.e("strRating", "" + strRating);
                }

                String finalvalue = "";
                if (strCat.equals("")) {
                    finalvalue = catId + "&subcatId=" + strSubCat + "&brand=" + strBrand + "&productType=" + strProductType + "&capacity=" + strcapacity + "&rating=" + strRating;

                } else {
                    finalvalue = strCat + "&subcatId=" + strSubCat + "&brand=" + strBrand + "&productType=" + strProductType + "&capacity=" + strcapacity + "&rating=" + strRating;
                }

                Log.e("RESULTSTRING", "" + finalvalue);

                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                intent.putExtra("catId", finalvalue);
                intent.putExtra("title", title);
                intent.putExtra("module", module);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}
