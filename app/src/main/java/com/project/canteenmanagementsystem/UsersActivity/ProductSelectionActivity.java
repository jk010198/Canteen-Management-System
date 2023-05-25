package com.project.canteenmanagementsystem.UsersActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.canteenmanagementsystem.Models.AddProducts;
import com.project.canteenmanagementsystem.R;
import com.project.canteenmanagementsystem.UsersActivity.Adaptor.AddProductArrayAdaptor;
import com.project.canteenmanagementsystem.UsersActivity.Model.CartInsertion;

import java.util.ArrayList;
import java.util.List;

public class ProductSelectionActivity extends AppCompatActivity {

    TextView textview_heading;
    ListView listView_products;
    List<AddProducts> productlist;
    DatabaseReference dbref;
    Button btn_viewcart;
    String product_category;
    AddProductArrayAdaptor addProductArrayAdaptor;

    ProgressDialog pd;
    TextView textview_grandtotal;
    TextView tv_nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_product_selection);

        // find view by id
        textview_heading = findViewById(R.id.textview_head);
        listView_products = findViewById(R.id.listview_products);
        btn_viewcart = findViewById(R.id.button_viewcart);
        textview_grandtotal = findViewById(R.id.tv_total_price);
        tv_nodata = findViewById(R.id.tv_product_nodata);

        // getting data from intent & set-up on textview
        product_category = getIntent().getStringExtra("category");
        textview_heading.setText(product_category);

        // database ref
        dbref = FirebaseDatabase.getInstance().getReference("Product").child(product_category);
        productlist = new ArrayList<>();

        // progress dialog set-up
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setTitle("Canteen Management System");
        pd.setMessage("Please Wait ");
        pd.show();

        try {
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    productlist.clear();
                    for (DataSnapshot getorder : dataSnapshot.getChildren()) {
                        AddProducts ap = getorder.getValue(AddProducts.class);
                        productlist.add(ap);
                    }

                    if (!(productlist.size() > 0)) {
                        tv_nodata.setVisibility(View.VISIBLE);
                    } else {
                        tv_nodata.setVisibility(View.INVISIBLE);
                    }

                    addProductArrayAdaptor = new AddProductArrayAdaptor(ProductSelectionActivity.this,
                            R.layout.listlayout_addproduct, (ArrayList<AddProducts>) productlist);
                    listView_products.setAdapter(addProductArrayAdaptor);
                    pd.dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }

        textview_grandtotal.setText(CartInsertion.cartprice+"");

        // viewcart method
        btn_viewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartInsertion.cartProducts.size() > 0) {
                    startActivity(new Intent(getApplicationContext(), ViewCart.class));
                } else {
                    Toast.makeText(ProductSelectionActivity.this, "Please select any products.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}