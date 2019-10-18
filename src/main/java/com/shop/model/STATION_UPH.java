package com.shop.model;

/**
 * @author
 * @create 2019-08-21 19:05
 */
public class STATION_UPH {
    private String  TIME_SLOT;
    private Integer DFU;
    private Integer FCT;
    private Integer CELL_S1;
    private Integer CELL_S2;
    private Integer CELL_S3;
    private Integer CCT_COMBO;
    private Integer WIFIBT_W1;
    private Integer WIFIBT_W2;
    private Integer UWB;
    private Integer SCOND;

    public String getTIME_SLOT() {
        return TIME_SLOT;
    }

    public void setTIME_SLOT(String TIME_SLOT) {


        this.TIME_SLOT = TIME_SLOT;
    }

    public Integer getDFU() {

        return DFU;
    }

    public void setDFU(Integer DFU) {
        if (DFU==null){
            DFU=0;
        }

        this.DFU = DFU;
    }

    public Integer getFCT() {
        return FCT;
    }

    public void setFCT(Integer FCT) {
        if (FCT==null){
            FCT=0;
        }
        this.FCT = FCT;
    }

    public Integer getCELL_S1() {
        return CELL_S1;
    }

    public void setCELL_S1(Integer CELL_S1) {
        if (CELL_S1==null){
            CELL_S1=0;
        }
        this.CELL_S1 = CELL_S1;
    }

    public Integer getCELL_S2() {
        return CELL_S2;
    }

    public void setCELL_S2(Integer CELL_S2) {
        if (CELL_S2==null){
            CELL_S2=0;
        }
        this.CELL_S2 = CELL_S2;
    }

    public Integer getCELL_S3() {
        return CELL_S3;
    }

    public void setCELL_S3(Integer CELL_S3) {
        if (CELL_S3==null){
            CELL_S3=0;
        }
        this.CELL_S3 = CELL_S3;
    }

    public Integer getCCT_COMBO() {
        return CCT_COMBO;
    }

    public void setCCT_COMBO(Integer CCT_COMBO) {
        if (CCT_COMBO==null){
            CCT_COMBO=0;
        }
        this.CCT_COMBO = CCT_COMBO;
    }

    public Integer getWIFIBT_W1() {
        return WIFIBT_W1;
    }

    public void setWIFIBT_W1(Integer WIFIBT_W1) {
        if (WIFIBT_W1==null){
            WIFIBT_W1=0;
        }
        this.WIFIBT_W1 = WIFIBT_W1;
    }

    public Integer getWIFIBT_W2() {
        return WIFIBT_W2;
    }

    public void setWIFIBT_W2(Integer WIFIBT_W2) {
        if (WIFIBT_W2==null){
            WIFIBT_W2=0;
        }
        this.WIFIBT_W2 = WIFIBT_W2;
    }

    public Integer getUWB() {
        return UWB;
    }

    public void setUWB(Integer UWB) {
        if (UWB==null){
            UWB=0;
        }
        this.UWB = UWB;
    }

    public Integer getSCOND() {
        return SCOND;
    }

    public void setSCOND(Integer SCOND) {
        if (SCOND==null){
            SCOND=0;
        }
        this.SCOND = SCOND;
    }

    @Override
    public String toString() {
        return "STATION_UPH{" +
                "TIME_SLOT='" + TIME_SLOT + '\'' +
                ", DFU=" + DFU +
                ", FCT=" + FCT +
                ", CELL_S1=" + CELL_S1 +
                ", CELL_S2=" + CELL_S2 +
                ", CELL_S3=" + CELL_S3 +
                ", CCT_COMBO=" + CCT_COMBO +
                ", WIFIBT_W1=" + WIFIBT_W1 +
                ", WIFIBT_W2=" + WIFIBT_W2 +
                ", UWB=" + UWB +
                ", SCOND=" + SCOND +
                '}';
    }
}
