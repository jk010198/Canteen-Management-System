package com.project.canteenmanagementsystem.AdminActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.project.canteenmanagementsystem.LoginActivity;
import com.project.canteenmanagementsystem.R;
import com.project.canteenmanagementsystem.SplashScreenActivity;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_admin_home);
    }

    //todays menu
    public void todayMenu(View view) {
        startActivity(new Intent(AdminHomeActivity.this, TodayMenuActivity.class));
    }

    //add product
    public void addProduct(View view) {
        startActivity(new Intent(AdminHomeActivity.this, AddProductActivity.class));
    }

    //view orders
    public void orders(View view) {
        startActivity(new Intent(AdminHomeActivity.this, ViewOrdersActivity.class));
    }

    // when user click back button
    @Override
    public void onBackPressed() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Canteen Management System");
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

    // create users_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // when clicking users_menu option
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        switch (i) {
            case R.id.logout_menu:
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setTitle("Logout.");
                adb.setMessage("Do you want to logout now ?");
                adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.clear();
                        edit.commit();
                        Toast.makeText(AdminHomeActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminHomeActivity.this, LoginActivity.class));
                        finish();

                    }
                });
                adb.setNegativeButton("No", null);
                adb.show();
                break;
        }
        return true;
    }
}