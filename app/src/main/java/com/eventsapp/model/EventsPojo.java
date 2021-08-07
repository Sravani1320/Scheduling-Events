package com.eventsapp.model;

import com.google.gson.annotations.SerializedName;

public class EventsPojo {
    //SELECT `eid`, `category`, `name`, `dat`, `venue`, `description`, `email` FROM `event` WHERE 1
    @SerializedName("eid")
    private String eid;

    @SerializedName("category")
    private String category;

    @SerializedName("name")
    private String name;

    @SerializedName("dat")
    private String dat;

    @SerializedName("venue")
    private String venue;

    @SerializedName("description")
    private String description;

    @SerializedName("email")
    private String email;

    @SerializedName("image")
    private String image;

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
