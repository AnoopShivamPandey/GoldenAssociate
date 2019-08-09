package com.Raamsa.raamsa.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class FlatData implements Serializable {
    String id,
            block_id,
            plot_type,
            project_type,
            name,
            area,
            unit_id,
            price,
            floor_no,
            width,
            length,
            bedroom,
            bathroom,
            studyroom,
            furnished,
            remarks,
            booking_status,
            hold_comment,
            is_faceing,
            created_by,
            status,
            created_at,
            updated_at;

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

    public String getPlot_type() {
        return plot_type;
    }

    public void setPlot_type(String plot_type) {
        this.plot_type = plot_type;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
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

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFloor_no() {
        return floor_no;
    }

    public void setFloor_no(String floor_no) {
        this.floor_no = floor_no;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getBedroom() {
        return bedroom;
    }

    public void setBedroom(String bedroom) {
        this.bedroom = bedroom;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public String getStudyroom() {
        return studyroom;
    }

    public void setStudyroom(String studyroom) {
        this.studyroom = studyroom;
    }

    public String getFurnished() {
        return furnished;
    }

    public void setFurnished(String furnished) {
        this.furnished = furnished;
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

    public String getHold_comment() {
        return hold_comment;
    }

    public void setHold_comment(String hold_comment) {
        this.hold_comment = hold_comment;
    }

    public String getIs_faceing() {
        return is_faceing;
    }

    public void setIs_faceing(String is_faceing) {
        this.is_faceing = is_faceing;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public static List<FlatData> createListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<FlatData>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createJsonFromList(List<FlatData> object) {
        Gson gson = new Gson();
        Type type = new TypeToken<FlatData>() {
        }.getType();
        return gson.toJson(object, type);
    }

    public static FlatData createobjectFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<FlatData>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createjsonFromObject(FlatData object) {
        Gson gson = new Gson();
        Type type = new TypeToken<FlatData>() {
        }.getType();
        return gson.toJson(object, type);
    }

    String unit_name,
            amount,
            flat_name,
            flat_id,
            face_name;

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFlat_name() {
        return flat_name;
    }

    public void setFlat_name(String flat_name) {
        this.flat_name = flat_name;
    }

    public String getFlat_id() {
        return flat_id;
    }

    public void setFlat_id(String flat_id) {
        this.flat_id = flat_id;
    }

    public String getFace_name() {
        return face_name;
    }

    public void setFace_name(String face_name) {
        this.face_name = face_name;
    }
}
