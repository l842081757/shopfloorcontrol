package com.shop.shopfloorcontroller;

import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2019-08-24 17:14
 */
@Component
public class TaskService {

    /*当前时间 Timestamp */
    public  Timestamp getActionTimestamp() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:MM:ss");
        String format = df.format(new Date());
        Date parse = null;
        try {
            parse = df.parse(format);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Timestamp Timestamp = new Timestamp(parse.getTime());
        return Timestamp;
    }

  /*  @Scheduled(fixedRate=5000)*/
    /*当前时间sqldate*/
    public List<java.sql.Date> Schedule()  {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String format = df.format(new Date());
        Calendar Yesterday = Calendar.getInstance();
        Yesterday.add(Calendar.DAY_OF_MONTH,-1);

        Calendar Tomorrow = Calendar.getInstance();
        Tomorrow.add(Calendar.DAY_OF_MONTH,1);

        Calendar YesterWeek = Calendar.getInstance();
        YesterWeek.add(Calendar.DAY_OF_MONTH,-7);

        Calendar YesterMonth = Calendar.getInstance();
        YesterMonth.add(Calendar.MONTH,-1);

        Date parse = null;
        try {
            parse = df.parse(format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<java.sql.Date> dateList = new ArrayList<>();
        java.sql.Date sqldate = new  java.sql.Date(parse.getTime());
        java.sql.Date YesterdayDate = new  java.sql.Date(Yesterday.getTimeInMillis());
        java.sql.Date TomorrowDate = new  java.sql.Date(Tomorrow.getTimeInMillis());
        java.sql.Date YesterWeekDate = new  java.sql.Date(YesterWeek.getTimeInMillis());
        java.sql.Date YesterMonthDate = new  java.sql.Date(YesterMonth.getTimeInMillis());
        dateList.add(0,sqldate);
        dateList.add(1,YesterdayDate);
        dateList.add(2,TomorrowDate);
        dateList.add(3,YesterWeekDate);
        dateList.add(4,YesterMonthDate);
        return dateList;
    }
    /*获取当前时间 年 月 日 时 分 秒*/
    public List<Integer> getTime(Date Date){
        List<Integer> TimeList = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            /*年*/
            String substring = df.format(new Date()).substring(0, 4);
            /*月*/
            int Month = calendar.get(Calendar.MONTH) + 1;
            /*日*/
            int DAY = calendar.get(Calendar.DAY_OF_MONTH);
            /*时*/
            int HOUR = calendar.get(Calendar.HOUR_OF_DAY);
            /*分*/
            int MINUTE = calendar.get(Calendar.MINUTE);
            /*秒*/
            int SECOND = calendar.get(Calendar.SECOND);
            /*毫*/
            long timeInMillis = calendar.getTimeInMillis();
        TimeList.add(0,Month );
        TimeList.add(1,DAY );
        TimeList.add(2,HOUR );
        TimeList.add(3,MINUTE );
        TimeList.add(4,SECOND );


        return  TimeList;
    }
    /*获取当前时间日期*/
    public String getactiondate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String format = df.format(new Date());
        Calendar calendar = Calendar.getInstance();

        Date parse = null;
        try {
            parse = df.parse(format);

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<java.sql.Date> dateList = new ArrayList<>();
        java.sql.Date sqldate = new  java.sql.Date(parse.getTime());
        java.sql.Date endsqldate = new  java.sql.Date(calendar.getTimeInMillis());
        dateList.add(0, sqldate);
        dateList.add(1, endsqldate);

       return endsqldate.toString();

    }





}
