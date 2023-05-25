package com.project.canteenmanagementsystem.AdminActivity.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.canteenmanagementsystem.Models.AddProducts;
import com.project.canteenmanagementsystem.R;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class FragmentAddTodaysMenu extends Fragment {

    String product_category[] = {"---Select Category---", "Breakfast", "Lunch", "Thali", "Noodles", "Rice", "Dessert", "Drinks"};
    String select_product_category, no_select_product;
    Spinner spinner_productcategory;
    EditText edittext_productName, edittext_productPrice;
    Button button_submitData;
    String id, productName, productPrice;
    DatabaseReference dbref;
    ProgressDialog progressDialog;
    ImageView imageView;

    Uri filePath;
    String download_url;
    Uri download_uri;
    int PICK_IMG = 1212;
    FirebaseStorage storage;
    StorageReference storageReference, ref;

    public FragmentAddTodaysMenu() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_todays_menu, container, false);

        // find view by id's
        edittext_productName = view.findViewById(R.id.edittext_productName);
        edittext_productPrice = view.findViewById(R.id.edittext_productPrice);
        button_submitData = view.findViewById(R.id.button_submitProductData);
        spinner_productcategory = view.findViewById(R.id.spinner_productCategory_todaymenu);
        imageView = view.findViewById(R.id.addpro_imageview);
        progressDialog = new ProgressDialog(getActivity());
        //////

        // database ref
        dbref = FirebaseDatabase.getInstance().getReference("Product");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        ///////////////

        // spinner set-up
        ArrayAdapter adaptor = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, product_category);
        spinner_productcategory.setAdapter(adaptor);
        spinner_productcategory.setDropDownVerticalOffset(110);
        spinner_productcategory.getGravity();

        spinner_productcategory.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((TextView) spinner_productcategory.getSelectedView()).setTextColor(Color.BLACK);
                ((TextView) spinner_productcategory.getSelectedView()).setTextSize(20);
            }
        });

        spinner_productcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                select_product_category = (String) adapterView.getItemAtPosition(position);
                no_select_product = (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ///////////

        // admin click on image button
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMG);
            }
        });
        //////////

        // submitting data
        button_submitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Canteen Management");
                progressDialog.setMessage("Please wait while we added product");
                progressDialog.setCancelable(false);
                progressDialog.show();

                if (filePath != null) {

                    ref = storageReference.child("todayspro_images/" + UUID.randomUUID().toString());

                    ref.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    download_uri = taskSnapshot.getDownloadUrl();
                                    download_url = String.valueOf(download_uri);
                                    uploadProductInfromation(download_url);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Failed ", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "Please select image.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
        return view;
        /////////
    }

    // method call when image download url getting
    public void uploadProductInfromation(String img_url) {

        id = dbref.push().getKey();
        productName = edittext_productName.getText().toString();
        productPrice = edittext_productPrice.getText().toString();

        if (productName.isEmpty()) {
            Toast.makeText(getActivity(), "Enter Product Name", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (productPrice.isEmpty()) {
            Toast.makeText(getActivity(), "Enter Product Price", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (no_select_product.equals("---Select Category---")) {
            Toast.makeText(getActivity(), "please select category", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            AddProducts ap = new AddProducts(id, productName, productPrice, select_product_category, img_url);
            dbref.child("TodaysMenu").child(id).setValue(ap);

            edittext_productName.setText("");
            edittext_productPrice.setText("");
            spinner_productcategory.setSelection(0);
            imageView.setImageResource(0);
            imageView.setBackgroundResource(R.drawable.add_image);
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    ////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMG && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}