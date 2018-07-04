package com.prism.pickany247.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;


import com.prism.pickany247.R;
import com.prism.pickany247.Response.AddressItem;
import com.prism.pickany247.UpdateAddressActivity;

import java.util.List;

public class AddressAdapter extends BaseAdapter{

    int selectedIndex = -1;
    private Context mContext;
    private List<AddressItem.ModulesBean> cartListBeanList;

    public AddressAdapter(Context mContext, List<AddressItem.ModulesBean> cartListBeanList) {
        this.mContext = mContext;
        this.cartListBeanList = cartListBeanList;
    }

    @Override
    public int getCount() {
        return cartListBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return cartListBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = View.inflate(mContext,R.layout.address_card,null);

        final AddressItem.ModulesBean cartListBean = cartListBeanList.get(i);

         TextView txtName,txtAddress,txtMobile,btnEdit;
         RadioButton radioButton;

        txtName = (TextView) view1.findViewById(R.id.txtName);
        txtAddress = (TextView) view1.findViewById(R.id.txtAddress);
        txtMobile = (TextView) view1.findViewById(R.id.txtMobile);
        btnEdit = (TextView) view1.findViewById(R.id.txtEdit);
        radioButton=(RadioButton) view1.findViewById(R.id.radioButton);


        txtName.setText(cartListBean.getName());
        txtAddress.setText(cartListBean.getAddress()+","+cartListBean.getCity()+","+cartListBean.getState()+","+cartListBean.getZip());
        txtMobile.setText("Mobile : "+cartListBean.getPhone());

        if(selectedIndex == i){
            radioButton.setChecked(true);
                btnEdit.setVisibility(View.VISIBLE);

        }
        else{
            radioButton.setChecked(false);
            btnEdit.setVisibility(View.GONE);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) mContext;
                Intent intent =new Intent(mContext, UpdateAddressActivity.class);
                intent.putExtra("addressid",cartListBean.getId());
                intent.putExtra("city",cartListBean.getCity());
                intent.putExtra("customername",cartListBean.getName());
                intent.putExtra("state",cartListBean.getState());
                intent.putExtra("mobile",cartListBean.getPhone());
                intent.putExtra("address",cartListBean.getAddress());
                intent.putExtra("zipcode",cartListBean.getZip());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

              // Toast.makeText(mContext,""+cartListBean.getAddressId(),Toast.LENGTH_SHORT).show();
            }
        });



        return view1;

    }

    public void setSelectedIndex(int index){
        selectedIndex = index;

    }

}
