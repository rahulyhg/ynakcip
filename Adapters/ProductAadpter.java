package com.prism.pickany247.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.ProductResponse;
import com.prism.pickany247.ProductDetailsActivity;

import java.util.List;
import java.util.StringTokenizer;

public class ProductAadpter extends RecyclerView.Adapter<ProductAadpter.MyViewHolder>{
    private Context mContext;
    private List<ProductResponse.FilteredProductsBean> homeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView txtPrice,txtName;
      //  public RatingBar ratingBar;
        public LinearLayout linearLayout;


        public MyViewHolder(View view) {
            super(view);

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            txtPrice = (TextView) view.findViewById(R.id.txtproductPrice);
            txtName = (TextView) view.findViewById(R.id.txtproductName);
            linearLayout=(LinearLayout)view.findViewById(R.id.parentLayout);
           // ratingBar=(RatingBar)view.findViewById(R.id.ratingBar);


        }
    }

    public ProductAadpter(Context mContext, List<ProductResponse.FilteredProductsBean> homekitchenList) {
        this.mContext = mContext;
        this.homeList = homekitchenList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stationery_product_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ProductResponse.FilteredProductsBean home = homeList.get(position);

        Log.e("CAPACITY0",""+home.getCapacity()+home.getUnit_price_incl_tax());

        // loading album cover using Glide library
        Glide.with(mContext).load(home.getImagePath()+home.getSingleImage()).into(holder.thumbnail);
        holder.txtName.setText(home.getProduct_name());



        if ("".equalsIgnoreCase(home.getCapacity()) ){

            holder.txtPrice.setText("\u20B9"+home.getUnit_price_incl_tax());
            Log.e("CAPACITY",""+home.getUnit_price_incl_tax());

        }else{

            String firstPrice="";
            String firstcapacity="";
            if(home.getUnit_price_incl_tax()!=null){
                StringTokenizer stringTokenizer =new StringTokenizer(home.getUnit_price_incl_tax());
                firstPrice = stringTokenizer.nextToken(",");
            }

            if (home.getCapacity()!=null){
                StringTokenizer stringTokenizer1 =new StringTokenizer(home.getCapacity());
                firstcapacity = stringTokenizer1.nextToken(",");
            }
            holder.txtPrice.setText("\u20B9"+firstPrice+"  ("+firstcapacity+")");
            Log.e("CAPACITY123",""+home.getUnit_price_incl_tax());
        }


      //  holder.ratingBar.setRating(Float.parseFloat(home.getRating()));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

     //                    Activity activity = (Activity) mContext;


                Intent intent = new Intent(mContext, ProductDetailsActivity.class);

                if (home.getModule().equalsIgnoreCase("mobiles")){
                    intent.putExtra("productId",home.getProduct_id()+"&itemId="+home.getItem_id());
                }
                else if (home.getModule().equalsIgnoreCase("celebrations")){

                    intent.putExtra("productId",home.getProduct_id()+"&maincatId="+home.getMain_category_id());
                }
                else {
                    intent.putExtra("productId",home.getProduct_id());
                }

                intent.putExtra("productName",home.getProduct_name());
                intent.putExtra("module",home.getModule());
                Log.e("MODULE",""+home.getModule());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


            }});

        }


    @Override
    public int getItemCount() {
        return homeList.size();
    }



}
