package com.Raamsa.raamsa.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Otp implements Serializable {
    private String message, status;
   // User data = new User();
    List<User> data = new ArrayList<>();
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

/*  public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }*/

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
    public static Otp createobjectFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Otp>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
