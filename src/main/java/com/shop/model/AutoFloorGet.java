package com.shop.model;

import java.util.Collections;

/**
 * @author
 * @create 2019-08-17 18:07
 */
public class AutoFloorGet implements Comparable<AutoFloorGet>{

    private  Integer dTarget;

    private  Integer dTargetsum;

    private  Integer hTarget;

    private  String lineName;

    private Integer actualOuts;

    private Integer actualOutsum;

    private Double  actualData;

    private Double  actualDatasum;

    private Double  achievingRate;

    private Double  achievingRatesum;

    private  Integer nTargetNight;

    private  Integer Sign;//标记

    private  Integer nTargetsumNight;

    private  Integer nhTargetNight;

    private  String nlineNameNight;

    private Integer nactualOutsNight;

    private Integer nactualOutsumNight;

    private Double  nactualDataNight;

    private Double  nactualDatasumNight;

    @Override
    public String toString() {
        return "AutoFloorGet{" +
                "dTarget=" + dTarget +
                ", dTargetsum=" + dTargetsum +
                ", hTarget=" + hTarget +
                ", lineName='" + lineName + '\'' +
                ", actualOuts=" + actualOuts +
                ", actualOutsum=" + actualOutsum +
                ", actualData=" + actualData +
                ", actualDatasum=" + actualDatasum +
                ", nTargetNight=" + nTargetNight +
                ", nTargetsumNight=" + nTargetsumNight +
                ", nhTargetNight=" + nhTargetNight +
                ", nlineNameNight='" + nlineNameNight + '\'' +
                ", nactualOutsNight=" + nactualOutsNight +
                ", nactualOutsumNight=" + nactualOutsumNight +
                ", nactualDataNight=" + nactualDataNight +
                ", nactualDatasumNight=" + nactualDatasumNight +
                '}';
    }

    public Integer getSign() {
        int Signint=0;
        if (Sign!=null){
            Signint=Sign;
        }

        return Signint;
    }

    public void setSign(Integer sign) {
        Sign = sign;
    }

    public Double getAchievingRatesum() {
        return achievingRatesum;
    }

    public void setAchievingRatesum(Double achievingRatesum) {
        this.achievingRatesum = achievingRatesum;
    }

    public Double getAchievingRate() {
        return achievingRate;
    }

    public void setAchievingRate(Double achievingRate) {
        this.achievingRate = achievingRate;
    }

    public Integer getnTargetNight() {
        return nTargetNight;
    }

    public void setnTargetNight(Integer nTargetNight) {
        this.nTargetNight = nTargetNight;
    }

    public Integer getnTargetsumNight() {
        return nTargetsumNight;
    }

    public void setnTargetsumNight(Integer nTargetsumNight) {
        this.nTargetsumNight = nTargetsumNight;
    }

    public Integer getNhTargetNight() {
        return nhTargetNight;
    }

    public void setNhTargetNight(Integer nhTargetNight) {
        this.nhTargetNight = nhTargetNight;
    }

    public String getNlineNameNight() {
        return nlineNameNight;
    }

    public void setNlineNameNight(String nlineNameNight) {
        this.nlineNameNight = nlineNameNight;
    }

    public Integer getNactualOutsNight() {
        return nactualOutsNight;
    }

    public void setNactualOutsNight(Integer nactualOutsNight) {
        this.nactualOutsNight = nactualOutsNight;
    }

    public Integer getNactualOutsumNight() {
        return nactualOutsumNight;
    }

    public void setNactualOutsumNight(Integer nactualOutsumNight) {
        this.nactualOutsumNight = nactualOutsumNight;
    }

    public Double getNactualDataNight() {
        return nactualDataNight;
    }

    public void setNactualDataNight(Double nactualDataNight) {
        this.nactualDataNight = nactualDataNight;
    }

    public Double getNactualDatasumNight() {
        return nactualDatasumNight;
    }

    public void setNactualDatasumNight(Double nactualDatasumNight) {
        this.nactualDatasumNight = nactualDatasumNight;
    }

    public Integer gethTarget() {
        return hTarget;
    }

    public void sethTarget(Integer hTarget) {
        this.hTarget = hTarget;
    }

    public Integer getdTarget() {
        return dTarget;
    }

    public void setdTarget(Integer dTarget) {
        this.dTarget = dTarget;
    }

    public Integer getdTargetsum() {
        return dTargetsum;
    }

    public void setdTargetsum(Integer dTargetsum) {
        this.dTargetsum = dTargetsum;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Integer getActualOuts() {
        return actualOuts;
    }

    public void setActualOuts(Integer actualOuts) {
        this.actualOuts = actualOuts;
    }

    public Integer getActualOutsum() {
        return actualOutsum;
    }

    public void setActualOutsum(Integer actualOutsum) {
        this.actualOutsum = actualOutsum;
    }

    public Double getActualData() {
        return actualData;
    }

    public void setActualData(Double actualData) {
        this.actualData = actualData;
    }

    public Double getActualDatasum() {
        return actualDatasum;
    }

    public void setActualDatasum(Double actualDatasum) {
        this.actualDatasum = actualDatasum;
    }

    public  int compareTo(AutoFloorGet NO3List){
        if (this.getActualOuts() < NO3List.getActualOuts()){
            return -1;
        }else if (this.getActualOuts() > NO3List.getActualOuts()){
            return 1;
        }else {
            return 0;
        }


    }

}
