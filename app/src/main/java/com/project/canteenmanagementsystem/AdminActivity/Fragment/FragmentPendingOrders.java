package com.project.canteenmanagementsystem.AdminActivity.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.project.canteenmanagementsystem.AdminActivity.Adaptor.OrderListAdaptor;
import com.project.canteenmanagementsystem.AdminActivity.UpdateDeleteOrderAcitivity;
import com.project.canteenmanagementsystem.R;
import com.project.canteenmanagementsystem.UsersActivity.Model.OrdersModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPendingOrders extends Fragment {

    ListView pending_orderlist;
    DatabaseReference dbref;
    List<OrdersModel> pendinglist;
    TextView tv_nodata;
    ProgressDialog progressDialog;
    int order_otp;

    public FragmentPendingOrders() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_orders, container, false);
        //////

        // find view by id's
        pending_orderlist = view.findViewById(R.id.listview_pending_orders);
        tv_nodata = view.findViewById(R.id.tv_product_nodata);
        progressDialog = new ProgressDialog(getActivity());
        ///////

        pendinglist = new ArrayList<>();

        // database ref
        dbref = FirebaseDatabase.getInstance().getReference("Orders");
        //////

        // progress dialog set-up
        progressDialog.setTitle("Canteen Management");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ////////

        try {
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    pendinglist.clear();
                    for (DataSnapshot getorder : dataSnapshot.getChildren()) {
                        OrdersModel om = getorder.getValue(OrdersModel.class);

                        if (om.getOrder_complete().equals("")) {
                            pendinglist.add(om);
                        }
                        progressDialog.dismiss();
                    }

                    // when list is empty it will appear
                    if (!(pendinglist.size() > 0)) {
                        tv_nodata.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    } else {
                        tv_nodata.setVisibility(View.INVISIBLE);
                        progressDialog.dismiss();
                    }
                    ///////

                    // list set-up with adaptor
                    OrderListAdaptor adapter = new OrderListAdaptor(getActivity(), pendinglist);
                    pending_orderlist.setAdapter(adapter);
                    ///////

                    // admin click's on single order
                    pending_orderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent i = new Intent(getActivity(), UpdateDeleteOrderAcitivity.class);
                            OrdersModel om = (OrdersModel) parent.getItemAtPosition(position);

                            order_otp = om.order_otp;
                            i.putExtra("id", om.getId());
                            i.putExtra("name", om.getName());
                            i.putExtra("mobile", om.getMobile());
                            i.putExtra("emailid", om.getEmailid());
                            i.putExtra("address", om.getAddress());
                            i.putExtra("orderid", om.getOrder_id());
                            i.putExtra("order_otp", order_otp);
                            i.putExtra("orderlist", om.getOrderlist());
                            i.putExtra("grandtotal", om.getGrandtotal());
                            i.putExtra("accept_prepare", om.getAccept_prepare());
                            i.putExtra("order_complete", om.getOrder_complete());
                            i.putExtra("order_date_time", om.getOrder_date_time());
                            i.putExtra("fromCompleteorder", "no");
                            startActivity(i);
                        }
                    });
                    ////////
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