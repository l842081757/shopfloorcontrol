package com.shop.model;

import org.elasticsearch.index.mapper.IdFieldMapper;

/**
 * @author
 * @create 2019-08-19 16:15
 */
public class AutoFloorUphS {
        private String TIME_SLOT;
        private String STATION_NAME;
        private Integer UPHS;
        private Integer hUPHS;

    public Integer gethUPHS() {
        return hUPHS;
    }

    public void sethUPHS(Integer hUPHS) {
        this.hUPHS = hUPHS;
    }

    public String getTIME_SLOT() {
        return TIME_SLOT;
    }

    public void setTIME_SLOT(String TIME_SLOT) {
        this.TIME_SLOT = TIME_SLOT;
    }

    public String getSTATION_NAME() {
        return STATION_NAME;
    }

    public void setSTATION_NAME(String STATION_NAME) {
        this.STATION_NAME = STATION_NAME;
    }

    public Integer getUPHS() {
        return UPHS;
    }

    public void setUPHS(Integer UPHS) {

        this.UPHS = UPHS;
    }

    @Override
    public String toString() {
        return "AutoFloorUphS{" +
                "TIME_SLOT='" + TIME_SLOT + '\'' +
                ", STATION_NAME='" + STATION_NAME + '\'' +
                ", UPHS=" + UPHS +
                '}';
    }
}
