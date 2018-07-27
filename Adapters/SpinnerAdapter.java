package com.prism.pickany247.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prism.pickany247.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater linflater;
    private TextView txt_1, txt_2;
    private List<String> str1;
    private List<String> str2;
    private List<String> str3;

    public SpinnerAdapter(Context context, List<String> s1, List<String> s2,List<String> s3) {
        mContext = context;
        str1 = s1;
        str2 = s2;
        str3 = s3;
        linflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return str1.size();
    }

    @Override
    public Object getItem(int arg0) {
        return str1.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = linflater.inflate(R.layout.spinner_raw, null);

        }

        txt_1 = (TextView) convertView.findViewById(R.id.txtlist1);
       // txt_2 = (TextView) convertView.findViewById(R.id.txtlist2);
        txt_1.setText(str1.get(position));
      //  txt_2.setText(str2.get(position));

        return convertView;

    } }