package com.project.canteenmanagementsystem.UsersActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.project.canteenmanagementsystem.R;
import com.project.canteenmanagementsystem.UsersActivity.Model.CartInsertion;
import com.project.canteenmanagementsystem.UsersActivity.Model.CartModel;
import com.project.canteenmanagementsystem.UsersActivity.Model.OrdersModel;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class PaymentGatewayActivity extends AppCompatActivity {

    EditText edittext_cardNo, edittext_expire, edittext_cvv, edittext_name_on_card;
    private TextView card_number, card_expire, card_cvv, card_name, textview_paymentamt;
    boolean isfirst = true;
    static String name, emailid, mobile, address;
    CartModel cartModel;
    String cart = "";
    int counter = 999;
    int otp_digit = 9999;
    static int otp;
    DatabaseReference dbref;
    Calendar calendar;
    static String current_date, current_date_time, order_id;
    static Font catFont, redFont, subFont, smallBold;
    static String pdf_pro_name, pdf_pro_price, pdf_pro_quantity;
    static CartModel pdf_product1;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_payment_gateway);

        // find view by id's
        edittext_cardNo = findViewById(R.id.edittext_card_number);
        edittext_expire = findViewById(R.id.edittext_expire);
        edittext_cvv = findViewById(R.id.edittext_cvv);
        edittext_name_on_card = findViewById(R.id.edittext_name_on_card);
        textview_paymentamt = findViewById(R.id.textview_paymentamount);
        card_number = (TextView) findViewById(R.id.card_number);
        card_expire = (TextView) findViewById(R.id.card_expire);
        card_cvv = (TextView) findViewById(R.id.card_cvv);
        card_name = (TextView) findViewById(R.id.card_name);

        //database ref
        dbref = FirebaseDatabase.getInstance().getReference("Orders");

        // get data from intent
        name = getIntent().getStringExtra("name");
        emailid = getIntent().getStringExtra("emailid");
        mobile = getIntent().getStringExtra("mobile");
        address = getIntent().getStringExtra("address");

        // font set-up for pdf
        catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                Font.BOLD);
        redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.NORMAL, BaseColor.RED);
        subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                Font.BOLD);
        smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                Font.BOLD);

        textview_paymentamt.setText("You have to Pay:- " + CartInsertion.cartprice);

        edittext_cardNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_number.setText("**** **** **** ****");
                } else {
                    String number = insertPeriodically(charSequence.toString().trim(), " ", 4);
                    card_number.setText(number);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edittext_expire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_expire.setText("MM/YY");
                } else {
                    String exp = insertPeriodically(charSequence.toString().trim(), "/", 2);
                    card_expire.setText(exp);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edittext_cvv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_cvv.setText("***");
                } else {

                    if (isfirst) {
                        card_cvv.setText("*");
                        isfirst = false;
                    } else {
                        card_cvv.setText(card_cvv.getText() + "*");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edittext_name_on_card.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count) {
                if (charSequence.toString().trim().length() == 0) {
                    card_name.setText("Your Name");
                } else {
                    card_name.setText(charSequence.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        for (int i = 0; i < CartInsertion.cartProducts.size(); i++) {
            cartModel = CartInsertion.cartProducts.get(i);
            cart += cartModel.name + "   X   " + cartModel.quantity + "\n";
        }

        // getting current date & time
        SimpleDateFormat sdf_date = new SimpleDateFormat("dd-MMM-yyyy");
        calendar = Calendar.getInstance();
        Calendar today = calendar;
        current_date = sdf_date.format(calendar.getTime());

        SimpleDateFormat formatter_time = new SimpleDateFormat("hh:mm a");
        String current_time = formatter_time.format(Calendar.getInstance().getTime());
        current_date_time = current_date + "  " + current_time;

        // checking folder with the name of "CanteenManagement"
        checkFolder();
    }

    public void checkFolder() {
        File directory = new File(Environment.getExternalStorageDirectory(), "CanteenManagement/");
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    // payment method & order placed.
    public void paymentMethod(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Canteen Management System");
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        // getting random number
        Random r = new Random();
        int no = r.nextInt(counter);

        String id = dbref.push().getKey();
        String grandtotal = String.valueOf(CartInsertion.cartprice);
        String accept_prepare = "";
        String order_complete = "";
        order_id = current_date + "_" + no;

        // getting random number
        Random random_otp = new Random();
        otp = random_otp.nextInt(otp_digit);

        // upload order in database
        OrdersModel om = new OrdersModel(id, name, mobile, emailid, address, order_id, cart, grandtotal, accept_prepare, order_complete,
                current_date_time,otp);
        dbref.child(id).setValue(om);

        // create pdf & write data
        try {
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/CanteenManagement/"
                    + current_date + "_" + no + ".pdf");

            if (!f.exists()) {
                f.createNewFile();
            }

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(f));
            doc.open();
            addTitlePage(doc);
            doc.close();
            progressDialog.dismiss();
            startActivity(new Intent(PaymentGatewayActivity.this, HomePageActivity.class));

        } catch (Exception e) {

        }
    }

    private static void addTitlePage(Document document)
            throws DocumentException {

        //for business name
        Paragraph shop_name = new Paragraph("BUSINESS NAME", catFont);
        shop_name.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(shop_name, 1);
        document.add(shop_name);
        //for business name end

        //for business add
        Paragraph shop_add = new Paragraph("BUSINESS Add", subFont);
        shop_add.setAlignment(Element.ALIGN_CENTER);
        addEmptyLine(shop_add, 2);
        document.add(shop_add);
        //for business add end

        // for to
        Paragraph add_to = new Paragraph("To,");
        add_to.setAlignment(Element.ALIGN_LEFT);
        document.add(add_to);
        //for to end

        //for customer_name
        Paragraph c_name = new Paragraph("Name:- " + name);
        c_name.setAlignment(Element.ALIGN_LEFT);
        document.add(c_name);
        //for customer_name end

        //for customer_mobile
        Paragraph c_mobile = new Paragraph("Mobile:- " + mobile);
        c_mobile.setAlignment(Element.ALIGN_LEFT);
        document.add(c_mobile);
        //for customer_mobile end

        //for customer_address
        Paragraph c_address = new Paragraph("Address:- " + address);
        c_address.setAlignment(Element.ALIGN_LEFT);
        document.add(c_address);
        //for customer_address end

        //for customer_orderid
        Paragraph c_orderid = new Paragraph("Order_id:- " + order_id);
        c_orderid.setAlignment(Element.ALIGN_LEFT);
        document.add(c_orderid);
        //for customer_orderid end

        //for customer_orderotp
        Paragraph c_orderotp = new Paragraph("Order_Otp:- " + otp);
        c_orderotp.setAlignment(Element.ALIGN_LEFT);
        document.add(c_orderotp);
        //for customer_orderotp end

        //for bill date
        Paragraph date = new Paragraph("Order Date:- " + current_date_time);
        date.setAlignment(Element.ALIGN_LEFT);
        addEmptyLine(date, 2);
        document.add(date);
        //for bill date end

        //for cart_data
        PdfPTable table = new PdfPTable(3);

        PdfPCell c1 = new PdfPCell(new Phrase("Product Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Quantity"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        for (int i = 0; i < CartInsertion.cartProducts.size(); i++) {
            pdf_product1 = CartInsertion.cartProducts.get(i);
            pdf_pro_name = pdf_product1.name;
            pdf_pro_price = pdf_product1.price;
            pdf_pro_quantity = String.valueOf(pdf_product1.quantity);
            table.addCell(pdf_pro_name);
            table.addCell(pdf_pro_quantity);
            table.addCell(pdf_pro_price);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
        }
        document.add(table);

        //for grand total
        Paragraph grand_total = new Paragraph("Grand Total:- " + CartInsertion.cartprice);
        grand_total.setAlignment(Element.ALIGN_RIGHT);
        grand_total.setIndentationRight(50);
        addEmptyLine(grand_total, 2);
        document.add(grand_total);
        document.newPage();
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static String insertPeriodically(String text, String insert, int period) {
        StringBuilder builder = new StringBuilder(text.length() + insert.length() * (text.length() / period) + 1);
        int index = 0;
        String prefix = "";
        while (index < text.length()) {
            builder.append(prefix);
            prefix = insert;
            builder.append(text.substring(index, Math.min(index + period, text.length())));
            index += period;
        }
        return builder.toString();
    }
}