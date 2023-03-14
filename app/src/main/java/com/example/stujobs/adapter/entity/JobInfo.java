package com.example.stujobs.adapter.entity;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 新闻信息
 *
 * @author xuexiang
 * @since 2019/4/7 下午12:07
 */
public class JobInfo implements Serializable {

    private static AtomicLong sAtomicLong = new AtomicLong();


    private int ID;
    private String company;
    private String position;
    private List<String> tags;
    private String price;
    private String like;
    private String pingjia;
    private String hr;
    private String more;
    private String request;

    private String more_praise;
    private String more_time;
    private int more_count;

    public JobInfo(int ID, String company, String position, List<String> tags, String price, String like, String pingjia, String hr, String more, String request) {
        this.ID = ID;
        this.company = company;
        this.position = position;
        this.tags = tags;
        this.price = price;
        this.like = like;
        this.pingjia = pingjia;
        this.hr = hr;
        this.more = more;
        this.request = request;
    }
    public JobInfo(String praise, String time,int count) {
        this.more_praise = praise;
        this.more_time = time;
        this.more_count = count;
    }

    public JobInfo(String company, String position) {
        this.company = company;
        this.position = position;
    }

    public String getHr() {
        return hr;
    }

    public void setHr(String hr) {
        this.hr = hr;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getMore_praise() {
        return more_praise;
    }

    public void setMore_praise(String more_praise) {
        this.more_praise = more_praise;
    }

    public String getMore_time() {
        return more_time;
    }

    public void setMore_time(String more_time) {
        this.more_time = more_time;
    }

    public int getMore_count() {
        return more_count;
    }

    public void setMore_count(int more_count) {
        this.more_count = more_count;
    }

    public JobInfo() {

    }


    public JobInfo setID(int ID) {
        this.ID = ID;
        return this;
    }

    public int getID() {
        return ID;
    }

    public String getCompany() {
        return company;
    }

    public JobInfo setCompany(String company) {
        this.company = company;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public JobInfo setPrice(String price) {
        price = price;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public JobInfo setPosition(String position) {
        this.position = position;
        return this;
    }

    public  List<String> getTags() {
        return tags;
    }

    public  JobInfo setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public String getLike() {
        return like;
    }

    public JobInfo setLike(String like) {
        this.like = like;
        return this;
    }

    public String getPingjia() {
        return pingjia;
    }

    public JobInfo setPingjia(String pingjia) {
        this.pingjia = pingjia;
        return this;
    }

//    @NonNull
//    @Override
//    public String toString() {
//        return "JobInfo{" +
//                "UserName='" + UserName + '\'' +
//                ", Tag='" + Cost + '\'' +
//                ", Title='" + Title + '\'' +
//                ", Summary='" + Tags + '\'' +
//                ", Praise=" + Praise +
//                ", Comment=" + Comment +
//                ", DetailUrl='" + DetailUrl + '\'' +
//                '}';
//    }

//    @NonNull
//    @Override
//    public JobInfo clone() {
//        try {
//            return (JobInfo) super.clone();
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//        return new JobInfo();
//    }
}
