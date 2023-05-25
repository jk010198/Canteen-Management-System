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
import com.project.canteenmanagementsystem.UsersActivity.Model.OrdersModel;

import java.util.ArrayList;


public class UserOrderListArrayAdaptor extends ArrayAdapter {

    Activity context;
    ArrayList<OrdersModel> ap;

    public UserOrderListArrayAdaptor(@NonNull Activity context, int resource, @NonNull ArrayList<OrdersModel> product) {
        super(context, resource, product);
        this.context = context;
        ap = product;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.users_myorders_listlayout, null, true);

        // find view by id's
        TextView textview_foodcategory = (TextView) rowView.findViewById(R.id.order_date);
        TextView textview_name = (TextView) rowView.findViewById(R.id.order_id);
        TextView textview_price = (TextView) rowView.findViewById(R.id.order_otp);

        // data set-up
        textview_foodcategory.setText("Order Date :- " + ap.get(position).order_date_time);
        textview_name.setText("Order ID :- " + ap.get(position).order_id);
        textview_price.setText("Order Otp :- " + ap.get(position).order_otp);

        return rowView;
    }
}