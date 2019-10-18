package com.shop.model;

import java.math.BigDecimal;
import java.util.Date;

public class AUTOFLOOR_OP_ISSUE {
    private BigDecimal id;

    private String area;

    private Integer areaint;

    private String floor;

    private String product;

    private String sn;

    private String lineName;

    private String preStation;

    private String errordetailed;

    private String stationName;

    private String machineNo;

    private String empNo;

    private BigDecimal errorType;

    private Date inputDatetime;

    private Integer ERRORTYPESUM;

    private Integer error_TypeNo;

    private Integer error_TypeTTL;
    private Integer error_TypeNo2;
    private Integer error_TypeNo3;
    private Integer error_TypeNo4;
    private Integer error_TypeNo5;
    private Integer error_TypeNo6;
    private Integer error_TypeNo7;
    private Integer error_TypeNo8;

    public String getErrordetailed() {
        return errordetailed;
    }

    public void setErrordetailed(String errordetailed) {
        this.errordetailed = errordetailed;
    }

    public Integer getAreaint() {
        return areaint;
    }

    public void setAreaint(Integer areaint) {
        this.areaint = areaint;
    }

    public Integer getERRORTYPESUM() {
        return ERRORTYPESUM;
    }

    public void setERRORTYPESUM(Integer ERRORTYPESUM) {
        this.ERRORTYPESUM = ERRORTYPESUM;
    }

    public Integer getError_TypeTTL() {
        return error_TypeTTL;
    }

    public void setError_TypeTTL(Integer error_TypeTTL) {
        this.error_TypeTTL = error_TypeTTL;
    }

    public Integer getError_TypeNo2() {
        return error_TypeNo2;
    }

    public void setError_TypeNo2(Integer error_TypeNo2) {
        this.error_TypeNo2 = error_TypeNo2;
    }

    public Integer getError_TypeNo3() {
        return error_TypeNo3;
    }

    public void setError_TypeNo3(Integer error_TypeNo3) {
        this.error_TypeNo3 = error_TypeNo3;
    }

    public Integer getError_TypeNo4() {
        return error_TypeNo4;
    }

    public void setError_TypeNo4(Integer error_TypeNo4) {
        this.error_TypeNo4 = error_TypeNo4;
    }

    public Integer getError_TypeNo5() {
        return error_TypeNo5;
    }

    public void setError_TypeNo5(Integer error_TypeNo5) {
        this.error_TypeNo5 = error_TypeNo5;
    }

    public Integer getError_TypeNo6() {
        return error_TypeNo6;
    }

    public void setError_TypeNo6(Integer error_TypeNo6) {
        this.error_TypeNo6 = error_TypeNo6;
    }

    public Integer getError_TypeNo7() {
        return error_TypeNo7;
    }

    public void setError_TypeNo7(Integer error_TypeNo7) {
        this.error_TypeNo7 = error_TypeNo7;
    }

    public Integer getError_TypeNo8() {
        return error_TypeNo8;
    }

    public void setError_TypeNo8(Integer error_TypeNo8) {
        this.error_TypeNo8 = error_TypeNo8;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor == null ? null : floor.trim();
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product == null ? null : product.trim();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName == null ? null : lineName.trim();
    }

    public String getPreStation() {
        return preStation;
    }

    public void setPreStation(String preStation) {
        this.preStation = preStation == null ? null : preStation.trim();
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

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo == null ? null : empNo.trim();
    }

    public BigDecimal getErrorType()
    {
        int errorTypeint=0;
        if (errorType!=null){
            errorTypeint=errorType.intValue();
        }
        BigDecimal bigerrorType=new BigDecimal(errorTypeint);
        return bigerrorType;
    }

    public void setErrorType(BigDecimal errorType) {
        this.errorType = errorType;
    }

    public Date getInputDatetime() {
        return inputDatetime;
    }

    public void setInputDatetime(Date inputDatetime) {
        this.inputDatetime = inputDatetime;
    }

    public Integer getError_TypeNo() {
        return error_TypeNo;
    }

    public void setError_TypeNo(Integer error_TypeNo) {
        this.error_TypeNo = error_TypeNo;
    }

    @Override
    public String toString() {
        return "AUTOFLOOR_OP_ISSUE{" +
                "id=" + id +
                ", area='" + area + '\'' +
                ", areaint=" + areaint +
                ", floor='" + floor + '\'' +
                ", product='" + product + '\'' +
                ", sn='" + sn + '\'' +
                ", lineName='" + lineName + '\'' +
                ", preStation='" + preStation + '\'' +
                ", stationName='" + stationName + '\'' +
                ", machineNo='" + machineNo + '\'' +
                ", empNo='" + empNo + '\'' +
                ", errorType=" + errorType +
                ", inputDatetime=" + inputDatetime +
                ", ERRORTYPESUM=" + ERRORTYPESUM +
                ", error_TypeNo=" + error_TypeNo +
                ", error_TypeTTL=" + error_TypeTTL +
                ", error_TypeNo2=" + error_TypeNo2 +
                ", error_TypeNo3=" + error_TypeNo3 +
                ", error_TypeNo4=" + error_TypeNo4 +
                ", error_TypeNo5=" + error_TypeNo5 +
                ", error_TypeNo6=" + error_TypeNo6 +
                ", error_TypeNo7=" + error_TypeNo7 +
                ", error_TypeNo8=" + error_TypeNo8 +
                '}';
    }
}