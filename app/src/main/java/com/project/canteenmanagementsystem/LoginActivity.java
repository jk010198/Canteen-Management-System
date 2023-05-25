package com.project.canteenmanagementsystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.canteenmanagementsystem.AdminActivity.AdminHomeActivity;
import com.project.canteenmanagementsystem.Models.RegistrationModel;
import com.project.canteenmanagementsystem.UsersActivity.HomePageActivity;
import com.project.canteenmanagementsystem.UsersActivity.SignupActivity;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LoginActivity extends AppCompatActivity {

    EditText edittext_emailid, edittext_password;
    SharedPreferences sp;
    FirebaseAuth auth;
    ProgressDialog pd;
    String emailid, password, role;
    DatabaseReference dbref;
    String sp_role;

    final int PERMISSION_REQUEST_CODE = 1212;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // find view by id
        edittext_emailid = findViewById(R.id.edittext_emailid);
        edittext_password = findViewById(R.id.edittext_password);
        //////

        // database ref
        auth = FirebaseAuth.getInstance();
        dbref = FirebaseDatabase.getInstance().getReference("Customer");
        ////

        // for checking user session
        sp = getSharedPreferences("login", MODE_PRIVATE);
        //////

        if (sp.contains("username") && sp.contains("password")) {
            String value = sp.getString("role", "defaultValue");

            if (value.equals("A")) {
                //FirebaseMessaging.getInstance().subscribeToTopic("User");
                startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                finish();
            } else {
                //FirebaseMessaging.getInstance().subscribeToTopic("User");
                startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
                finish();
            }
        }

        requestPermission();
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    public void signIn(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }

    public void loginIn(View view) {
        emailid = edittext_emailid.getText().toString();
        password = edittext_password.getText().toString();

        pd = new ProgressDialog(this);
        pd.setTitle("Login");
        pd.setMessage("Please Wait");
        pd.setCancelable(false);
        pd.show();

        if (emailid.isEmpty() || password.isEmpty()) {
            pd.dismiss();
            Toast.makeText(this, "Please Fill the Fields", Toast.LENGTH_SHORT).show();
        } else {

            dbref.orderByChild("email").equalTo(emailid)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                RegistrationModel rm = ds.getValue(RegistrationModel.class);
                                role = rm.role;
                                loginMainMethod();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
        }
    }

    public void loginMainMethod() {
        if (!(emailid.isEmpty() || password.isEmpty())) {

            auth.signInWithEmailAndPassword(emailid, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("username", emailid);
                        edit.putString("password", password);
                        edit.putString("role", role);
                        edit.commit();
                        // FirebaseMessaging.getInstance().subscribeToTopic("User");
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        if (role.equals("A")) {
                            Intent i = new Intent(getApplicationContext(), AdminHomeActivity.class);
                            i.putExtra("current_email", emailid);
                            i.putExtra("role", role);
                            pd.dismiss();
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(getApplicationContext(), HomePageActivity.class);
                            i.putExtra("current_email", emailid);
                            i.putExtra("role", role);
                            pd.dismiss();
                            startActivity(i);
                            finish();
                        }
                    } else {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, "not sign", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            pd.dismiss();
            Toast.makeText(this, "Please Fill the Fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void forgetPassword(View view) {
        startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean externalstorageread_Accepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean externalstoragewrite_Accepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (externalstorageread_Accepted && externalstoragewrite_Accepted) {
                    } else {
                        Toast.makeText(this, "Please Allow All thePermissions.", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                                requestPermissions(new String[]{READ_PHONE_STATE, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                                        PERMISSION_REQUEST_CODE);
                                return;
                            }
                        }
                    }
                    if (externalstorageread_Accepted && externalstoragewrite_Accepted) {
                    } else {
                        Toast.makeText(this, "Please Allow All thePermissions.", Toast.LENGTH_SHORT).show();
                        System.exit(0);
                        finish();
                    }
                }
                break;
        }
    }

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
}