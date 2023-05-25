package com.project.canteenmanagementsystem.AdminActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.canteenmanagementsystem.AdminActivity.Adaptor.ProductListAdaptor;
import com.project.canteenmanagementsystem.Models.AddProducts;
import com.project.canteenmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

public class DeleteUpdateProductActivity extends AppCompatActivity {

    ListView list_product;
    DatabaseReference dbref, dbref_delete;
    List<AddProducts> productlist;
    TextView tv_nodata;
    String product_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_delete_update_product);

        // find view by id's
        list_product = findViewById(R.id.listview_deleteproductlist);
        tv_nodata = findViewById(R.id.tv_product_nodata);
        /////////////////

        productlist = new ArrayList<>();

        // getting data from intent
        product_category = getIntent().getStringExtra("category");
        //////////////

        // database set-up
        dbref = FirebaseDatabase.getInstance().getReference("Product").child(product_category);
        dbref_delete = FirebaseDatabase.getInstance().getReference("Product").child(product_category);
        /////////////

        try {
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    productlist.clear();
                    for (DataSnapshot getorder : dataSnapshot.getChildren()) {
                        AddProducts ap = getorder.getValue(AddProducts.class);
                        productlist.add(ap);
                    }

                    // when list is empty text will appear
                    if (!(productlist.size() > 0)) {
                        tv_nodata.setVisibility(View.VISIBLE);
                    } else {
                        tv_nodata.setVisibility(View.INVISIBLE);
                    }
                    //////////

                    // list set-up with adaptor
                    ProductListAdaptor adapter = new ProductListAdaptor(DeleteUpdateProductActivity.this, productlist);
                    list_product.setAdapter(adapter);
                    ////////////

                    // when click singel product
                    list_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent i = new Intent(DeleteUpdateProductActivity.this, UpdateTodaysProduct.class);
                            AddProducts ap = (AddProducts) parent.getItemAtPosition(position);
                            i.putExtra("product_category", ap.getFood_category());
                            i.putExtra("id", ap.getId());
                            i.putExtra("name", ap.getName());
                            i.putExtra("price", ap.getPrice());
                            i.putExtra("img_url", ap.getImg_url());
                            i.putExtra("product_activity", "yes");
                            startActivity(i);
                        }
                    });
                    ///////////
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(DeleteUpdateProductActivity.this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }
}