package com.Raamsa.raamsa.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Projects implements Serializable {
    String id,
            project_name,
            image,
            started_date,
            description,
            status,
            is_clickable,
            created_at,
            updated_at;

    List<Blocks> blocks = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStarted_date() {
        return started_date;
    }

    public void setStarted_date(String started_date) {
        this.started_date = started_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIs_clickable() {
        return is_clickable;
    }

    public void setIs_clickable(String is_clickable) {
        this.is_clickable = is_clickable;
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

    public List<Blocks> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Blocks> blocks) {
        this.blocks = blocks;
    }

    public static List<Projects> createListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Projects>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createJsonFromList(List<Projects> object) {
        Gson gson = new Gson();
        Type type = new TypeToken<Projects>() {
        }.getType();
        return gson.toJson(object, type);
    }

    public static Projects createobjectFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Projects>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createjsonFromObject(Projects object) {
        Gson gson = new Gson();
        Type type = new TypeToken<Projects>() {
        }.getType();
        return gson.toJson(object, type);
    }

    String
            project_type,
            area,
            price,
            unit,
            development_charge,
            state_id,
            city_id,
            location_detail,
            nearest_location,
            how_to_reach,
            why_purchase,
            layout_plan,
            location_map,
            running_status,
            amount;

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDevelopment_charge() {
        return development_charge;
    }

    public void setDevelopment_charge(String development_charge) {
        this.development_charge = development_charge;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getLocation_detail() {
        return location_detail;
    }

    public void setLocation_detail(String location_detail) {
        this.location_detail = location_detail;
    }

    public String getNearest_location() {
        return nearest_location;
    }

    public void setNearest_location(String nearest_location) {
        this.nearest_location = nearest_location;
    }

    public String getHow_to_reach() {
        return how_to_reach;
    }

    public void setHow_to_reach(String how_to_reach) {
        this.how_to_reach = how_to_reach;
    }

    public String getWhy_purchase() {
        return why_purchase;
    }

    public void setWhy_purchase(String why_purchase) {
        this.why_purchase = why_purchase;
    }

    public String getLayout_plan() {
        return layout_plan;
    }

    public void setLayout_plan(String layout_plan) {
        this.layout_plan = layout_plan;
    }

    public String getLocation_map() {
        return location_map;
    }

    public void setLocation_map(String location_map) {
        this.location_map = location_map;
    }

    public String getRunning_status() {
        return running_status;
    }

    public void setRunning_status(String running_status) {
        this.running_status = running_status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
