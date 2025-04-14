package com.example.firebase;

import java.io.Serializable;

public class Video1Model implements Serializable {
    public Video1Model(String url, String desc, String title) {
        this.url = url;
        this.desc = desc;
        this.title = title;
    }
    public Video1Model() {
    }
    private String title;
    private String desc;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
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


}
