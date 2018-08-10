package com.prism.pickany247.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.prism.pickany247.Modules.Celebrations.CelebrationsHomeActivity;
import com.prism.pickany247.Modules.Grocery.GroceryHomeActivity;
import com.prism.pickany247.Modules.HomeKitchen.KitchenHomeActivity;
import com.prism.pickany247.Modules.Mobiles.MobileHomeActivity;
import com.prism.pickany247.Modules.Rentals.RentalsHomeActivity;
import com.prism.pickany247.R;
import com.prism.pickany247.Model.HomeItem;
import com.prism.pickany247.Modules.Stationery.StationeryHomeActivity;


import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
    private Context mContext;
    private List<HomeItem> homeList;
    // ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbNail;
        TextView txtTitle;


        public MyViewHolder(View view) {
            super(view);

            thumbNail = (ImageView) view.findViewById(R.id.thumbnail);
            txtTitle=(TextView)view.findViewById(R.id.txtTitle);

            }
    }

    public HomeAdapter(Context mContext, List<HomeItem> homekitchenList) {
        this.mContext = mContext;
        this.homeList = homekitchenList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final HomeItem home = homeList.get(position);

        holder.txtTitle.setText(home.getTitle());
        try{
            // loading album cover using Glide library
            Glide.with(mContext).load(home.getImage()).skipMemoryCache( true ).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.thumbNail);

        } catch (IndexOutOfBoundsException e) {
            // TODO: handle exception
            e.printStackTrace();

        }

        holder.thumbNail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //stationery
                if (home.getId().equalsIgnoreCase("9") || home.getTitle().equalsIgnoreCase("Stationery")) {

//                    Activity activity = (Activity) mContext;
                    Intent intent = new Intent(mContext, StationeryHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                   // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                // mobiles
                else  if (home.getId().equalsIgnoreCase("6")|| home.getTitle().equalsIgnoreCase("Mobiles")) {

//                    Activity activity = (Activity) mContext;
                    Intent intent = new Intent(mContext, MobileHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                // grocery
                else  if (home.getId().equalsIgnoreCase("58")|| home.getTitle().equalsIgnoreCase("Grocery")) {

//                    Activity activity = (Activity) mContext;
                    Intent intent = new Intent(mContext, GroceryHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                // celebrations
                else  if (home.getId().equalsIgnoreCase("8")|| home.getTitle().equalsIgnoreCase("Celebrations")) {

//                    Activity activity = (Activity) mContext;
                    Intent intent = new Intent(mContext, CelebrationsHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                // home Kitchen
                else  if (home.getId().equalsIgnoreCase("5")|| home.getTitle().equalsIgnoreCase("Home Kitchen")) {

//                    Activity activity = (Activity) mContext;
                    Intent intent = new Intent(mContext, KitchenHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                // car & Bike rentals
                else  if (home.getId().equalsIgnoreCase("7")|| home.getTitle().equalsIgnoreCase("Car & Bike Rentals")) {

//                    Activity activity = (Activity) mContext;
                    Intent intent = new Intent(mContext, RentalsHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                // Bath & Body
                else  if (home.getId().equalsIgnoreCase("54")|| home.getTitle().equalsIgnoreCase("Bath & Body")) {

//                    Activity activity = (Activity) mContext;
                    Intent intent = new Intent(mContext, KitchenHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    // mContext.startActivity(intent);
                    // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                // Services
                else  if (home.getId().equalsIgnoreCase("55")|| home.getTitle().equalsIgnoreCase("Services")) {

//                    Activity activity = (Activity) mContext;
                    Intent intent = new Intent(mContext, KitchenHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    // mContext.startActivity(intent);
                    // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                // Hotels
                else  if (home.getId().equalsIgnoreCase("56")|| home.getTitle().equalsIgnoreCase("Hotels")) {

//                    Activity activity = (Activity) mContext;
                    Intent intent = new Intent(mContext, KitchenHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    // mContext.startActivity(intent);
                    // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

                // Electronic Accessories
                else  if (home.getId().equalsIgnoreCase("57")|| home.getTitle().equalsIgnoreCase("Electronic Accessories")) {

//                    Activity activity = (Activity) mContext;
                    Intent intent = new Intent(mContext, KitchenHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    // mContext.startActivity(intent);
                    // activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }



            }
        });


    }


    @Override
    public int getItemCount() {
        return homeList.size();
    }


/*
    @Override
    public int getItemViewType(int position) {
        if(position% 2 == 1) {
            return 2;
        }else{
            return 3;
        }
    }
*/
}
