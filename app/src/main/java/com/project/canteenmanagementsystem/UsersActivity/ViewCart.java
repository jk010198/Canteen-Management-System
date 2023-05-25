package com.project.canteenmanagementsystem.UsersActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.project.canteenmanagementsystem.R;
import com.project.canteenmanagementsystem.UsersActivity.Adaptor.CartArrayAdaptor;
import com.project.canteenmanagementsystem.UsersActivity.Model.CartInsertion;
import com.project.canteenmanagementsystem.UsersActivity.Model.CartModel;

import java.util.ArrayList;
import java.util.List;

public class ViewCart extends AppCompatActivity {

    ListView lv;
    List<CartModel> viewcart;
    CartArrayAdaptor cartArrayAdaptor;
    CartModel product;
    TextView tv_grandtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_view_cart);

        lv = findViewById(R.id.listview_viewcart);
        tv_grandtotal = findViewById(R.id.tv_grandtotal);
        tv_grandtotal.setText("GrandTotal:-" + CartInsertion.cartprice + "   ");

        viewcart = new ArrayList<>();

        for (int i = 0; i < CartInsertion.cartProducts.size(); i++) {
            product = CartInsertion.cartProducts.get(i);
            viewcart.add(product);
        }

        cartArrayAdaptor = new CartArrayAdaptor(ViewCart.this, R.layout.list_layout_cart, viewcart);
        lv.setAdapter(cartArrayAdaptor);
    }

    public void nextToPayment(View view) {
        Intent i = new Intent(ViewCart.this, DeliveryDetails.class);
        startActivity(i);
    }
}