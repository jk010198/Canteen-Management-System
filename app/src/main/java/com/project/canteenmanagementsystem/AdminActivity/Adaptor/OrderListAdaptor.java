package com.project.canteenmanagementsystem.AdminActivity.Adaptor;

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

import java.util.List;

public class OrderListAdaptor extends ArrayAdapter<OrdersModel> {

    private Activity context;
    public static List<OrdersModel> ordertlist;
    public static String name, add, model_name, model_issue, date;

    public OrderListAdaptor(Activity context, List<OrdersModel> orderlist) {
        super(context, R.layout.complete_orderlist_layout, orderlist);
        this.context = context;
        this.ordertlist = orderlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listview = inflater.inflate(R.layout.complete_orderlist_layout, null, true);

        TextView textView_customername = listview.findViewById(R.id.textview_customername);
        TextView textView_customermobile = listview.findViewById(R.id.textview_mobile);
        TextView textView_grandtotal = listview.findViewById(R.id.textview_grandtotal);

        OrdersModel om = ordertlist.get(position);

        textView_customername.setText(om.name);
        textView_customermobile.setText("Mobile:- " + om.mobile);
        textView_grandtotal.setText("₹ " + om.grandtotal);

        return listview;
    }
}