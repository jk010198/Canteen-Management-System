package com.project.canteenmanagementsystem.UsersActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.canteenmanagementsystem.LoginActivity;
import com.project.canteenmanagementsystem.Models.RegistrationModel;
import com.project.canteenmanagementsystem.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    EditText edittext_name, edittext_emailid, edittext_mobileno, edittext_password, edittext_confirm_password;
    FirebaseAuth auth;
    DatabaseReference dbref;
    String id, name, emailid, mobileno, role = "C", password, confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_signup);

        // find view by ids
        edittext_name = findViewById(R.id.edittext_name);
        edittext_emailid = findViewById(R.id.edittext_emailid);
        edittext_mobileno = findViewById(R.id.edittext_mobileno);
        edittext_password = findViewById(R.id.edittext_password);
        edittext_confirm_password = findViewById(R.id.edittext_confirm_password);
        ///////////

        // ref from firebase database
        auth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference("Customer");
        ///////////////
    }

    public void register(View view) {
        id = dbref.push().getKey();
        name = edittext_name.getText().toString();
        emailid = edittext_emailid.getText().toString();
        mobileno = edittext_mobileno.getText().toString();
        password = edittext_password.getText().toString();
        confirm_password = edittext_confirm_password.getText().toString();
        String Validemail = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";

        Matcher matcher = Pattern.compile(Validemail).matcher(emailid);

        if (name.isEmpty()) {
            Toast.makeText(this, "please enter valid data name", Toast.LENGTH_SHORT).show();
        } else if (!(matcher.matches())) {
            Toast.makeText(this, "please enter valid data emailid", Toast.LENGTH_SHORT).show();
        } else if (mobileno.length() == 0 || mobileno.length() < 10) {
            Toast.makeText(this, "please enter valid data mobile no", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty() || confirm_password.isEmpty()) {
            Toast.makeText(this, "please enter valid data password/c_password", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6 || confirm_password.length() < 6) {
            Toast.makeText(this, "please enter valid data password/c_password min 6", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confirm_password)) {
            Toast.makeText(this, "please enter valid data password/c_password not match", Toast.LENGTH_SHORT).show();
        } else {
            auth.fetchProvidersForEmail(emailid).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<ProviderQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                }
            });

            auth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(SignupActivity.this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                RegistrationModel rm = new RegistrationModel(id, name, mobileno, emailid, role, password);
                                dbref.child(id).setValue(rm);
                                Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));

                            } else {
                                Toast.makeText(SignupActivity.this, "unsuccessful. Email has already taken", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}