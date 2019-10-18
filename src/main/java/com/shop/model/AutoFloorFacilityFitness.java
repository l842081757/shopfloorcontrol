package com.shop.model;

/**
 * @author
 * @create 2019-10-10 14:01
 */
public class AutoFloorFacilityFitness {
    private  String Linename;
    private  Double DFUStation=0.0;
    private  Double FCTStation=0.0;
    private  Double S1Station=0.0;
    private  Double S2Station=0.0;
    private  Double S3Station=0.0;
    private  Double CCTStation=0.0;
    private  Double W1Station=0.0;
    private  Double W2Station=0.0;
    private  Double UWBStation=0.0;
    private  Double SCONDStation=0.0;
    private  Integer Area=0;
    private  String PRODUCT;

    public String getPRODUCT() {
        return PRODUCT;
    }

    public void setPRODUCT(String PRODUCT) {
        this.PRODUCT = PRODUCT;
    }

    public Integer getArea() {
        return Area;
    }

    public void setArea(Integer area) {
        Area = area;
    }

    public String getLinename() {
        return Linename;
    }

    public void setLinename(String linename) {
        Linename = linename;
    }

    public Double getDFUStation() {
        return DFUStation;
    }

    public void setDFUStation(Double DFUStation) {
        this.DFUStation = DFUStation;
    }

    public Double getFCTStation() {
        return FCTStation;
    }

    public void setFCTStation(Double FCTStation) {
        this.FCTStation = FCTStation;
    }

    public Double getS1Station() {
        return S1Station;
    }

    public void setS1Station(Double s1Station) {
        S1Station = s1Station;
    }

    public Double getS2Station() {
        return S2Station;
    }

    public void setS2Station(Double s2Station) {
        S2Station = s2Station;
    }

    public Double getS3Station() {
        return S3Station;
    }

    public void setS3Station(Double s3Station) {
        S3Station = s3Station;
    }

    public Double getCCTStation() {
        return CCTStation;
    }

    public void setCCTStation(Double CCTStation) {
        this.CCTStation = CCTStation;
    }

    public Double getW1Station() {
        return W1Station;
    }

    public void setW1Station(Double w1Station) {
        W1Station = w1Station;
    }

    public Double getW2Station() {
        return W2Station;
    }

    public void setW2Station(Double w2Station) {
        W2Station = w2Station;
    }

    public Double getUWBStation() {
        return UWBStation;
    }

    public void setUWBStation(Double UWBStation) {
        this.UWBStation = UWBStation;
    }

    public Double getSCONDStation() {
        return SCONDStation;
    }

    public void setSCONDStation(Double SCONDStation) {
        this.SCONDStation = SCONDStation;
    }
}
