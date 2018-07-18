package com.prism.pickany247;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.gson.Gson;
import com.prism.pickany247.Adapters.MyCustomAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Response.CheckBoxItem;
import com.prism.pickany247.Response.StationeryCatResponse;
import com.prism.pickany247.Singleton.AppController;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity extends AppCompatActivity {
    ArrayList<CheckBoxItem> checkBoxItemList = new ArrayList<CheckBoxItem>();
    MyCustomAdapter dataAdapter = null;
    StationeryCatResponse homeResponse = new StationeryCatResponse();
    Gson gson;
    @BindView(R.id.rbPrice)
    RadioButton rbPrice;
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
    @BindView(R.id.rangeSeekbar3)
    CrystalRangeSeekbar rangeSeekbar3;
    @BindView(R.id.textMin1)
    TextView textMin1;
    @BindView(R.id.textMax1)
    TextView textMax1;
    @BindView(R.id.priceLayout)
    LinearLayout priceLayout;
    @BindView(R.id.catList)
    ListView catList;
    @BindView(R.id.catLayout)
    LinearLayout catLayout;
    @BindView(R.id.btnApply)
    Button btnApply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);

        // price range
        priceRange();

        rbPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceRange();
                priceLayout.setVisibility(View.VISIBLE);
                catLayout.setVisibility(View.GONE);


            }
        });



        rbCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareCatData();
                catLayout.setVisibility(View.VISIBLE);
                priceLayout.setVisibility(View.GONE);


            }
        });




    }

    private void priceRange() {

        // set listener
        rangeSeekbar3.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                textMin1.setText(String.valueOf(minValue));
                textMax1.setText(String.valueOf(maxValue));
            }
        });

        // set final value listener
        rangeSeekbar3.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });

    }


    private void prepareCatData() {

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_HOME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                homeResponse = gson.fromJson(response, StationeryCatResponse.class);
                checkBoxItemList.clear();
                boolean value;

                for (StationeryCatResponse.CategoriesBean mainCategoriesBean : homeResponse.getCategories()) {

                    if (mainCategoriesBean.getCategory_name().equals("Arts & Crafts")){

                         value = true;
                    }
                    else {
                        value =false;
                    }

                    checkBoxItemList.add(new CheckBoxItem(mainCategoriesBean.getId(), mainCategoriesBean.getCategory_name(), value));

                }

                //create an ArrayAdaptar from the String Array
                dataAdapter = new MyCustomAdapter(FilterActivity.this, R.layout.row, checkBoxItemList);
               // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                catList.setAdapter(dataAdapter);
                dataAdapter.notifyDataSetChanged();

                // sub cat items
                rbSubCat.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        priceLayout.setVisibility(View.GONE);
                        catLayout.setVisibility(View.GONE);

                        StringBuffer responseText = new StringBuffer();
                        // responseText.append("The following were selected...\n");

                        checkBoxItemList = dataAdapter.checkBoxItemList;
                        for (int i = 0; i < checkBoxItemList.size(); i++) {
                            CheckBoxItem checkBoxItem = checkBoxItemList.get(i);
                            if (checkBoxItem.isSelected()) {
                                responseText.append("" + checkBoxItem.getId()+",");

                            }
                        }

                        String catId = responseText.deleteCharAt(responseText.length()-1).toString();

                        Toast.makeText(getApplicationContext(),catId, Toast.LENGTH_LONG).show();

                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // simpleSwipeRefreshLayout.setRefreshing(true);
            }
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

}
