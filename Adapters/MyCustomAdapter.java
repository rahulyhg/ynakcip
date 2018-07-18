package com.prism.pickany247.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import com.prism.pickany247.R;
import com.prism.pickany247.Response.CheckBoxItem;

import java.util.ArrayList;

public class MyCustomAdapter extends ArrayAdapter<CheckBoxItem> {

    public ArrayList<CheckBoxItem> checkBoxItemList;

    public MyCustomAdapter(Context context, int textViewResourceId,
                           ArrayList<CheckBoxItem> checkBoxItemList) {
        super(context, textViewResourceId, checkBoxItemList);
        this.checkBoxItemList = new ArrayList<CheckBoxItem>();
        this.checkBoxItemList.addAll(checkBoxItemList);
    }

    private class ViewHolder {
       // TextView code;
        CheckBox name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));

        if (convertView == null) {
           /* LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.row, null);*/

            convertView=LayoutInflater.from(getContext()).inflate(R.layout.row,parent,false);

            holder = new ViewHolder();
           // holder.code = (TextView) convertView.findViewById(R.id.code);
            holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
            convertView.setTag(holder);

            holder.name.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    CheckBoxItem checkBoxItem = (CheckBoxItem) cb.getTag();
                   /* Toast.makeText(getContext(),
                            "Clicked on Checkbox: " + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();*/
                    checkBoxItem.setSelected(cb.isChecked());
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CheckBoxItem checkBoxItem = checkBoxItemList.get(position);
      //  holder.code.setText(" (" + checkBoxItem.getCode() + ")");
        holder.name.setText(checkBoxItem.getName());
        holder.name.setChecked(checkBoxItem.isSelected());
        holder.name.setTag(checkBoxItem);

        return convertView;

    }
}
