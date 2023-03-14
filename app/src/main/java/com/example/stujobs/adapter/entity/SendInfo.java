package com.example.stujobs.adapter.entity;

public class SendInfo {
    private int id;
    private int job_id;
    private String position;
    private String company;
    private String title;
    private String result;
    private String url;
    private String name;

    public SendInfo(int id, int job_id,String position, String company, String result,String title, String url, String name) {
        this.id = id;
        this.job_id = job_id;
        this.position = position;
        this.company = company;
        this.result = result;
        this.title = title;
        this.url = url;
        this.name = name;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
