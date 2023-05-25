package com.project.canteenmanagementsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText edittext_mobileno, edittext_otp, edittext_password, edittext_confirm_password;
    Button button_getOtp, button_submitData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        edittext_mobileno = findViewById(R.id.edittext_mobileno);
        edittext_otp = findViewById(R.id.edittext_otp);
        edittext_password = findViewById(R.id.edittext_password);
        edittext_confirm_password = findViewById(R.id.edittext_confirm_password);
        button_getOtp = findViewById(R.id.button_getotp);
        button_submitData = findViewById(R.id.button_submitData);

        /*
        edittext_otp.setEnabled(false);
        edittext_password.setEnabled(false);
        edittext_confirm_password.setEnabled(false);
        button_submitData.setEnabled(false);
         */
    }

    public void getOtp(View view) {
        /*
        int mobileNo = edittext_mobileno.getText().toString().length();
        if (mobileNo == 0 || mobileNo < 10){
            Toast.makeText(this, "Please enter valid data mobileno", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "correct", Toast.LENGTH_SHORT).show();
            edittext_mobileno.setEnabled(false);
            button_getOtp.setEnabled(false);

            edittext_otp.setEnabled(true);
            edittext_password.setEnabled(true);
            edittext_confirm_password.setEnabled(true);
            button_submitData.setEnabled(true);
        }
         */
    }

    public void submit(View view) {
        /*
        int otp = edittext_otp.getText().toString().length();
        String password = edittext_password.getText().toString();
        String confirm_password = edittext_confirm_password.getText().toString();
        
        if (otp == 0 || otp < 6){
            Toast.makeText(this, "please enter valid data otp", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty() || confirm_password.isEmpty()){
            Toast.makeText(this, "please enter valid data password/c_password", Toast.LENGTH_SHORT).show();
        } else if(password.length() < 6 || confirm_password.length() < 6){
            Toast.makeText(this, "please enter valid data password/c_password min 6", Toast.LENGTH_SHORT).show();
        } else if(!password.equals(confirm_password)){
            Toast.makeText(this, "please enter valid data password/c_password not match", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "correct", Toast.LENGTH_SHORT).show();
        }
         */
    }
}
