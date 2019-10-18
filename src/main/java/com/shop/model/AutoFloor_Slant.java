package com.shop.model;

import java.math.BigDecimal;
import java.util.Date;

public class AutoFloor_Slant {
    private BigDecimal id;

    private String floor;

    private String lineName;

    private Date startTime;

    private Date endTime;

    private BigDecimal times;

    private String product;

    private String Line_name;

    private Integer Times;

    private Double DateTimes;

    public Double getDateTimes() {
        return DateTimes;
    }

    public void setDateTimes(Double dateTimes) {
        DateTimes = dateTimes;
    }

    public String getLine_name() {
        return Line_name;
    }

    public void setLine_name(String line_name) {
        Line_name = line_name;
    }

    public void setTimes(Integer times) {
        Times = times;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor == null ? null : floor.trim();
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getTimes() {
        return times;
    }

    public void setTimes(BigDecimal times) {
        this.times = times;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product == null ? null : product.trim();
    }
}