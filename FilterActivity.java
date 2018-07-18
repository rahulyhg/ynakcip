package com.prism.pickany247;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.prism.pickany247.Adapters.MyCustomAdapter;
import com.prism.pickany247.Response.Country;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    ArrayList<Country> countryList = new ArrayList<Country>();
    ListView listView;
    MyCustomAdapter dataAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);



        // get seekbar from view
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar)findViewById(R.id.rangeSeekbar3);

// get min and max text view
        final TextView tvMin = (TextView)findViewById(R.id.textMin1);
        final TextView tvMax = (TextView) findViewById(R.id.textMax1);

// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue));
                tvMax.setText(String.valueOf(maxValue));
            }
        });

// set final value listener
        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });


        displayListView();

        RadioGroup radioGroup =(RadioGroup)findViewById(R.id.radioGroup);
        RadioButton rb1 =(RadioButton)findViewById(R.id.rbCat);
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Country> countryList = dataAdapter.countryList;
                for(int i=0;i<countryList.size();i++){
                    Country country = countryList.get(i);
                    if(country.isSelected()){
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),responseText, Toast.LENGTH_LONG).show();

            }
        });




    }

    private void displayListView() {

        //Array list of countries

        Country country = new Country("Afghanistan",false);
        countryList.add(country);
        country = new Country("Albania",true);
        countryList.add(country);
        country = new Country("Algeria",false);
        countryList.add(country);
        country = new Country("American Samoa",true);
        countryList.add(country);
        country = new Country("Andorra",true);
        countryList.add(country);
        country = new Country("Angola",false);
        countryList.add(country);
        country = new Country("Anguilla",false);
        countryList.add(country);

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.row, countryList);
        ListView listView = (ListView) findViewById(R.id.catList);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);




    }


}
