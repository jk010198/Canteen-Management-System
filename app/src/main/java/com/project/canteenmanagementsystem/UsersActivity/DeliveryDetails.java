package com.project.canteenmanagementsystem.UsersActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.canteenmanagementsystem.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeliveryDetails extends AppCompatActivity {

    EditText et_name;
    EditText et_emailid;
    EditText et_mobileno;
    EditText et_address;
    String valitdation_name, valitdation_emailid, valitdation_mobileno, valitdation_address;
    SharedPreferences sharedPreferences;
    String sharedpref_email;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_delivery_details);

        // find view by id
        et_name = findViewById(R.id.delivery_edittext_name);
        et_emailid = findViewById(R.id.delivery_edittext_emailid);
        et_mobileno = findViewById(R.id.delivery_edittext_mobileno);
        et_address = findViewById(R.id.delivery_edittext_address);
        progressDialog = new ProgressDialog(this);

        // get data from shared pref.
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        sharedpref_email = sharedPreferences.getString("username", "");
        et_emailid.setText(sharedpref_email);
    }

    // clicking on payment
    public void payment(View view) {
        progressDialog.setTitle("Canteen Management");
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);

        valitdation_name = et_name.getText().toString();
        valitdation_emailid = et_emailid.getText().toString();
        valitdation_mobileno = et_mobileno.getText().toString();
        valitdation_address = et_address.getText().toString();

        String Validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";

        // in-build method for email-id checking
        Matcher matcher = Pattern.compile(Validemail).matcher(valitdation_emailid);

        if (valitdation_name.isEmpty()) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (!(matcher.matches())) {
            Toast.makeText(this, "Please enter valid emailid.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (valitdation_mobileno.length() == 0 || valitdation_mobileno.length() < 10) {
            Toast.makeText(this, "Please enter valid mobile number.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (valitdation_address.isEmpty()) {
            Toast.makeText(this, "Please enter address.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            Intent intent = new Intent(DeliveryDetails.this, PaymentGatewayActivity.class);
            intent.putExtra("name", valitdation_name);
            intent.putExtra("emailid", valitdation_emailid);
            intent.putExtra("mobile", valitdation_mobileno);
            intent.putExtra("address", valitdation_address);
            startActivity(intent);
            progressDialog.dismiss();
        }
    }
}