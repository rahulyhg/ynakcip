package com.prism.pickany247;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
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
import com.prism.pickany247.Adapters.SpinnerAdapter;
import com.prism.pickany247.Adapters.ViewPagerAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Helper.PrefManager;
import com.prism.pickany247.Model.ViewPagerItem;
import com.prism.pickany247.Response.ProductResponse;
import com.prism.pickany247.Singleton.AppController;
import com.rd.PageIndicatorView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailsActivity extends AppCompatActivity {
    @BindView(R.id.rbDescription)
    RadioButton rbDescription;
    @BindView(R.id.rbProductDetails)
    RadioButton rbProductDetails;
    @BindView(R.id.txtBullets)
    TextView txtBullets;
    @BindView(R.id.mobileLayout)
    LinearLayout mobileLayout;
    @BindView(R.id.spinnerPrice)
    Spinner spinnerPrice;
    @BindView(R.id.spinnerLayout)
    LinearLayout spinnerLayout;
    @BindView(R.id.spinCelebWeight)
    Spinner spinCelebWeight;
    @BindView(R.id.spinnerCelebWeightLayout)
    LinearLayout spinnerCelebWeightLayout;
    @BindView(R.id.eggless)
    CheckBox eggless;
    @BindView(R.id.heartShape)
    CheckBox heartShape;
    @BindView(R.id.spinCelebTime)
    Spinner spinCelebTime;
    @BindView(R.id.spinnerCelebTimeLayout)
    LinearLayout spinnerCelebTimeLayout;
    @BindView(R.id.spinCelebFlavour)
    Spinner spinCelebFlavour;
    @BindView(R.id.spinnerCelebFlavourLayout)
    LinearLayout spinnerCelebFlavourLayout;
    @BindView(R.id.celebrationLayout)
    LinearLayout celebrationLayout;
    @BindView(R.id.addtoCombo)
    Button addtoCombo;
    @BindView(R.id.txtPriceSymbol)
    TextView txtPriceSymbol;
    @BindView(R.id.messageonCake)
    EditText messageonCake;
    private ProgressDialog pDialog;
    AppController appController;
    Gson gson;
    ProductResponse productResponse = new ProductResponse();
    List<ViewPagerItem> viewPagerItemslist = new ArrayList<>();
    TextView txtName, txtPrice, txtDescription;
    RatingBar ratingBar;
    Button btnAddToCart, btnBuyNow;
    private PrefManager pref;
    String userid;
    public String selectedPrice, selectedItemId, flavourValue, timeslotValue, egglessPrice, heartshapePrice, egglessValue, heartshapeValue;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String id = getIntent().getStringExtra("productId");
        Log.e("PRODUCTID", "" + id);
        String title = getIntent().getStringExtra("productName");
        String module = getIntent().getStringExtra("module");

        getSupportActionBar().setTitle(title);
        appController = (AppController) getApplicationContext();

        pref = new PrefManager(getApplicationContext());

        // Displaying user information from shared preferences
        HashMap<String, String> profile = pref.getUserDetails();
        userid = profile.get("id");


        if (appController.isConnection()) {

            // Showing progress dialog before making http request
            pDialog = new ProgressDialog(this);

            pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.setContentView(R.layout.my_progress);

            prepareProductDetailsData(id, module);


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

    private void prepareProductDetailsData(final String id, final String module) {
        Log.e("PRODUCT_URL", "" + Api.PRODUCTS_URL + module + "&productId=" + id);
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.PRODUCTS_URL + module + "&productId=" + id, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                pDialog.dismiss();

                gson = new Gson();
                productResponse = gson.fromJson(response, ProductResponse.class);

                String allImages = "";
                String imagePath = "";
                for (final ProductResponse.FilteredProductsBean filteredProductsBean : productResponse.getFiltered_products()) {

                    allImages = filteredProductsBean.getAllImages();

                    imagePath = filteredProductsBean.getImagePath();
                    Log.e("IMAGEPATH", "" + imagePath + allImages);

                    txtName = (TextView) findViewById(R.id.txtProductName);
                    txtPrice = (TextView) findViewById(R.id.txtPrice);
                    ratingBar = (RatingBar) findViewById(R.id.ratingBar);
                    txtDescription = (TextView) findViewById(R.id.txtDescription);

                    txtName.setText(filteredProductsBean.getProduct_name());

                    if (filteredProductsBean.getRating().equalsIgnoreCase("")) {
                        ratingBar.setRating(Float.parseFloat("0"));
                    } else {
                        ratingBar.setRating(Float.parseFloat(filteredProductsBean.getRating()));
                    }

                    txtDescription.setText(filteredProductsBean.getMessage());


                    // grocery items capacity

                    if (module.equalsIgnoreCase("grocery")) {

                        spinnerLayout.setVisibility(View.VISIBLE);

                        final List<String> listCapacity = new ArrayList<>();
                        final List<String> listUnitPrice = new ArrayList<>();
                        final List<String> listItemId = new ArrayList<>();

                        // capacity
                        String[] itemsCapacity = filteredProductsBean.getCapacity().split(",");
                        for (String itemCap : itemsCapacity) {
                            System.out.println("itemCap = " + itemCap);
                            listCapacity.add(itemCap);
                        }

                        // unit price
                        String[] itemsUnitPrice = filteredProductsBean.getUnit_price_incl_tax().split(",");
                        for (String itemUnit : itemsUnitPrice) {
                            System.out.println("itemUnit = " + itemUnit);
                            listUnitPrice.add(itemUnit);
                        }

                        // itemsid
                        String[] itemsId = filteredProductsBean.getItem_id().split(",");
                        for (String itemsid : itemsId) {
                            System.out.println("itemsID = " + itemsid);
                            listItemId.add(itemsid);
                        }


                        Spinner spinner = (Spinner) findViewById(R.id.spinnerPrice);
                        SpinnerAdapter adapter = new SpinnerAdapter(ProductDetailsActivity.this, listCapacity, listUnitPrice, listItemId);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                selectedPrice = listUnitPrice.get(position);
                                selectedItemId = listItemId.get(position);

                                txtPrice.setText("\u20B9" + selectedPrice);

                                // Toast.makeText(getApplicationContext(),""+price+"---"+itemId,Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        // MOBILES
                    } else if (module.equalsIgnoreCase("mobiles")) {
                        txtPrice.setText("\u20B9" + filteredProductsBean.getUnit_price_incl_tax());
                        mobileLayout.setVisibility(View.VISIBLE);

                        String arr[] = filteredProductsBean.getFeatures().split("\n\n");

                        int bulletGap = (int) dp(10);

                        final SpannableStringBuilder ssb1 = new SpannableStringBuilder();
                        for (int i = 0; i < arr.length; i++) {
                            String line = arr[i];
                            SpannableString ss = new SpannableString(line);
                            ss.setSpan(new BulletSpan(bulletGap), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            ssb1.append(ss);

                            //avoid last "\n"
                            if (i + 1 < arr.length)
                                ssb1.append("\n\n");

                        }

                        txtBullets.setText(ssb1);
                        rbDescription.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                if (isChecked) {

                                    txtBullets.setText(ssb1);
                                }
                            }
                        });

                        rbProductDetails.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                if (isChecked) {

                                    String longDescription = "Screen: " + filteredProductsBean.getScreen() +
                                            "\n" + "Processor: " + filteredProductsBean.getProcessor() + "\n" +
                                            "Storage: " + filteredProductsBean.getRam() + " - " + filteredProductsBean.getStorage() + "\n"
                                            + "Camera :" + filteredProductsBean.getCamera() + "\n" +
                                            "Battery: " + filteredProductsBean.getBattery();

                                    String arr[] = longDescription.split("\n");

                                    int bulletGap = (int) dp(10);

                                    SpannableStringBuilder ssb = new SpannableStringBuilder();
                                    for (int i = 0; i < arr.length; i++) {
                                        String line = arr[i];
                                        SpannableString ss = new SpannableString(line);
                                        ss.setSpan(new BulletSpan(bulletGap), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        ssb.append(ss);

                                        //avoid last "\n"
                                        if (i + 1 < arr.length)
                                            ssb.append("\n\n");

                                    }
                                    txtBullets.setText(ssb);
                                }
                            }
                        });

                        // CELEBARATIONS
                    } else if (module.equalsIgnoreCase("celebrations")) {

                        addtoCombo.setVisibility(View.VISIBLE);
                        txtPriceSymbol.setVisibility(View.VISIBLE);


                        if (filteredProductsBean.getMain_category_name().equalsIgnoreCase("Cakes")) {

                            celebrationLayout.setVisibility(View.VISIBLE);

                            final List<String> listCapacity = new ArrayList<>();
                            final List<String> listUnitPrice = new ArrayList<>();
                            final List<String> listItemId = new ArrayList<>();
                            final List<String> listFlavour = new ArrayList<>();
                            final List<String> listTimeslot = new ArrayList<>();

                            if (filteredProductsBean.getFlavour().equalsIgnoreCase("")) {
                                spinnerCelebFlavourLayout.setVisibility(View.GONE);

                            }

                            // capacity
                            final String[] itemsCapacity = filteredProductsBean.getCapacity().split(",");
                            for (String itemCap : itemsCapacity) {
                                System.out.println("itemCap = " + itemCap);
                                listCapacity.add(itemCap);
                            }

                            // unit price
                            String[] itemsUnitPrice = filteredProductsBean.getUnit_price_incl_tax().split(",");
                            for (String itemUnit : itemsUnitPrice) {
                                System.out.println("itemUnit = " + itemUnit);
                                listUnitPrice.add(itemUnit);
                            }

                            // itemsid
                            String[] itemsId = filteredProductsBean.getItem_id().split(",");
                            for (String itemsid : itemsId) {
                                System.out.println("itemsID = " + itemsid);
                                listItemId.add(itemsid);
                            }

                            // flavour
                            final String[][] flavour = {filteredProductsBean.getFlavour().split(",")};
                            for (String flav : flavour[0]) {
                                System.out.println("flav = " + flav);
                                listFlavour.add(flav);
                            }

                            // timeslot
                            final String[][] timeslot = {filteredProductsBean.getDelivery_time().split(",")};
                            for (String time : timeslot[0]) {
                                System.out.println("time = " + time);
                                listTimeslot.add(time);
                            }


                            // spin Weight
                            SpinnerAdapter adapter = new SpinnerAdapter(ProductDetailsActivity.this, listCapacity, listUnitPrice, listItemId);
                            spinCelebWeight.setAdapter(adapter);
                            spinCelebWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    selectedPrice = listUnitPrice.get(position);
                                    selectedItemId = listItemId.get(position);

                                    txtPrice.setText("" + selectedPrice);

                                    heartShape.setChecked(false);
                                    eggless.setChecked(false);


                                    if (listCapacity.get(position).equals("0.5")) {

                                        heartShape.setVisibility(View.GONE);
                                    } else {
                                        heartShape.setVisibility(View.VISIBLE);
                                    }

                                    // Toast.makeText(getApplicationContext(),""+price+"---"+itemId,Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                            // spin flavour

                            ArrayAdapter<String> flavourAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listFlavour);
                            flavourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinCelebFlavour.setAdapter(flavourAdapter);
                            spinCelebFlavour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    flavourValue = parent.getItemAtPosition(position).toString();
                                    Log.e("FLAVOUR", "" + flavourValue);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                            // spin timeslot

                            ArrayAdapter<String> timeslotAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, listTimeslot);
                            timeslotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinCelebTime.setAdapter(timeslotAdapter);
                            spinCelebTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    timeslotValue = parent.getItemAtPosition(position).toString();
                                    Log.e("TIMESLOT", "" + timeslotValue);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                            // eggless checkbox
                            eggless.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                                    if (isChecked) {

                                        NumberFormat nf = NumberFormat.getInstance();
                                        try {
                                            double number = nf.parse(txtPrice.getText().toString()).doubleValue();
                                            double number2 = nf.parse(filteredProductsBean.getEggless_amt()).doubleValue();
                                            double sum = number + number2;
                                            txtPrice.setText("" + sum);

                                            egglessPrice = filteredProductsBean.getEggless_amt();
                                            egglessValue = "Yes";

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    } else {

                                        NumberFormat nf = NumberFormat.getInstance();
                                        try {
                                            double number = nf.parse(txtPrice.getText().toString()).doubleValue();
                                            double number2 = nf.parse(filteredProductsBean.getEggless_amt()).doubleValue();
                                            double sum = number - number2;
                                            txtPrice.setText("" + sum);

                                            egglessPrice = "0";
                                            egglessValue = "No";

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            });

                            // eggless checkbox
                            heartShape.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                    if (isChecked) {

                                        NumberFormat nf = NumberFormat.getInstance();
                                        try {
                                            double number = nf.parse(txtPrice.getText().toString()).doubleValue();
                                            double number2 = nf.parse(filteredProductsBean.getHeart_shape_amt()).doubleValue();
                                            double sum = number + number2;
                                            txtPrice.setText("" + sum);

                                            heartshapePrice = filteredProductsBean.getHeart_shape_amt();

                                            heartshapeValue = "Yes";

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    } else {

                                        NumberFormat nf = NumberFormat.getInstance();
                                        try {
                                            double number = nf.parse(txtPrice.getText().toString()).doubleValue();
                                            double number2 = nf.parse(filteredProductsBean.getHeart_shape_amt()).doubleValue();
                                            double sum = number - number2;
                                            txtPrice.setText("" + sum);

                                            heartshapePrice = "0";
                                            heartshapeValue = "No";

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });


                            // message on cake


                        } else {

                            txtPrice.setText("" + filteredProductsBean.getUnit_price_incl_tax());
                        }


                    } else {

                        txtPrice.setText("\u20B9" + filteredProductsBean.getUnit_price_incl_tax());
                        spinnerLayout.setVisibility(View.GONE);
                    }


                    btnAddToCart = (Button) findViewById(R.id.btnAddtocart);
                    btnBuyNow = (Button) findViewById(R.id.btnbuynow);

                    btnAddToCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String cart = "ADD TO CART";


                            if (module.equalsIgnoreCase("grocery")) {
                                addtocartData(userid, selectedItemId, "", filteredProductsBean.getModule(), filteredProductsBean.getCart_type(), "1",
                                        filteredProductsBean.getUnit_price_incl_tax(), filteredProductsBean.getTax_rate(), filteredProductsBean.getDiscount(), filteredProductsBean.getColor(), filteredProductsBean.getEggless(), filteredProductsBean.getEggless_amt(),
                                        filteredProductsBean.getHeart_shape(), filteredProductsBean.getHeart_shape_amt(), filteredProductsBean.getFlavour(), filteredProductsBean.getMessage(), filteredProductsBean.getDelivery_time(), cart);

                            } else if (module.equalsIgnoreCase("mobiles")) {

                                Log.e("SUREH_ID", "" + filteredProductsBean.getItem_id());

                                addtocartData(userid, filteredProductsBean.getItem_id(), "", filteredProductsBean.getModule(), filteredProductsBean.getCart_type(), "1",
                                        filteredProductsBean.getUnit_price_incl_tax(), filteredProductsBean.getTax_rate(), filteredProductsBean.getDiscount(), filteredProductsBean.getColor(), filteredProductsBean.getEggless(), filteredProductsBean.getEggless_amt(),
                                        filteredProductsBean.getHeart_shape(), filteredProductsBean.getHeart_shape_amt(), filteredProductsBean.getFlavour(), filteredProductsBean.getMessage(), filteredProductsBean.getDelivery_time(), cart);

                            } else if (module.equalsIgnoreCase("celebrations")) {

                                if (filteredProductsBean.getMain_category_name().equalsIgnoreCase("Cakes")) {

                                    if (!messageonCake.getText().toString().isEmpty() && !selectedItemId.isEmpty() && !timeslotValue.isEmpty() && !flavourValue.isEmpty()) {

                                        addtocartData(userid, selectedItemId, "", filteredProductsBean.getModule(), filteredProductsBean.getCart_type(), "1",
                                                txtPrice.getText().toString(), filteredProductsBean.getTax_rate(), filteredProductsBean.getDiscount(), filteredProductsBean.getColor(), egglessValue, egglessPrice,
                                                heartshapeValue, heartshapePrice, flavourValue, messageonCake.getText().toString(), timeslotValue, cart);
                                    } else {

                                        Toast.makeText(getApplicationContext(), "Select/Enter Mandatory Fields", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    addtocartData(userid, filteredProductsBean.getItem_id(), "", filteredProductsBean.getModule(), filteredProductsBean.getCart_type(), "1",
                                            filteredProductsBean.getUnit_price_incl_tax(), filteredProductsBean.getTax_rate(), filteredProductsBean.getDiscount(), filteredProductsBean.getColor(), filteredProductsBean.getEggless(), filteredProductsBean.getEggless_amt(),
                                            filteredProductsBean.getHeart_shape(), filteredProductsBean.getHeart_shape_amt(), filteredProductsBean.getFlavour(), filteredProductsBean.getMessage(), filteredProductsBean.getDelivery_time(), cart);

                                }

                            } else {
                                addtocartData(userid, filteredProductsBean.getProduct_id(), "", filteredProductsBean.getModule(), filteredProductsBean.getCart_type(), "1",
                                        filteredProductsBean.getUnit_price_incl_tax(), filteredProductsBean.getTax_rate(), filteredProductsBean.getDiscount(), filteredProductsBean.getColor(), filteredProductsBean.getEggless(), filteredProductsBean.getEggless_amt(),
                                        filteredProductsBean.getHeart_shape(), filteredProductsBean.getHeart_shape_amt(), filteredProductsBean.getFlavour(), filteredProductsBean.getMessage(), filteredProductsBean.getDelivery_time(), cart);

                            }
                        }
                    });

                    btnBuyNow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String cart = "BUY NOW";
                            if (module.equalsIgnoreCase("grocery")) {
                                addtocartData(userid, selectedItemId, "", filteredProductsBean.getModule(), filteredProductsBean.getCart_type(), "1",
                                        filteredProductsBean.getUnit_price_incl_tax(), filteredProductsBean.getTax_rate(), filteredProductsBean.getDiscount(), filteredProductsBean.getColor(), filteredProductsBean.getEggless(), filteredProductsBean.getEggless_amt(),
                                        filteredProductsBean.getHeart_shape(), filteredProductsBean.getHeart_shape_amt(), filteredProductsBean.getFlavour(), filteredProductsBean.getMessage(), filteredProductsBean.getDelivery_time(), cart);

                            } else if (module.equalsIgnoreCase("mobiles")) {

                                Log.e("SUREH_ID", "" + filteredProductsBean.getItem_id());

                                addtocartData(userid, filteredProductsBean.getItem_id(), "", filteredProductsBean.getModule(), filteredProductsBean.getCart_type(), "1",
                                        filteredProductsBean.getUnit_price_incl_tax(), filteredProductsBean.getTax_rate(), filteredProductsBean.getDiscount(), filteredProductsBean.getColor(), filteredProductsBean.getEggless(), filteredProductsBean.getEggless_amt(),
                                        filteredProductsBean.getHeart_shape(), filteredProductsBean.getHeart_shape_amt(), filteredProductsBean.getFlavour(), filteredProductsBean.getMessage(), filteredProductsBean.getDelivery_time(), cart);

                            } else if (module.equalsIgnoreCase("celebrations")) {

                                if (filteredProductsBean.getMain_category_name().equalsIgnoreCase("Cakes")) {

                                    if (!messageonCake.getText().toString().isEmpty() && !selectedItemId.isEmpty() && !timeslotValue.isEmpty() && !flavourValue.isEmpty()) {

                                        addtocartData(userid, selectedItemId, "", filteredProductsBean.getModule(), filteredProductsBean.getCart_type(), "1",
                                                txtPrice.getText().toString(), filteredProductsBean.getTax_rate(), filteredProductsBean.getDiscount(), filteredProductsBean.getColor(), egglessValue, egglessPrice,
                                                heartshapeValue, heartshapePrice, flavourValue, messageonCake.getText().toString(), timeslotValue, cart);
                                    } else {

                                        Toast.makeText(getApplicationContext(), "Select/Enter Mandatory Fields", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    addtocartData(userid, filteredProductsBean.getItem_id(), "", filteredProductsBean.getModule(), filteredProductsBean.getCart_type(), "1",
                                            filteredProductsBean.getUnit_price_incl_tax(), filteredProductsBean.getTax_rate(), filteredProductsBean.getDiscount(), filteredProductsBean.getColor(), filteredProductsBean.getEggless(), filteredProductsBean.getEggless_amt(),
                                            filteredProductsBean.getHeart_shape(), filteredProductsBean.getHeart_shape_amt(), filteredProductsBean.getFlavour(), filteredProductsBean.getMessage(), filteredProductsBean.getDelivery_time(), cart);

                                }

                            } else {
                                addtocartData(userid, filteredProductsBean.getProduct_id(), "", filteredProductsBean.getModule(), filteredProductsBean.getCart_type(), "1",
                                        filteredProductsBean.getUnit_price_incl_tax(), filteredProductsBean.getTax_rate(), filteredProductsBean.getDiscount(), filteredProductsBean.getColor(), filteredProductsBean.getEggless(), filteredProductsBean.getEggless_amt(),
                                        filteredProductsBean.getHeart_shape(), filteredProductsBean.getHeart_shape_amt(), filteredProductsBean.getFlavour(), filteredProductsBean.getMessage(), filteredProductsBean.getDelivery_time(), cart);

                            }
                        }
                    });

