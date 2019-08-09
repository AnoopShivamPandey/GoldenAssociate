package com.Raamsa.raamsa.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Blocks implements Serializable {

    String id,
            project_id,
            block_name,
            block_type,
            avalibilable_for,
            block_photo,
            block_document,
            remark,
            status,
            created_by,
            created_at,
            updated_at,
            block_id,
            plot_type,
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
            project_name,
            unit,
            development_charge,
            state_id,
            city_id,
            location_detail,
            description,
            nearest_location,
            how_to_reach,
            why_purchase,
            image,
            layout_plan,
            location_map,
            running_status,
            started_date,
            faceing_name,
            unit_name;

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getFaceing_name() {
        return faceing_name;
    }

    public void setFaceing_name(String faceing_name) {
        this.faceing_name = faceing_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

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

    public String getBlock_photo() {
        return block_photo;
    }

    public void setBlock_photo(String block_photo) {
        this.block_photo = block_photo;
    }

    public String getBlock_document() {
        return block_document;
    }

    public void setBlock_document(String block_document) {
        this.block_document = block_document;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getStarted_date() {
        return started_date;
    }

    public void setStarted_date(String started_date) {
        this.started_date = started_date;
    }
    /* String id,
            project_id,
            block_name,
            block_type,
            avalibilable_for,
            block_photo,
            block_document,
            remark,
            status,
            created_by,
            created_at,
            updated_at;*/
    List<Plots> flatdetails=new ArrayList<>();

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

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

    public String getBlock_photo() {
        return block_photo;
    }

    public void setBlock_photo(String block_photo) {
        this.block_photo = block_photo;
    }

    public String getBlock_document() {
        return block_document;
    }

    public void setBlock_document(String block_document) {
        this.block_document = block_document;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
    }*/

    public List<Plots> getFlatdetails() {
        return flatdetails;
    }

    public void setFlatdetails(List<Plots> flatdetails) {
        this.flatdetails = flatdetails;
    }

    public static List<Blocks> createListFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Blocks>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createJsonFromList(List<Blocks> object) {
        Gson gson = new Gson();
        Type type = new TypeToken<Blocks>() {
        }.getType();
        return gson.toJson(object, type);
    }

    public static Blocks createobjectFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Blocks>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static String createjsonFromObject(Blocks object) {
        Gson gson = new Gson();
        Type type = new TypeToken<Blocks>() {
        }.getType();
        return gson.toJson(object, type);
    }
}
