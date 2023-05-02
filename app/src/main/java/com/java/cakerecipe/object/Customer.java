package com.java.cakerecipe.object;

import java.io.Serializable;

public class Customer implements Serializable {
    private String name, phone, recipeName, dateTime;

    public Customer() {
    }

    public Customer(String name, String phone, String recipeName, String dateTime) {
        this.name = name;
        this.phone = phone;
        this.recipeName = recipeName;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
