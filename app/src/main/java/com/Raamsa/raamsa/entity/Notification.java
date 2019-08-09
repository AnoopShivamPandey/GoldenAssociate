package com.Raamsa.raamsa.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class Notification implements Serializable {

  String notification_msg;

    public String getNotification_msg() {
        return notification_msg;
    }

    public void setNotification_msg(String notification_msg) {
        this.notification_msg = notification_msg;
    }
    public static Notification createobjectFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Notification>() {
        }.getType();
        return gson.fromJson(json, type);
    }
    public static List<Notification> createJsonInList(String str) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Notification>>() {
        }.getType();
        return gson.fromJson(str, type);}

   /* public static List<Notification> createListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Notification>>() {
        }.getType();
        return gson.fromJson(json, type);
    }*/
}
