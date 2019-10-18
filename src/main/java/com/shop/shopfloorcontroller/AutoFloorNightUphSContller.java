package com.shop.shopfloorcontroller;

import com.shop.model.AUTOFLOOR_TARGET;
import com.shop.model.AmountsUPH;
import com.shop.model.AutoFloorUphS;
import com.shop.model.SumNumber;
import com.shop.services.AUTOFLOOR_TARGEService;
import com.shop.services.AutoFloor_RateService;
import com.shop.services.AutoFloor_uphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author
 * @create 2019-08-27 11:35
 */
@Controller
public class AutoFloorNightUphSContller {
    @Autowired
    AutoFloor_uphService autoFloorUphService;
    @Autowired
    AUTOFLOOR_TARGEService  autofloorTargeService;
    @Autowired
    AutoFloor_RateService  autoFloor_rateService;

    @Autowired
    TaskService taskService;

    @Autowired
    TimeUtility timeUtility;

    /*产能状况*/
    @RequestMapping(value ="/AllfloorsNightOutput",method = RequestMethod.GET)
    public  String AllfloorsOutput(@RequestParam("Linename") String Linename,@RequestParam(value = "Productname",required = false)String Productname , Map map){
        String ProductnameCCT="CCT_COMBO";
        String ProductnameWIFIBT="WIFI/BT_W1";
        String ProductnameWIFIBT2="WIFI/BT_W2";

        long StartdebugTime =System.currentTimeMillis();
        /*每时间段每个工站Map*/
        List<java.sql.Date> sqlList = taskService.Schedule();
        Date schedule =sqlList.get(0);
        /*当前日期减1*/
        Date YesterdayDate =sqlList.get(1);
        /*当前日期加1*/
        Date TomorrowDate =sqlList.get(2);
        /*获取当前分钟数*/
        List<Integer> TimeList = taskService.getTime(new Date());
        Integer Htime = TimeList.get(2);
        Integer MINUTEs = TimeList.get(3);
        Integer  actionMinuteD=0;
        if (Htime!=null&&Htime>=20){
            Integer actionMinute=((Htime-20)*60+MINUTEs)-30;
            actionMinuteD=actionMinute;
        }
        /*当前机台运作分钟数*/
        Integer actionMINUTE=0;
        Double timeMinutes= 60.0;
        /* 分钟非空判断*/
        if (MINUTEs==null){
            MINUTEs=0;
        }
        /*时间区间判断 生产时间*/
        if (MINUTEs<=29){
            actionMINUTE=MINUTEs+30;
        }else if (MINUTEs>=29){
            actionMINUTE= MINUTEs-30;
        }
        /*过24时 时间变量*/
        Date StartDate =new Date();
        Date EndDate=new Date();
        if (timeUtility.Timequantum2029_0000()){
            StartDate =schedule;
            EndDate=TomorrowDate;
        }else if (timeUtility.Timequantum0000_2029()){
            StartDate =YesterdayDate;
            EndDate=schedule;
        }
        if (timeUtility.Timequantum0000_0830()){
            schedule=YesterdayDate;
        }

        Map<String,Object>  AllUph = new HashMap<>();
        SumNumber sumNumber = new SumNumber();
        /*工站总数*/
        Integer DFUsum=0;
        Integer FCTsum=0;
        Integer S1sum=0;
        Integer S2sum=0;
        Integer S3sum=0;
        Integer CCTsum=0;
        Integer W1sum=0;
        Integer W2sum=0;
        Integer UWBsum=0;
        Integer SCONDsum=0;
        /*实时工站总量*/
        Integer actionDFUsum=0;
        Integer actionFCTsum=0;
        Integer actionS1sum=0;
        Integer actionS2sum=0;
        Integer actionS3sum=0;
        Integer actionCCTsum=0;
        Integer actionW1sum=0;
        Integer actionW2sum=0;
        Integer actionUWBsum=0;
        Integer actionSCONDsum=0;
        /*异常机台List*/
        List<AmountsUPH>   DFUAmountsList     = new ArrayList<AmountsUPH>();
        List<AmountsUPH>   FCTAmountsList      = new ArrayList<AmountsUPH>();
        List<AmountsUPH>    S1AmountsList     = new ArrayList<AmountsUPH>();
        List<AmountsUPH>    S2AmountsList     = new ArrayList<AmountsUPH>();
        List<AmountsUPH>    S3AmountsList      = new ArrayList<AmountsUPH>();
        List<AmountsUPH>   CCTAmountsList     = new ArrayList<AmountsUPH>();
        List<AmountsUPH>    W1AmountsList      = new ArrayList<AmountsUPH>();
        List<AmountsUPH>    W2AmountsList     = new ArrayList<AmountsUPH>();
        List<AmountsUPH>   UWBAmountsList      = new ArrayList<AmountsUPH>();
        List<AmountsUPH> SCONDAmountsList      = new ArrayList<AmountsUPH>();

        AUTOFLOOR_TARGET targetsum = new AUTOFLOOR_TARGET();
        if (timeUtility.Timequantum0830_2029()){
            targetsum = autofloorTargeService.LinenameTarGet(Linename,"Night",YesterdayDate,Productname);
        }else {
            targetsum = autofloorTargeService.LinenameTarGet(Linename,"Night", schedule,Productname);
            /*实时时段段目标值*/
        }
        /*线体小时目标*/
        BigDecimal hTargets = targetsum.gethTarget();
        int hTarget = hTargets.intValue();
        Integer hTargetInteger =Integer.parseInt(hTargets.toString());
        map.put("HTarget",hTargets);
        /*线体楼层名称*/
        String Floorname = targetsum.gettFloor();
        if (Floorname.equals("D07-3F")){
            Floorname="D073F";
        }else if (Floorname.equals("D05-2F")){
            Floorname="D052F";
        }
        /*线体天目标*/
        BigDecimal DTarget = targetsum.getdTarget();
        map.put("DTarget",DTarget);

        /*机种工站判断*/
        if (Productname.equals("Macan")){
            ProductnameCCT="CCT";
            ProductnameWIFIBT="WIFI/BT";
            ProductnameWIFIBT2="WIFI/BT2";
        }

            if (targetsum.getWorkingHours().doubleValue()==9.5){
                if (timeUtility.Timequantum2030_2130()){
                }else if (timeUtility.Timequantum2330_0030()) {
                    Integer actionTimeh = actionMinuteD - 30;
                }else if (timeUtility.Timequantum0030_0730()){
                    Integer actionTimeh =actionMinuteD-90;
                }else if (Htime>=7&&MINUTEs>=30||Htime>7&&MINUTEs>0) {
                    actionMinuteD = 570;
                }
            }else if (targetsum.getWorkingHours().doubleValue()==7.67){
                if (timeUtility.Timequantum2030_2130()){
                }else if (timeUtility.Timequantum2330_0030()) {
                    Integer actionTimeh = actionMinuteD - 20;

                }else if (timeUtility.Timequantum0030_0530()){
                    Integer actionTimeh =actionMinuteD-80;

                }else if (Htime>=5&&MINUTEs>=30||Htime>5&&MINUTEs>0) {
                    actionMinuteD = 460;
                }
            }else if (targetsum.getWorkingHours().doubleValue()==8.67){
                if (timeUtility.Timequantum2030_2130()){
                }else if (timeUtility.Timequantum2330_0030()) {
                    Integer actionTimeh = actionMinuteD - 20;
                }else if (timeUtility.Timequantum0030_0730()){
                    Integer actionTimeh =actionMinuteD-80;

                }else if (Htime>=6&&MINUTEs>=30||Htime>6&&MINUTEs>0) {
                    actionMinuteD = 520;
                }
            }
        /*All 每小时工站数据*/
        List<AutoFloorUphS> autoFloorUphS = autoFloorUphService.WorkstationOutNight(Linename,StartDate,EndDate,Floorname,Productname);
        for (int i = 0; i < autoFloorUphS.size(); i++) {
            AutoFloorUphS autoFloorUphS1 = autoFloorUphS.get(i);

            String time_slot = autoFloorUphS1.getTIME_SLOT();
            if (time_slot.equals("20:30~21:30")){
                if (autoFloorUphS1.getSTATION_NAME().equals("DFU")) {
                    AllUph.put("20:30~21:30DFU", autoFloorUphS1.getUPHS());
                    DFUsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("FCT")) {
                    AllUph.put("20:30~21:30FTC", autoFloorUphS1.getUPHS());
                    FCTsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S1")) {
                    AllUph.put("20:30~21:30S1", autoFloorUphS1.getUPHS());
                    S1sum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S2")) {
                    AllUph.put("20:30~21:30S2", autoFloorUphS1.getUPHS());
                    S2sum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S3")) {
                    AllUph.put("20:30~21:30S3", autoFloorUphS1.getUPHS());
                    S3sum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameCCT)) {
                    AllUph.put("20:30~21:30CCT", autoFloorUphS1.getUPHS());
                    CCTsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT)) {
                    AllUph.put("20:30~21:30W1", autoFloorUphS1.getUPHS());
                    W1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT2)) {
                    AllUph.put("20:30~21:30W2", autoFloorUphS1.getUPHS());
                    W2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("SCOND")) {
                    AllUph.put("20:30~21:30SCOND", autoFloorUphS1.getUPHS());
                    SCONDsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("UWB")) {
                    AllUph.put("20:30~21:30UWB", autoFloorUphS1.getUPHS());
                    UWBsum+=autoFloorUphS1.getUPHS();
                }
            } else if (time_slot.equals("21:30~22:30")) {
                if (autoFloorUphS1.getSTATION_NAME().equals("DFU")) {
                    AllUph.put("21:30~22:30DFU", autoFloorUphS1.getUPHS());
                    DFUsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("FCT")) {
                    AllUph.put("21:30~22:30FTC", autoFloorUphS1.getUPHS());
                    FCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S1")) {
                    AllUph.put("21:30~22:30S1", autoFloorUphS1.getUPHS());
                    S1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S2")) {
                    AllUph.put("21:30~22:30S2", autoFloorUphS1.getUPHS());
                    S2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S3")) {
                    AllUph.put("21:30~22:30S3", autoFloorUphS1.getUPHS());
                    S3sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameCCT)) {
                    AllUph.put("21:30~22:30CCT", autoFloorUphS1.getUPHS());
                    CCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT)) {
                    AllUph.put("21:30~22:30W1", autoFloorUphS1.getUPHS());
                    W1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT2)) {
                    AllUph.put("21:30~22:30W2", autoFloorUphS1.getUPHS());
                    W2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("SCOND")) {
                    AllUph.put("21:30~22:30SCOND", autoFloorUphS1.getUPHS());
                    SCONDsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("UWB")) {
                    AllUph.put("21:30~22:30UWB", autoFloorUphS1.getUPHS());
                    UWBsum+=autoFloorUphS1.getUPHS();
                }

            } else if (time_slot.equals("22:30~23:30")) {
                if (autoFloorUphS1.getSTATION_NAME().equals("DFU")) {
                    AllUph.put("22:30~23:30DFU", autoFloorUphS1.getUPHS());
                    DFUsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("FCT")) {
                    AllUph.put("22:30~23:30FTC", autoFloorUphS1.getUPHS());
                    FCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S1")) {
                    AllUph.put("22:30~23:30S1", autoFloorUphS1.getUPHS());
                    S1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S2")) {
                    AllUph.put("22:30~23:30S2", autoFloorUphS1.getUPHS());
                    S2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S3")) {
                    AllUph.put("22:30~23:30S3", autoFloorUphS1.getUPHS());
                    S3sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameCCT)) {
                    AllUph.put("22:30~23:30CCT", autoFloorUphS1.getUPHS());
                    CCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT)) {
                    AllUph.put("22:30~23:30W1", autoFloorUphS1.getUPHS());
                    W1sum+=autoFloorUphS1.getUPHS();


                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT2)) {
                    AllUph.put("22:30~23:30W2", autoFloorUphS1.getUPHS());
                    W2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("SCOND")) {
                    AllUph.put("22:30~23:30SCOND", autoFloorUphS1.getUPHS());
                    SCONDsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("UWB")) {
                    AllUph.put("22:30~23:30UWB", autoFloorUphS1.getUPHS());
                    UWBsum+=autoFloorUphS1.getUPHS();


                }

                //*23:30~0:30 时间段对比*//*
            } else if (time_slot.equals("23:30~0:30")) {
                if (autoFloorUphS1.getSTATION_NAME().equals("DFU")) {
                    AllUph.put("23:30~0:30DFU", autoFloorUphS1.getUPHS());
                    DFUsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("FCT")) {
                    AllUph.put("23:30~0:30FTC", autoFloorUphS1.getUPHS());
                    FCTsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S1")) {
                    AllUph.put("23:30~0:30S1", autoFloorUphS1.getUPHS());
                    S1sum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S2")) {
                    AllUph.put("23:30~0:30S2", autoFloorUphS1.getUPHS());
                    S2sum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S3")) {
                    AllUph.put("23:30~0:30S3", autoFloorUphS1.getUPHS());
                    S3sum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameCCT)) {
                    AllUph.put("23:30~0:30CCT", autoFloorUphS1.getUPHS());
                    CCTsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT)) {
                    AllUph.put("23:30~0:30W1", autoFloorUphS1.getUPHS());
                    W1sum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT2)) {
                    AllUph.put("23:30~0:30W2", autoFloorUphS1.getUPHS());
                    W2sum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("SCOND")) {
                    AllUph.put("23:30~0:30SCOND", autoFloorUphS1.getUPHS());
                    SCONDsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("UWB")) {
                    AllUph.put("23:30~0:30UWB", autoFloorUphS1.getUPHS());
                    UWBsum+=autoFloorUphS1.getUPHS();
                }
                //*0:30~1:30 时间段对比*//*
            } else if (time_slot.equals("0:30~1:30")) {
                if (autoFloorUphS1.getSTATION_NAME().equals("DFU")) {
                    AllUph.put("0:30~1:30DFU", autoFloorUphS1.getUPHS());
                    DFUsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("FCT")) {
                    AllUph.put("0:30~1:30FTC", autoFloorUphS1.getUPHS());
                    FCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S1")) {
                    AllUph.put("0:30~1:30S1", autoFloorUphS1.getUPHS());
                    S1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S2")) {
                    AllUph.put("0:30~1:30S2", autoFloorUphS1.getUPHS());
                    S2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S3")) {
                    AllUph.put("0:30~1:30S3", autoFloorUphS1.getUPHS());
                    S3sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameCCT)) {
                    AllUph.put("0:30~1:30CCT", autoFloorUphS1.getUPHS());
                    CCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT)) {
                    AllUph.put("0:30~1:30W1", autoFloorUphS1.getUPHS());
                    W1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT2)) {
                    AllUph.put("0:30~1:30W2", autoFloorUphS1.getUPHS());
                    W2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("SCOND")) {
                    AllUph.put("0:30~1:30SCOND", autoFloorUphS1.getUPHS());
                    SCONDsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("UWB")) {
                    AllUph.put("0:30~1:30UWB", autoFloorUphS1.getUPHS());
                    UWBsum+=autoFloorUphS1.getUPHS();


                }
                //*1:30~2:30 时间段对比*//*
            } else if (time_slot.equals("1:30~2:30")) {
                if (autoFloorUphS1.getSTATION_NAME().equals("DFU")) {
                    AllUph.put("1:30~2:30DFU", autoFloorUphS1.getUPHS());
                    DFUsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("FCT")) {
                    AllUph.put("1:30~2:30FTC", autoFloorUphS1.getUPHS());
                    FCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S1")) {
                    AllUph.put("1:30~2:30S1", autoFloorUphS1.getUPHS());
                    S1sum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S2")) {
                    AllUph.put("1:30~2:30S2", autoFloorUphS1.getUPHS());
                    S2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S3")) {
                    AllUph.put("1:30~2:30S3", autoFloorUphS1.getUPHS());
                    S3sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameCCT)) {
                    AllUph.put("1:30~2:30CCT", autoFloorUphS1.getUPHS());
                    CCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT)) {
                    AllUph.put("1:30~2:30W1", autoFloorUphS1.getUPHS());
                    W1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT2)) {
                    AllUph.put("1:30~2:30W2", autoFloorUphS1.getUPHS());
                    W2sum+=autoFloorUphS1.getUPHS();


                } else if (autoFloorUphS1.getSTATION_NAME().equals("SCOND")) {
                    AllUph.put("1:30~2:30SCOND", autoFloorUphS1.getUPHS());
                    SCONDsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("UWB")) {
                    AllUph.put("1:30~2:30UWB", autoFloorUphS1.getUPHS());
                    UWBsum+=autoFloorUphS1.getUPHS();
                }
            }else if (time_slot.equals("2:30~3:30")) {

                if (autoFloorUphS1.getSTATION_NAME().equals("DFU")) {
                    AllUph.put("2:30~3:30DFU", autoFloorUphS1.getUPHS());
                    DFUsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("FCT")) {
                    AllUph.put("2:30~3:30FTC", autoFloorUphS1.getUPHS());
                    FCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S1")) {
                    AllUph.put("2:30~3:30S1", autoFloorUphS1.getUPHS());
                    S1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S2")) {
                    AllUph.put("2:30~3:30S2", autoFloorUphS1.getUPHS());
                    S2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S3")) {
                    AllUph.put("2:30~3:30S3", autoFloorUphS1.getUPHS());
                    S3sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameCCT)) {
                    AllUph.put("2:30~3:30CCT", autoFloorUphS1.getUPHS());
                    CCTsum+=autoFloorUphS1.getUPHS();


                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT)) {
                    AllUph.put("2:30~3:30W1", autoFloorUphS1.getUPHS());
                    W1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT2)) {
                    AllUph.put("2:30~3:30W2", autoFloorUphS1.getUPHS());
                    W2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("SCOND")) {
                    AllUph.put("2:30~3:30SCOND", autoFloorUphS1.getUPHS());
                    SCONDsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("UWB")) {
                    AllUph.put("2:30~3:30UWB", autoFloorUphS1.getUPHS());
                    UWBsum+=autoFloorUphS1.getUPHS();
                }

                //*3:30~4:30 时间段对比*//*
            } else if (time_slot.equals("3:30~4:30")) {
                //*---------------wip3:30~4:30 时间段对比--------------------*//*
                if (autoFloorUphS1.getSTATION_NAME().equals("DFU")) {
                    AllUph.put("3:30~4:30DFU", autoFloorUphS1.getUPHS());
                    DFUsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("FCT")) {
                    AllUph.put("3:30~4:30FTC", autoFloorUphS1.getUPHS());
                    FCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S1")) {
                    AllUph.put("3:30~4:30S1", autoFloorUphS1.getUPHS());
                    S1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S2")) {
                    AllUph.put("3:30~4:30S2", autoFloorUphS1.getUPHS());
                    S2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S3")) {
                    AllUph.put("3:30~4:30S3", autoFloorUphS1.getUPHS());
                    S3sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameCCT)) {
                    AllUph.put("3:30~4:30CCT", autoFloorUphS1.getUPHS());
                    CCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT)) {
                    AllUph.put("3:30~4:30W1", autoFloorUphS1.getUPHS());
                    W1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT2)) {
                    AllUph.put("3:30~4:30W2", autoFloorUphS1.getUPHS());
                    W2sum+=autoFloorUphS1.getUPHS();


                } else if (autoFloorUphS1.getSTATION_NAME().equals("SCOND")) {
                    AllUph.put("3:30~4:30SCOND", autoFloorUphS1.getUPHS());
                    SCONDsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("UWB")) {
                    AllUph.put("3:30~4:30UWB", autoFloorUphS1.getUPHS());
                    UWBsum+=autoFloorUphS1.getUPHS();
                }

                //*4:30~5:30 时间段对比*//*
            } else if (time_slot.equals("4:30~5:30")) {
                if (autoFloorUphS1.getSTATION_NAME().equals("DFU")) {
                    AllUph.put("4:30~5:30DFU", autoFloorUphS1.getUPHS());
                    DFUsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("FCT")) {
                    AllUph.put("4:30~5:30FTC", autoFloorUphS1.getUPHS());
                    FCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S1")) {
                    AllUph.put("4:30~5:30S1", autoFloorUphS1.getUPHS());
                    S1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S2")) {
                    AllUph.put("4:30~5:30S2", autoFloorUphS1.getUPHS());
                    S2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S3")) {
                    AllUph.put("4:30~5:30S3", autoFloorUphS1.getUPHS());
                    S3sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameCCT)) {
                    AllUph.put("4:30~5:30CCT", autoFloorUphS1.getUPHS());
                    CCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT)) {
                    AllUph.put("4:30~5:30W1", autoFloorUphS1.getUPHS());
                    W1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT2)) {
                    AllUph.put("4:30~5:30W2", autoFloorUphS1.getUPHS());
                    W2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("SCOND")) {
                    AllUph.put("4:30~5:30SCOND", autoFloorUphS1.getUPHS());
                    SCONDsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("UWB")) {
                    AllUph.put("4:30~5:30UWB", autoFloorUphS1.getUPHS());
                    UWBsum+=autoFloorUphS1.getUPHS();
                }
                //*5:30~6:30 时间段对比*//*
            } else if (time_slot.equals("5:30~6:30")) {
                if (autoFloorUphS1.getSTATION_NAME().equals("DFU")) {
                    AllUph.put("5:30~6:30DFU", autoFloorUphS1.getUPHS());
                    DFUsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("FCT")) {
                    AllUph.put("5:30~6:30FTC", autoFloorUphS1.getUPHS());
                    FCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S1")) {
                    AllUph.put("5:30~6:30S1", autoFloorUphS1.getUPHS());
                    S1sum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S2")) {
                    AllUph.put("5:30~6:30S2", autoFloorUphS1.getUPHS());
                    S2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S3")) {
                    AllUph.put("5:30~6:30S3", autoFloorUphS1.getUPHS());
                    S3sum+=autoFloorUphS1.getUPHS();


                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameCCT)) {
                    AllUph.put("5:30~6:30CCT", autoFloorUphS1.getUPHS());
                    CCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT)) {
                    AllUph.put("5:30~6:30W1", autoFloorUphS1.getUPHS());
                    W1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT2)) {
                    AllUph.put("5:30~6:30W2", autoFloorUphS1.getUPHS());
                    W2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("SCOND")) {
                    AllUph.put("5:30~6:30SCOND", autoFloorUphS1.getUPHS());
                    SCONDsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("UWB")) {
                    AllUph.put("5:30~6:30UWB", autoFloorUphS1.getUPHS());
                    UWBsum+=autoFloorUphS1.getUPHS();
                }
                //*6:30~7:30 时间段对比*//*
            } else if (time_slot.equals("6:30~7:30")) {
                if (autoFloorUphS1.getSTATION_NAME().equals("DFU")) {
                    AllUph.put("6:30~7:30DFU", autoFloorUphS1.getUPHS());
                    DFUsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("FCT")) {
                    AllUph.put("6:30~7:30FTC", autoFloorUphS1.getUPHS());
                    FCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S1")) {
                    AllUph.put("6:30~7:30S1", autoFloorUphS1.getUPHS());
                    S1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S2")) {
                    AllUph.put("6:30~7:30S2", autoFloorUphS1.getUPHS());
                    S2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S3")) {
                    AllUph.put("6:30~7:30S3", autoFloorUphS1.getUPHS());
                    S3sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameCCT)) {
                    AllUph.put("6:30~7:30CCT", autoFloorUphS1.getUPHS());
                    CCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT)) {
                    AllUph.put("6:30~7:30W1", autoFloorUphS1.getUPHS());
                    W1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT2)) {
                    AllUph.put("6:30~7:30W2", autoFloorUphS1.getUPHS());
                    W2sum+=autoFloorUphS1.getUPHS();


                } else if (autoFloorUphS1.getSTATION_NAME().equals("SCOND")) {
                    AllUph.put("6:30~7:30SCOND", autoFloorUphS1.getUPHS());
                    SCONDsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("UWB")) {
                    AllUph.put("6:30~7:30UWB", autoFloorUphS1.getUPHS());
                    UWBsum+=autoFloorUphS1.getUPHS();
                }

                //*7:30~8:30时间段对比*//*
            } else if (time_slot.equals("7:30~8:30")) {
                if (autoFloorUphS1.getSTATION_NAME().equals("DFU")) {
                    AllUph.put("7:30~8:30DFU", autoFloorUphS1.getUPHS());
                    DFUsum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("FCT")) {
                    AllUph.put("7:30~8:30FTC", autoFloorUphS1.getUPHS());
                    FCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S1")) {
                    AllUph.put("7:30~8:30S1", autoFloorUphS1.getUPHS());
                    S1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S2")) {
                    AllUph.put("7:30~8:30S2", autoFloorUphS1.getUPHS());
                    S2sum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals("CELL_S3")) {
                    AllUph.put("7:30~8:30S3", autoFloorUphS1.getUPHS());
                    S3sum+=autoFloorUphS1.getUPHS();
                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameCCT)) {
                    AllUph.put("7:30~8:30CCT", autoFloorUphS1.getUPHS());
                    CCTsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT)) {
                    AllUph.put("7:30~8:30W1", autoFloorUphS1.getUPHS());
                    W1sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals(ProductnameWIFIBT2)) {
                    AllUph.put("7:30~8:30W2", autoFloorUphS1.getUPHS());
                    W2sum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("SCOND")) {
                    AllUph.put("7:30~8:30SCOND", autoFloorUphS1.getUPHS());
                    SCONDsum+=autoFloorUphS1.getUPHS();

                } else if (autoFloorUphS1.getSTATION_NAME().equals("UWB")) {
                    AllUph.put("7:30~8:30UWB", autoFloorUphS1.getUPHS());
                    UWBsum+=autoFloorUphS1.getUPHS();
                }

            }
        }


