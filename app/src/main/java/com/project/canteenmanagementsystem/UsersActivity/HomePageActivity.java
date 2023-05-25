package com.project.canteenmanagementsystem.UsersActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.project.canteenmanagementsystem.LoginActivity;
import com.project.canteenmanagementsystem.Models.AddProducts;
import com.project.canteenmanagementsystem.R;
import com.project.canteenmanagementsystem.SplashScreenActivity;
import com.project.canteenmanagementsystem.UsersActivity.Adaptor.AddProductArrayAdaptor;
import com.project.canteenmanagementsystem.UsersActivity.Model.CartInsertion;
import com.project.canteenmanagementsystem.UsersActivity.Model.CartModel;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    ListView listView_product;
    List<AddProducts> productlist;
    DatabaseReference dbref;
    Button btn_viewcart;
    AddProductArrayAdaptor addProductArrayAdaptor;
    static CartInsertion cartInsertion;
    static CartModel product;
    static List<CartModel> viewcart1;
    ProgressDialog pd;
    TextView tv_grandtotal;
    TextView tv_no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_home_page);

        // find view by id's
        listView_product = findViewById(R.id.listview_todays_menu);
        btn_viewcart = findViewById(R.id.button_viewcart);
        tv_grandtotal = findViewById(R.id.tv_total_price);
        tv_no_data = findViewById(R.id.textview_no_data);
        ////////////////////

        viewcart1 = new ArrayList<>();
        CartInsertion.cartProducts = new ArrayList<CartModel>();

        // database ref
        dbref = FirebaseDatabase.getInstance().getReference("Product").child("TodaysMenu");
        productlist = new ArrayList<>();
        ///////////

        // progress dialog set-up & show
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setTitle("Canteen Management System");
        pd.setMessage("Please Wait ");
        pd.show();
        //////////////////

        try {
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    productlist.clear();
                    for (DataSnapshot getorder : dataSnapshot.getChildren()) {
                        AddProducts ap = getorder.getValue(AddProducts.class);
                        productlist.add(ap);
                    }

                    // when list is empty no-data will appear
                    if (!(productlist.size() > 0)) {
                        tv_no_data.setVisibility(View.VISIBLE);
                        pd.dismiss();
                    } else {
                        tv_no_data.setVisibility(View.INVISIBLE);
                        pd.dismiss();
                    }
                    /////

                    // list set-up with adaptor
                    addProductArrayAdaptor = new AddProductArrayAdaptor(HomePageActivity.this,
                            R.layout.listlayout_addproduct, (ArrayList<AddProducts>) productlist);
                    listView_product.setAdapter(addProductArrayAdaptor);
                    pd.dismiss();
                    /////////////
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
        }

        // viewcart button method
        btn_viewcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CartInsertion.cartProducts.size() > 0) {
                    startActivity(new Intent(getApplicationContext(), ViewCart.class));
                } else {
                    Toast.makeText(HomePageActivity.this, "Please select any products.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /////////
    }

    // all method works on fab button clicking start ///
    public void drinks(View view) {
        Intent i = new Intent(getApplicationContext(), ProductSelectionActivity.class);
        i.putExtra("category", "Drinks");
        startActivity(i);
    }

    public void dessert(View view) {
        Intent i = new Intent(getApplicationContext(), ProductSelectionActivity.class);
        i.putExtra("category", "Dessert");
        startActivity(i);
    }

    public void rice(View view) {
        Intent i = new Intent(getApplicationContext(), ProductSelectionActivity.class);
        i.putExtra("category", "Rice");
        startActivity(i);
    }

    public void noodles(View view) {
        Intent i = new Intent(getApplicationContext(), ProductSelectionActivity.class);
        i.putExtra("category", "Noodles");
        startActivity(i);
    }

    public void thali(View view) {
        Intent i = new Intent(getApplicationContext(), ProductSelectionActivity.class);
        i.putExtra("category", "Thali");
        startActivity(i);
    }

    public void lunch(View view) {
        Intent i = new Intent(getApplicationContext(), ProductSelectionActivity.class);
        i.putExtra("category", "Lunch");
        startActivity(i);
    }

    public void breakfast(View view) {
        Intent i = new Intent(getApplicationContext(), ProductSelectionActivity.class);
        i.putExtra("category", "Breakfast");
        startActivity(i);
    }
    // all method works on fab button clicking end ///

    // create users_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.users_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // when clicking users_menu option
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        switch (i) {
            case R.id.cart_menu:
                if (CartInsertion.cartProducts.size() > 0) {
                    startActivity(new Intent(getApplicationContext(), ViewCart.class));
                } else {
                    Toast.makeText(HomePageActivity.this, "Please select any products.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.our_orders:
                startActivity(new Intent(getApplicationContext(), OurOrderActivity.class));
                break;

            case R.id.logout_menu:
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setTitle("Close Application.");
                adb.setMessage("Do you want to logout now...?");
                adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.clear();
                        edit.commit();
                        Toast.makeText(HomePageActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomePageActivity.this, LoginActivity.class));
                        finish();
                    }
                });
                adb.setNegativeButton("No", null);
                adb.show();
                break;
        }
        return true;
    }

    // when user click back button
    @Override
    public void onBackPressed() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Close Application.");
        adb.setMessage("Do you want to exit ?");
        adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                intent.putExtra("exit_code", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        adb.setNegativeButton("No", null);
        adb.show();
    }
    ////////////
}