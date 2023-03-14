package com.example.stujobs.adapter.entity.filter;

import com.yangbin.base.BaseFilterBean;

/**
 * @author luys
 * @describe 区域选择子Entity
 * @date 2018/5/15
 * @email samluys@foxmail.com
 */
public class FilterAreaTwoEntity extends BaseFilterBean {

    /**
     * 区域名称
     */
    private String name;
    /**
     * 区域对应的ID
     */
    private int area_id;
    /**
     * 选择状态 0 选择 1 选择
     */
    private int selected;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    @Override
    public String getItemName() {
        return name;
    }

    @Override
    public int getId() {
        return area_id;
    }

    @Override
    public int getSelecteStatus() {
        return selected;
    }

    @Override
    public void setSelecteStatus(int status) {
        this.selected = status;
    }

    @Override
    public String getSortTitle() {
        return null;
    }

    @Override
    public String getSortKey() {
        return null;
    }
}