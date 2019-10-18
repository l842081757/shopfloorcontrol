package com.shop.model;

/**
 * @author
 * @create 2019-08-21 16:26
 */
/* 不达标数据*/
public class NotUP {

private String NotStation_Name;

private String Nottime_slot;

private Integer Notlable;

    public String getNotStation_Name() {
        return NotStation_Name;
    }

    public void setNotStation_Name(String notStation_Name) {
        NotStation_Name = notStation_Name;
    }

    public String getNottime_slot() {
        return Nottime_slot;
    }

    public void setNottime_slot(String nottime_slot) {
        Nottime_slot = nottime_slot;
    }

    public Integer getNotlable() {
        return Notlable;
    }

    public void setNotlable(Integer notlable) {
        Notlable = notlable;
    }

    @Override
    public String toString() {
        return "NotUP{" +
                "NotStation_Name='" + NotStation_Name + '\'' +
                ", Nottime_slot='" + Nottime_slot + '\'' +
                ", Notlable=" + Notlable +
                '}';
    }
}
