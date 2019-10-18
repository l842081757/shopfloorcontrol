package com.shop.model;

/**
 * @author
 * @create 2019-08-21 15:51
 */
public class AmountsUPH {
    /* 机台号*/
    private  String  Machine_no;

    /*机台目标*/
    private Integer  amounttarget;

    /*机台UPH*/
    private Integer Amountuph;
    /*线体名称*/
    private String  LINE_NAME;
    /*Fail数量*/
    private Integer FAIL_Q;
    /*直通率*/
    private Double FPY;
    /*良率*/
    private Double YIELD;
    /*误测率*/
    private Double MISDETET;
    /*直通率目标*/
    private Double FPYTarget;
    /*Unknown*/
    private Double Unknown;
    /*Wip */
    private String WipMACHINE_NO ;
    private Integer WipMachine_Uph;

    public Double getUnknown() {
        return Unknown;
    }

    public void setUnknown(Double unknown) {
        Unknown = unknown;
    }

    public Double getFPYTarget() {
        return FPYTarget;
    }

    public void setFPYTarget(Double FPYTarget) {
        this.FPYTarget = FPYTarget;
    }

    public Integer getFAIL_Q() {
        return FAIL_Q;
    }

    public void setFAIL_Q(Integer FAIL_Q) {
        this.FAIL_Q = FAIL_Q;
    }

    public Double getFPY() {
        return FPY;
    }

    public void setFPY(Double FPY) {
        this.FPY = FPY;
    }

    public Double getYIELD() {
        return YIELD;
    }

    public void setYIELD(Double YIELD) {
        this.YIELD = YIELD;
    }

    public Double getMISDETET() {
        return MISDETET;
    }

    public void setMISDETET(Double MISDETET) {
        this.MISDETET = MISDETET;
    }

    public String getLINE_NAME() {
        return LINE_NAME;
    }

    public void setLINE_NAME(String LINE_NAME) {
        this.LINE_NAME = LINE_NAME;
    }

    public String getMachine_no() {
        return Machine_no;
    }

    public void setMachine_no(String machine_no) {
        Machine_no = machine_no;
    }

    public Integer getAmounttarget() {
        return amounttarget;
    }

    public void setAmounttarget(Integer amounttarget) {
        this.amounttarget = amounttarget;
    }

    public Integer getAmountuph() {
        return Amountuph;
    }

    public void setAmountuph(Integer amountuph) {
        Amountuph = amountuph;
    }

    public String getWipMACHINE_NO() {
        return WipMACHINE_NO;
    }

    public void setWipMACHINE_NO(String wipMACHINE_NO) {
        WipMACHINE_NO = wipMACHINE_NO;
    }

    public Integer getWipMachine_Uph() {
        return WipMachine_Uph;
    }

    public void setWipMachine_Uph(Integer wipMachine_Uph) {
        if (wipMachine_Uph==null){
            WipMachine_Uph = 0;
        }

        WipMachine_Uph = wipMachine_Uph;
    }

    @Override
    public String toString() {
        return "AmountsUPH{" +
                "Machine_no='" + Machine_no + '\'' +
                ", amounttarget=" + amounttarget +
                ", Amountuph=" + Amountuph +
                ", WipMACHINE_NO='" + WipMACHINE_NO + '\'' +
                ", WipMachine_Uph=" + WipMachine_Uph +
                '}';
    }
}
