package com.project.canteenmanagementsystem.UsersActivity.Model;

public class OrdersModel {

    public String id;
    public String name;
    public String mobile;
    public String emailid;
    public String address;
    public String order_id;
    public String orderlist;
    public String grandtotal;
    public String accept_prepare;
    public String order_complete;
    public String order_date_time;
    public int order_otp;

    public OrdersModel() {
    }

    public OrdersModel(String id, String name, String mobile, String emailid, String address, String order_id, String orderlist,
                       String grandtotal, String accept_prepare, String order_complete, String order_date_time, int order_otp) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.emailid = emailid;
        this.address = address;
        this.order_id = order_id;
        this.orderlist = orderlist;
        this.grandtotal = grandtotal;
        this.accept_prepare = accept_prepare;
        this.order_complete = order_complete;
        this.order_date_time = order_date_time;
        this.order_otp = order_otp;
    }

    public String getOrder_id() {
        return order_id;
    }

    public int getOrder_otp() {
        return order_otp;
    }

    public String getOrder_date_time() {
        return order_date_time;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmailid() {
        return emailid;
    }

    public String getAddress() {
        return address;
    }

    public String getOrderlist() {
        return orderlist;
    }

    public String getGrandtotal() {
        return grandtotal;
    }

    public String getAccept_prepare() {
        return accept_prepare;
    }

    public String getOrder_complete() {
        return order_complete;
    }
}