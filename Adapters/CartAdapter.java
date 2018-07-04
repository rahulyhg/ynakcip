package com.prism.pickany247.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prism.pickany247.Interface.CartProductClickListener;
import com.prism.pickany247.Interface.CartDataSet;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.Product;

public class CartAdapter extends BaseAdapter {

    private final CartDataSet cartDataSet;
    private final CartProductClickListener productClickListener;
    private final LayoutInflater layoutInflater;
    private final Context mcontext;

    public CartAdapter(CartDataSet cartDataSet, CartProductClickListener productClickListener, LayoutInflater layoutInflater, Context mcontext) {
        this.cartDataSet = cartDataSet;
        this.productClickListener = productClickListener;
        this.layoutInflater = layoutInflater;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return cartDataSet.size();
    }

    @Override
    public Product getItem(int position) {
        return cartDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cartDataSet.getId(position);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = createView(parent);
            view.setTag(ViewHolder.from(view));
        }
        Product product = cartDataSet.get(position);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        update(viewHolder, product);
        return view;
    }

    private View createView(ViewGroup parent) {
        return layoutInflater.inflate(R.layout.cart_product, parent, false);
    }

    private void update(ViewHolder viewHolder, final Product product) {
        viewHolder.name.setText(product.getName());
        viewHolder.quantity.setText(String.valueOf(product.getQuantity()));
        viewHolder.price.setText("\u20B9"+product.price);
        // loading album cover using Glide library
        Glide.with(mcontext).load(product.getThumbnail()).into(viewHolder.thumbnail);

        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productClickListener.onMinusClick(product);
            }
        });
        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productClickListener.onPlusClick(product);
            }
        });

        viewHolder.saveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productClickListener.onSaveClick(product);
            }
        });

        viewHolder.removeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productClickListener.onRemoveClick(product);
            }
        });


    }

    private static final class ViewHolder {
        final TextView name;
        final TextView quantity;
        final View minus;
        final View plus;
        final ImageView thumbnail;
        final TextView price;
        final LinearLayout saveLayout;
        final LinearLayout removeLayout;

        static ViewHolder from(View view) {
            return new ViewHolder(
                    ((TextView) view.findViewById(R.id.product_name)),
                    ((TextView) view.findViewById(R.id.product_quantity)),
                    view.findViewById(R.id.product_minus),
                    view.findViewById(R.id.product_plus),
                    ((ImageView)view.findViewById(R.id.thumbnail)),
                    ((TextView)view.findViewById(R.id.product_Price)),
                    ((LinearLayout)view.findViewById(R.id.linearSaveItem)),
                    ((LinearLayout)view.findViewById(R.id.linearremove))

            );
        }

        private ViewHolder(TextView name, TextView quantity, View minus, View plus, ImageView imageView, TextView price, LinearLayout viewById, LinearLayout byId) {
            this.name = name;
            this.quantity = quantity;
            this.minus = minus;
            this.plus = plus;
            this.thumbnail=imageView;
            this.price=price;
            this.saveLayout=viewById;
            this.removeLayout=byId;

        }
    }

}
