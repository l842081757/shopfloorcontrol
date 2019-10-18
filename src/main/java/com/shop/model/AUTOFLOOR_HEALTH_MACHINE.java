package com.shop.model;

import java.math.BigDecimal;
import java.util.Date;

public class AUTOFLOOR_HEALTH_MACHINE {
    private BigDecimal id;

    private String floor;

    private String lineName;

    private String product;

    private String stationName;

    private String machineNo;

    private BigDecimal input;

    private BigDecimal pass;

    private BigDecimal fail;

    private BigDecimal retest;

    private BigDecimal unknown;

    private BigDecimal firstPass;

    private String cycleType;

    private Date updateTime;

    private BigDecimal failCount;

    private Date startDate;

    private Date endDate;

    private String stationname;

    private Double  misdetet;

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
    }

    public Double getMisdetet() {
        return misdetet;
    }

    public void setMisdetet(Double misdetet) {
        this.misdetet = misdetet;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product == null ? null : product.trim();
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo == null ? null : machineNo.trim();
    }

    public BigDecimal getInput() {
        return input;
    }

    public void setInput(BigDecimal input) {
        this.input = input;
    }

    public BigDecimal getPass() {
        return pass;
    }

    public void setPass(BigDecimal pass) {
        this.pass = pass;
    }

    public BigDecimal getFail() {
        return fail;
    }

    public void setFail(BigDecimal fail) {
        this.fail = fail;
    }

    public BigDecimal getRetest() {
        return retest;
    }

    public void setRetest(BigDecimal retest) {
        this.retest = retest;
    }

    public BigDecimal getUnknown() {
        return unknown;
    }

    public void setUnknown(BigDecimal unknown) {
        this.unknown = unknown;
    }

    public BigDecimal getFirstPass() {
        return firstPass;
    }

    public void setFirstPass(BigDecimal firstPass) {
        this.firstPass = firstPass;
    }

    public String getCycleType() {
        return cycleType;
    }

    public void setCycleType(String cycleType) {
        this.cycleType = cycleType == null ? null : cycleType.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getFailCount() {
        return failCount;
    }

    public void setFailCount(BigDecimal failCount) {
        this.failCount = failCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}