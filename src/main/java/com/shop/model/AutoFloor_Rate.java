package com.shop.model;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Date;

public class AutoFloor_Rate implements Comparable<AutoFloor_Rate> {
    private BigDecimal id;

    private String floor;

    private Integer ROWNUM;

    private String lineName;

    private String product;

    private String stationName;

    private String stationname;

    private BigDecimal input;

    private BigDecimal pass;

    private BigDecimal TTL_fail;

    private BigDecimal fail;

    private BigDecimal retest;

    private BigDecimal unknown;

    private BigDecimal firstPass;

    private Date startTime;

    private Date endTime;

    private String timeSlot;

    private Date updateTime;

    private Double Rate;

    private  Double RATE1;

    private String MACHINE_NO;

    private String LINE_NAME;

    private Double Yield;

    private Double Misdetet;

    private Double misdetet;

    private Double unknownRate;

    private Double FPY;

    private Double ZTY;

    private Integer Fail_Q;

    private Integer FAIL_QTY;

    private String  FAIL_ITEM;
    private String  machine_no;


    public Integer getROWNUM() {
        return ROWNUM;
    }

    public void setROWNUM(Integer ROWNUM) {
        this.ROWNUM = ROWNUM;
    }

    public String getMachine_no() {
        return machine_no;
    }

    public void setMachine_no(String machine_no) {
        this.machine_no = machine_no;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
    }

    public BigDecimal getTTL_fail() {
        return TTL_fail;
    }

    public void setTTL_fail(BigDecimal TTL_fail) {
        this.TTL_fail = TTL_fail;
    }

    public String getLINE_NAME() {
        return LINE_NAME;
    }

    public void setLINE_NAME(String LINE_NAME) {
        this.LINE_NAME = LINE_NAME;
    }

    public Double getYield() {
        return Yield;
    }

    public void setYield(Double yield) {
        Yield = yield;
    }

    public Double getMisdetet() {
        return Misdetet;
    }

    public void setMisdetet(Double misdetet) {
        Misdetet = misdetet;
    }

    public Double getUnknownRate() {
        return unknownRate;
    }

    public void setUnknownRate(Double unknownRate) {
        this.unknownRate = unknownRate;
    }

    public Double getFPY() {
        return FPY;
    }

    public void setFPY(Double FPY) {
        this.FPY = FPY;
    }

    public Integer getFAIL_QTY() {
        return FAIL_QTY;
    }

    public void setFAIL_QTY(Integer FAIL_QTY) {
        this.FAIL_QTY = FAIL_QTY;
    }

    public String getFAIL_ITEM() {
        return FAIL_ITEM;
    }

    public void setFAIL_ITEM(String FAIL_ITEM) {
        this.FAIL_ITEM = FAIL_ITEM;
    }

    public String getMACHINE_NO() {
        return MACHINE_NO;
    }

    public void setMACHINE_NO(String MACHINE_NO) {
        this.MACHINE_NO = MACHINE_NO;
    }

    public Double getZTY() {
        return ZTY;
    }

    public void setZTY(Double ZTY) {
        this.ZTY = ZTY;
    }

    public Integer getFail_Q() {
        return Fail_Q;
    }

    public void setFail_Q(Integer fail_Q) {
        Fail_Q = fail_Q;
    }

    public Double getRATE1() {
        return RATE1;
    }

    public void setRATE1(Double RATE1) {
        this.RATE1 = RATE1;
    }

    public Double getRate() {
        return Rate;
    }

    public void setRate(Double rate) {
        Rate = rate;
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

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot == null ? null : timeSlot.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AutoFloor_Rate{" +
                "id=" + id +
                ", floor='" + floor + '\'' +
                ", lineName='" + lineName + '\'' +
                ", product='" + product + '\'' +
                ", stationName='" + stationName + '\'' +
                ", input=" + input +
                ", pass=" + pass +
                ", fail=" + fail +
                ", retest=" + retest +
                ", unknown=" + unknown +
                ", firstPass=" + firstPass +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", timeSlot='" + timeSlot + '\'' +
                ", updateTime=" + updateTime +
                ", Rate=" + Rate +
                ", RATE1=" + RATE1 +
                '}';
    }

    @Override
    public int compareTo(@NotNull AutoFloor_Rate Top5List) {
        if (this.RATE1 < Top5List.RATE1){
            return -1;
        }else if (this.RATE1 > Top5List.RATE1){
            return 1;
        }else {
            return 0;
        }
    }
}