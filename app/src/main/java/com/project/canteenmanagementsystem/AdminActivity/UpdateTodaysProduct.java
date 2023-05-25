package com.project.canteenmanagementsystem.AdminActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.canteenmanagementsystem.R;

import java.io.IOException;
import java.util.UUID;

public class UpdateTodaysProduct extends AppCompatActivity {

    DatabaseReference dbref, dbref_delete;
    String id, name, price, category, select_product_category, product_imgurl, product_activity;
    EditText edittext_update_productname, edittext_update_productprice;
    Spinner food_category;
    String product_category[] = {"---Select Category---", "Breakfast", "Lunch", "Thali", "Noodles", "Rice", "Dessert", "Drinks"};
    int category_positon;

    int PICK_IMG = 1213, URL_CODE = 0;
    StorageReference storageReference, ref;
    Uri filePath;
    Uri uri;
    Uri download_uri;
    String url;
    FirebaseStorage storage;
    ProgressDialog progressDialog;
    ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_update_product);

        // find view by id's
        edittext_update_productname = findViewById(R.id.edittext_updateproductName);
        edittext_update_productprice = findViewById(R.id.edittext_updateproductPrice);
        food_category = findViewById(R.id.spinner_updateproductCategory);
        imageview = findViewById(R.id.updateproduct_imageview);
        progressDialog = new ProgressDialog(this);
        //////////

        // database ref
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        ////////////

        // data retrive from intent
        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        id = getIntent().getStringExtra("id");
        category = getIntent().getStringExtra("product_category");
        product_imgurl = getIntent().getStringExtra("img_url");
        product_activity = getIntent().getStringExtra("product_activity");
        ///////////

        //data set on edit_text
        edittext_update_productname.setText(getIntent().getStringExtra("name"));
        edittext_update_productprice.setText(getIntent().getStringExtra("price"));
        uri = Uri.parse(product_imgurl);
        /////////

        // progress dialog set-up
        progressDialog.setTitle("Downloading...");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ///////////

        // image set-up with the help of glide
        Glide
                .with(this)
                .load(product_imgurl)
                .into(imageview);
        progressDialog.dismiss();
        //////////////

        // when image-view clicked
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMG);
            }
        });
        ///////////////

        // spinner set-up
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, product_category);
        food_category.setAdapter(adaptor);
        food_category.setDropDownVerticalOffset(110);
        food_category.getGravity();

        food_category.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) food_category.getSelectedView()).setTextColor(Color.BLACK);
                ((TextView) food_category.getSelectedView()).setTextSize(20);
            }
        });
        //////////

        // as per value fetch from database some cases are here for set the food category
        switch (category) {
            case "---Select Category---":
                category_positon = 0;
                break;

            case "Breakfast":
                category_positon = 1;
                break;

            case "Lunch":
                category_positon = 2;
                break;

            case "Thali":
                category_positon = 3;
                break;

            case "Noodles":
                category_positon = 4;
                break;

            case "Rice":
                category_positon = 5;
                break;

            case "Dessert":
                category_positon = 6;
                break;

            case "Drinks":
                category_positon = 7;
                break;
        }
        /////////

        food_category.setSelection(category_positon);

        // when clicked on spinner value
        food_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                select_product_category = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        /////////

        //config firebase
        if (product_activity.equals("yes")) {
            dbref = FirebaseDatabase.getInstance().getReference().child("Product").child(category).child(id);
            dbref_delete = FirebaseDatabase.getInstance().getReference("Product");
        } else {
            dbref = FirebaseDatabase.getInstance().getReference().child("Product").child("TodaysMenu").child(id);
            dbref_delete = FirebaseDatabase.getInstance().getReference("Product");
        }
        /////
    }

    // delete product
    public void delete(View view) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Canteen Management System");
        adb.setMessage("Are you sure that you want to delete"+ edittext_update_productname.getText().toString()+" item." );
        adb.setNegativeButton("NO", null);
        adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (product_activity.equals("yes")) {
                    dbref_delete.child(category).child(id).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            imageDelete();
                            Toast.makeText(UpdateTodaysProduct.this, "Deleted...", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    dbref_delete.child("TodaysMenu").child(id).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            imageDelete();
                            Toast.makeText(UpdateTodaysProduct.this, "Deleted...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


                onBackPressed();
            }
        });
        adb.show();
    }
    ////////////

    // update image
    public void updateImgData(View view) {

        if (URL_CODE == 0) {
            updateData();
        } else {

            if (filePath != null) {
                final ProgressDialog progressDialog = new ProgressDialog(UpdateTodaysProduct.this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                URL_CODE = 1212;
                                progressDialog.dismiss();
                                download_uri = taskSnapshot.getDownloadUrl();
                                imageDelete();
                                updateData();
                                Toast.makeText(UpdateTodaysProduct.this, "Upload successfully.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(UpdateTodaysProduct.this, "Upload unsuccessfully.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });
            } else {
                Toast.makeText(UpdateTodaysProduct.this, "Atleast One Image Is Compalsury", Toast.LENGTH_SHORT).show();
            }
        }
    }
    ////////

    // update data
    public void updateData() {

        if (URL_CODE == 1212) {
            url = String.valueOf(download_uri);
        } else {
            url = product_imgurl;
        }

        if (edittext_update_productname.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Product Name.", Toast.LENGTH_SHORT).show();
        } else if (edittext_update_productprice.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Product Price.", Toast.LENGTH_SHORT).show();
        } else if (select_product_category.equals("---Select Category---")) {
            Toast.makeText(this, "please select category", Toast.LENGTH_SHORT).show();
        } else {

            dbref.child("name").setValue(edittext_update_productname.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                Toast.makeText(UpdateTodaysProduct.this, "Updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UpdateTodaysProduct.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            dbref.child("price").setValue(edittext_update_productprice.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                Toast.makeText(UpdateTodaysProduct.this, "Updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UpdateTodaysProduct.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            dbref.child("img_url").setValue(url)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(UpdateTodaysProduct.this, "Updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UpdateTodaysProduct.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            dbref.child("food_category").setValue(select_product_category)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                Toast.makeText(UpdateTodaysProduct.this, "Updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UpdateTodaysProduct.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            onBackPressed();
        }
    }
    ///////

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            URL_CODE = 1212;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // when update image old image delete from data base code
    public void imageDelete() {
        StorageReference del = storage.getReferenceFromUrl(product_imgurl);

        del.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(UpdateTodaysProduct.this, "zala", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(UpdateTodaysProduct.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //////////
}