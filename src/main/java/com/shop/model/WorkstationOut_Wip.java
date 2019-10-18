package com.shop.model;

/**
 * @author
 * @create 2019-08-28 19:12
 */
public class WorkstationOut_Wip {
    private String station_name;
    private Integer station_Uph;

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public Integer getStation_Uph() {
        return station_Uph;
    }

    public void setStation_Uph(Integer station_Uph) {
        this.station_Uph = station_Uph;
    }

    @Override
    public String toString() {
        return "WorkstationOut_Wip{" +
                "station_name='" + station_name + '\'' +
                ", station_Uph=" + station_Uph +
                '}';
    }
}
