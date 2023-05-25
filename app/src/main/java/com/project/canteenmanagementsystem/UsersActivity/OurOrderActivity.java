package com.project.canteenmanagementsystem.UsersActivity;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.canteenmanagementsystem.R;
import com.project.canteenmanagementsystem.UsersActivity.Adaptor.UserOrderListArrayAdaptor;
import com.project.canteenmanagementsystem.UsersActivity.Model.OrdersModel;

import java.util.ArrayList;
import java.util.List;

public class OurOrderActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference dbref;
    List<OrdersModel> olist;
    SharedPreferences sharedPreferences;
    String sharedpref_email;
    TextView tv_nodata;
    OrdersModel om;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_our_order);

        tv_nodata = findViewById(R.id.tv_nodata);
        listView = findViewById(R.id.listview);
        olist = new ArrayList<>();

        // get data from shared pref.
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        sharedpref_email = sharedPreferences.getString("username", "");
        //////////

        dbref = FirebaseDatabase.getInstance().getReference("Orders");

        dbref.orderByChild("emailid").equalTo(sharedpref_email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                olist.clear();
                for (DataSnapshot getorder : dataSnapshot.getChildren()) {
                    om = getorder.getValue(OrdersModel.class);

                    if (!(om.order_complete.equals("Completed order and Otp"))) {
                        olist.add(om);
                    }
                }
                if (!(olist.size() > 0)) {
                    tv_nodata.setVisibility(View.VISIBLE);
                } else {
                    tv_nodata.setVisibility(View.INVISIBLE);
                }

                // list set-up with adaptor
                UserOrderListArrayAdaptor orderlistAdaptor = new UserOrderListArrayAdaptor(OurOrderActivity.this,
                        R.layout.users_myorders_listlayout, (ArrayList<OrdersModel>) olist);
                listView.setAdapter(orderlistAdaptor);
                ////////////////////////

                // showing orderlist when click on single order
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AlertDialog.Builder adb = new AlertDialog.Builder( OurOrderActivity.this);
                        adb.setTitle("Canteen Management System");
                        adb.setMessage(om.orderlist);
                        adb.setNeutralButton("OK", null);
                        adb.show();
                    }
                });
                ////////////

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}