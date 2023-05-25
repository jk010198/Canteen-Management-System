package com.project.canteenmanagementsystem.AdminActivity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.canteenmanagementsystem.R;

public class UpdateDeleteOrderAcitivity extends AppCompatActivity {

    EditText et_name, et_mobile, et_email, et_address, et_orderid, et_orderlist, et_grandtotal, et_order_accept_prepare, et_order_complete,
            et_order_date_time, et_order_otp;
    String id, fromdeliveryfrag;
    Button btn_accept_order, btn_reject_order, btn_complete_order, btn_otp_verify;
    DatabaseReference dbref;
    FirebaseDatabase dbref_delete;
    String order_otp;
    TextView tv_order_completed_status, tv_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_update_delete_order_acitivity);

        // find view by id's
        btn_accept_order = findViewById(R.id.button_acceptorder);
        btn_reject_order = findViewById(R.id.button_rejectorder);
        btn_complete_order = findViewById(R.id.button_ordercomplete);
        btn_otp_verify = findViewById(R.id.button_otpverify);
        et_name = findViewById(R.id.edittext_name);
        et_mobile = findViewById(R.id.edittext_mobile);
        et_email = findViewById(R.id.edittext_email);
        et_address = findViewById(R.id.edittext_address);
        et_orderid = findViewById(R.id.edittext_orderid);
        et_orderlist = findViewById(R.id.edittext_orderlist);
        et_grandtotal = findViewById(R.id.edittext_grandtotal);
        et_order_accept_prepare = findViewById(R.id.edittext_order_status);
        et_order_complete = findViewById(R.id.edittext_order_completed_status);
        et_order_date_time = findViewById(R.id.edittext_order_date_time);
        et_order_otp = findViewById(R.id.edittext_order_otp);

        tv_order_completed_status = findViewById(R.id.tv_order_completed_status);
        tv_otp = findViewById(R.id.tv_order_otp);
        /////////////

        // getting data from intent and set-up on edittext which not edit able
        id = getIntent().getStringExtra("id");
        et_name.setText(getIntent().getStringExtra("name"));
        et_mobile.setText(getIntent().getStringExtra("mobile"));
        et_email.setText(getIntent().getStringExtra("emailid"));
        et_address.setText("\n" + getIntent().getStringExtra("address") + "\n");
        et_orderid.setText(getIntent().getStringExtra("orderid"));
        et_orderlist.setText("\n" + getIntent().getStringExtra("orderlist"));
        et_grandtotal.setText(getIntent().getStringExtra("grandtotal"));
        et_order_accept_prepare.setText(getIntent().getStringExtra("accept_prepare"));
        et_order_complete.setText(getIntent().getStringExtra("order_complete"));
        et_order_date_time.setText(getIntent().getStringExtra("order_date_time"));
        fromdeliveryfrag = getIntent().getStringExtra("fromCompleteorder");
        order_otp = getIntent().getStringExtra("order_otp");
        ///////////////

        // logic for hiding button's is some cases  start /////
        if (et_order_accept_prepare.getText().toString().isEmpty()) {
            btn_complete_order.setVisibility(View.GONE);
            btn_otp_verify.setVisibility(View.GONE);

            tv_order_completed_status.setVisibility(View.GONE);
            et_order_complete.setVisibility(View.GONE);
            tv_otp.setVisibility(View.GONE);
            et_order_otp.setVisibility(View.GONE);
        }

        if (et_order_accept_prepare.getText().toString().equals("Accept order")) {
            btn_accept_order.setVisibility(View.GONE);
            btn_reject_order.setVisibility(View.GONE);
            btn_otp_verify.setVisibility(View.GONE);

            tv_otp.setVisibility(View.GONE);
            et_order_otp.setVisibility(View.GONE);
        }

        if (et_order_accept_prepare.getText().toString().equals("Reject order")) {
            btn_accept_order.setVisibility(View.GONE);
            btn_reject_order.setVisibility(View.GONE);
            btn_complete_order.setVisibility(View.GONE);
            btn_otp_verify.setVisibility(View.GONE);
        }

        if (et_order_complete.getText().toString().equals("Completed order")) {
            btn_accept_order.setVisibility(View.GONE);
            btn_reject_order.setVisibility(View.GONE);
            btn_complete_order.setVisibility(View.GONE);

            tv_otp.setVisibility(View.VISIBLE);
            et_order_otp.setVisibility(View.VISIBLE);
            btn_otp_verify.setVisibility(View.VISIBLE);
        }

        if (fromdeliveryfrag.equals("yes")) {
            btn_accept_order.setVisibility(View.GONE);
            btn_reject_order.setVisibility(View.GONE);
            btn_complete_order.setVisibility(View.GONE);
        }
        // logic for hiding button's is some cases  end /////

        // database ref
        dbref = FirebaseDatabase.getInstance().getReference().child("Orders").child(id);
        //////
    }

    // when clicked user get notice that "your order is accepted and food is being prepared"
    public void acceptOrder(View view) {
        et_order_accept_prepare.setText("Accept order");
        dbref.child("accept_prepare").setValue("Accept order").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UpdateDeleteOrderAcitivity.this, "Updated", Toast.LENGTH_SHORT).show();
                //onBackPressed();
            }
        });
    }
    ///////////////

    // when clicked user get notice that "your order is rejeected"
    public void rejectOrder(View view) {
        et_order_accept_prepare.setText("Reject order");
        dbref.child("accept_prepare").setValue("Reject order").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UpdateDeleteOrderAcitivity.this, "Updated", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
    /////////////

    // update order-complete status
    public void orderComplete(View view) {
        et_order_complete.setText("Completed order");
        dbref.child("order_complete").setValue("Completed order").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UpdateDeleteOrderAcitivity.this, "Updated", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
    /////////////

    // verify otp
    public void verifyOrderOtp(View view) {

        if (et_order_otp.getText().toString().equals(order_otp)) {

            et_order_complete.setText("Completed order and Otp");
            dbref.child("order_complete").setValue("Completed order and Otp").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(UpdateDeleteOrderAcitivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        } else {
            Toast.makeText(this, "OTP not match, Please check your OTP.", Toast.LENGTH_SHORT).show();
        }
    }
    ////////////

    // delete order method
    public void deleteOrder(View view) {
        AlertDialog.Builder adb = new AlertDialog.Builder(UpdateDeleteOrderAcitivity.this);
        adb.setTitle("Canteen Management System");
        adb.setMessage("Do you want to delete " + et_name.getText().toString() + "'s complaint ?");
        adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbref_delete = FirebaseDatabase.getInstance();
                dbref_delete.getReference("Orders").child(id).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(UpdateDeleteOrderAcitivity.this, "Deleted...", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                });
            }
        });
        adb.setNegativeButton("cancel", null);
        adb.show();
    }

    //////////////
}