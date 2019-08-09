package com.Raamsa.raamsa.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class Payment_Reciept_Data implements Serializable {
    String id,
            purchase_id,
            payment_sort_by,
            invice_number,
            payment_mode,
            cheque_number,
            cheque_branch_name,
            cheque_bank_name,
            check_date,
            amount,
            approve_date,
            remarks,
            status,
            created_by,
            created_at,
            updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(String purchase_id) {
        this.purchase_id = purchase_id;
    }

    public String getPayment_sort_by() {
        return payment_sort_by;
    }

    public void setPayment_sort_by(String payment_sort_by) {
        this.payment_sort_by = payment_sort_by;
    }

    public String getInvice_number() {
        return invice_number;
    }

    public void setInvice_number(String invice_number) {
        this.invice_number = invice_number;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getCheque_number() {
        return cheque_number;
    }

    public void setCheque_number(String cheque_number) {
        this.cheque_number = cheque_number;
    }

    public String getCheque_branch_name() {
        return cheque_branch_name;
    }

    public void setCheque_branch_name(String cheque_branch_name) {
        this.cheque_branch_name = cheque_branch_name;
    }

    public String getCheque_bank_name() {
        return cheque_bank_name;
    }

    public void setCheque_bank_name(String cheque_bank_name) {
        this.cheque_bank_name = cheque_bank_name;
    }

    public String getCheck_date() {
        return check_date;
    }

    public void setCheck_date(String check_date) {
        this.check_date = check_date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApprove_date() {
        return approve_date;
    }

    public void setApprove_date(String approve_date) {
        this.approve_date = approve_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }


    public static List<Payment_Reciept_Data> createListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Payment_Reciept_Data>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createJsonFromList(List<Payment_Reciept_Data> object) {
        Gson gson = new Gson();
        Type type = new TypeToken<Payment_Reciept_Data>() {
        }.getType();
        return gson.toJson(object, type);
    }

    public static Payment_Reciept_Data createobjectFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Payment_Reciept_Data>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createjsonFromObject(Payment_Reciept_Data object) {
        Gson gson = new Gson();
        Type type = new TypeToken<Payment_Reciept_Data>() {
        }.getType();
        return gson.toJson(object, type);
    }
}
