package com.Raamsa.raamsa.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class Plots implements Serializable {
    String id,
            block_id,
            name,
            area,
            remarks,
            booking_status,
            is_faceing,
            unit_id,
            unit_name,
            faceing,
            image,
            price;
    String development_charge;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlock_id() {
        return block_id;
    }

    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    public String getIs_faceing() {
        return is_faceing;
    }

    public void setIs_faceing(String is_faceing) {
        this.is_faceing = is_faceing;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getFaceing() {
        return faceing;
    }

    public void setFaceing(String faceing) {
        this.faceing = faceing;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static List<Plots> createListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Plots>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createJsonFromList(List<Plots> object) {
        Gson gson = new Gson();
        Type type = new TypeToken<Plots>() {
        }.getType();
        return gson.toJson(object, type);
    }

    public static Plots createobjectFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Plots>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createjsonFromObject(Plots object) {
        Gson gson = new Gson();
        Type type = new TypeToken<Plots>() {
        }.getType();
        return gson.toJson(object, type);
    }

    String
            hold_comment,
            amount,
            plot_name,
            face_name,
            project_type;

    public String getHold_comment() {
        return hold_comment;
    }

    public void setHold_comment(String hold_comment) {
        this.hold_comment = hold_comment;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPlot_name() {
        return plot_name;
    }

    public void setPlot_name(String plot_name) {
        this.plot_name = plot_name;
    }

    public String getFace_name() {
        return face_name;
    }

    public void setFace_name(String face_name) {
        this.face_name = face_name;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDevelopment_charge() {
        return development_charge;
    }

    public void setDevelopment_charge(String development_charge) {
        this.development_charge = development_charge;
    }

    String block_name,block_type,avalibilable_for,nearest_location,description;

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public String getBlock_type() {
        return block_type;
    }

    public void setBlock_type(String block_type) {
        this.block_type = block_type;
    }

    public String getAvalibilable_for() {
        return avalibilable_for;
    }

    public void setAvalibilable_for(String avalibilable_for) {
        this.avalibilable_for = avalibilable_for;
    }

    public String getNearest_location() {
        return nearest_location;
    }

    public void setNearest_location(String nearest_location) {
        this.nearest_location = nearest_location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