// add to Combo Button
                    addtoCombo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (filteredProductsBean.getMain_category_name().equalsIgnoreCase("Cakes")) {


                                if (!messageonCake.getText().toString().isEmpty() && !selectedItemId.isEmpty() && !timeslotValue.isEmpty() && !flavourValue.isEmpty()) {

                                    addtocartData(userid, selectedItemId, "", filteredProductsBean.getModule(), "combo", "1",
                                            txtPrice.getText().toString(), filteredProductsBean.getTax_rate(), filteredProductsBean.getDiscount(), filteredProductsBean.getColor(), egglessValue, egglessPrice,
                                            heartshapeValue, heartshapePrice, flavourValue, messageonCake.getText().toString(), timeslotValue, "");

                                } else {

                                    Toast.makeText(getApplicationContext(), "Select/Enter Mandatory Fields", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                addtocartData(userid, filteredProductsBean.getItem_id(), "", filteredProductsBean.getModule(), "combo", "1",
                                        filteredProductsBean.getUnit_price_incl_tax(), filteredProductsBean.getTax_rate(), filteredProductsBean.getDiscount(), filteredProductsBean.getColor(), filteredProductsBean.getEggless(), filteredProductsBean.getEggless_amt(),
                                        filteredProductsBean.getHeart_shape(), filteredProductsBean.getHeart_shape_amt(), filteredProductsBean.getFlavour(), filteredProductsBean.getMessage(), filteredProductsBean.getDelivery_time(), "");

                            }
                        }
                    });

                }

                // slider Images 
                String[] namesList = allImages.split(",");

                Log.e("ALLIMAGESLIDES", "" + namesList.length);

                for (String name : namesList) {

                    System.out.println(name);

                    Log.e("IMAGESLIDES", "" + name);

                    viewPagerItemslist.add(new ViewPagerItem(imagePath + name));
                }

                final PageIndicatorView pageIndicatorView = findViewById(R.id.pageIndicatorView);
                ViewPagerAdapter adapter = new ViewPagerAdapter(getApplicationContext(), viewPagerItemslist);
                ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
                viewPager.setAdapter(adapter);
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        pageIndicatorView.setSelection(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });


                //dynamic carAdapter
                // carAdapter.registerDataSetObserver(indicator.getDataSetObserver());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
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

    // submit details
    private void addtocartData(final String userid, final String itemid, final String requestCode, final String module,
                               final String cartType, final String quantity, final String unitPrice, final String tax_rate,
                               final String discount, final String color, final String eggless,
                               final String egglessAmount, final String heartShape, final String heartShapeAmount,
                               final String flavour, final String message, final String timeslot, final String btnValue) {


        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Api.ADD_TO_CART_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSE : ", "" + response);
                        // hideDialog();

                        pDialog.hide();

                        if (btnValue.equals("ADD TO CART")) {

                            if (btnAddToCart.getText().equals("GO TO CART")) {
                                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            } else {
                                btnAddToCart.setText("GO TO CART");
                                Toast.makeText(getApplicationContext(), "Item Added to cart", Toast.LENGTH_SHORT).show();
                            }
                        } else if (btnValue.equalsIgnoreCase("BUY NOW")) {
                            Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }


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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("item_id", itemid);
                params.put("request_code", requestCode);
                params.put("category", module);
                params.put("cart_type", cartType);
                params.put("quantity", quantity);
                params.put("unit_price_incl_tax", unitPrice);
                params.put("tax_rate", tax_rate);
                params.put("discount", discount);
                params.put("color", color);
                params.put("eggless", eggless);
                params.put("eggless_amt", egglessAmount);
                params.put("heart_shape", heartShape);
                params.put("heart_shape_amt", heartShapeAmount);
                params.put("flavour", flavour);
                params.put("message", message);
                params.put("delivery_time", timeslot);

                Log.e("RESPONSE_Parasms: ", "" + params);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    private float dp(int dp) {
        return getResources().getDisplayMetrics().density * dp;
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
