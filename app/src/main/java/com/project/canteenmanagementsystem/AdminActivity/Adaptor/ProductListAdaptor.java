package com.project.canteenmanagementsystem.AdminActivity.Adaptor;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.project.canteenmanagementsystem.Models.AddProducts;
import com.project.canteenmanagementsystem.R;

import java.util.List;

public class ProductListAdaptor extends ArrayAdapter<AddProducts> {

    private Activity context;
    public static List<AddProducts> ordertlist;
    public static String name, add, model_name, model_issue, date;

    public ProductListAdaptor(Activity context, List<AddProducts> orderlist){
        super(context, R.layout.productlist_layout, orderlist);
        this.context = context;
        this.ordertlist = orderlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listview = inflater.inflate(R.layout.productlist_layout, null, true);

        TextView textView_foodcategory = listview.findViewById(R.id.textview_menucategory);
        TextView textView_productname = listview.findViewById(R.id.textview_menuname);
        TextView textView_productprice = listview.findViewById(R.id.textview_menuprice);

        AddProducts ap = ordertlist.get(position);

        textView_foodcategory.setText("Food Category:- "+ap.food_category);
        textView_productname.setText(ap.name);
        textView_productprice.setText("₹ "+ap.price);

        return  listview;
    }
}