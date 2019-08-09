package com.Raamsa.raamsa.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class Wallet implements Serializable {
    String wallet_amount;

    public String getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public static List<Wallet> createListFromJson(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Wallet>>() {}.getType();
        return gson.fromJson(json, type);
    }
    public static String createJsonFromList(List<Wallet> object){
        Gson gson = new Gson();
        Type type = new TypeToken<Wallet>() {}.getType();
        return gson.toJson(object, type);
    }

    public static Wallet createobjectFromJson(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<Wallet>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static String createjsonFromObject(Wallet object){
        Gson gson = new Gson();
        Type type = new TypeToken<Wallet>() {}.getType();
        return gson.toJson(object, type);
    }
}
