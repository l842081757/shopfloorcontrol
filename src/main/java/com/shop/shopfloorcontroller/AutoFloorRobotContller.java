package com.shop.shopfloorcontroller;


import com.shop.dao.AUTOFLOOR_TARGETMapper;
import com.shop.model.AUTOFLOOR_TARGET;
import com.shop.model.AutoFloor_Wait_Time;
import com.shop.services.AUTOFLOOR_TARGEService;
import com.shop.services.AutoFloor_RoBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * @author
 * @create 2019-09-25 10:55
 */
@Controller
public class AutoFloorRobotContller {

    @Autowired
    AutoFloor_RoBotService autoFloor_roBotService;

    @Autowired
    AUTOFLOOR_TARGEService autofloorService;

    @Autowired
    TaskService taskService;

    @Autowired
    TimeUtility timeUtility;
    @Autowired
    AUTOFLOOR_TARGETMapper autofloor_targetMapper;

    @RequestMapping(value = "/AllfloorRobot",method = RequestMethod.GET)
    public String AutoFloorRobot(@RequestParam("TimeDay") String TimeDay,@RequestParam(value = "FloorName",required = false) String FloorName,Map map){
        List<Integer> TimeList = taskService.getTime(new Date());
        Integer Htime = TimeList.get(2);
        Integer MINUTEs = TimeList.get(3);

        List<java.sql.Date> schedule = taskService.Schedule();
        /*当前日期*/
        Date schedules =schedule.get(0);
        /*当前时间*/
        Date date = schedule.get(0);
        /*当前日期减1*/
        Date YesterdayDate =schedule.get(1);
        /*当前日期加1*/
        Date TomorrowDate =schedule.get(2);
        /*当前时间*/
        /*过24时 时间变量*/
        Date StartDate =null;
        Date EndDate=null;
        if (timeUtility.Timequantum2029_0000()){
            StartDate =date;
            EndDate=TomorrowDate;
        }else if (timeUtility.Timequantum0000_2029()){
            StartDate =YesterdayDate;
            EndDate=date;
        }
        if (timeUtility.Timequantum0000_0830()){
            schedules =YesterdayDate;
        }
        List<AUTOFLOOR_TARGET> AutoTargets=new ArrayList<>();
        //班次天类别
        String DayType=null;
        if (timeUtility.Timequantum0830_2029()){
            DayType="Day";
        }else {
            DayType="Night";
        }
        if (timeUtility.Timequantum0000_0830()){
            schedules=YesterdayDate;
        }

        if (TimeDay.equals("two")){
            AutoTargets = autofloor_targetMapper.RobotLinenameTarGet("Day",FloorName,FloorName+"ARF", schedules);
        }else if (TimeDay.equals("Day")){
            AutoTargets = autofloor_targetMapper.RobotLinenameTarGet("Day",FloorName,FloorName+"ARF", schedules);
        }else if (TimeDay.equals("Night")){
            AutoTargets = autofloor_targetMapper.RobotLinenameTarGet("Night",FloorName,FloorName+"ARF", StartDate);
        }else if (TimeDay.equals("Yesterday")){
            AutoTargets = autofloor_targetMapper. RobotLinenameTarGetYesterday(FloorName,FloorName+"ARF", schedules,DayType);
        }




        List<AutoFloor_Wait_Time> AutoFloor_Wait_TimeList= new ArrayList<>();

        Integer TTLAnonormalsumA  =0;
        Double  TTLANoNormalRateA =0.0;
        Double  TTLAPlateRateA    =0.0;
        Integer TTLAnonormalsumB  =0;
        Double  TTLANoNormalRateB =0.0;
        Double  TTLAPlateRateB    =0.0;
        Integer TTLAnonormalsumC  =0;
        Double  TTLANoNormalRateC =0.0;
        Double  TTLAPlateRateC    =0.0;
        Integer TTLAnonormalsumD  =0;
        Double  TTLANoNormalRateD =0.0;
        Double  TTLAPlateRateD    =0.0;
        Integer TTLAnonormalsumE  =0;
        Double  TTLANoNormalRateE =0.0;
        Double  TTLAPlateRateE    =0.0;
        Integer TTLAnonormalsumF  =0;
        Double  TTLANoNormalRateF =0.0;
        Double  TTLAPlateRateF    =0.0;
        Integer TTLAnonormalsumG  =0;
        Double  TTLANoNormalRateG =0.0;
        Double  TTLAPlateRateG    =0.0;
        Integer TTLAnonormalsumH  =0;
        Double  TTLANoNormalRateH =0.0;
        Double  TTLAPlateRateH    =0.0;

        Integer TTLLinenameNoNormalSum  =0;
        Double  TTLLinenameNoNormalRate =0.0;
        Double  TTLLinenamePlateRate    =0.0;

        int   LineSum=0;
        for (int i = 0; i < AutoTargets.size(); i++) {
            LineSum++;
            int one=0;
            int two=0;
            int three=0;
            Integer LinenameNoNormalSum=0;
            Double LineTTLNoNormalRateOne=0.0;
            Double LineTTLNoNormalRateTwo=0.0;
            Double LineTTLNoNormalRateThree=0.0;

            Double LineTTLPlateRateOne=0.0;
            Double LineTTLPlateRateTwo=0.0;
            Double LineTTLPlateRateThree=0.0;
            AUTOFLOOR_TARGET autofloorTarget = AutoTargets.get(i);
            AutoFloor_Wait_Time autoFloor_wait_times= new AutoFloor_Wait_Time();
            autoFloor_wait_times.setLine_name(autofloorTarget.getLineName());

            AutoFloor_Wait_Time autoFloor_wait_timeA =null;
            if (TimeDay.equals("two")){
                if (timeUtility.Timequantum0830_1029()){
                    autoFloor_wait_timeA=autoFloor_roBotService.RobotAnalyze08(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-A");
                    if (autoFloor_wait_timeA!=null) {
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else if (timeUtility.Timequantum2030_2229()) {
                    autoFloor_wait_timeA=autoFloor_roBotService.RobotAnalyze20(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-A");
                    if (autoFloor_wait_timeA!=null) {
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else {
                    autoFloor_wait_timeA=autoFloor_roBotService.RobotAnalyze(FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-A");

                }
            }else if (TimeDay.equals("Day")){
                autoFloor_wait_timeA = autoFloor_roBotService.RobotAnalyzeDay(schedules,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-A");
                if (autoFloor_wait_timeA!=null) {
                    if (timeUtility.Timequantum0830_1230()){
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum1230_2030()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum1230_1930()){
                                autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum1230_1730()){
                                autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum1230_1830()){
                                autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }
                    }else {
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((10) * 3600));
                            autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((10) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((8) * 3600));
                            autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((8) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((9) * 3600));
                            autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((9) * 3600));
                        }
                    }
                }
            }else if (TimeDay.equals("Night")){
                autoFloor_wait_timeA = autoFloor_roBotService.RobotAnalyzeNight(StartDate,EndDate,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-A");
                if (autoFloor_wait_timeA!=null) {
                    if (timeUtility.Timequantum2029_0000()){
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum0000_2029()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum0000_0730()){
                                autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum0000_0530()){
                                autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum0000_0630()){
                                autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }
                    }
                }
            }else if (TimeDay.equals("Yesterday")){
                autoFloor_wait_timeA = autoFloor_roBotService.RobotAnalyzeYesterday(StartDate,EndDate,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-A");
                if (autoFloor_wait_timeA!=null) {
                    if (autofloorTarget.getWorkingHours().doubleValue() == 19) {
                        if (timeUtility.Timequantum0000_0730()){
                            autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((Htime+12) * 3600));
                            autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((Htime+12) * 3600));
                        }else {
                            autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((19) * 3600));
                            autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((19) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 15.34) {
                        if (timeUtility.Timequantum0000_0530()){
                            autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((Htime+10) * 3600));
                            autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((Htime+10) * 3600));
                        }else {
                            autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((15) * 3600));
                            autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((15) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 17.34) {
                        if (timeUtility.Timequantum0000_0630()){
                            autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((Htime+11) * 3600));
                            autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((Htime+11) * 3600));
                        }else {
                            autoFloor_wait_timeA.setAwaitNoNormalRate(autoFloor_wait_timeA.getAwaitNoNormalRate() / ((17) * 3600));
                            autoFloor_wait_timeA.setAwaitPlateRate(autoFloor_wait_timeA.getAwaitPlateRate() / ((17) * 3600));
                        }
                    }
                  }
            }
            if (autoFloor_wait_timeA!=null){
                one++;
                Integer awaitNoNormalSum = autoFloor_wait_timeA.getAwaitNoNormalSum();
                TTLAnonormalsumA+=awaitNoNormalSum;
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setANoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeA.getAwaitNoNormalRate();
                TTLANoNormalRateA+=awaitNoNormalRate;
                LineTTLNoNormalRateOne+=awaitNoNormalRate;
                autoFloor_wait_times.setANoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeA.getAwaitPlateRate();
                TTLAPlateRateA+=awaitPlateRate;
                LineTTLPlateRateOne+=awaitPlateRate;
                autoFloor_wait_times.setAPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setANoNormalSum(0);
                autoFloor_wait_times.setANoNormalRate(0.0);
                autoFloor_wait_times.setAPlateRate(0.0);
            }


            AutoFloor_Wait_Time autoFloor_wait_timeB = null;
            if (TimeDay.equals("two")){
                if (timeUtility.Timequantum0830_1029()){
                    autoFloor_wait_timeB=autoFloor_roBotService.RobotAnalyze08(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-B");
                    if (autoFloor_wait_timeB!=null) {
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else if (timeUtility.Timequantum2030_2229()) {
                    autoFloor_wait_timeB=autoFloor_roBotService.RobotAnalyze20(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-B");
                    if (autoFloor_wait_timeB!=null) {
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else {
                    autoFloor_wait_timeB=autoFloor_roBotService.RobotAnalyze(FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-B");

                }
            }else if (TimeDay.equals("Day")){
                autoFloor_wait_timeB = autoFloor_roBotService.RobotAnalyzeDay(schedules,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-B");
                if (autoFloor_wait_timeB!=null) {
                    if (timeUtility.Timequantum0830_1230()){
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum1230_2030()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum1230_1930()){
                                autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum1230_1730()){
                                autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum1230_1830()){
                                autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }
                    }else {
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((10) * 3600));
                            autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((10) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((8) * 3600));
                            autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((8) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((9) * 3600));
                            autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((9) * 3600));
                        }
                    }
                }

            }else if (TimeDay.equals("Night")){
                autoFloor_wait_timeB = autoFloor_roBotService.RobotAnalyzeNight(StartDate,EndDate,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-B");
                if (autoFloor_wait_timeB!=null) {
                    if (timeUtility.Timequantum2029_0000()){
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum0000_2029()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum0000_0730()){
                                autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum0000_0530()){
                                autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum0000_0630()){
                                autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }

                    }
                }

            }else if (TimeDay.equals("Yesterday")){
                autoFloor_wait_timeB = autoFloor_roBotService.RobotAnalyzeYesterday(StartDate,EndDate,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-B");
                if (autoFloor_wait_timeB!=null) {
                    if (autofloorTarget.getWorkingHours().doubleValue() ==19) {
                        if (timeUtility.Timequantum0000_0730()){
                            autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((Htime+12) * 3600));
                            autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((Htime+12) * 3600));
                        }else {
                            autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((19) * 3600));
                            autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((19) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 15.34) {
                        if (timeUtility.Timequantum0000_0530()){
                            autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((Htime+10) * 3600));
                            autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((Htime+10) * 3600));
                        }else {
                            autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((15) * 3600));
                            autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((15) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 17.34) {
                        if (timeUtility.Timequantum0000_0630()){
                            autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((Htime+11) * 3600));
                            autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((Htime+11) * 3600));
                        }else {
                            autoFloor_wait_timeB.setAwaitNoNormalRate(autoFloor_wait_timeB.getAwaitNoNormalRate() / ((17) * 3600));
                            autoFloor_wait_timeB.setAwaitPlateRate(autoFloor_wait_timeB.getAwaitPlateRate() / ((17) * 3600));
                        }
                    }
                }
            }

            if (autoFloor_wait_timeB!=null){
                two++;
                Integer awaitNoNormalSum = autoFloor_wait_timeB.getAwaitNoNormalSum();
                TTLAnonormalsumB+=awaitNoNormalSum;
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setBNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeB.getAwaitNoNormalRate();
                TTLANoNormalRateB+=awaitNoNormalRate;
                LineTTLNoNormalRateTwo+=awaitNoNormalRate;
                autoFloor_wait_times.setBNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeB.getAwaitPlateRate();
                TTLAPlateRateB+=awaitPlateRate;
                LineTTLPlateRateTwo+=awaitPlateRate;
                autoFloor_wait_times.setBPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setBNoNormalSum(0);
                autoFloor_wait_times.setBNoNormalRate(0.0);
                autoFloor_wait_times.setBPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeC = null;
            if (TimeDay.equals("two")){
                if (timeUtility.Timequantum0830_1029()){
                    autoFloor_wait_timeC=autoFloor_roBotService.RobotAnalyze08(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-C");
                    if (autoFloor_wait_timeC!=null) {
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else if (timeUtility.Timequantum2030_2229()) {
                    autoFloor_wait_timeC=autoFloor_roBotService.RobotAnalyze20(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-C");
                    if (autoFloor_wait_timeC!=null) {
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else {
                    autoFloor_wait_timeC=autoFloor_roBotService.RobotAnalyze(FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-C");

                }
            }else if (TimeDay.equals("Day")){
                autoFloor_wait_timeC = autoFloor_roBotService.RobotAnalyzeDay(schedules,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-C");
                if (autoFloor_wait_timeC!=null) {
                    if (timeUtility.Timequantum0830_1230()){
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum1230_2030()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum1230_1930()){
                                autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum1230_1730()){
                                autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum1230_1830()){
                                autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }
                    }else {
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((10) * 3600));
                            autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((10) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((8) * 3600));
                            autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((8) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((9) * 3600));
                            autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((9) * 3600));
                        }
                    }
                }
            }else if (TimeDay.equals("Night")){
                autoFloor_wait_timeC = autoFloor_roBotService.RobotAnalyzeNight(StartDate,EndDate, FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-C");
                if (autoFloor_wait_timeC!=null) {
                    if (timeUtility.Timequantum2029_0000()){
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum0000_2029()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum0000_0730()){
                                autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum0000_0530()){
                                autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum0000_0630()){
                                autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }

                    }
                }

            }else if (TimeDay.equals("Yesterday")){
                autoFloor_wait_timeC = autoFloor_roBotService.RobotAnalyzeYesterday(StartDate,EndDate,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-C");
                if (autoFloor_wait_timeC!=null) {
                    if (autofloorTarget.getWorkingHours().doubleValue() == 19) {
                        if (timeUtility.Timequantum0000_0730()){
                            autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((Htime+12) * 3600));
                            autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((Htime+12) * 3600));
                        }else {
                            autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((19) * 3600));
                            autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((19) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 15.34) {
                        if (timeUtility.Timequantum0000_0530()){
                            autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((Htime+10) * 3600));
                            autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((Htime+10) * 3600));
                        }else {
                            autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((15) * 3600));
                            autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((15) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 17.34) {
                        if (timeUtility.Timequantum0000_0630()){
                            autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((Htime+11) * 3600));
                            autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((Htime+11) * 3600));
                        }else {
                            autoFloor_wait_timeC.setAwaitNoNormalRate(autoFloor_wait_timeC.getAwaitNoNormalRate() / ((17) * 3600));
                            autoFloor_wait_timeC.setAwaitPlateRate(autoFloor_wait_timeC.getAwaitPlateRate() / ((17) * 3600));
                        }
                    }
                }
            }
            if (autoFloor_wait_timeC!=null){
                two++;
                Integer awaitNoNormalSum = autoFloor_wait_timeC.getAwaitNoNormalSum();
                TTLAnonormalsumC+=awaitNoNormalSum;
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setCNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeC.getAwaitNoNormalRate();
                TTLANoNormalRateC+=awaitNoNormalRate;
                LineTTLNoNormalRateTwo+=awaitNoNormalRate;
                autoFloor_wait_times.setCNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeC.getAwaitPlateRate();
                TTLAPlateRateC+=awaitPlateRate;
                LineTTLPlateRateTwo+=awaitPlateRate;
                autoFloor_wait_times.setCPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setCNoNormalSum(0);
                autoFloor_wait_times.setCNoNormalRate(0.0);
                autoFloor_wait_times.setCPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeD = null;
            if (TimeDay.equals("two")){
                if (timeUtility.Timequantum0830_1029()){
                    autoFloor_wait_timeD=autoFloor_roBotService.RobotAnalyze08(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-D");
                    if (autoFloor_wait_timeD!=null) {
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else if (timeUtility.Timequantum2030_2229()) {
                    autoFloor_wait_timeD=autoFloor_roBotService.RobotAnalyze20(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-D");
                    if (autoFloor_wait_timeD!=null) {
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else {
                    autoFloor_wait_timeD=autoFloor_roBotService.RobotAnalyze(FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-D");

                }
            }else if (TimeDay.equals("Day")){
                autoFloor_wait_timeD = autoFloor_roBotService.RobotAnalyzeDay(schedules,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-D");
                if (autoFloor_wait_timeD!=null) {
                    if (timeUtility.Timequantum0830_1230()){
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum1230_2030()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum1230_1930()){
                                autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum1230_1730()){
                                autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum1230_1830()){
                                autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }
                    }else {
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((10) * 3600));
                            autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((10) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((8) * 3600));
                            autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((8) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((9) * 3600));
                            autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((9) * 3600));
                        }
                    }
                }

            }else if (TimeDay.equals("Night")){
                autoFloor_wait_timeD = autoFloor_roBotService.RobotAnalyzeNight(StartDate,EndDate, FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-D");
                if (autoFloor_wait_timeD!=null) {
                    if (timeUtility.Timequantum2029_0000()){
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum0000_2029()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum0000_0730()){
                                autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum0000_0530()){
                                autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum0000_0630()){
                                autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }

                    }
                }
            }else if (TimeDay.equals("Yesterday")){
                autoFloor_wait_timeD = autoFloor_roBotService.RobotAnalyzeYesterday(StartDate,EndDate,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-D");
                if (autoFloor_wait_timeD!=null) {
                    if (autofloorTarget.getWorkingHours().doubleValue() == 19) {
                        if (timeUtility.Timequantum0000_0730()){
                            autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((Htime+12) * 3600));
                            autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((Htime+12) * 3600));
                        }else {
                            autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((19) * 3600));
                            autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((19) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 15.34) {
                        if (timeUtility.Timequantum0000_0530()){
                            autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((Htime+10) * 3600));
                            autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((Htime+10) * 3600));
                        }else {
                            autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((15) * 3600));
                            autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((15) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 17.34) {
                        if (timeUtility.Timequantum0000_0630()){
                            autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((Htime+11) * 3600));
                            autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((Htime+11) * 3600));
                        }else {
                            autoFloor_wait_timeD.setAwaitNoNormalRate(autoFloor_wait_timeD.getAwaitNoNormalRate() / ((17) * 3600));
                            autoFloor_wait_timeD.setAwaitPlateRate(autoFloor_wait_timeD.getAwaitPlateRate() / ((17) * 3600));
                        }
                    }
                }
            }
            if (autoFloor_wait_timeD!=null){
                two++;
                Integer awaitNoNormalSum = autoFloor_wait_timeD.getAwaitNoNormalSum();
                TTLAnonormalsumD+=awaitNoNormalSum;
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setDNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeD.getAwaitNoNormalRate();
                TTLANoNormalRateD+=awaitNoNormalRate;
                LineTTLNoNormalRateTwo+=awaitNoNormalRate;
                autoFloor_wait_times.setDNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeD.getAwaitPlateRate();
                TTLAPlateRateD+=awaitPlateRate;
                LineTTLPlateRateTwo+=awaitPlateRate;
                autoFloor_wait_times.setDPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setDNoNormalSum(0);
                autoFloor_wait_times.setDNoNormalRate(0.0);
                autoFloor_wait_times.setDPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeE = null;
            if (TimeDay.equals("two")){
                if (timeUtility.Timequantum0830_1029()){
                    autoFloor_wait_timeE=autoFloor_roBotService.RobotAnalyze08(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-E");
                    if (autoFloor_wait_timeE!=null) {
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else if (timeUtility.Timequantum2030_2229()) {
                    autoFloor_wait_timeE=autoFloor_roBotService.RobotAnalyze20(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-E");
                    if (autoFloor_wait_timeE!=null) {
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else {
                    autoFloor_wait_timeE=autoFloor_roBotService.RobotAnalyze(FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-E");

                }
            }else if (TimeDay.equals("Day")){
                autoFloor_wait_timeE = autoFloor_roBotService.RobotAnalyzeDay(schedules,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-E");
                if (autoFloor_wait_timeE!=null) {
                    if (timeUtility.Timequantum0830_1230()){
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum1230_2030()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum1230_1930()){
                                autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum1230_1730()){
                                autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum1230_1830()){
                                autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }
                    }else {
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((10) * 3600));
                            autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((10) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((8) * 3600));
                            autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((8) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((9) * 3600));
                            autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((9) * 3600));
                        }
                    }
                }
            }else if (TimeDay.equals("Night")){
                autoFloor_wait_timeE = autoFloor_roBotService.RobotAnalyzeNight(StartDate,EndDate, FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-E");
                if (autoFloor_wait_timeE!=null) {
                    if (timeUtility.Timequantum2029_0000()){
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum0000_2029()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum0000_0730()){
                                autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum0000_0530()){
                                autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum0000_0630()){
                                autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }

                    }
                }

            }else if (TimeDay.equals("Yesterday")){
                autoFloor_wait_timeE = autoFloor_roBotService.RobotAnalyzeYesterday(StartDate,EndDate,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-E");
                if (autoFloor_wait_timeE!=null) {
                    if (autofloorTarget.getWorkingHours().doubleValue() == 19) {
                        if (timeUtility.Timequantum0000_0730()){
                            autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((Htime+12) * 3600));
                            autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((Htime+12) * 3600));
                        }else {
                            autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((19) * 3600));
                            autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((19) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 15.34) {
                        if (timeUtility.Timequantum0000_0530()){
                            autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((Htime+10) * 3600));
                            autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((Htime+10) * 3600));
                        }else {
                            autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((15) * 3600));
                            autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((15) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 17.34) {
                        if (timeUtility.Timequantum0000_0630()){
                            autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((Htime+11) * 3600));
                            autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((Htime+11) * 3600));
                        }else {
                            autoFloor_wait_timeE.setAwaitNoNormalRate(autoFloor_wait_timeE.getAwaitNoNormalRate() / ((17) * 3600));
                            autoFloor_wait_timeE.setAwaitPlateRate(autoFloor_wait_timeE.getAwaitPlateRate() / ((17) * 3600));
                        }
                    }
                }
            }
            if (autoFloor_wait_timeE!=null){
                three++;
                Integer awaitNoNormalSum = autoFloor_wait_timeE.getAwaitNoNormalSum();
                TTLAnonormalsumE+=awaitNoNormalSum;
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setENoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeE.getAwaitNoNormalRate();
                TTLANoNormalRateE+=awaitNoNormalRate;
                LineTTLNoNormalRateThree+=awaitNoNormalRate;
                autoFloor_wait_times.setENoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeE.getAwaitPlateRate();
                TTLAPlateRateE+=awaitPlateRate;
                LineTTLPlateRateThree+=awaitPlateRate;
                autoFloor_wait_times.setEPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setENoNormalSum(0);
                autoFloor_wait_times.setENoNormalRate(0.0);
                autoFloor_wait_times.setEPlateRate(0.0);
            }
            AutoFloor_Wait_Time autoFloor_wait_timeF = null;
            if (TimeDay.equals("two")){
                if (timeUtility.Timequantum0830_1029()){
                    autoFloor_wait_timeF=autoFloor_roBotService.RobotAnalyze08(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-F");
                    if (autoFloor_wait_timeF!=null) {
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else if (timeUtility.Timequantum2030_2229()) {
                    autoFloor_wait_timeF=autoFloor_roBotService.RobotAnalyze20(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-F");
                    if (autoFloor_wait_timeF!=null) {
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else {
                    autoFloor_wait_timeF=autoFloor_roBotService.RobotAnalyze(FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-F");

                }
            }else if (TimeDay.equals("Day")){
                autoFloor_wait_timeF = autoFloor_roBotService.RobotAnalyzeDay(schedules,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-F");
                if (autoFloor_wait_timeF!=null) {
                    if (timeUtility.Timequantum0830_1230()){
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum1230_2030()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum1230_1930()){
                                autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum1230_1730()){
                                autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum1230_1830()){
                                autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }
                    }else {
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((10) * 3600));
                            autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((10) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((8) * 3600));
                            autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((8) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((9) * 3600));
                            autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((9) * 3600));
                        }
                    }
                }

            }else if (TimeDay.equals("Night")){
                autoFloor_wait_timeF = autoFloor_roBotService.RobotAnalyzeNight(StartDate,EndDate, FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-F");
                if (autoFloor_wait_timeF!=null) {
                    if (timeUtility.Timequantum2029_0000()){
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum0000_2029()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum0000_0730()){
                                autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum0000_0530()){
                                autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum0000_0630()){
                                autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }

                    }
                }
            }else if (TimeDay.equals("Yesterday")){
                autoFloor_wait_timeF = autoFloor_roBotService.RobotAnalyzeYesterday(StartDate,EndDate,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-F");
                if (autoFloor_wait_timeF!=null) {
                    if (autofloorTarget.getWorkingHours().doubleValue() == 19) {
                        if (timeUtility.Timequantum0000_0730()){
                            autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((Htime+12) * 3600));
                            autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((Htime+12) * 3600));
                        }else {
                            autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((19) * 3600));
                            autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((19) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 15.34) {
                        if (timeUtility.Timequantum0000_0530()){
                            autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((Htime+10) * 3600));
                            autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((Htime+10) * 3600));
                        }else {
                            autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((15) * 3600));
                            autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((15) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 17.34) {
                        if (timeUtility.Timequantum0000_0630()){
                            autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((Htime+11) * 3600));
                            autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((Htime+11) * 3600));
                        }else {
                            autoFloor_wait_timeF.setAwaitNoNormalRate(autoFloor_wait_timeF.getAwaitNoNormalRate() / ((17) * 3600));
                            autoFloor_wait_timeF.setAwaitPlateRate(autoFloor_wait_timeF.getAwaitPlateRate() / ((17) * 3600));
                        }
                    }
                }
            }
            if (autoFloor_wait_timeF!=null){
                three++;
                Integer awaitNoNormalSum = autoFloor_wait_timeF.getAwaitNoNormalSum();
                TTLAnonormalsumF+=awaitNoNormalSum;
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setFNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeF.getAwaitNoNormalRate();
                TTLANoNormalRateF+=awaitNoNormalRate;
                LineTTLNoNormalRateThree+=awaitNoNormalRate;
                autoFloor_wait_times.setFNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeF.getAwaitPlateRate();
                TTLAPlateRateF+=awaitPlateRate;
                LineTTLPlateRateThree+=awaitPlateRate;
                autoFloor_wait_times.setFPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setFNoNormalSum(0);
                autoFloor_wait_times.setFNoNormalRate(0.0);
                autoFloor_wait_times.setFPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeG = null;
            if (TimeDay.equals("two")){
                if (timeUtility.Timequantum0830_1029()){
                    autoFloor_wait_timeG=autoFloor_roBotService.RobotAnalyze08(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-G");
                    if (autoFloor_wait_timeG!=null) {
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else if (timeUtility.Timequantum2030_2229()) {
                    autoFloor_wait_timeG=autoFloor_roBotService.RobotAnalyze20(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-G");
                    if (autoFloor_wait_timeG!=null) {
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else {
                    autoFloor_wait_timeG=autoFloor_roBotService.RobotAnalyze(FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-G");

                }
            }else if (TimeDay.equals("Day")){
                autoFloor_wait_timeG = autoFloor_roBotService.RobotAnalyzeDay(schedules,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-G");
                if (autoFloor_wait_timeG!=null) {
                    if (timeUtility.Timequantum0830_1230()){
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum1230_2030()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum1230_1930()){
                                autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum1230_1730()){
                                autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum1230_1830()){
                                autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }
                    }else {
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((10) * 3600));
                            autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((10) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((8) * 3600));
                            autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((8) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((9) * 3600));
                            autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((9) * 3600));
                        }
                    }
                }
            }else if (TimeDay.equals("Night")){
                autoFloor_wait_timeG = autoFloor_roBotService.RobotAnalyzeNight(StartDate,EndDate, FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-G");
                if (autoFloor_wait_timeG!=null) {
                    if (timeUtility.Timequantum2029_0000()){
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum0000_2029()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum0000_0730()){
                                autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum0000_0530()){
                                autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum0000_0630()){
                                autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }

                    }
                }
            }else if (TimeDay.equals("Yesterday")){
                autoFloor_wait_timeG = autoFloor_roBotService.RobotAnalyzeYesterday(StartDate,EndDate,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-G");
                if (autoFloor_wait_timeG!=null) {
                    if (autofloorTarget.getWorkingHours().doubleValue() == 19) {
                        if (timeUtility.Timequantum0000_0730()){
                            autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((Htime+12) * 3600));
                            autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((Htime+12) * 3600));
                        }else {
                            autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((19) * 3600));
                            autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((19) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 15.34) {
                        if (timeUtility.Timequantum0000_0530()){
                            autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((Htime+10) * 3600));
                            autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((Htime+10) * 3600));
                        }else {
                            autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((15) * 3600));
                            autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((15) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 17.34) {
                        if (timeUtility.Timequantum0000_0630()){
                            autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((Htime+11) * 3600));
                            autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((Htime+11) * 3600));
                        }else {
                            autoFloor_wait_timeG.setAwaitNoNormalRate(autoFloor_wait_timeG.getAwaitNoNormalRate() / ((17) * 3600));
                            autoFloor_wait_timeG.setAwaitPlateRate(autoFloor_wait_timeG.getAwaitPlateRate() / ((17) * 3600));
                        }
                    }
                }
            }
            if (autoFloor_wait_timeG!=null){
                three++;
                Integer awaitNoNormalSum = autoFloor_wait_timeG.getAwaitNoNormalSum();
                TTLAnonormalsumG+=awaitNoNormalSum;
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setGNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeG.getAwaitNoNormalRate();
                TTLANoNormalRateG+=awaitNoNormalRate;
                LineTTLNoNormalRateThree+=awaitNoNormalRate;
                autoFloor_wait_times.setGNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeG.getAwaitPlateRate();
                TTLAPlateRateG+=awaitPlateRate;
                LineTTLPlateRateThree+=awaitPlateRate;
                autoFloor_wait_times.setGPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setGNoNormalSum(0);
                autoFloor_wait_times.setGNoNormalRate(0.0);
                autoFloor_wait_times.setGPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeH = null;
            if (TimeDay.equals("two")){
                if (timeUtility.Timequantum0830_1029()){
                    autoFloor_wait_timeH=autoFloor_roBotService.RobotAnalyze08(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-H");
                    if (autoFloor_wait_timeH!=null) {
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else if (timeUtility.Timequantum2030_2229()) {
                    autoFloor_wait_timeH=autoFloor_roBotService.RobotAnalyze20(date,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-H");
                    if (autoFloor_wait_timeH!=null) {
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }
                }else {
                    autoFloor_wait_timeH=autoFloor_roBotService.RobotAnalyze(FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-H");

                }
            }else if (TimeDay.equals("Day")){
                autoFloor_wait_timeH = autoFloor_roBotService.RobotAnalyzeDay(schedules,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-H");
                if (autoFloor_wait_timeH!=null) {
                    if (timeUtility.Timequantum0830_1230()){
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum1230_2030()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum1230_1930()){
                                autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum1230_1730()){
                                autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum1230_1830()){
                                autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }
                    }else {
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((10) * 3600));
                            autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((10) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((8) * 3600));
                            autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((8) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((9) * 3600));
                            autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((9) * 3600));
                        }
                    }
                }
            }else if (TimeDay.equals("Night")){
                autoFloor_wait_timeH = autoFloor_roBotService.RobotAnalyzeNight(StartDate,EndDate, FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-H");
                if (autoFloor_wait_timeH!=null) {
                    if (timeUtility.Timequantum2029_0000()){
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                        autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum0000_2029()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum0000_0730()){
                                autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((10) * 3600));
                                autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum0000_0530()){
                                autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((8) * 3600));
                                autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum0000_0630()){
                                autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                                autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((Htime+3) * 3600));
                            }else {
                                autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((9) * 3600));
                                autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((9) * 3600));
                            }
                        }

                    }
                }
            }else if (TimeDay.equals("Yesterday")){
                autoFloor_wait_timeH = autoFloor_roBotService.RobotAnalyzeYesterday(StartDate,EndDate,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-H");
                if (autoFloor_wait_timeH!=null) {
                    if (autofloorTarget.getWorkingHours().doubleValue() == 19) {
                        if (timeUtility.Timequantum0000_0730()){
                            autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((Htime+12) * 3600));
                            autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((Htime+12) * 3600));
                        }else {
                            autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((19) * 3600));
                            autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((19) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 15.34) {
                        if (timeUtility.Timequantum0000_0530()){
                            autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((Htime+10) * 3600));
                            autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((Htime+10) * 3600));
                        }else {
                            autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((15) * 3600));
                            autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((15) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 17.34) {
                        if (timeUtility.Timequantum0000_0630()){
                            autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((Htime+11) * 3600));
                            autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((Htime+11) * 3600));
                        }else {
                            autoFloor_wait_timeH.setAwaitNoNormalRate(autoFloor_wait_timeH.getAwaitNoNormalRate() / ((17) * 3600));
                            autoFloor_wait_timeH.setAwaitPlateRate(autoFloor_wait_timeH.getAwaitPlateRate() / ((17) * 3600));
                        }
                    }
                }
            }
            if (autoFloor_wait_timeH!=null){
                three++;
                Integer awaitNoNormalSum = autoFloor_wait_timeH.getAwaitNoNormalSum();
                TTLAnonormalsumH+=awaitNoNormalSum;
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setHNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeH.getAwaitNoNormalRate();
                TTLANoNormalRateH+=awaitNoNormalRate;
                LineTTLNoNormalRateThree+=awaitNoNormalRate;
                autoFloor_wait_times.setHNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeH.getAwaitPlateRate();
                TTLAPlateRateH+=awaitPlateRate;
                LineTTLPlateRateThree+=awaitPlateRate;
                autoFloor_wait_times.setHPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setHNoNormalSum(0);
                autoFloor_wait_times.setHNoNormalRate(0.0);
                autoFloor_wait_times.setHPlateRate(0.0);
            }
            Double LinenameNoNormalRate=0.0;
            Double LinenamePlateRate=0.0;

             LinenameNoNormalRate=LineTTLNoNormalRateOne+(LineTTLNoNormalRateTwo/3)+(LineTTLNoNormalRateThree/4)/3;
             LinenamePlateRate= LineTTLPlateRateOne+(LineTTLPlateRateTwo/3)+(LineTTLPlateRateThree/4)/3;

            TTLLinenameNoNormalSum+=LinenameNoNormalSum;
            TTLLinenameNoNormalRate+=LinenameNoNormalRate;
            TTLLinenamePlateRate+=LinenamePlateRate;
            autoFloor_wait_times.setLineNoNormalRate(LinenameNoNormalRate);
            autoFloor_wait_times.setLinePlateRate(LinenamePlateRate);
            autoFloor_wait_times.setLineNoNormalSum(LinenameNoNormalSum);
            AutoFloor_Wait_TimeList.add(autoFloor_wait_times);
        }




        Map<String,Object> TTLMap=new HashMap<>();

        TTLMap.put("TTLLinenameNoNormalSum",TTLLinenameNoNormalSum );
        TTLMap.put("TTLLinenameNoNormalRate",TTLLinenameNoNormalRate/LineSum);
        TTLMap.put("TTLLinenamePlateRate",TTLLinenamePlateRate/LineSum);

        TTLMap.put("TTLAnonormalsumA",TTLAnonormalsumA);
        TTLMap.put("TTLANoNormalRateA",TTLANoNormalRateA/LineSum);
        TTLMap.put("TTLAPlateRateA",TTLAPlateRateA/LineSum);

        TTLMap.put("TTLAnonormalsumB",TTLAnonormalsumB);
        TTLMap.put("TTLANoNormalRateB",TTLANoNormalRateB/LineSum);
        TTLMap.put("TTLAPlateRateB",TTLAPlateRateB/LineSum);

        TTLMap.put("TTLAnonormalsumC",TTLAnonormalsumC );
        TTLMap.put("TTLANoNormalRateC",TTLANoNormalRateC/LineSum);
        TTLMap.put("TTLAPlateRateC",TTLAPlateRateC/LineSum);

        TTLMap.put("TTLAnonormalsumD",TTLAnonormalsumD );
        TTLMap.put("TTLANoNormalRateD",TTLANoNormalRateD/LineSum);
        TTLMap.put("TTLAPlateRateD",TTLAPlateRateD/LineSum);

        TTLMap.put("TTLAnonormalsumE",TTLAnonormalsumE );
        TTLMap.put("TTLANoNormalRateE",TTLANoNormalRateE/LineSum);
        TTLMap.put("TTLAPlateRateE",TTLAPlateRateE/LineSum);

        TTLMap.put("TTLAnonormalsumF",TTLAnonormalsumF );
        TTLMap.put("TTLANoNormalRateF",TTLANoNormalRateF/LineSum);
        TTLMap.put("TTLAPlateRateF",TTLAPlateRateF/LineSum);

        TTLMap.put("TTLANoNormalRateG",TTLANoNormalRateG/LineSum);
        TTLMap.put("TTLAnonormalsumG",TTLAnonormalsumG);
        TTLMap.put("TTLAPlateRateG",TTLAPlateRateG/LineSum );

        TTLMap.put("TTLAnonormalsumH",TTLAnonormalsumH  );
        TTLMap.put("TTLANoNormalRateH",TTLANoNormalRateH/LineSum);
        TTLMap.put("TTLAPlateRateH",TTLAPlateRateH/LineSum    );
        map.put("TTLMap", TTLMap);
        map.put("AutoFloor_Wait_TimeList",AutoFloor_Wait_TimeList);


        List<AutoFloor_Wait_Time> autoFloor_wait_timesList = new ArrayList<>();

        if (TimeDay.equals("two")){
            if (timeUtility.Timequantum0830_1029()){
                autoFloor_wait_timesList =autoFloor_roBotService.RobotAnalyzeTOP508(date,FloorName);
            }else {
                autoFloor_wait_timesList =autoFloor_roBotService.RobotAnalyzeTOP5(FloorName);

            }
        }else if (TimeDay.equals("Day")){
            autoFloor_wait_timesList =autoFloor_roBotService.RobotAnalyzeTOP5Day(date,FloorName);
        }else if (TimeDay.equals("Night")){
            if (timeUtility.Timequantum2030_2229()){
                autoFloor_wait_timesList =autoFloor_roBotService.RobotAnalyzeTOP520(date,FloorName);
            }else {
                autoFloor_wait_timesList =autoFloor_roBotService.RobotAnalyzeTOP5Night(StartDate,EndDate,FloorName);
            }

        }else if (TimeDay.equals("Yesterday")){
            autoFloor_wait_timesList =autoFloor_roBotService.RobotAnalyzeTOP5Yesterday(StartDate,EndDate,FloorName);
        }

        List<AutoFloor_Wait_Time> RobotTop5List=new ArrayList<>();

        for (int i = 0; i < autoFloor_wait_timesList.size(); i++) {
            AutoFloor_Wait_Time Robottop =new AutoFloor_Wait_Time();
            AutoFloor_Wait_Time RobotAnalyzeTOP5 = autoFloor_wait_timesList.get(i);
            String line_name = RobotAnalyzeTOP5.getLine_name();
            Robottop.setLine_name(line_name);
            String newline_name ="";
            if (line_name!=null&&!("").equals(line_name)){
                newline_name =line_name.substring(0, line_name.length() - 2);
            }

            if (TimeDay.equals("two")){
                Robottop.setAwaitNoNormalSum(RobotAnalyzeTOP5.getAwaitNoNormalSum());
                if (timeUtility.Timequantum0830_1029()){
                        int HtimeRate=Htime-8;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                }else if (timeUtility.Timequantum2030_2229()) {
                        int HtimeRate=Htime-20;
                        if (HtimeRate==0){
                            HtimeRate=1;
                        }
                        Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((HtimeRate) * 3600));
                }else {
                        Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate());
                }
            }else if (TimeDay.equals("Day")){
                AUTOFLOOR_TARGET autofloorTarget = autofloorService.LinenameTarGet(newline_name, "Day",StartDate, "");
                Robottop.setAwaitNoNormalSum(RobotAnalyzeTOP5.getAwaitNoNormalSum());
                  if (timeUtility.Timequantum0830_1230()){
                      int HtimeRate=Htime-8;
                      if (HtimeRate==0){
                          HtimeRate=1;
                      }
                        Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate() / ((HtimeRate) * 3600));
                    }else if (timeUtility.Timequantum1230_2030()){
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            if (timeUtility.Timequantum1230_1930()){
                                Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                            }else {
                                Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate() / ((10) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            if (timeUtility.Timequantum1230_1730()){
                                Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                            }else {
                                Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate() / ((8) * 3600));
                            }
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            if (timeUtility.Timequantum1230_1830()){
                                Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate() / ((Htime+3) * 3600));
                            }else {
                                Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate() / ((9) * 3600));
                            }
                        }
                    }else {
                        if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                            Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate() / ((10) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                            Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate() / ((8) * 3600));
                        } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                            Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate() / ((9) * 3600));
                        }
                    }

            }else if (TimeDay.equals("Night")){
                AUTOFLOOR_TARGET autofloorTarget = autofloorService.LinenameTarGet(newline_name, "Night", StartDate, "");
                Robottop.setAwaitNoNormalSum(RobotAnalyzeTOP5.getAwaitNoNormalSum());
                if (timeUtility.Timequantum2029_0000()){
                    int HtimeRate=Htime-20;
                    if (HtimeRate==0){
                        HtimeRate=1;
                    }
                             Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((HtimeRate) * 3600));
                }else if (timeUtility.Timequantum0000_2029()){
                    if (autofloorTarget.getWorkingHours().doubleValue() == 9.5) {
                        if (timeUtility.Timequantum0000_0730()){
                            Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((Htime+3) * 3600));
                        }else {
                            Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((10) * 3600));

                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 7.67) {
                        if (timeUtility.Timequantum0000_0530()){
                            Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((Htime+3) * 3600));
                        }else {
                            Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((8) * 3600));
                        }
                    } else if (autofloorTarget.getWorkingHours().doubleValue() == 8.67) {
                        if (timeUtility.Timequantum0000_0630()){
                            Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((Htime+3) * 3600));
                        }else {
                            Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((9) * 3600));
                        }
                    }
                }

            }else if (TimeDay.equals("Yesterday")){
                List<AUTOFLOOR_TARGET> autofloor_targets = autofloor_targetMapper.RobotLinenameTarGetYesterday(FloorName, newline_name, schedules,DayType);
                AUTOFLOOR_TARGET autofloorTarget = new AUTOFLOOR_TARGET();
                if (autofloor_targets.size()!=0){
                    autofloorTarget=autofloor_targets.get(0);
                }
                Robottop.setAwaitNoNormalSum(RobotAnalyzeTOP5.getAwaitNoNormalSum());
                if (autofloorTarget.getWorkingHours().doubleValue()  == 19) {
                    if (timeUtility.Timequantum0000_0730()){
                        Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((Htime+12)  * 3600));
                    }else {
                        Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((19) * 3600));

                    }
                } else if (autofloorTarget.getWorkingHours().doubleValue() == 15.34) {
                    if (timeUtility.Timequantum0000_0530()){
                        Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((Htime+10) * 3600));
                    }else {
                        Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((15) * 3600));
                    }
                } else if (autofloorTarget.getWorkingHours().doubleValue() == 17.34) {
                    if (timeUtility.Timequantum0000_0630()){
                        Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((Htime+11) * 3600));
                    }else {
                        Robottop.setAwaitNoNormalRate(RobotAnalyzeTOP5.getAwaitNoNormalRate()/ ((17) * 3600));
                    }
                }

            }
            String slant="";
            List<AutoFloor_Wait_Time> TOP5Site = new ArrayList<>();
            if (TimeDay.equals("two")){
                if (timeUtility.Timequantum0830_1029()){
                    TOP5Site = autoFloor_roBotService.RobotAnalyzeTOP5Site08(schedules,RobotAnalyzeTOP5.getLine_name());
                }else {
                    TOP5Site = autoFloor_roBotService.RobotAnalyzeTOP5Site(RobotAnalyzeTOP5.getLine_name());
                }
            }else if (TimeDay.equals("Day")){
                    TOP5Site = autoFloor_roBotService.RobotAnalyzeTOP5SiteDay(schedules,RobotAnalyzeTOP5.getLine_name());
            }else if (TimeDay.equals("Night")){
                if (timeUtility.Timequantum2030_2229()){
                    TOP5Site = autoFloor_roBotService.RobotAnalyzeTOP5Site20(schedules,RobotAnalyzeTOP5.getLine_name());
                }else {
                    TOP5Site = autoFloor_roBotService.RobotAnalyzeTOP5SiteNight(StartDate,EndDate,RobotAnalyzeTOP5.getLine_name());
                }
            }else if (TimeDay.equals("Yesterday")){
                    TOP5Site = autoFloor_roBotService.RobotAnalyzeTOP5SiteYesterday(StartDate,EndDate,RobotAnalyzeTOP5.getLine_name());
            }
            for (AutoFloor_Wait_Time autoFloor_wait_time : TOP5Site) {
                slant+=autoFloor_wait_time.getSlant_Local()+"*"+autoFloor_wait_time.getCoun()+", ";
            }

            Robottop.setSlant_Local(slant);
            RobotTop5List.add(Robottop);
        }
        map.put("FloorName",FloorName);
        map.put("RobotTop5List", RobotTop5List);

        return "realtime/AllFloorsRobot";

    }

}
