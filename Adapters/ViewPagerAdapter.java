package com.prism.pickany247.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.prism.pickany247.R;
import com.prism.pickany247.Model.ViewPagerItem;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<ViewPagerItem> images;
    private LayoutInflater inflater;
    private Context context;

    public ViewPagerAdapter(Context context, List<ViewPagerItem> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.slide_card, view, false);

        final ViewPagerItem addCardItem =images.get(position);
        ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.thumbnail);

        try {
            Glide.with(context).load(addCardItem.getImage()).into(myImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}