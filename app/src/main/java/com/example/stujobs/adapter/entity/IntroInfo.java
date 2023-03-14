package com.example.stujobs.adapter.entity;

public class IntroInfo {
    private String title;
    private String url;

    public IntroInfo(String i_title, String i_url) {
        title = i_title;
        url = i_url;
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

}
