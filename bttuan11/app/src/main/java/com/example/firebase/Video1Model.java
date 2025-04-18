package com.example.firebase;

import java.io.Serializable;

public class Video1Model implements Serializable {
    private String desc;
    private String title;
    private String url;
    private String email;
    private String avatarUrl;
    private int likeCount;

    public String getDesc() {
        return desc;
    }

    public String getEmail() {
        return email;
    }
    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Video1Model() {

    }


}