/*时间段实时目标*/
        Double StopTime=19.8;
        Integer SumUphTarget            =0;
        Integer Targetaction2030_2130=0;
        Integer Targetaction2130_2230=0;
        Integer Targetaction2230_2330=0;
        Integer Targetaction0030_0130=0;
        Integer Targetaction0130_0230=0;
        Integer Targetaction0230_0330=0;
        Integer Targetaction0330_0430=0;
        Integer Targetaction0430_0530=0;
        Integer Targetaction0530_0630=0;
        Integer Targetaction0630_0730=0;
        Integer Targetaction0730_0830=0;
        Integer TargetH10=0;
        Integer TargetH9=0;
        Integer TargetH8=0;
        if (targetsum.getWorkingHours().doubleValue()==9.5){
            TargetH10=1;
            if (timeUtility.Timequantum2030_2130()){
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE) * 0.5;
                    Targetaction2030_2130=doubleTime.intValue();
                }
            }else if (timeUtility.Timequantum2130_2230()){
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime= ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);

                    Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                    Targetaction2130_2230=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum2230_2330()){
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=doubleTime.intValue();

                }
            }else if (timeUtility.Timequantum2330_0030()){
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                }

            }else if (timeUtility.Timequantum0030_0130()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0130_0230()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0230_0330()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0330_0430()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0430_0530()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=targetsum.gethTarget().intValue();
                    Targetaction0430_0530=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0530_0630()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=targetsum.gethTarget().intValue();
                    Targetaction0430_0530=targetsum.gethTarget().intValue();
                    Targetaction0530_0630=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0630_0730()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE)* 0.5;
                    Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=targetsum.gethTarget().intValue();
                    Targetaction0430_0530=targetsum.gethTarget().intValue();
                    Targetaction0530_0630=targetsum.gethTarget().intValue();
                    Targetaction0630_0730=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0730_0830()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=targetsum.gethTarget().intValue();
                    Targetaction0430_0530=targetsum.gethTarget().intValue();
                    Targetaction0530_0630=targetsum.gethTarget().intValue();
                    Targetaction0630_0730=targetsum.gethTarget().intValue();
                }

            }else {
                Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                Targetaction2130_2230=targetsum.gethTarget().intValue();
                Targetaction2230_2330=targetsum.gethTarget().intValue();
                Targetaction0030_0130=targetsum.gethTarget().intValue();
                Targetaction0130_0230=targetsum.gethTarget().intValue();
                Targetaction0230_0330=targetsum.gethTarget().intValue();
                Targetaction0330_0430=targetsum.gethTarget().intValue();
                Targetaction0430_0530=targetsum.gethTarget().intValue();
                Targetaction0530_0630=targetsum.gethTarget().intValue();
                Targetaction0630_0730=targetsum.gethTarget().intValue();
            }
        }else if (targetsum.getWorkingHours().doubleValue()==7.67) {
            TargetH8=1;
            if (timeUtility.Timequantum2030_2130()){
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE) * 0.5;

                    Targetaction2030_2130=doubleTime.intValue();
                }
            }else if (timeUtility.Timequantum2130_2230()){
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime= ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum2230_2330()){
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=doubleTime.intValue();

                }
            }else if (timeUtility.Timequantum2330_0030()){
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                }

            }else if (timeUtility.Timequantum0030_0130()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0130_0230()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0230_0330()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0330_0430()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0430_0530()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE)* 0.5;
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=targetsum.gethTarget().intValue();
                    Targetaction0430_0530=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0530_0630()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=targetsum.gethTarget().intValue();
                    Targetaction0430_0530=targetsum.gethTarget().intValue();
                }

            }else if (timeUtility.Timequantum0630_0730()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE)* 0.5;
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=targetsum.gethTarget().intValue();
                    Targetaction0430_0530=targetsum.gethTarget().intValue();
                }

            }else if (timeUtility.Timequantum0730_0830()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=targetsum.gethTarget().intValue();
                    Targetaction0430_0530=targetsum.gethTarget().intValue();
                }

            }else {
                Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                Targetaction2130_2230=targetsum.gethTarget().intValue();
                Targetaction2230_2330=targetsum.gethTarget().intValue();
                Targetaction0030_0130=targetsum.gethTarget().intValue();
                Targetaction0130_0230=targetsum.gethTarget().intValue();
                Targetaction0230_0330=targetsum.gethTarget().intValue();
                Targetaction0330_0430=targetsum.gethTarget().intValue();
                Targetaction0430_0530=targetsum.gethTarget().intValue();

            }

        }else if (targetsum.getWorkingHours().doubleValue()==8.67){
            TargetH9=1;
            if (timeUtility.Timequantum2030_2130()){
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE) * 0.5;
                    Targetaction2030_2130=doubleTime.intValue();
                }
            }else if (timeUtility.Timequantum2130_2230()){
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime= ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum2230_2330()){
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=doubleTime.intValue();

                }

            }else if (timeUtility.Timequantum2330_0030()){
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                }

            }else if (timeUtility.Timequantum0030_0130()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Targetaction2030_2130=targetsum.gethTarget().intValue() ;
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0130_0230()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0230_0330()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0330_0430()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0430_0530()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=targetsum.gethTarget().intValue();
                    Targetaction0430_0530=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0530_0630()) {

                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=targetsum.gethTarget().intValue();
                    Targetaction0430_0530=targetsum.gethTarget().intValue();
                    Targetaction0530_0630=doubleTime.intValue();
                }

            }else if (timeUtility.Timequantum0630_0730()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE)* 0.5;
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=targetsum.gethTarget().intValue();
                    Targetaction0430_0530=targetsum.gethTarget().intValue();
                    Targetaction0530_0630=targetsum.gethTarget().intValue();
                }

            }else if (timeUtility.Timequantum0730_0830()) {
                if (hTargetInteger != 0 && hTargetInteger != null) {
                    Double doubleTime = ((targetsum.gethTarget().doubleValue() / 60) * actionMINUTE);
                    Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                    Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                    Targetaction2130_2230=targetsum.gethTarget().intValue();
                    Targetaction2230_2330=targetsum.gethTarget().intValue();
                    Targetaction0030_0130=targetsum.gethTarget().intValue();
                    Targetaction0130_0230=targetsum.gethTarget().intValue();
                    Targetaction0230_0330=targetsum.gethTarget().intValue();
                    Targetaction0330_0430=targetsum.gethTarget().intValue();
                    Targetaction0430_0530=targetsum.gethTarget().intValue();
                    Targetaction0530_0630=targetsum.gethTarget().intValue();
                }

            }else {
                Double stopTime =(StopTime*(targetsum.gethTarget().doubleValue()/60));
                Targetaction2030_2130=targetsum.gethTarget().intValue()-stopTime.intValue();
                Targetaction2130_2230=targetsum.gethTarget().intValue();
                Targetaction2230_2330=targetsum.gethTarget().intValue();
                Targetaction0030_0130=targetsum.gethTarget().intValue();
                Targetaction0130_0230=targetsum.gethTarget().intValue();
                Targetaction0230_0330=targetsum.gethTarget().intValue();
                Targetaction0330_0430=targetsum.gethTarget().intValue();
                Targetaction0430_0530=targetsum.gethTarget().intValue();
                Targetaction0530_0630=targetsum.gethTarget().intValue();
            }
        }

        SumUphTarget=Targetaction2030_2130+Targetaction2130_2230+Targetaction2230_2330+Targetaction0030_0130+Targetaction0130_0230+Targetaction0230_0330 +Targetaction0330_0430 +Targetaction0430_0530 +Targetaction0530_0630 +Targetaction0630_0730;
        if (timeUtility.Timequantum0830_2029()) {
            SumUphTarget=targetsum.getdTarget().intValue();
        }
        map.put("TargetH10",TargetH10 );
        map.put("TargetH9",TargetH9 );
        map.put("TargetH8",TargetH8 );
        map.put("SumUphTarget",SumUphTarget);

        /*每个工站的小时目标*/
        /*每个机台的每小时目标*/
        Integer DFUamountsum= 0;
        DFUamountsum= autoFloorUphService.Workstationamount(Floorname,Linename,"DFU",schedule,Productname);

        Integer DFUActionSum = 0;
        DFUActionSum =autoFloorUphService.MachinaeActionSum(Floorname, "DFU", schedule,Productname,Linename);
        Integer UnusualDFUMachinae=0;
        if (DFUamountsum!=0&DFUActionSum!=0){
            UnusualDFUMachinae= DFUamountsum-DFUActionSum;
        }
        map.put("UnusualDFUMachinae",UnusualDFUMachinae);
        Integer DFUamounts=0;
        /*机台每分钟目标*/
        Double actionDFUamounts=0.0;
        if(DFUamountsum!=0){
            DFUamounts = hTarget / DFUamountsum;
            sumNumber.setDFUamount(DFUamounts);
            /*机台每分钟产量*/
            Double DFUsecondamount = DFUamounts/timeMinutes;
            actionDFUamounts=DFUsecondamount*actionMinuteD;

        }

        Integer FCTamountsum = 0;
        FCTamountsum = autoFloorUphService.Workstationamount(Floorname,Linename,"FCT",schedule,Productname);
        Integer FCTActionSum = 0;
        FCTActionSum = autoFloorUphService.MachinaeActionSum(Floorname, "FCT", schedule,Productname,Linename);
        Integer UnusualFCTMachinae=0;
        if (FCTamountsum!=0&FCTActionSum!=0){
            UnusualFCTMachinae= FCTamountsum-FCTActionSum;
        }
        map.put("UnusualFCTMachinae",UnusualFCTMachinae);
        Integer FCTamounts=0;
        /*机台每分钟目标*/
        Double actionFCTamounts=0.0;
        if(FCTamountsum!=0){
            FCTamounts = hTarget / FCTamountsum;
            sumNumber.setFCTamount(FCTamounts);
            /*机台每分钟产量*/
            Double FCTsecondamount = FCTamounts/timeMinutes;
            actionFCTamounts=FCTsecondamount*actionMinuteD;
        }

        Integer S1amountsum = 0;
        S1amountsum =autoFloorUphService.Workstationamount(Floorname,Linename,"CELL_S1",schedule,Productname);
        Integer S1ActionSum = 0;
        S1ActionSum = autoFloorUphService.MachinaeActionSum(Floorname, "CELL_S1", schedule,Productname,Linename);
        Integer UnusualS1Machinae=0;
        if (S1amountsum!=0&S1ActionSum!=0){
            UnusualS1Machinae= S1amountsum-S1ActionSum;
        }
        map.put("UnusualS1Machinae",UnusualS1Machinae);
        Integer S1amounts=0;
        /*机台每分钟目标*/
        Double actionS1amounts=0.0;
        if(S1amountsum!=0){
            S1amounts = hTarget / S1amountsum;
            sumNumber.setS1amount(S1amounts);
            /*机台每分钟产量*/
            Double S1secondamount = S1amounts/timeMinutes;
            actionS1amounts=S1secondamount*actionMinuteD;
        }

        Integer S2amountsum= 0;
        S2amountsum= autoFloorUphService.Workstationamount(Floorname,Linename,"CELL_S2",schedule,Productname);
        Integer S2ActionSum = 0;
        S2ActionSum =autoFloorUphService.MachinaeActionSum(Floorname, "CELL_S2", schedule,Productname,Linename);
        Integer UnusualS2Machinae=0;
        if (S2amountsum!=0&S2ActionSum!=0){
            UnusualS2Machinae= S2amountsum-S2ActionSum;
        }
        map.put("UnusualS2Machinae",UnusualS2Machinae);
        Integer S2amounts=0;
        /*机台每分钟目标*/
        Double actionS2amounts=0.0;
        if(S2amountsum!=0){
            S2amounts = hTarget / S2amountsum;
            sumNumber.setS2amount(S2amounts);
            /*机台每分钟产量*/
            Double S2secondamount = S2amounts/timeMinutes;
            actionS2amounts=S2secondamount*actionMinuteD;
        }

        Integer S3amountsum = 0;
        S3amountsum =autoFloorUphService.Workstationamount(Floorname,Linename,"CELL_S3",schedule,Productname);
        Integer S3ActionSum = 0;
        S3ActionSum =autoFloorUphService.MachinaeActionSum(Floorname, "CELL_S3", schedule,Productname,Linename);
        Integer UnusualS3Machinae=0;
        if (S3amountsum!=0&S3ActionSum!=0){
            UnusualS3Machinae= S3amountsum-S3ActionSum;
        }
        map.put("UnusualS3Machinae",UnusualS3Machinae);
        Integer S3amounts=0;
        /*机台每分钟目标*/
        Double actionS3amounts=0.0;
        if(S3amountsum!=0){
            S3amounts = hTarget / S3amountsum;
            sumNumber.setS3amount(S3amounts);
            /*机台每分钟产量*/
            Double S3secondamount = S3amounts/timeMinutes;
            actionS3amounts=S3secondamount*actionMinuteD;
        }

        Integer CCTamountsum = 0;
        CCTamountsum = autoFloorUphService.Workstationamount(Floorname,Linename,ProductnameCCT,schedule,Productname);
        Integer CCTActionSum = 0;
        CCTActionSum =autoFloorUphService.MachinaeActionSum(Floorname, ProductnameCCT, schedule,Productname,Linename);
        Integer UnusualCCTMachinae=0;
        if (CCTamountsum!=0&CCTActionSum!=0){
            UnusualCCTMachinae= CCTamountsum-CCTActionSum;
        }
        map.put("UnusualCCTMachinae",UnusualCCTMachinae);
        Integer CCTamounts=0;
        /*机台每分钟目标*/
        Double actionCCTamounts=0.0;
        if(CCTamountsum!=0){
            CCTamounts = hTarget / CCTamountsum;
            sumNumber.setCCTamount(CCTamounts);
            /*机台每分钟产量*/
            Double CCTsecondamount = CCTamounts/timeMinutes;
            actionCCTamounts=CCTsecondamount*actionMinuteD;
        }
        Integer W1amountsum = 0;
        W1amountsum = autoFloorUphService.Workstationamount(Floorname,Linename,ProductnameWIFIBT,schedule,Productname);
        Integer W1ActionSum = 0;
        W1ActionSum =autoFloorUphService.MachinaeActionSum(Floorname, ProductnameWIFIBT, schedule,Productname,Linename);
        Integer UnusualW1Machinae=0;
        if (W1amountsum!=0&W1ActionSum!=0){
            UnusualW1Machinae= W1amountsum-W1ActionSum;
        }
        map.put("UnusualW1Machinae",UnusualW1Machinae);
        Integer W1amounts=0;
        /*机台每分钟目标*/
        Double actionW1amounts=0.0;
        if(W1amountsum!=0){
            W1amounts = hTarget / W1amountsum;
            sumNumber.setW1amount(W1amounts);
            /*机台每分钟产量*/
            Double W1secondamount = W1amounts/timeMinutes;
            actionW1amounts=W1secondamount*actionMinuteD;
        }

        Integer W2amountsum = 0;
        W2amountsum =autoFloorUphService.Workstationamount(Floorname,Linename, ProductnameWIFIBT2,schedule,Productname);
        Integer W2ActionSum = 0;
        W2ActionSum =autoFloorUphService.MachinaeActionSum(Floorname, ProductnameWIFIBT2, schedule,Productname,Linename);
        Integer UnusualW2Machinae=0;
        if (W2amountsum!=0&W2ActionSum!=0){
            UnusualW2Machinae= W2amountsum-W2ActionSum;
        }
        map.put("UnusualW2Machinae",UnusualW2Machinae);
        Integer W2amounts=0;
        /*机台每分钟目标*/
        Double actionW2amounts=0.0;
        if(W2amountsum!=0){
            W2amounts = hTarget / W2amountsum;
            sumNumber.setW2amount(W2amounts);
            /*机台每分钟产量*/
            Double W2secondamount = W2amounts/timeMinutes;
            actionW2amounts=W2secondamount*actionMinuteD;
        }

        Integer UWBamountsum = 0;
        UWBamountsum = autoFloorUphService.Workstationamount(Floorname,Linename,"UWB",schedule,Productname);
        Integer UWBActionSum = 0;
        UWBActionSum = autoFloorUphService.MachinaeActionSum(Floorname, "UWB", schedule,Productname,Linename);
        Integer UnusualUWBMachinae=0;
        if (UWBamountsum!=0&UWBActionSum!=0){
            UnusualUWBMachinae= UWBamountsum-UWBActionSum;
        }
        map.put("UnusualUWBMachinae",UnusualUWBMachinae);
        Integer UWBamounts=0;
        /*机台每分钟目标*/
        Double actionUWBamounts=0.0;
        if(UWBamountsum!=0){
            UWBamounts = hTarget / UWBamountsum;
            sumNumber.setUWBamount(UWBamounts);
            /*机台每分钟产量*/
            Double UWBsecondamount = UWBamounts/timeMinutes;
            actionUWBamounts=UWBsecondamount*actionMinuteD;
        }

        Integer SCONDamountsum = 0;
        SCONDamountsum =autoFloorUphService.Workstationamount(Floorname,Linename,"SCOND",schedule,Productname);
        Integer SCONDActionSum = 0;
        SCONDActionSum =autoFloorUphService.MachinaeActionSum(Floorname, "SCOND", schedule,Productname,Linename);
        Integer UnusualSCONDMachinae=0;
        if (SCONDamountsum!=0&SCONDActionSum!=0){
            UnusualSCONDMachinae= SCONDamountsum-SCONDActionSum;
        }
        map.put("UnusualSCONDMachinae",UnusualSCONDMachinae);
        Integer SCONDamounts=0;
        /*机台每分钟目标*/
        Double actionSCONDamounts=0.0;
        if(SCONDamountsum!=0){
            SCONDamounts = hTarget / SCONDamountsum;
            sumNumber.setSCONDamount(SCONDamounts);
            /*机台每分钟产量*/
            Double SCONDsecondamount = SCONDamounts/timeMinutes;
            actionSCONDamounts=SCONDsecondamount*actionMinuteD;
        }

        Double DFUFPYTarget=98.90;
        Double FCTFPYTarget=99.20;
        Double S1FPYTarget=97.35;
        Double S2FPYTarget=96.40;
        Double S3FPYTarget=98.45;
        Double CCTFPYTarget=99.49;
        Double W1FPYTarget=97.45;
        Double W2FPYTarget=98.99;
        Double UWBFPYTarget=98.95;
        Double SCONDFPYTarget=98.47;
        if (SumUphTarget-CCTsum>200){
            sumNumber.setActionCCTsum(actionCCTamounts.intValue());
            List<AmountsUPH> amountsUPHS = autoFloorUphService.MachinaeUPHNight(ProductnameCCT, StartDate,EndDate,Linename, Floorname, Productname);
            List<AmountsUPH> amountsDetail = autoFloorUphService.MachinaeDetailNight(ProductnameCCT, StartDate,EndDate,Linename, Floorname, Productname);
            CCTAmountsList.addAll(amountsDetail);
            for (int i = 0; i < amountsUPHS.size(); i++) {
                AmountsUPH  CCTAmounts= new AmountsUPH();
                AmountsUPH amountsUPH = amountsUPHS.get(i);
                if (amountsUPH.getAmountuph()<actionCCTamounts){

                    CCTAmounts.setLINE_NAME(amountsUPH.getLINE_NAME());
                    CCTAmounts.setAmountuph(amountsUPH.getAmountuph());
                    CCTAmounts.setMachine_no(amountsUPH.getMachine_no());
                    List<AmountsUPH> amountsUPHSList= new ArrayList<>();
                    amountsUPHSList =autoFloorUphService.MachinaeUPHFPYTop3Night(amountsUPH.getMachine_no(), StartDate, EndDate, Linename, Floorname, Productname);

                    Integer fail_q=0;
                    Double  fpy=0.0;
                    Double  YIELD=0.0;
                    Double  MISDETET=0.0;

                    if (amountsUPHSList.size()!=0){
                        AmountsUPH amountsUPH1 = amountsUPHSList.get(0);
                        if (amountsUPH1.getFAIL_Q()!=null){
                            fail_q = amountsUPH1.getFAIL_Q();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHfpy = amountsUPH1.getFPY();
                            BigDecimal fpybig =new BigDecimal(UPHfpy*100);
                            fpy = fpybig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHYIELD = amountsUPH1.getYIELD();
                            BigDecimal YIELDbig =new BigDecimal(UPHYIELD*100);
                            YIELD = YIELDbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHMISDETET = amountsUPH1.getMISDETET();
                            BigDecimal MISDETETbig =new BigDecimal(UPHMISDETET*100);
                            MISDETET = MISDETETbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                    }
                    CCTAmounts.setFAIL_Q(fail_q);
                    CCTAmounts.setFPY(fpy);
                    CCTAmounts.setYIELD(YIELD);
                    CCTAmounts.setMISDETET(MISDETET);
                    CCTAmounts.setFPYTarget(CCTFPYTarget);
                    CCTAmountsList.add(CCTAmounts);
                }
            }
            if (CCTAmountsList.size()>=3){
                CCTAmountsList=CCTAmountsList.subList(0,3);
            }else if (CCTAmountsList.size()==2){
                CCTAmountsList= CCTAmountsList.subList(0,2);
            }else if (CCTAmountsList.size()==1){
                CCTAmountsList= CCTAmountsList.subList(0,1);
            }
        }
        if (SumUphTarget-DFUsum>200){
            sumNumber.setActionDFUsum(actionDFUamounts.intValue());
            List<AmountsUPH> amountsUPHS = autoFloorUphService.MachinaeUPHNight("DFU",  StartDate,EndDate, Linename, Floorname, Productname);
            List<AmountsUPH> amountsDetail = autoFloorUphService.MachinaeDetailNight("DFU",StartDate,EndDate,Linename, Floorname, Productname);
            DFUAmountsList.addAll(amountsDetail);
            for (int i = 0; i < amountsUPHS.size(); i++) {
                AmountsUPH amountsUPH = amountsUPHS.get(i);
                AmountsUPH  DFUAmounts= new AmountsUPH();
                if (amountsUPH.getAmountuph()<actionDFUamounts){

                    DFUAmounts.setLINE_NAME(amountsUPH.getLINE_NAME());
                    DFUAmounts.setAmountuph(amountsUPH.getAmountuph());
                    DFUAmounts.setMachine_no(amountsUPH.getMachine_no());
                    List<AmountsUPH> amountsUPHSList= new ArrayList<>();
                    amountsUPHSList =autoFloorUphService.MachinaeUPHFPYTop3Night(amountsUPH.getMachine_no(), StartDate, EndDate, Linename, Floorname, Productname);

                    Integer fail_q=0;
                    Double  fpy=0.0;
                    Double  YIELD=0.0;
                    Double  MISDETET=0.0;

                    if (amountsUPHSList.size()!=0){
                        AmountsUPH amountsUPH1 = amountsUPHSList.get(0);
                        if (amountsUPH1.getFAIL_Q()!=null){
                            fail_q = amountsUPH1.getFAIL_Q();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHfpy = amountsUPH1.getFPY();
                            BigDecimal fpybig =new BigDecimal(UPHfpy*100);
                            fpy = fpybig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHYIELD = amountsUPH1.getYIELD();
                            BigDecimal YIELDbig =new BigDecimal(UPHYIELD*100);
                            YIELD = YIELDbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHMISDETET = amountsUPH1.getMISDETET();
                            BigDecimal MISDETETbig =new BigDecimal(UPHMISDETET*100);
                            MISDETET = MISDETETbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                    }
                    DFUAmounts.setFAIL_Q(fail_q);
                    DFUAmounts.setFPY(fpy);
                    DFUAmounts.setYIELD(YIELD);
                    DFUAmounts.setMISDETET(MISDETET);
                    DFUAmounts.setFPYTarget(DFUFPYTarget);
                    DFUAmountsList.add(DFUAmounts);
                }
            }
            if ( DFUAmountsList.size()>=3){
                DFUAmountsList= DFUAmountsList.subList(0,3);
            }else if ( DFUAmountsList.size()==2){
                DFUAmountsList=  DFUAmountsList.subList(0,2);
            }else if ( DFUAmountsList.size()==1){
                DFUAmountsList=  DFUAmountsList.subList(0,1);
            }
        }
        if (SumUphTarget-FCTsum>200){
            sumNumber.setActionFCTsum(actionFCTamounts.intValue());
            List<AmountsUPH> amountsUPHS = autoFloorUphService.MachinaeUPHNight("FCT",  StartDate,EndDate, Linename, Floorname, Productname);
            List<AmountsUPH> amountsDetail = autoFloorUphService.MachinaeDetailNight("FCT",StartDate,EndDate,Linename, Floorname, Productname);
            FCTAmountsList.addAll(amountsDetail);
            for (int i = 0; i < amountsUPHS.size(); i++) {
                AmountsUPH  FCTAmounts= new AmountsUPH();
                AmountsUPH amountsUPH = amountsUPHS.get(i);
                if (amountsUPH.getAmountuph()<actionFCTamounts){

                    FCTAmounts.setLINE_NAME(amountsUPH.getLINE_NAME());
                    FCTAmounts.setAmountuph(amountsUPH.getAmountuph());
                    FCTAmounts.setMachine_no(amountsUPH.getMachine_no());
                    List<AmountsUPH> amountsUPHSList= new ArrayList<>();
                    amountsUPHSList =autoFloorUphService.MachinaeUPHFPYTop3Night(amountsUPH.getMachine_no(), StartDate, EndDate, Linename, Floorname, Productname);


                    Integer fail_q=0;
                    Double  fpy=0.0;
                    Double  YIELD=0.0;
                    Double  MISDETET=0.0;

                    if (amountsUPHSList.size()!=0){
                        AmountsUPH amountsUPH1 = amountsUPHSList.get(0);
                        if (amountsUPH1.getFAIL_Q()!=null){
                            fail_q = amountsUPH1.getFAIL_Q();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHfpy = amountsUPH1.getFPY();
                            BigDecimal fpybig =new BigDecimal(UPHfpy*100);
                            fpy = fpybig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHYIELD = amountsUPH1.getYIELD();
                            BigDecimal YIELDbig =new BigDecimal(UPHYIELD*100);
                            YIELD = YIELDbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHMISDETET = amountsUPH1.getMISDETET();
                            BigDecimal MISDETETbig =new BigDecimal(UPHMISDETET*100);
                            MISDETET = MISDETETbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                    }
                    FCTAmounts.setFAIL_Q(fail_q);
                    FCTAmounts.setFPY(fpy);
                    FCTAmounts.setYIELD(YIELD);
                    FCTAmounts.setMISDETET(MISDETET);
                    FCTAmounts.setFPYTarget(FCTFPYTarget);
                    FCTAmountsList.add(FCTAmounts);
                }
            }
            if ( FCTAmountsList.size()>=3){
                FCTAmountsList= FCTAmountsList.subList(0,3);
            }else if ( FCTAmountsList.size()==2){
                FCTAmountsList=  FCTAmountsList.subList(0,2);
            }else if ( FCTAmountsList.size()==1){
                FCTAmountsList=  FCTAmountsList.subList(0,1);
            }
        }
        if (SumUphTarget-S1sum>200){
            sumNumber.setActionS1sum(actionS1amounts.intValue());
            List<AmountsUPH> amountsUPHS = autoFloorUphService.MachinaeUPHNight("CELL_S1",  StartDate,EndDate, Linename, Floorname, Productname);
            List<AmountsUPH> amountsDetail = autoFloorUphService.MachinaeDetailNight("CELL_S1",StartDate,EndDate,Linename, Floorname, Productname);
            S1AmountsList.addAll(amountsDetail);
            for (int i = 0; i < amountsUPHS.size(); i++) {
                AmountsUPH  S1Amounts= new  AmountsUPH();
                AmountsUPH amountsUPH = amountsUPHS.get(i);
                if (amountsUPH.getAmountuph()<actionS1amounts){

                    S1Amounts.setLINE_NAME(amountsUPH.getLINE_NAME());
                    S1Amounts.setAmountuph(amountsUPH.getAmountuph());
                    S1Amounts.setMachine_no(amountsUPH.getMachine_no());
                    List<AmountsUPH> amountsUPHSList= new ArrayList<>();
                    amountsUPHSList =autoFloorUphService.MachinaeUPHFPYTop3Night(amountsUPH.getMachine_no(), StartDate, EndDate, Linename, Floorname, Productname);


                    Integer fail_q=0;
                    Double  fpy=0.0;
                    Double  YIELD=0.0;
                    Double  MISDETET=0.0;

                    if (amountsUPHSList.size()!=0){
                        AmountsUPH amountsUPH1 = amountsUPHSList.get(0);
                        if (amountsUPH1.getFAIL_Q()!=null){
                            fail_q = amountsUPH1.getFAIL_Q();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHfpy = amountsUPH1.getFPY();
                            BigDecimal fpybig =new BigDecimal(UPHfpy*100);
                            fpy = fpybig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHYIELD = amountsUPH1.getYIELD();
                            BigDecimal YIELDbig =new BigDecimal(UPHYIELD*100);
                            YIELD = YIELDbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHMISDETET = amountsUPH1.getMISDETET();
                            BigDecimal MISDETETbig =new BigDecimal(UPHMISDETET*100);
                            MISDETET = MISDETETbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                    }
                    S1Amounts.setFAIL_Q(fail_q);
                    S1Amounts.setFPY(fpy);
                    S1Amounts.setYIELD(YIELD);
                    S1Amounts.setMISDETET(MISDETET);
                    S1Amounts.setFPYTarget(S1FPYTarget);
                    S1AmountsList.add(S1Amounts);
                }
            }
            if ( S1AmountsList.size()>=3){
                S1AmountsList= S1AmountsList.subList(0,3);
            }else if ( S1AmountsList.size()==2){
                S1AmountsList=  S1AmountsList.subList(0,2);
            }else if ( S1AmountsList.size()==1){
                S1AmountsList=  S1AmountsList.subList(0,1);
            }
        }
        if (SumUphTarget-S2sum>200){
            sumNumber.setActionS2sum(actionS2amounts.intValue());
            List<AmountsUPH> amountsUPHS = autoFloorUphService.MachinaeUPHNight("CELL_S2", StartDate,EndDate, Linename, Floorname, Productname);
            List<AmountsUPH> amountsDetail = autoFloorUphService.MachinaeDetailNight("CELL_S2",StartDate,EndDate,Linename, Floorname, Productname);
            S2AmountsList.addAll(amountsDetail);
            for (int i = 0; i < amountsUPHS.size(); i++) {
                AmountsUPH  S2Amounts= new  AmountsUPH();
                AmountsUPH amountsUPH = amountsUPHS.get(i);
                if (amountsUPH.getAmountuph()<actionS2amounts){

                    S2Amounts.setLINE_NAME(amountsUPH.getLINE_NAME());
                    S2Amounts.setAmountuph(amountsUPH.getAmountuph());
                    S2Amounts.setMachine_no(amountsUPH.getMachine_no());
                    List<AmountsUPH> amountsUPHSList= new ArrayList<>();
                    amountsUPHSList =autoFloorUphService.MachinaeUPHFPYTop3Night(amountsUPH.getMachine_no(), StartDate, EndDate, Linename, Floorname, Productname);


                    Integer fail_q=0;
                    Double  fpy=0.0;
                    Double  YIELD=0.0;
                    Double  MISDETET=0.0;

                    if (amountsUPHSList.size()!=0){
                        AmountsUPH amountsUPH1 = amountsUPHSList.get(0);
                        if (amountsUPH1.getFAIL_Q()!=null){
                            fail_q = amountsUPH1.getFAIL_Q();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHfpy = amountsUPH1.getFPY();
                            BigDecimal fpybig =new BigDecimal(UPHfpy*100);
                            fpy = fpybig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHYIELD = amountsUPH1.getYIELD();
                            BigDecimal YIELDbig =new BigDecimal(UPHYIELD*100);
                            YIELD = YIELDbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHMISDETET = amountsUPH1.getMISDETET();
                            BigDecimal MISDETETbig =new BigDecimal(UPHMISDETET*100);
                            MISDETET = MISDETETbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                    }
                    S2Amounts.setFAIL_Q(fail_q);
                    S2Amounts.setFPY(fpy);
                    S2Amounts.setYIELD(YIELD);
                    S2Amounts.setMISDETET(MISDETET);
                    S2Amounts.setFPYTarget(S2FPYTarget);
                    S2AmountsList.add(S2Amounts);
                }
            }
            if ( S2AmountsList.size()>=3){
                S2AmountsList= S2AmountsList.subList(0,3);
            }else if ( S2AmountsList.size()==2){
                S2AmountsList=  S2AmountsList.subList(0,2);
            }else if ( S2AmountsList.size()==1){
                S2AmountsList=  S2AmountsList.subList(0,1);
            }
        }
        if (SumUphTarget-S3sum>200){
            sumNumber.setActionS3sum(actionS3amounts.intValue());
            List<AmountsUPH> amountsUPHS = autoFloorUphService.MachinaeUPHNight("CELL_S3", StartDate,EndDate, Linename, Floorname, Productname);
            List<AmountsUPH> amountsDetail = autoFloorUphService.MachinaeDetailNight("CELL_S3",StartDate,EndDate,Linename, Floorname, Productname);
            S3AmountsList.addAll(amountsDetail);
            for (int i = 0; i < amountsUPHS.size(); i++) {
                AmountsUPH  S3Amounts= new  AmountsUPH();
                AmountsUPH amountsUPH = amountsUPHS.get(i);
                if (amountsUPH.getAmountuph()<actionS3amounts){

                    S3Amounts.setLINE_NAME(amountsUPH.getLINE_NAME());
                    S3Amounts.setAmountuph(amountsUPH.getAmountuph());
                    S3Amounts.setMachine_no(amountsUPH.getMachine_no());
                    List<AmountsUPH> amountsUPHSList= new ArrayList<>();
                    amountsUPHSList =autoFloorUphService.MachinaeUPHFPYTop3Night(amountsUPH.getMachine_no(), StartDate, EndDate, Linename, Floorname, Productname);


                    Integer fail_q=0;
                    Double  fpy=0.0;
                    Double  YIELD=0.0;
                    Double  MISDETET=0.0;

                    if (amountsUPHSList.size()!=0){
                        AmountsUPH amountsUPH1 = amountsUPHSList.get(0);
                        if (amountsUPH1.getFAIL_Q()!=null){
                            fail_q = amountsUPH1.getFAIL_Q();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHfpy = amountsUPH1.getFPY();
                            BigDecimal fpybig =new BigDecimal(UPHfpy*100);
                            fpy = fpybig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHYIELD = amountsUPH1.getYIELD();
                            BigDecimal YIELDbig =new BigDecimal(UPHYIELD*100);
                            YIELD = YIELDbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHMISDETET = amountsUPH1.getMISDETET();
                            BigDecimal MISDETETbig =new BigDecimal(UPHMISDETET*100);
                            MISDETET = MISDETETbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                    }
                    S3Amounts.setFAIL_Q(fail_q);
                    S3Amounts.setFPY(fpy);
                    S3Amounts.setYIELD(YIELD);
                    S3Amounts.setMISDETET(MISDETET);
                    S3Amounts.setFPYTarget(S3FPYTarget);
                    S3AmountsList.add(S3Amounts);
                }
            }
            if (S3AmountsList.size()>=3){
                S3AmountsList=S3AmountsList.subList(0,3);
            }else if (S3AmountsList.size()==2){
                S3AmountsList= S3AmountsList.subList(0,2);
            }else if (S3AmountsList.size()==1){
                S3AmountsList= S3AmountsList.subList(0,1);
            }

        }
        if (SumUphTarget-UWBsum>200){
            sumNumber.setActionUWBsum(actionUWBamounts.intValue());
            List<AmountsUPH> amountsUPHS = autoFloorUphService.MachinaeUPHNight("UWB", StartDate,EndDate, Linename, Floorname, Productname);
            List<AmountsUPH> amountsDetail = autoFloorUphService.MachinaeDetailNight("UWB",StartDate,EndDate,Linename, Floorname, Productname);
            UWBAmountsList.addAll(amountsDetail);
            for (int i = 0; i < amountsUPHS.size(); i++) {
                AmountsUPH  UWBAmounts= new AmountsUPH();
                AmountsUPH amountsUPH = amountsUPHS.get(i);
                if (amountsUPH.getAmountuph()<actionUWBamounts){

                    UWBAmounts.setLINE_NAME(amountsUPH.getLINE_NAME());
                    UWBAmounts.setAmountuph(amountsUPH.getAmountuph());
                    UWBAmounts.setMachine_no(amountsUPH.getMachine_no());
                    List<AmountsUPH> amountsUPHSList= new ArrayList<>();
                    amountsUPHSList =autoFloorUphService.MachinaeUPHFPYTop3Night(amountsUPH.getMachine_no(), StartDate, EndDate, Linename, Floorname, Productname);


                    Integer fail_q=0;
                    Double  fpy=0.0;
                    Double  YIELD=0.0;
                    Double  MISDETET=0.0;

                    if (amountsUPHSList.size()!=0){
                        AmountsUPH amountsUPH1 = amountsUPHSList.get(0);
                        if (amountsUPH1.getFAIL_Q()!=null){
                            fail_q = amountsUPH1.getFAIL_Q();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHfpy = amountsUPH1.getFPY();
                            BigDecimal fpybig =new BigDecimal(UPHfpy*100);
                            fpy = fpybig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHYIELD = amountsUPH1.getYIELD();
                            BigDecimal YIELDbig =new BigDecimal(UPHYIELD*100);
                            YIELD = YIELDbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHMISDETET = amountsUPH1.getMISDETET();
                            BigDecimal MISDETETbig =new BigDecimal(UPHMISDETET*100);
                            MISDETET = MISDETETbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                    }
                    UWBAmounts.setFAIL_Q(fail_q);
                    UWBAmounts.setFPY(fpy);
                    UWBAmounts.setYIELD(YIELD);
                    UWBAmounts.setMISDETET(MISDETET);
                    UWBAmounts.setFPYTarget(UWBFPYTarget);
                    UWBAmountsList.add(UWBAmounts);
                }
            }
            if ( UWBAmountsList.size()>=3){
                UWBAmountsList= UWBAmountsList.subList(0,3);
            }else if ( UWBAmountsList.size()==2){
                UWBAmountsList=  UWBAmountsList.subList(0,2);
            }else if ( UWBAmountsList.size()==1){
                UWBAmountsList=  UWBAmountsList.subList(0,1);
            }
        }
        if (SumUphTarget-W1sum>200){
            sumNumber.setActionW1sum(actionW1amounts.intValue());
            List<AmountsUPH> amountsUPHS = autoFloorUphService.MachinaeUPHNight(ProductnameWIFIBT,  StartDate,EndDate, Linename, Floorname, Productname);
            List<AmountsUPH> amountsDetail = autoFloorUphService.MachinaeDetailNight(ProductnameWIFIBT, StartDate,EndDate,Linename, Floorname, Productname);
            W1AmountsList.addAll(amountsDetail);
            for (int i = 0; i < amountsUPHS.size(); i++) {
                AmountsUPH  W1Amounts= new  AmountsUPH();
                AmountsUPH amountsUPH = amountsUPHS.get(i);
                if (amountsUPH.getAmountuph()<actionW1amounts){

                    W1Amounts.setLINE_NAME(amountsUPH.getLINE_NAME());
                    W1Amounts.setAmountuph(amountsUPH.getAmountuph());
                    W1Amounts.setMachine_no(amountsUPH.getMachine_no());
                    List<AmountsUPH> amountsUPHSList= new ArrayList<>();
                    amountsUPHSList =autoFloorUphService.MachinaeUPHFPYTop3Night(amountsUPH.getMachine_no(), StartDate, EndDate, Linename, Floorname, Productname);


                    Integer fail_q=0;
                    Double  fpy=0.0;
                    Double  YIELD=0.0;
                    Double  MISDETET=0.0;

                    if (amountsUPHSList.size()!=0){
                        AmountsUPH amountsUPH1 = amountsUPHSList.get(0);
                        if (amountsUPH1.getFAIL_Q()!=null){
                            fail_q = amountsUPH1.getFAIL_Q();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHfpy = amountsUPH1.getFPY();
                            BigDecimal fpybig =new BigDecimal(UPHfpy*100);
                            fpy = fpybig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHYIELD = amountsUPH1.getYIELD();
                            BigDecimal YIELDbig =new BigDecimal(UPHYIELD*100);
                            YIELD = YIELDbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHMISDETET = amountsUPH1.getMISDETET();
                            BigDecimal MISDETETbig =new BigDecimal(UPHMISDETET*100);
                            MISDETET = MISDETETbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                    }
                    W1Amounts.setFAIL_Q(fail_q);
                    W1Amounts.setFPY(fpy);
                    W1Amounts.setYIELD(YIELD);
                    W1Amounts.setMISDETET(MISDETET);
                    W1Amounts.setFPYTarget(W1FPYTarget);
                    W1AmountsList.add(W1Amounts);
                }
            }
            if ( W1AmountsList.size()>=3){
                W1AmountsList= W1AmountsList.subList(0,3);
            }else if ( W1AmountsList.size()==2){
                W1AmountsList=  W1AmountsList.subList(0,2);
            }else if ( W1AmountsList.size()==1){
                W1AmountsList=  W1AmountsList.subList(0,1);
            }
        }
        if (SumUphTarget-W2sum>200){
            sumNumber.setActionW2sum(actionW2amounts.intValue());
            List<AmountsUPH> amountsUPHS = autoFloorUphService.MachinaeUPHNight(ProductnameWIFIBT2, StartDate,EndDate, Linename, Floorname, Productname);
            List<AmountsUPH> amountsDetail = autoFloorUphService.MachinaeDetailNight(ProductnameWIFIBT2, StartDate,EndDate,Linename, Floorname, Productname);
            W2AmountsList.addAll(amountsDetail);
            for (int i = 0; i < amountsUPHS.size(); i++) {
                AmountsUPH  W2Amounts= new  AmountsUPH();
                AmountsUPH amountsUPH = amountsUPHS.get(i);
                if (amountsUPH.getAmountuph()<actionW2amounts){

                    W2Amounts.setLINE_NAME(amountsUPH.getLINE_NAME());
                    W2Amounts.setAmountuph(amountsUPH.getAmountuph());
                    W2Amounts.setMachine_no(amountsUPH.getMachine_no());
                    List<AmountsUPH> amountsUPHSList= new ArrayList<>();
                    amountsUPHSList =autoFloorUphService.MachinaeUPHFPYTop3Night(amountsUPH.getMachine_no(), StartDate, EndDate, Linename, Floorname, Productname);


                    Integer fail_q=0;
                    Double  fpy=0.0;
                    Double  YIELD=0.0;
                    Double  MISDETET=0.0;

                    if (amountsUPHSList.size()!=0){
                        AmountsUPH amountsUPH1 = amountsUPHSList.get(0);
                        if (amountsUPH1.getFAIL_Q()!=null){
                            fail_q = amountsUPH1.getFAIL_Q();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHfpy = amountsUPH1.getFPY();
                            BigDecimal fpybig =new BigDecimal(UPHfpy*100);
                            fpy = fpybig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHYIELD = amountsUPH1.getYIELD();
                            BigDecimal YIELDbig =new BigDecimal(UPHYIELD*100);
                            YIELD = YIELDbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHMISDETET = amountsUPH1.getMISDETET();
                            BigDecimal MISDETETbig =new BigDecimal(UPHMISDETET*100);
                            MISDETET = MISDETETbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                    }
                    W2Amounts.setFAIL_Q(fail_q);
                    W2Amounts.setFPY(fpy);
                    W2Amounts.setYIELD(YIELD);
                    W2Amounts.setMISDETET(MISDETET);
                    W2Amounts.setFPYTarget(W2FPYTarget);
                    W2AmountsList.add(W2Amounts);
                }
            }
            if ( W2AmountsList.size()>=3){
                W2AmountsList= W2AmountsList.subList(0,3);
            }else if ( W2AmountsList.size()==2){
                W2AmountsList=  W2AmountsList.subList(0,2);
            }else if ( W2AmountsList.size()==1){
                W2AmountsList=  W2AmountsList.subList(0,1);
            }
        }
        if (SumUphTarget-SCONDsum>200){
            sumNumber.setActionSCONDsum(actionSCONDamounts.intValue());
            List<AmountsUPH> amountsUPHS = autoFloorUphService.MachinaeUPHNight("SCOND", StartDate,EndDate, Linename, Floorname, Productname);
            List<AmountsUPH> amountsDetail = autoFloorUphService.MachinaeDetailNight("SCOND", StartDate,EndDate,Linename, Floorname, Productname);
            SCONDAmountsList.addAll(amountsDetail);
            for (int i = 0; i < amountsUPHS.size(); i++) {
                AmountsUPH  SCONDAmounts= new AmountsUPH();
                AmountsUPH amountsUPH = amountsUPHS.get(i);
                if (amountsUPH.getAmountuph()<actionSCONDamounts){

                    SCONDAmounts.setLINE_NAME(amountsUPH.getLINE_NAME());
                    SCONDAmounts.setAmountuph(amountsUPH.getAmountuph());
                    SCONDAmounts.setMachine_no(amountsUPH.getMachine_no());
                    List<AmountsUPH> amountsUPHSList= new ArrayList<>();
                    amountsUPHSList =autoFloorUphService.MachinaeUPHFPYTop3Night(amountsUPH.getMachine_no(), StartDate, EndDate, Linename, Floorname, Productname);


                    Integer fail_q=0;
                    Double  fpy=0.0;
                    Double  YIELD=0.0;
                    Double  MISDETET=0.0;

                    if (amountsUPHSList.size()!=0){
                        AmountsUPH amountsUPH1 = amountsUPHSList.get(0);
                        if (amountsUPH1.getFAIL_Q()!=null){
                            fail_q=amountsUPH1.getFAIL_Q().intValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHfpy = amountsUPH1.getFPY();
                            BigDecimal fpybig =new BigDecimal(UPHfpy*100);
                            fpy = fpybig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHYIELD = amountsUPH1.getYIELD();
                            BigDecimal YIELDbig =new BigDecimal(UPHYIELD*100);
                            YIELD = YIELDbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                        if (amountsUPH1.getFPY()!=null){
                            Double UPHMISDETET = amountsUPH1.getMISDETET();
                            BigDecimal MISDETETbig =new BigDecimal(UPHMISDETET*100);
                            MISDETET = MISDETETbig.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        }
                    }

                    SCONDAmounts.setFAIL_Q(fail_q);
                    SCONDAmounts.setFPY(fpy);
                    SCONDAmounts.setYIELD(YIELD);
                    SCONDAmounts.setMISDETET(MISDETET);
                    SCONDAmounts.setFPYTarget(SCONDFPYTarget);
                    SCONDAmountsList.add(SCONDAmounts);
                }
            }
            if ( SCONDAmountsList.size()>=3){
                SCONDAmountsList= SCONDAmountsList.subList(0,3);
            }else if ( SCONDAmountsList.size()==2){
                SCONDAmountsList=  SCONDAmountsList.subList(0,2);
            }else if ( SCONDAmountsList.size()==1){
                SCONDAmountsList=  SCONDAmountsList.subList(0,1);
            }
        }
        sumNumber.setCCTsum(CCTsum);
        sumNumber.setDFUsum(DFUsum);
        sumNumber.setFCTsum(FCTsum);
        sumNumber.setS1sum(S1sum);
        sumNumber.setS2sum(S2sum);
        sumNumber.setS3sum(S3sum);
        sumNumber.setUWBsum(UWBsum);
        sumNumber.setW1sum(W1sum);
        sumNumber.setW2sum(W2sum);
        sumNumber.setSCONDsum(SCONDsum);
        map.put("Targetaction2030_2130",Targetaction2030_2130);
        map.put("Targetaction2130_2230",Targetaction2130_2230);
        map.put("Targetaction2230_2330",Targetaction2230_2330);
        map.put("Targetaction0030_0130",Targetaction0030_0130);
        map.put("Targetaction0130_0230",Targetaction0130_0230);
        map.put("Targetaction0230_0330",Targetaction0230_0330);
        map.put("Targetaction0330_0430",Targetaction0330_0430);
        map.put("Targetaction0430_0530",Targetaction0430_0530);
        map.put("Targetaction0530_0630",Targetaction0530_0630);
        map.put("Targetaction0630_0730",Targetaction0630_0730);

        map.put("sumNumber" , sumNumber);
        map.put("DFUAmountsList",   DFUAmountsList);
        map.put("FCTAmountsList",   FCTAmountsList);
        map.put("S1AmountsList",    S1AmountsList);
        map.put("S2AmountsList",    S2AmountsList);
        map.put("S3AmountsList",    S3AmountsList);
        map.put("CCTAmountsList", CCTAmountsList);
        map.put("W1AmountsList",    W1AmountsList);
        map.put("W2AmountsList",    W2AmountsList);
        map.put("UWBAmountsList",   UWBAmountsList);
        map.put("SCONDAmountsList", SCONDAmountsList);
        map.put("AllUph",AllUph);
        /*工站小时目标*/
        AllUph.put("hUPHS", hTarget);
        map.put("FloorName", Floorname);
        /*产线名称*/
        AllUph.put("Linename", Linename);
        long EnddebugTime =System.currentTimeMillis();
        System.out.println("程序运行时间："+(EnddebugTime-StartdebugTime)+"ms");
        return "realtime/AllFloorsNightOutput";

    }


}
