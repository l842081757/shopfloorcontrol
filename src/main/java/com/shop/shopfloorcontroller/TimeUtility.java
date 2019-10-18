package com.shop.shopfloorcontroller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class TimeUtility {
    public Boolean Timequantum0030_0130(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("00:30");
            //定义结束时间
            endTime2030 = df.parse("01:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0130_0230(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("01:30");
            //定义结束时间
            endTime2030 = df.parse("02:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0230_0330(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("02:30");
            //定义结束时间
            endTime2030 = df.parse("03:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0330_0430(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("03:30");
            //定义结束时间
            endTime2030 = df.parse("04:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0430_0530(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("04:30");
            //定义结束时间
            endTime2030 = df.parse("05:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0530_0630(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("05:30");
            //定义结束时间
            endTime2030 = df.parse("06:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0630_0730(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("06:30");
            //定义结束时间
            endTime2030 = df.parse("07:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0730_0830(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("07:30");
            //定义结束时间
            endTime2030 = df.parse("08:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0830_0930(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime0830 = null;
        Date endTime0930 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime0830 = df.parse("8:30");
            //定义结束时间
            endTime0930 = df.parse("9:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime0830,endTime0930);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0930_1230(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime0830 = null;
        Date endTime0930 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime0830 = df.parse("9:30");
            //定义结束时间
            endTime0930 = df.parse("12:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime0830,endTime0930);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum1230_1730(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime0830 = null;
        Date endTime0930 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime0830 = df.parse("12:30");
            //定义结束时间
            endTime0930 = df.parse("17:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime0830,endTime0930);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum1230_1930(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime0830 = null;
        Date endTime0930 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime0830 = df.parse("12:30");
            //定义结束时间
            endTime0930 = df.parse("19:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime0830,endTime0930);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }

    public Boolean Timequantum1230_1830(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime0830 = null;
        Date endTime0930 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime0830 = df.parse("12:30");
            //定义结束时间
            endTime0930 = df.parse("18:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime0830,endTime0930);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }


    public Boolean Timequantum0930_1030(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime0930 = null;
        Date endTime1030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime0930 = df.parse("9:30");
            //定义结束时间
            endTime1030 = df.parse("10:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime0930,endTime1030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum1030_1130(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1030 = null;
        Date endTime1130 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1030 = df.parse("10:30");
            //定义结束时间
            endTime1130 = df.parse("11:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1030,endTime1130);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum1130_1230(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1130 = null;
        Date endTime1230 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1130 = df.parse("11:30");
            //定义结束时间
            endTime1230 = df.parse("12:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1130,endTime1230);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum1230_1330(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1230 = null;
        Date endTime1330 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1230 = df.parse("12:30");
            //定义结束时间
            endTime1330 = df.parse("13:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1230,endTime1330);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }

    public Boolean Timequantum1330_1430(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1330 = null;
        Date endTime1430 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1330 = df.parse("13:30");
            //定义结束时间
            endTime1430 = df.parse("14:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1330,endTime1430);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }

    public Boolean Timequantum1430_1530(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1430 = null;
        Date endTime1530 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1430 = df.parse("14:30");
            //定义结束时间
            endTime1530 = df.parse("15:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1430,endTime1530);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum1530_1630(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1530 = null;
        Date endTime1630 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1530 = df.parse("15:30");
            //定义结束时间
            endTime1630 = df.parse("16:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1530,endTime1630);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum1630_1730(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1630 = null;
        Date endTime1730 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1630 = df.parse("16:30");
            //定义结束时间
            endTime1730 = df.parse("17:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1630,endTime1730);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }

    public Boolean Timequantum1730_1830(){
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
            //初始化
            Date nowTime =null;
            Date beginTime1730 = null;
            Date endTime1830 = null;
             try {
                     //格式化当前时间格式
                nowTime = df.parse(df.format(new Date()));
                    //定义开始时间
                 beginTime1730 = df.parse("17:30");
                    //定义结束时间
                 endTime1830 = df.parse("18:30");

            } catch (Exception e) {
            e.printStackTrace();

             }
        //调用判断方法
         boolean flag = belongCalendar(nowTime, beginTime1730,endTime1830);
             //输出为结果
            if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
             }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
             }

    }

    public Boolean Timequantum1830_1930(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1830 = null;
        Date endTime1930 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1830 = df.parse("18:30");
            //定义结束时间
            endTime1930 = df.parse("19:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1830,endTime1930);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum1930_2030(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("19:30");
            //定义结束时间
            endTime2030 = df.parse("20:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum2030_2130(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("20:30");
            //定义结束时间
            endTime2030 = df.parse("21:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum2130_2230(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("21:30");
            //定义结束时间
            endTime2030 = df.parse("22:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum2230_2330(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("22:30");
            //定义结束时间
            endTime2030 = df.parse("23:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum2330_0030(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("23:30");
            //定义结束时间
            endTime2030 = df.parse("24:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }
    }
    public Boolean Timequantum2130_0030(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("21:30");
            //定义结束时间
            endTime2030 = df.parse("24:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }
    }

    public Boolean Timequantum0030_0830(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("00:30");
            //定义结束时间
            endTime2030 = df.parse("08:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }
    }
    public Boolean Timequantum0000_0830(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("00:00");
            //定义结束时间
            endTime2030 = df.parse("08:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }
    }
    public Boolean Timequantum0030_0730(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("00:30");
            //定义结束时间
            endTime2030 = df.parse("07:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }
    }
    public Boolean Timequantum0030_0630(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("00:30");
            //定义结束时间
            endTime2030 = df.parse("06:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }
    }
    public Boolean Timequantum0030_0530(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("00:30");
            //定义结束时间
            endTime2030 = df.parse("05:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }
    }

    public Boolean Timequantum0830_1029(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("8:30");
            //定义结束时间
            endTime2030 = df.parse("10:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum2030_2229(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("20:30");
            //定义结束时间
            endTime2030 = df.parse("22:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum1030_2029(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("10:29");
            //定义结束时间
            endTime2030 = df.parse("20:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum2029_0000(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("20:30");
            //定义结束时间
            endTime2030 = df.parse("24:00");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0000_2029(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("00:01");
            //定义结束时间
            endTime2030 = df.parse("20:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0830_2029(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("08:30");
            //定义结束时间
            endTime2030 = df.parse("20:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0730_2029(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("07:30");
            //定义结束时间
            endTime2030 = df.parse("20:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0830_0000(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("08:30");
            //定义结束时间
            endTime2030 = df.parse("24:00");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0000_0530(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("00:00");
            //定义结束时间
            endTime2030 = df.parse("05:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0000_0630(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("00:00");
            //定义结束时间
            endTime2030 = df.parse("06:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0000_0730(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("00:00");
            //定义结束时间
            endTime2030 = df.parse("07:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum0830_1230(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("08:30");
            //定义结束时间
            endTime2030 = df.parse("12:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }
    public Boolean Timequantum1230_2030(){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        //初始化
        Date nowTime =null;
        Date beginTime1930 = null;
        Date endTime2030 = null;
        try {
            //格式化当前时间格式
            nowTime = df.parse(df.format(new Date()));
            //定义开始时间
            beginTime1930 = df.parse("12:30");
            //定义结束时间
            endTime2030 = df.parse("20:30");

        } catch (Exception e) {
            e.printStackTrace();

        }
        //调用判断方法
        boolean flag = belongCalendar(nowTime, beginTime1930,endTime2030);
        //输出为结果
        if(flag){
            //处于规定的时间段内，执行对应的逻辑代码
            return true;
        }else{
            //不处于规定的时间段内，执行对应的逻辑代码
            return false;
        }

    }

/**
      * 判断时间是否在时间段内
      * @param nowTime
      * @param beginTime
      * @param endTime
      * @return
      */
 public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        //设置当前时间
         Calendar date = Calendar.getInstance();
         date.setTime(nowTime);
        //设置开始时间
         Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        //设置结束时间
        Calendar end = Calendar.getInstance();
         end.setTime(endTime);
         //处于开始时间之后，和结束时间之前的判断
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {

            return false;
        }
        }
        }
