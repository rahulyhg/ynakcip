package com.prism.pickany247.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.GroceryHomeResponse;
import com.prism.pickany247.ProductListActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroceryCatAdapter extends RecyclerView.Adapter<GroceryCatAdapter.MyViewHolder>{
    private Context mContext;
    private List<GroceryHomeResponse.CategoriesBean> homeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView thumbnail;
        public TextView txtName;
        public LinearLayout linearLayout;


        public MyViewHolder(View view) {
            super(view);

            thumbnail = (CircleImageView) view.findViewById(R.id.thumbnail);
            txtName = (TextView) view.findViewById(R.id.txtCatName);
            linearLayout=(LinearLayout)view.findViewById(R.id.parentLayout);


        }
    }

    public GroceryCatAdapter(Context mContext, List<GroceryHomeResponse.CategoriesBean> homekitchenList) {
        this.mContext = mContext;
        this.homeList = homekitchenList;
    }

    @Override
    public GroceryCatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stationery_home_card, parent, false);

        return new GroceryCatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final GroceryHomeResponse.CategoriesBean home = homeList.get(position);

        // loading album cover using Glide library
        Glide.with(mContext).load(home.getImage()).into(holder.thumbnail);
        holder.txtName.setText(home.getCategory_name());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    Activity activity = (Activity) mContext;
                Intent intent = new Intent(mContext, ProductListActivity.class);
                intent.putExtra("catId", home.getId());
                intent.putExtra("title", home.getCategory_name());
                intent.putExtra("module", home.getModule());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


            }
        });


    }


    @Override
    public int getItemCount() {
        return homeList.size();
    }



}
