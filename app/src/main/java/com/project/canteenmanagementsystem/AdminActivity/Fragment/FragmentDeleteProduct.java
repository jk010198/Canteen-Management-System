package com.project.canteenmanagementsystem.AdminActivity.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.canteenmanagementsystem.AdminActivity.Adaptor.ProductListAdaptor;
import com.project.canteenmanagementsystem.AdminActivity.DeleteUpdateProductActivity;
import com.project.canteenmanagementsystem.AdminActivity.UpdateTodaysProduct;
import com.project.canteenmanagementsystem.Models.AddProducts;
import com.project.canteenmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDeleteProduct extends Fragment {

    Button button_breakfast, button_lunch, button_thali, button_noodles, button_rice, button_dessert, button_drinks;

    public FragmentDeleteProduct() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_delete_product, container, false);

        // find view by id's
        button_breakfast = view.findViewById(R.id.button_breakfast);
        button_lunch = view.findViewById(R.id.button_lunch);
        button_thali = view.findViewById(R.id.button_thali);
        button_noodles = view.findViewById(R.id.button_noodles);
        button_rice = view.findViewById(R.id.button_rice);
        button_dessert = view.findViewById(R.id.button_dessert);
        button_drinks = view.findViewById(R.id.button_drinks);
        /////////

        // when admin click's on button it will navigate to another page... same in all button's method
        button_breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DeleteUpdateProductActivity.class);
                i.putExtra("category",button_breakfast.getText().toString());
                startActivity(i);
            }
        });

        button_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DeleteUpdateProductActivity.class);
                i.putExtra("category",button_lunch.getText().toString());
                startActivity(i);
            }
        });

        button_thali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DeleteUpdateProductActivity.class);
                i.putExtra("category",button_thali.getText().toString());
                startActivity(i);
            }
        });

        button_noodles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DeleteUpdateProductActivity.class);
                i.putExtra("category",button_noodles.getText().toString());
                startActivity(i);
            }
        });

        button_rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DeleteUpdateProductActivity.class);
                i.putExtra("category",button_rice.getText().toString());
                startActivity(i);
            }
        });

        button_dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DeleteUpdateProductActivity.class);
                i.putExtra("category",button_dessert.getText().toString());
                startActivity(i);
            }
        });

        button_drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DeleteUpdateProductActivity.class);
                i.putExtra("category",button_drinks.getText().toString());
                startActivity(i);
            }
        });
        //////////////

        return view;
    }
}