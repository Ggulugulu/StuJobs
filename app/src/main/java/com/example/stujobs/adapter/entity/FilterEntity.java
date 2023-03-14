package com.example.stujobs.adapter.entity;

import com.example.stujobs.adapter.entity.filter.FilterAreaOneEntity;
import com.example.stujobs.adapter.entity.filter.FilterMulSelectEntity;
import com.example.stujobs.adapter.entity.filter.FilterSelectedEntity;

import java.util.List;

/**
 * @author luys
 * @describe 筛选数据
 * @date 2018/5/15
 * @email samluys@foxmail.com
 */
public class FilterEntity {


    /**
     * 区域
     */
    private List<FilterAreaOneEntity> area;
    /**
     * 薪资
     */
    private List<FilterSelectedEntity> price;
    /**
     * 筛选
     */
    private List<FilterMulSelectEntity> mulSelect;
    /**
     * 职位
     */
    private List<FilterMulSelectEntity> position;

    public List<FilterAreaOneEntity> getArea() {
        return area;
    }

    public void setArea(List<FilterAreaOneEntity> area) {
        this.area = area;
    }

    public List<FilterSelectedEntity> getPrice() {
        return price;
    }

    public void setPrice(List<FilterSelectedEntity> price) {
        this.price = price;
    }

    public List<FilterMulSelectEntity> getMulSelect() {
        return mulSelect;
    }

    public void setMulSelect(List<FilterMulSelectEntity> mulSelect) {
        this.mulSelect = mulSelect;
    }

    public List<FilterMulSelectEntity> getPosition() {
        return position;
    }

    public void setPosition(List<FilterMulSelectEntity> position) {
        this.position = position;
    }
}
