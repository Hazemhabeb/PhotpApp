package com.hazem.photpapp.model;

import java.util.ArrayList;

/**
 * Created by hazem on 7/29/2017.
 */

public class Post {
    private String id;
    private String userId;
    private String userName;
    private String disc;
    private String userImage;
    private String image;
    private String time;
    private ArrayList<String> likes=new ArrayList<>();
    private ArrayList<String> hidden=new ArrayList<>();
    private ArrayList<String> report=new ArrayList<>();

    public Post() {
        disc="";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }


    public String getUserImage() {
        return userImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public ArrayList<String> getHidden() {
        return hidden;
    }

    public void setHidden(ArrayList<String> hidden) {
        this.hidden = hidden;
    }

    public ArrayList<String> getReport() {
        return report;
    }

    public void setReport(ArrayList<String> report) {
        this.report = report;
    }
}
