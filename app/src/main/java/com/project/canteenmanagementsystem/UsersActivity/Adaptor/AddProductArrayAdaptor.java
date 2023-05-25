package com.project.canteenmanagementsystem.UsersActivity.Adaptor;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.project.canteenmanagementsystem.Models.AddProducts;
import com.project.canteenmanagementsystem.R;
import com.project.canteenmanagementsystem.UsersActivity.Model.CartInsertion;
import com.project.canteenmanagementsystem.UsersActivity.Model.CartModel;
import com.project.canteenmanagementsystem.UsersActivity.ViewCart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AddProductArrayAdaptor extends ArrayAdapter {

    Activity context;
    ArrayList<AddProducts> ap;
    ImageButton ib_add, ib_remove;
    public static TextView tv_count, tv_total_price;
    static int count, total;
    Button view_cart;
    String pro_name = "";
    HashMap<Integer, View> allRows = new HashMap<Integer, View>();
    CartModel cartModel;
    Uri uri;

    public AddProductArrayAdaptor(@NonNull Activity context, int resource, @NonNull ArrayList<AddProducts> product) {
        super(context, resource, product);
        this.context = context;
        ap = product;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        Log.d("CART", "Building view for :" + position);
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.listlayout_addproduct, null, true);
        allRows.put(position, rowView);
        Log.d("CART", "Building view for :" + rowView);

        // find view by id's
        ImageView imageView = rowView.findViewById(R.id.productlist_imageview);
        TextView textview_foodcategory = (TextView) rowView.findViewById(R.id.tv_productcategory);
        TextView textview_name = (TextView) rowView.findViewById(R.id.textview_foodname);
        TextView textview_price = (TextView) rowView.findViewById(R.id.tv_product_price);
        ib_add = rowView.findViewById(R.id.ib_add);
        ib_remove = rowView.findViewById(R.id.ib_remove);
        tv_count = rowView.findViewById(R.id.tv_count);
        tv_total_price = context.findViewById(R.id.tv_total_price);
        view_cart = context.findViewById(R.id.button_viewcart);

        //Setting counts from Cart
        for (CartModel product : CartInsertion.cartProducts) {
            tv_total_price.setText(CartInsertion.cartprice + "");
            if (product.id.equals(ap.get(position).id)) {
                tv_count.setText(product.quantity + "");
            }
        }

        //move to view cart section
        view_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductsToCart();
                if (CartInsertion.cartProducts.size() > 0) {
                    context.startActivity(new Intent(getContext(), ViewCart.class));
                } else {
                    Toast.makeText(context, "Please Select any products.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //add qty
        ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count = Integer.parseInt(((TextView) rowView.findViewById(R.id.tv_count)).getText().toString());
                count++;

                int qty = Integer.parseInt(ap.get(position).price);
                total = total + qty;
                tv_total_price.setText(total + "");
                //Toast.makeText(context, "" + total, Toast.LENGTH_SHORT).show();

                ((TextView) rowView.findViewById(R.id.tv_count)).setText(count + "");

                addProductsToCart();
            }
        });

        // remove qty
        ib_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = Integer.parseInt(((TextView) rowView.findViewById(R.id.tv_count)).getText().toString());
                if (!(count == 0)) {
                    count--;
                    int qty = Integer.parseInt(ap.get(position).price);
                    total = total - qty;
                    tv_total_price.setText(total + "");

                    ((TextView) rowView.findViewById(R.id.tv_count)).setText(count + "");
                } else {
                    Toast.makeText(context, "Empty cart", Toast.LENGTH_SHORT).show();
                }
                addProductsToCart();
            }
        });

        // data set-up
        textview_foodcategory.setText("Category:- " + ap.get(position).food_category);
        textview_name.setText(ap.get(position).name);
        textview_price.setText("₹ " + ap.get(position).price);
        uri = Uri.parse(ap.get(position).img_url);

        Glide
                .with(context)
                .load(uri)
                .into(imageView);

        return rowView;
    }

    void addProductsToCart() {

        int p = 0;
        List<View> rowsList = new ArrayList<>(allRows.values());

        for (View view : rowsList) {
            Log.d("CART", "P= " + p);
            String pid = ap.get(p).id;

            int count = Integer.parseInt(((TextView) view.findViewById(R.id.tv_count)).getText().toString());
            if (count > 0) {
                // removing before insertion
                for (int i = 0; i < CartInsertion.cartProducts.size(); i++) {
                    CartModel product = CartInsertion.cartProducts.get(i);
                    if (product.id.equals(pid)) {
                        CartInsertion.cartProducts.remove(product);
                    }
                }
                // adding
                cartModel = new CartModel(pid, ap.get(p).name, ap.get(p).price, count, total);
                CartInsertion.cartProducts.add(cartModel);
                CartInsertion.cartprice = total;
                tv_total_price.setText("" + CartInsertion.cartprice);

            } else {
                // removing product
                for (int i = 0; i < CartInsertion.cartProducts.size(); i++) {
                    CartModel product = CartInsertion.cartProducts.get(i);
                    if (product.id.equals(pid)) {
                        CartInsertion.cartProducts.remove(product);
                    }
                }
            }
            p++;
        }
        Log.d("ALLCART", CartInsertion.cartProducts.toString());
    }
}