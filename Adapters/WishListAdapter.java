package com.prism.pickany247.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.WishlistItem;


import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {

    private Context mContext;
    private List<WishlistItem.WishListBean> cartListBeanList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtItemName,txtPrice,btnDecrement;
        public ImageView thumbnail;


        public MyViewHolder(View view) {
            super(view);
            txtItemName = (TextView) view.findViewById(R.id.item_name);
            txtPrice = (TextView) view.findViewById(R.id.item_price);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            btnDecrement=(TextView) view.findViewById(R.id.remove_item);

        }
    }


    public WishListAdapter(Context mContext, List<WishlistItem.WishListBean> cartListBeanList) {
        this.mContext = mContext;
        this.cartListBeanList = cartListBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wish_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final WishlistItem.WishListBean cartListBean = cartListBeanList.get(position);

        holder.txtItemName.setText(cartListBean.getProduct_name());
        holder.txtPrice.setText("\u20B9"+cartListBean.getUnit_price());

        // loading album cover using Glide library
        Glide.with(mContext).load(cartListBean.getImage()).into(holder.thumbnail);

        holder.btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                delete(cartListBean.getId());
                cartListBeanList.remove(position);
                notifyDataSetChanged();

            }
        });
    }



    @Override
    public int getItemCount() {
        return cartListBeanList.size();
    }


    private void delete(String id){

        StringRequest dr = new StringRequest(Request.Method.DELETE, Api.DELETE_WISHLIST_URL+id,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("DELETE",""+response);


                        /*Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();*/
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.

                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(dr);
    }



}