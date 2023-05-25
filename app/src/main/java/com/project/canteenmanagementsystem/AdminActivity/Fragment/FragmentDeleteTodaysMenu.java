package com.project.canteenmanagementsystem.AdminActivity.Fragment;


import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.canteenmanagementsystem.AdminActivity.Adaptor.ProductListAdaptor;
import com.project.canteenmanagementsystem.AdminActivity.UpdateTodaysProduct;
import com.project.canteenmanagementsystem.Models.AddProducts;
import com.project.canteenmanagementsystem.R;

import java.util.ArrayList;
import java.util.List;


public class FragmentDeleteTodaysMenu extends Fragment {

    ListView todaysMenu;
    DatabaseReference dbref, dbref_delete;
    List<AddProducts> productlist;
    TextView tv_nodata;
    ProgressDialog progressDialog;

    public FragmentDeleteTodaysMenu() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delete_todays_menu, container, false);
        /////

        // find view by id's
        todaysMenu = view.findViewById(R.id.listview_todays_menu);
        tv_nodata = view.findViewById(R.id.tv_product_nodata);
        progressDialog = new ProgressDialog(getActivity());
        /////

        productlist = new ArrayList<>();

        // database ref
        dbref = FirebaseDatabase.getInstance().getReference("Product").child("TodaysMenu");
        dbref_delete = FirebaseDatabase.getInstance().getReference("Product");
        ///////

        // progress-dialog set-up
        progressDialog.setTitle("Canteen Management");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ////////

        try {
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    productlist.clear();
                    for (DataSnapshot getorder : dataSnapshot.getChildren()) {
                        AddProducts ap = getorder.getValue(AddProducts.class);
                        productlist.add(ap);
                        progressDialog.dismiss();
                    }

                    // when list is empty no-data will appear
                    if (!(productlist.size() > 0)) {
                        tv_nodata.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    } else {
                        tv_nodata.setVisibility(View.INVISIBLE);
                        progressDialog.dismiss();
                    }
                    //////

                    // list set-up with adaptor
                    ProductListAdaptor adapter = new ProductListAdaptor(getActivity(), productlist);
                    todaysMenu.setAdapter(adapter);
                    ////////

                    // admin click on single row
                    todaysMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(getActivity(), UpdateTodaysProduct.class);
                            AddProducts ap = (AddProducts) parent.getItemAtPosition(position);
                            i.putExtra("product_category", ap.getFood_category());
                            i.putExtra("id", ap.getId());
                            i.putExtra("name", ap.getName());
                            i.putExtra("price", ap.getPrice());
                            i.putExtra("img_url", ap.getImg_url());
                            i.putExtra("product_activity", "no");
                            startActivity(i);
                        }
                    });
                    ///////
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), "" + e, Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}