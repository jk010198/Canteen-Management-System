package com.project.canteenmanagementsystem.Models;

public class RegistrationModel {

    public String id;
    public String name;
    public String mobileno;
    public String email;
    public String role;
    public String password;

    public RegistrationModel() {}

    public RegistrationModel(String id, String name, String mobileno, String email, String role , String password){
        this.id = id;
        this.name = name;
        this.mobileno = mobileno;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }
}
