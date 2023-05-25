package com.project.canteenmanagementsystem.UsersActivity.Adaptor;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.canteenmanagementsystem.R;
import com.project.canteenmanagementsystem.UsersActivity.Model.CartModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CartArrayAdaptor extends ArrayAdapter {

    Activity context;
    List<CartModel> cm;

    public CartArrayAdaptor(@NonNull Activity context, int resource, @NonNull List<CartModel> product) {
        super(context, resource, product);
        this.context = context;
        cm = product;
        Collections.sort(cm);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.list_layout_cart, null, true);

        // find view by id's
        TextView txtName = (TextView) rowView.findViewById(R.id.tv_productname);
        TextView txtprice = (TextView) rowView.findViewById(R.id.tv_product_price);
        TextView txtquantity = (TextView) rowView.findViewById(R.id.tv_product_quantity);
        //TextView txtqtyname = (TextView) rowView.findViewById(R.id.tv_qty_name);

        // data set-up
        txtName.setText("Product Name:- " + cm.get(position).name);
        txtprice.setText("Product Price:-" + cm.get(position).price);
        txtquantity.setText(cm.get(position).quantity + "");
        return rowView;
    }
}