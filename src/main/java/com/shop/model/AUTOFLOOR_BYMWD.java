package com.shop.model;

import java.math.BigDecimal;
import java.util.Date;

public class AUTOFLOOR_BYMWD {
    private BigDecimal id;

    private String tMonth;

    private String tRemark;

    private String tMark;

    private Date startDate;

    private Date endDate;

    private BigDecimal flag;

    private Integer Health;

    private Integer UNHealth;

    private Integer Warning;

    public Integer getHealth() {
        return Health;
    }

    public void setHealth(Integer health) {
        Health = health;
    }

    public Integer getUNHealth() {
        return UNHealth;
    }

    public void setUNHealth(Integer UNHealth) {
        this.UNHealth = UNHealth;
    }

    public Integer getWarning() {
        return Warning;
    }

    public void setWarning(Integer warning) {
        Warning = warning;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String gettMonth() {
        return tMonth;
    }

    public void settMonth(String tMonth) {
        this.tMonth = tMonth == null ? null : tMonth.trim();
    }

    public String gettRemark() {
        return tRemark;
    }

    public void settRemark(String tRemark) {
        this.tRemark = tRemark == null ? null : tRemark.trim();
    }

    public String gettMark() {
        return tMark;
    }

    public void settMark(String tMark) {
        this.tMark = tMark == null ? null : tMark.trim();
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

    public BigDecimal getFlag() {
        return flag;
    }

    public void setFlag(BigDecimal flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "AUTOFLOOR_BYMWD{" +
                "id=" + id +
                ", tMonth='" + tMonth + '\'' +
                ", tRemark='" + tRemark + '\'' +
                ", tMark='" + tMark + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", flag=" + flag +
                '}';
    }
}