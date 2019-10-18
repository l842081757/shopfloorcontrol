package com.shop.shopfloorcontroller;

import com.shop.model.AUTOFLOOR_TARGET;
import com.shop.model.AutoFloor_Rate;
import com.shop.services.AUTOFLOOR_TARGEService;
import com.shop.services.AutoFloor_RateService;
import com.shop.services.AutoFloor_uphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.util.Date;
import java.util.*;

/**
 * @author
 * @create 2019-08-28 15:58
 */
@Controller
public class AutoFloormidetectContller {
    @Autowired
    AutoFloor_RateService autoFloor_rateService;
    @Autowired
    AutoFloor_uphService autoFloor_uphService;
    @Autowired
    TaskService taskService;
    @Autowired
    TimeUtility timeUtility;
    @Autowired
    AUTOFLOOR_TARGEService autofloorTargeService;
    private  static String FloorName="D073F";
    @RequestMapping(value = "/Allfloormidetect",method = RequestMethod.GET)
    public String Midetectdata(Map map ,@RequestParam("TimeDay")String TimeDay,@RequestParam("Linename") String linename,@RequestParam("productName") String productName){
        /*每时间段每个工站Map*/
        List<java.sql.Date> sqlList = taskService.Schedule();
        /*当前日期*/
        Date schedule =sqlList.get(0);
        /*当前日期减1*/
        Date YesterdayDate =sqlList.get(1);
        /*当前日期加1*/
        Date TomorrowDate =sqlList.get(2);
        /*当前时间*/
        /*过24时 时间变量*/
        Date StartDate =null;
        Date EndDate=null;
        if (timeUtility.Timequantum2029_0000()){
            StartDate =schedule;
            EndDate=TomorrowDate;
        }else if (timeUtility.Timequantum0000_2029()){
            StartDate =YesterdayDate;
            EndDate=schedule;
        }

        AUTOFLOOR_TARGET targetsum = new AUTOFLOOR_TARGET();
        if (timeUtility.Timequantum0830_2029()){
            targetsum = autofloorTargeService.LinenameTarGet(linename,"Day", schedule,productName);
        }else {
            /*实时时段段目标值*/
            targetsum = autofloorTargeService.LinenameTarGet(linename,"Night",YesterdayDate,productName);
        }


        /*线体小时目标*/
        BigDecimal hTargets = targetsum.gethTarget();
        int hTarget = hTargets.intValue();
        Integer hTargetInteger =Integer.parseInt(hTargets.toString());
        map.put("HTarget",hTargets);
        /*线体楼层名称*/
        String Floorname = targetsum.gettFloor();
        /*线体天目标*/
        BigDecimal DTarget = targetsum.getdTarget();


        Date actionDate = schedule;
        List<AutoFloor_Rate> YieldList = new ArrayList<>();
        List<AutoFloor_Rate> MisdetetList = new ArrayList<>();
        List<AutoFloor_Rate> UnknownList = new ArrayList<>();
        List<AutoFloor_Rate> MidetectAutoList=new ArrayList<>();
        List<AutoFloor_Rate> autoFloor_ratesYield  =new ArrayList<>();
        List<AutoFloor_Rate> autoFloor_ratesMisdetet =new ArrayList<>();
        List<AutoFloor_Rate> autoFloor_ratesUnknown  =new ArrayList<>();
        String TimeDayFail="";

        if (TimeDay.equals("two")){
            if (timeUtility.Timequantum0830_1029()){
                YieldList= autoFloor_rateService.AutoFloorRate_Yield08aciton( schedule, Floorname, productName, linename);
                MisdetetList = autoFloor_rateService.AutoFloorRate_Misdetet08aciton(schedule,Floorname, productName, linename);
                UnknownList = autoFloor_rateService.AutoFloorRate_Unknown08aciton(schedule,Floorname, productName, linename);
                MidetectAutoList = autoFloor_rateService.MidetectAuto08(schedule,Floorname, productName, linename);
                autoFloor_ratesYield    = autoFloor_rateService.AutoFloorRate_Yield08aciton(schedule,FloorName,  productName, linename);
                autoFloor_ratesMisdetet = autoFloor_rateService.AutoFloorRate_Misdetet08aciton(schedule,FloorName,  productName, linename);
                autoFloor_ratesUnknown  = autoFloor_rateService.AutoFloorRate_Unknown08aciton(schedule,FloorName,  productName, linename);
                TimeDayFail="two";
            }else {
                YieldList=autoFloor_rateService.AutoFloorRate_Yield(Floorname, productName, linename);
                MisdetetList = autoFloor_rateService.AutoFloorRate_Misdetet(Floorname, productName, linename);
                UnknownList = autoFloor_rateService.AutoFloorRate_Unknown(Floorname, productName, linename);
                MidetectAutoList = autoFloor_rateService.MidetectAutoTwo(schedule, Floorname, productName, linename);
                 autoFloor_ratesYield    =autoFloor_rateService.AutoFloorRate_Yield(FloorName,  productName, linename);
                 autoFloor_ratesMisdetet =autoFloor_rateService.AutoFloorRate_Misdetet(FloorName,  productName, linename);
                 autoFloor_ratesUnknown  =autoFloor_rateService.AutoFloorRate_Unknown(FloorName,  productName, linename);
                TimeDayFail="two";
            }
        }else if (TimeDay.equals("Day")){
            YieldList=autoFloor_rateService.AutoFloorRate_YieldDay(actionDate,Floorname, productName, linename);
            MisdetetList = autoFloor_rateService.AutoFloorRate_MisdetetDay(actionDate,Floorname, productName, linename);
            UnknownList = autoFloor_rateService.AutoFloorRate_UnknownDay(actionDate,Floorname, productName, linename);
            MidetectAutoList = autoFloor_rateService.MidetectAutoDay(schedule,Floorname, productName, linename);
            autoFloor_ratesYield    =autoFloor_rateService.AutoFloorRate_YieldDay(actionDate,FloorName,  productName, linename);
            autoFloor_ratesMisdetet =autoFloor_rateService.AutoFloorRate_MisdetetDay(actionDate,FloorName,  productName, linename);
            autoFloor_ratesUnknown  =autoFloor_rateService.AutoFloorRate_UnknownDay(actionDate,FloorName,  productName, linename);
            TimeDayFail="Day";
        }else if (TimeDay.equals("Night")){
            if (timeUtility.Timequantum2030_2229()){
                YieldList=autoFloor_rateService.AutoFloorRate_Yield20aciton(schedule,Floorname, productName, linename);
                MisdetetList = autoFloor_rateService.AutoFloorRate_Misdetet20aciton(schedule,Floorname, productName, linename);
                UnknownList = autoFloor_rateService.AutoFloorRate_Unknown20aciton(schedule,Floorname, productName, linename);
                MidetectAutoList = autoFloor_rateService.MidetectAuto20(schedule,Floorname, productName, linename);
                 autoFloor_ratesYield    =autoFloor_rateService.AutoFloorRate_Yield20aciton(schedule,FloorName, productName, linename);
                 autoFloor_ratesMisdetet =autoFloor_rateService.AutoFloorRate_Misdetet20aciton(schedule,FloorName, productName, linename);
                 autoFloor_ratesUnknown  =autoFloor_rateService.AutoFloorRate_Unknown20aciton(schedule,FloorName, productName, linename);
                TimeDayFail="Night";
            }else {
                YieldList=autoFloor_rateService.AutoFloorRate_YieldNight(StartDate,EndDate,Floorname, productName, linename);
                MisdetetList = autoFloor_rateService.AutoFloorRate_MisdetetNight(StartDate,EndDate,Floorname, productName, linename);
                UnknownList = autoFloor_rateService.AutoFloorRate_UnknownNight(StartDate,EndDate,Floorname, productName, linename);
                MidetectAutoList = autoFloor_rateService.MidetectAutoNight(StartDate,EndDate,Floorname, productName, linename);
                autoFloor_ratesYield    =autoFloor_rateService.AutoFloorRate_YieldNight(StartDate,EndDate,FloorName,  productName, linename);
                autoFloor_ratesMisdetet =autoFloor_rateService.AutoFloorRate_MisdetetNight(StartDate,EndDate,FloorName,  productName, linename);
                autoFloor_ratesUnknown  =autoFloor_rateService.AutoFloorRate_UnknownNight(StartDate,EndDate,FloorName,  productName, linename);
                TimeDayFail="Night";
            }

        }else if (TimeDay.equals("Yesterday")){
            YieldList=autoFloor_rateService.AutoFloorRate_YieldYesterday(StartDate,EndDate,Floorname, productName, linename);
            MisdetetList = autoFloor_rateService.AutoFloorRate_MisdetetYesterday(StartDate,EndDate,Floorname, productName, linename);
            UnknownList = autoFloor_rateService.AutoFloorRate_UnknownYesterday(StartDate,EndDate,Floorname, productName, linename);
            MidetectAutoList = autoFloor_rateService.MidetectAutoYesterday(StartDate,EndDate,Floorname, productName, linename);
            autoFloor_ratesYield    =autoFloor_rateService.AutoFloorRate_YieldYesterday(StartDate,EndDate,FloorName,  productName, linename);
            autoFloor_ratesMisdetet =autoFloor_rateService.AutoFloorRate_MisdetetYesterday(StartDate,EndDate,FloorName,  productName, linename);
            autoFloor_ratesUnknown  =autoFloor_rateService.AutoFloorRate_UnknownYesterday(StartDate,EndDate,FloorName,  productName, linename);
            TimeDayFail="Yesterday";

        }
        map.put("TimeDayFail",TimeDayFail);
        map.put("linename",linename);
        String ProductnameCCT="CCT_COMBO";
        String ProductnameWIFIBT="WIFI/BT_W1";
        String ProductnameWIFIBT2="WIFI/BT_W2";
        if (productName.equals("Macan")){
            ProductnameCCT="CCT";
            ProductnameWIFIBT="WIFI/BT";
            ProductnameWIFIBT2="WIFI/BT2";
        }
        Integer DFUMachinaeSum=0;
        Integer FCTMachinaeSum=0;
        Integer S1MachinaeSum=0;
        Integer S2MachinaeSum=0;
        Integer S3MachinaeSum=0;
        Integer CCTMachinaeSum=0;
        Integer W1MachinaeSum=0;
        Integer W2MachinaeSum=0;
        Integer UWBMachinaeSum=0;
        Integer SCONDMachinaeSum=0;


        DFUMachinaeSum= autoFloor_uphService.Workstationamount(Floorname, linename,"DFU",  schedule, productName);
        FCTMachinaeSum= autoFloor_uphService.Workstationamount(Floorname,  linename,"FCT", schedule, productName);
        S1MachinaeSum= autoFloor_uphService.Workstationamount(Floorname,  linename,"CELL_S1", schedule, productName);
        S2MachinaeSum= autoFloor_uphService.Workstationamount(Floorname, linename,"CELL_S2",  schedule, productName);
        S3MachinaeSum= autoFloor_uphService.Workstationamount(Floorname, linename,"CELL_S3",  schedule, productName);
        CCTMachinaeSum= autoFloor_uphService.Workstationamount(Floorname,linename,  ProductnameCCT,  schedule, productName);
        W1MachinaeSum= autoFloor_uphService.Workstationamount(Floorname, linename,ProductnameWIFIBT, schedule, productName);
        W2MachinaeSum= autoFloor_uphService.Workstationamount(Floorname, linename,ProductnameWIFIBT2,  schedule, productName);
        UWBMachinaeSum= autoFloor_uphService.Workstationamount(Floorname, linename,"UWB",  schedule,  productName);
        SCONDMachinaeSum= autoFloor_uphService.Workstationamount(Floorname,linename, "SCOND",  schedule, productName);
        map.put("DFUMachinaeSum", DFUMachinaeSum);
        map.put("FCTMachinaeSum",FCTMachinaeSum );
        map.put("S1MachinaeSum", S1MachinaeSum);
        map.put("S2MachinaeSum", S2MachinaeSum);
        map.put("S3MachinaeSum",S3MachinaeSum);
        map.put("CCTMachinaeSum",CCTMachinaeSum);
        map.put("W1MachinaeSum",W1MachinaeSum);
        map.put("W2MachinaeSum",W2MachinaeSum);
        map.put("UWBMachinaeSum",UWBMachinaeSum);
        map.put("SCONDMachinaeSum",SCONDMachinaeSum);

        Integer InputAll        =0;
        Integer InputAllsum     =0;
        Integer PassAll         =0;
        Integer PassAllsum      =0;
        Double  YieldAll        =1.0;
        Integer YieldAllsum     =0;
        Double  MisdetetAll     =0.0;
        Integer MisdetetAllsum  =0;
        Double  UnknownAll      =0.0;
        Integer UnknownAllsum   =0;
        Double  FPYAll          =0.0;
        Integer FPYAllsum       =0;

        /*---------- Input pass -------------*/
        Map<String,AutoFloor_Rate>  MidetectAutoMap=new HashMap<>();
        for (int i = 0; i < MidetectAutoList.size(); i++) {
            AutoFloor_Rate autoFloor_rate = MidetectAutoList.get(i);
            if (autoFloor_rate.getStationName().equals("DFU")){
                AutoFloor_Rate AutoFloor_Auto = new AutoFloor_Rate();
                BigDecimal input = autoFloor_rate.getInput();
                if (input.intValue()!=0){
                    InputAll+=input.intValue();
                    InputAllsum++;
                }
                AutoFloor_Auto.setInput(input);
                BigDecimal pass = autoFloor_rate.getPass();

                if (pass.intValue()!=0){
                    PassAll+=pass.intValue();
                    PassAllsum++;
                }
                AutoFloor_Auto.setPass(pass);
                MidetectAutoMap.put("AutoDFU",AutoFloor_Auto);
            }else if (autoFloor_rate.getStationName().equals("FCT")){
                AutoFloor_Rate AutoFloor_Auto = new AutoFloor_Rate();
                BigDecimal input = autoFloor_rate.getInput();
                if (input.intValue()!=0){
                    InputAll+=input.intValue();
                    InputAllsum++;
                }
                AutoFloor_Auto.setInput(input);
                BigDecimal pass = autoFloor_rate.getPass();

                if (pass.intValue()!=0){
                    PassAll+=pass.intValue();
                    PassAllsum++;
                }
                AutoFloor_Auto.setPass(pass);
                MidetectAutoMap.put("AutoFCT",AutoFloor_Auto);
            }else if (autoFloor_rate.getStationName().equals("CELL_S1")){
                AutoFloor_Rate AutoFloor_Auto = new AutoFloor_Rate();
                BigDecimal input = autoFloor_rate.getInput();
                if (input.intValue()!=0){
                    InputAll+=input.intValue();
                    InputAllsum++;
                }
                AutoFloor_Auto.setInput(input);
                BigDecimal pass = autoFloor_rate.getPass();

                if (pass.intValue()!=0){
                    PassAll+=pass.intValue();
                    PassAllsum++;
                }
                AutoFloor_Auto.setPass(pass);
                MidetectAutoMap.put("AutoS1",AutoFloor_Auto);

            }else if (autoFloor_rate.getStationName().equals("CELL_S2")){
                AutoFloor_Rate AutoFloor_Auto = new AutoFloor_Rate();
                BigDecimal input = autoFloor_rate.getInput();
                if (input.intValue()!=0){
                    InputAll+=input.intValue();
                    InputAllsum++;
                }
                AutoFloor_Auto.setInput(input);
                BigDecimal pass = autoFloor_rate.getPass();

                if (pass.intValue()!=0){
                    PassAll+=pass.intValue();
                    PassAllsum++;
                }
                AutoFloor_Auto.setPass(pass);
                MidetectAutoMap.put("AutoS2",AutoFloor_Auto);

            }else if (autoFloor_rate.getStationName().equals("CELL_S3")){
                AutoFloor_Rate AutoFloor_Auto = new AutoFloor_Rate();
                BigDecimal input = autoFloor_rate.getInput();
                if (input.intValue()!=0){
                    InputAll+=input.intValue();
                    InputAllsum++;
                }
                AutoFloor_Auto.setInput(input);
                BigDecimal pass = autoFloor_rate.getPass();

                if (pass.intValue()!=0){
                    PassAll+=pass.intValue();
                    PassAllsum++;
                }
                AutoFloor_Auto.setPass(pass);
                MidetectAutoMap.put("AutoS3",AutoFloor_Auto);

            }else if (autoFloor_rate.getStationName().equals(ProductnameCCT)){
                AutoFloor_Rate AutoFloor_Auto = new AutoFloor_Rate();
                BigDecimal input = autoFloor_rate.getInput();
                if (input.intValue()!=0){
                    InputAll+=input.intValue();
                    InputAllsum++;
                }
                AutoFloor_Auto.setInput(input);
                BigDecimal pass = autoFloor_rate.getPass();

                if (pass.intValue()!=0){
                    PassAll+=pass.intValue();
                    PassAllsum++;
                }
                AutoFloor_Auto.setPass(pass);
                MidetectAutoMap.put("AutoCCT",AutoFloor_Auto);
            }else if (autoFloor_rate.getStationName().equals(ProductnameWIFIBT)){
                AutoFloor_Rate AutoFloor_Auto = new AutoFloor_Rate();
                BigDecimal input = autoFloor_rate.getInput();
                if (input.intValue()!=0){
                    InputAll+=input.intValue();
                    InputAllsum++;
                }
                AutoFloor_Auto.setInput(input);
                BigDecimal pass = autoFloor_rate.getPass();

                if (pass.intValue()!=0){
                    PassAll+=pass.intValue();
                    PassAllsum++;
                }
                AutoFloor_Auto.setPass(pass);
                MidetectAutoMap.put("AutoW1",AutoFloor_Auto);
            }else if (autoFloor_rate.getStationName().equals(ProductnameWIFIBT2)){
                AutoFloor_Rate AutoFloor_Auto = new AutoFloor_Rate();
                BigDecimal input = autoFloor_rate.getInput();
                if (input.intValue()!=0){
                    InputAll+=input.intValue();
                    InputAllsum++;
                }
                AutoFloor_Auto.setInput(input);
                BigDecimal pass = autoFloor_rate.getPass();

                if (pass.intValue()!=0){
                    PassAll+=pass.intValue();
                    PassAllsum++;
                }
                AutoFloor_Auto.setPass(pass);
                MidetectAutoMap.put("AutoW2",AutoFloor_Auto);
            }else if (autoFloor_rate.getStationName().equals("SCOND")){
                AutoFloor_Rate AutoFloor_Auto = new AutoFloor_Rate();
                BigDecimal input = autoFloor_rate.getInput();
                if (input.intValue()!=0){
                    InputAll+=input.intValue();
                    InputAllsum++;
                }
                AutoFloor_Auto.setInput(input);
                BigDecimal pass = autoFloor_rate.getPass();

                if (pass.intValue()!=0){
                    PassAll+=pass.intValue();
                    PassAllsum++;
                }
                AutoFloor_Auto.setPass(pass);
                MidetectAutoMap.put("AutoSCOND",AutoFloor_Auto);
            }else if (autoFloor_rate.getStationName().equals("UWB")){
                AutoFloor_Rate AutoFloor_Auto = new AutoFloor_Rate();
                BigDecimal input = autoFloor_rate.getInput();
                if (input.intValue()!=0){
                    InputAll+=input.intValue();
                    InputAllsum++;
                }
                AutoFloor_Auto.setInput(input);
                BigDecimal pass = autoFloor_rate.getPass();

                if (pass.intValue()!=0){
                    PassAll+=pass.intValue();
                    PassAllsum++;
                }
                AutoFloor_Auto.setPass(pass);
                MidetectAutoMap.put("AutoUWB",AutoFloor_Auto);
            }
        }
        map.put("MidetectAutoMap",MidetectAutoMap);
        Map<String,Object>  YieldMap=new HashMap<>();
/*---------- 良率 -------------*/
        /*-----ALL--------*/
        Double ALLYieldALL=1.0;
        for (AutoFloor_Rate floor_rate : autoFloor_ratesYield) {
            ALLYieldALL*=floor_rate.getRate();
        }
        map.put("ALLYieldALL", ALLYieldALL);

        for (int i = 0; i < YieldList.size(); i++) {
            AutoFloor_Rate autoFloor_rate = YieldList.get(i);
            if (autoFloor_rate.getStationName().equals("DFU")){
                Double Yield = autoFloor_rate.getRate();
                /* 良率比例*/
                Yield*=100;
                Double YieldRatio=0.0;
                if (Yield!=0){
                    BigDecimal YieldBig = new BigDecimal(Yield);
                    Double Yielddoubles = YieldBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    YieldRatio=Yielddoubles;
                    YieldAllsum++;
                }
                YieldAll+=YieldRatio;
                YieldMap.put("YieldDFU",YieldRatio);
            }else if (autoFloor_rate.getStationName().equals("FCT")){
                Double Yield = autoFloor_rate.getRate();
                /* 良率比例*/
                Yield*=100;
                Double YieldRatio=0.0;
                if (Yield!=0){
                    BigDecimal YieldBig = new BigDecimal(Yield);
                    Double Yielddoubles = YieldBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    YieldRatio=Yielddoubles;
                    YieldAllsum++;
                }
                YieldAll+=YieldRatio;
                YieldMap.put("YieldFCT",YieldRatio);
            }else if (autoFloor_rate.getStationName().equals("CELL_S1")){
                Double Yield = autoFloor_rate.getRate();
                /* 良率比例*/
                Yield*=100;
                Double YieldRatio=0.0;
                if (Yield!=0){
                    BigDecimal YieldBig = new BigDecimal(Yield);
                    Double Yielddoubles = YieldBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    YieldRatio=Yielddoubles;
                    YieldAllsum++;
                }
                YieldAll+=YieldRatio;
                YieldMap.put("YieldS1",YieldRatio);

            }else if (autoFloor_rate.getStationName().equals("CELL_S2")){
                Double Yield = autoFloor_rate.getRate();
                /* 良率比例*/
                Yield*=100;
                Double YieldRatio=0.0;
                if (Yield!=0){
                    BigDecimal YieldBig = new BigDecimal(Yield);
                    Double Yielddoubles = YieldBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    YieldRatio=Yielddoubles;
                    YieldAllsum++;
                }
                YieldAll+=YieldRatio;
                YieldMap.put("YieldS2",YieldRatio);

            }else if (autoFloor_rate.getStationName().equals("CELL_S3")){
                Double Yield = autoFloor_rate.getRate();
                /* 良率比例*/
                Yield*=100;
                Double YieldRatio=0.0;
                if (Yield!=0){
                    BigDecimal YieldBig = new BigDecimal(Yield);
                    Double Yielddoubles = YieldBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    YieldRatio=Yielddoubles;
                    YieldAllsum++;
                }
                YieldAll+=YieldRatio;
                YieldMap.put("YieldS3",YieldRatio);

            }else if (autoFloor_rate.getStationName().equals(ProductnameCCT)){
                Double Yield = autoFloor_rate.getRate();
                /* 良率比例*/
                Yield*=100;
                Double YieldRatio=0.0;
                if (Yield!=0){
                    BigDecimal YieldBig = new BigDecimal(Yield);
                    Double Yielddoubles = YieldBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    YieldRatio=Yielddoubles;
                    YieldAllsum++;
                }
                YieldAll+=YieldRatio;
                YieldMap.put("YieldCCT",YieldRatio);
            }else if (autoFloor_rate.getStationName().equals(ProductnameWIFIBT)){
                Double Yield = autoFloor_rate.getRate();
                /* 良率比例*/
                Yield*=100;
                Double YieldRatio=0.0;
                if (Yield!=0){
                    BigDecimal YieldBig = new BigDecimal(Yield);
                    Double Yielddoubles = YieldBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    YieldRatio=Yielddoubles;
                    YieldAllsum++;
                }
                YieldAll+=YieldRatio;
                YieldMap.put("YieldW1",YieldRatio);
            }else if (autoFloor_rate.getStationName().equals(ProductnameWIFIBT2)){
                Double Yield = autoFloor_rate.getRate();
                /* 良率比例*/
                Yield*=100;
                Double YieldRatio=0.0;
                if (Yield!=0){
                    BigDecimal YieldBig = new BigDecimal(Yield);
                    Double Yielddoubles = YieldBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    YieldRatio=Yielddoubles;
                    YieldAllsum++;
                }
                YieldAll+=YieldRatio;
                YieldMap.put("YieldW2",YieldRatio);
            }else if (autoFloor_rate.getStationName().equals("SCOND")){
                Double Yield = autoFloor_rate.getRate();
                /* 良率比例*/
                Yield*=100;
                Double YieldRatio=0.0;
                if (Yield!=0){
                    BigDecimal YieldBig = new BigDecimal(Yield);
                    Double Yielddoubles = YieldBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    YieldRatio=Yielddoubles;
                    YieldAllsum++;
                }
                YieldAll+=YieldRatio;
                YieldMap.put("YieldSCOND",YieldRatio);
            }else if (autoFloor_rate.getStationName().equals("UWB")){
                Double Yield = autoFloor_rate.getRate();
                /* 良率比例*/
                Yield*=100;
                Double YieldRatio=0.0;
                if (Yield!=0){
                    BigDecimal YieldBig = new BigDecimal(Yield);
                    Double Yielddoubles = YieldBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    YieldRatio=Yielddoubles;
                    YieldAllsum++;
                }
                YieldAll+=YieldRatio;
                YieldMap.put("YieldUWB",YieldRatio);
            }
        }

/*---------- 误测率 --------------*/
        /*---------ALL---------*/
        Double ALLMisdetetALL=0.0;
        for (AutoFloor_Rate floor_rate : autoFloor_ratesMisdetet) {
            ALLMisdetetALL+=floor_rate.getRate();
        }
        map.put("ALLMisdetetALL", ALLMisdetetALL);
        Map<String,Object> MisdetetMap= new HashMap<>();

            for (int i = 0; i < MisdetetList.size(); i++) {
                AutoFloor_Rate autoFloor_rate = MisdetetList.get(i);
               if (autoFloor_rate.getStationName().equals("DFU")){
                   Double Misdetet = autoFloor_rate.getRate();
                   /* 良率比例*/
                   Misdetet*=100;
                   Double MisdetetRatio=0.0;
                   if (Misdetet!=0){
                       BigDecimal MisdetetBig = new BigDecimal(Misdetet);
                       Double MisdetetDoubles = MisdetetBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                       MisdetetRatio=MisdetetDoubles;
                       MisdetetAllsum++;
                   }
                   MisdetetAll+=MisdetetRatio;
                   MisdetetMap.put("MisdetetDFU",MisdetetRatio);
               }else if (autoFloor_rate.getStationName().equals("FCT")){
                   Double Misdetet = autoFloor_rate.getRate();
                   /* 良率比例*/
                   Misdetet*=100;
                   Double MisdetetRatio=0.0;
                   if (Misdetet!=0){
                       BigDecimal MisdetetBig = new BigDecimal(Misdetet);
                       Double MisdetetDoubles = MisdetetBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                       MisdetetRatio=MisdetetDoubles;
                       MisdetetAllsum++;
                   }
                   MisdetetAll+=MisdetetRatio;
                   MisdetetMap.put("MisdetetFCT",MisdetetRatio);

               }else if (autoFloor_rate.getStationName().equals("CELL_S1")){
                   Double Misdetet = autoFloor_rate.getRate();
                   /* 良率比例*/
                   Misdetet*=100;
                   Double MisdetetRatio=0.0;
                   if (Misdetet!=0){
                       BigDecimal MisdetetBig = new BigDecimal(Misdetet);
                       Double MisdetetDoubles = MisdetetBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                       MisdetetRatio=MisdetetDoubles;
                       MisdetetAllsum++;
                   }
                   MisdetetAll+=MisdetetRatio;
                   MisdetetMap.put("MisdetetS1",MisdetetRatio);

               }else if (autoFloor_rate.getStationName().equals("CELL_S2")){
                   Double Misdetet = autoFloor_rate.getRate();
                   /* 良率比例*/
                   Misdetet*=100;
                   Double MisdetetRatio=0.0;
                   if (Misdetet!=0){
                       BigDecimal MisdetetBig = new BigDecimal(Misdetet);
                       Double MisdetetDoubles = MisdetetBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                       MisdetetRatio=MisdetetDoubles;
                       MisdetetAllsum++;
                   }
                   MisdetetMap.put("MisdetetS2",MisdetetRatio);

               }else if (autoFloor_rate.getStationName().equals("CELL_S3")){
                   Double Misdetet = autoFloor_rate.getRate();
                   /* 良率比例*/
                   Misdetet*=100;
                   Double MisdetetRatio=0.0;
                   if (Misdetet!=0){
                       BigDecimal MisdetetBig = new BigDecimal(Misdetet);
                       Double MisdetetDoubles = MisdetetBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                       MisdetetRatio=MisdetetDoubles;
                       MisdetetAllsum++;
                   }
                   MisdetetAll+=MisdetetRatio;
                   MisdetetMap.put("MisdetetS3",MisdetetRatio);

               }else if (autoFloor_rate.getStationName().equals(ProductnameCCT)){
                   Double Misdetet = autoFloor_rate.getRate();
                   /* 良率比例*/
                   Misdetet*=100;
                   Double MisdetetRatio=0.0;
                   if (Misdetet!=0){
                       BigDecimal MisdetetBig = new BigDecimal(Misdetet);
                       Double MisdetetDoubles = MisdetetBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                       MisdetetRatio=MisdetetDoubles;
                       MisdetetAllsum++;
                   }
                   MisdetetAll+=MisdetetRatio;
                   MisdetetMap.put("MisdetetCCT",MisdetetRatio);

               }else if (autoFloor_rate.getStationName().equals(ProductnameWIFIBT)){
                   Double Misdetet = autoFloor_rate.getRate();
                   /* 良率比例*/
                   Misdetet*=100;
                   Double MisdetetRatio=0.0;
                   if (Misdetet!=0){
                       BigDecimal MisdetetBig = new BigDecimal(Misdetet);
                       Double MisdetetDoubles = MisdetetBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                       MisdetetRatio=MisdetetDoubles;
                       MisdetetAllsum++;
                   }
                   MisdetetAll+=MisdetetRatio;
                   MisdetetMap.put("MisdetetW1",MisdetetRatio);

               }else if (autoFloor_rate.getStationName().equals(ProductnameWIFIBT2)){
                   Double Misdetet = autoFloor_rate.getRate();
                   /* 良率比例*/
                   Misdetet*=100;
                   Double MisdetetRatio=0.0;
                   if (Misdetet!=0){
                       BigDecimal MisdetetBig = new BigDecimal(Misdetet);
                       Double MisdetetDoubles = MisdetetBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                       MisdetetRatio=MisdetetDoubles;
                       MisdetetAllsum++;
                   }
                   MisdetetAll+=MisdetetRatio;
                   MisdetetMap.put("MisdetetW2",MisdetetRatio);

               }else if (autoFloor_rate.getStationName().equals("SCOND")){
                   Double Misdetet = autoFloor_rate.getRate();
                   /* 良率比例*/
                   Misdetet*=100;
                   Double MisdetetRatio=0.0;
                   if (Misdetet!=0){
                       BigDecimal MisdetetBig = new BigDecimal(Misdetet);
                       Double MisdetetDoubles = MisdetetBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                       MisdetetRatio=MisdetetDoubles;
                       MisdetetAllsum++;
                   }
                   MisdetetAll+=MisdetetRatio;
                   MisdetetMap.put("MisdetetSCOND",MisdetetRatio);

               }else if (autoFloor_rate.getStationName().equals("UWB")){
                   Double Misdetet = autoFloor_rate.getRate();
                   /* 良率比例*/
                   Misdetet*=100;
                   Double MisdetetRatio=0.0;
                   if (Misdetet!=0){
                       BigDecimal MisdetetBig = new BigDecimal(Misdetet);
                       Double MisdetetDoubles = MisdetetBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                       MisdetetRatio=MisdetetDoubles;
                       MisdetetAllsum++;
                   }
                   MisdetetAll+=MisdetetRatio;
                   MisdetetMap.put("MisdetetUWB",MisdetetRatio);

               }
            }


/*-------------- UnKnown率 -------------- */
        /*------ALL----------*/
        Double ALLUnknownALL=0.0;
        for (AutoFloor_Rate floor_rate : autoFloor_ratesUnknown) {
            ALLUnknownALL+=floor_rate.getRate();
        }
        map.put("ALLUnknownALL",ALLUnknownALL);
        Map<String,Object> UnknownMap= new HashMap<>();
        for (int i = 0; i < UnknownList.size(); i++) {
            AutoFloor_Rate autoFloor_rate = UnknownList.get(i);
            if (autoFloor_rate.getStationName().equals("DFU")){
                Double Unknown = autoFloor_rate.getRate();
                /* 良率比例*/
                Unknown*=100;
                Double UnknownRatio=0.0;
                if (Unknown!=0){
                    BigDecimal UnknownBig = new BigDecimal(Unknown);
                    Double UnknownDoubles = UnknownBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    UnknownRatio=UnknownDoubles;
                    UnknownAllsum++;
                }
                UnknownAll+=UnknownRatio;
                UnknownMap.put("UnknownDFU",UnknownRatio);
            }else if (autoFloor_rate.getStationName().equals("FCT")){
                Double Unknown = autoFloor_rate.getRate();
                /* 良率比例*/
                Unknown*=100;
                Double UnknownRatio=0.0;
                if (Unknown!=0){
                    BigDecimal UnknownBig = new BigDecimal(Unknown);
                    Double UnknownDoubles = UnknownBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    UnknownRatio=UnknownDoubles;
                    UnknownAllsum++;
                }
                UnknownAll+=UnknownRatio;
                UnknownMap.put("UnknownFCT",UnknownRatio);

            }else if (autoFloor_rate.getStationName().equals("CELL_S1")){
                Double Unknown = autoFloor_rate.getRate();
                /* 良率比例*/
                Unknown*=100;
                Double UnknownRatio=0.0;
                if (Unknown!=0){
                    BigDecimal UnknownBig = new BigDecimal(Unknown);
                    Double UnknownDoubles = UnknownBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    UnknownRatio=UnknownDoubles;
                    UnknownAllsum++;
                }
                UnknownAll+=UnknownRatio;
                UnknownMap.put("UnknownS1",UnknownRatio);

            }else if (autoFloor_rate.getStationName().equals("CELL_S2")){
                Double Unknown = autoFloor_rate.getRate();
                /* 良率比例*/
                Unknown*=100;
                Double UnknownRatio=0.0;
                if (Unknown!=0){
                    BigDecimal UnknownBig = new BigDecimal(Unknown);
                    Double UnknownDoubles = UnknownBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    UnknownRatio=UnknownDoubles;
                    UnknownAllsum++;
                }
                UnknownAll+=UnknownRatio;
                UnknownMap.put("UnknownS2",UnknownRatio);

            }else if (autoFloor_rate.getStationName().equals("CELL_S3")){
                Double Unknown = autoFloor_rate.getRate();
                /* 良率比例*/
                Unknown*=100;
                Double UnknownRatio=0.0;
                if (Unknown!=0){
                    BigDecimal UnknownBig = new BigDecimal(Unknown);
                    Double UnknownDoubles = UnknownBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    UnknownRatio=UnknownDoubles;
                    UnknownAllsum++;
                }
                UnknownAll+=UnknownRatio;
                UnknownMap.put("UnknownS3",UnknownRatio);

            }else if (autoFloor_rate.getStationName().equals(ProductnameCCT)){
                Double Unknown = autoFloor_rate.getRate();
                /* 良率比例*/
                Unknown*=100;
                Double UnknownRatio=0.0;
                if (Unknown!=0){
                    BigDecimal UnknownBig = new BigDecimal(Unknown);
                    Double UnknownDoubles = UnknownBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    UnknownRatio=UnknownDoubles;
                    UnknownAllsum++;
                }
                UnknownAll+=UnknownRatio;
                UnknownMap.put("UnknownCCT",UnknownRatio);

            }else if (autoFloor_rate.getStationName().equals(ProductnameWIFIBT)){
                Double Unknown = autoFloor_rate.getRate();
                /* 良率比例*/
                Unknown*=100;
                Double UnknownRatio=0.0;
                if (Unknown!=0){
                    BigDecimal UnknownBig = new BigDecimal(Unknown);
                    Double UnknownDoubles = UnknownBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    UnknownRatio=UnknownDoubles;
                    UnknownAllsum++;
                }
                UnknownAll+=UnknownRatio;
                UnknownMap.put("UnknownW1",UnknownRatio);

            }else if (autoFloor_rate.getStationName().equals(ProductnameWIFIBT2)){
                Double Unknown = autoFloor_rate.getRate();
                /* 良率比例*/
                Unknown*=100;
                Double UnknownRatio=0.0;
                if (Unknown!=0){
                    BigDecimal UnknownBig = new BigDecimal(Unknown);
                    Double UnknownDoubles = UnknownBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    UnknownRatio=UnknownDoubles;
                    UnknownAllsum++;
                }
                UnknownAll+=UnknownRatio;
                UnknownMap.put("UnknownW2",UnknownRatio);

            }else if (autoFloor_rate.getStationName().equals("SCOND")){
                Double Unknown = autoFloor_rate.getRate();
                /* 良率比例*/
                Unknown*=100;
                Double UnknownRatio=0.0;
                if (Unknown!=0){
                    BigDecimal UnknownBig = new BigDecimal(Unknown);
                    Double UnknownDoubles = UnknownBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    UnknownRatio=UnknownDoubles;
                    UnknownAllsum++;
                }
                UnknownAll+=UnknownRatio;
                UnknownMap.put("UnknownSCOND",UnknownRatio);

            }else if (autoFloor_rate.getStationName().equals("UWB")){
                Double Unknown = autoFloor_rate.getRate();
                /* 良率比例*/
                Unknown*=100;
                Double UnknownRatio=0.0;
                if (Unknown!=0){
                    BigDecimal UnknownBig = new BigDecimal(Unknown);
                    Double UnknownDoubles = UnknownBig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    UnknownRatio=UnknownDoubles;
                    UnknownAllsum++;
                }
                UnknownAll+=UnknownRatio;
                UnknownMap.put("UnknownUWB",UnknownRatio);
            }
        }
        /*---------- 直通率 --------------*/

        Object  misdetetDFU   = MisdetetMap.get("MisdetetDFU");
        Object  misdetetFCT   = MisdetetMap.get("MisdetetFCT");
        Object  misdetetS1    = MisdetetMap.get("MisdetetS1");
        Object  misdetetS2    = MisdetetMap.get("MisdetetS2");
        Object  misdetetS3    = MisdetetMap.get("MisdetetS3");
        Object  misdetetCCT   = MisdetetMap.get("MisdetetCCT");
        Object  misdetetW1    = MisdetetMap.get("MisdetetW1");
        Object  misdetetW2    = MisdetetMap.get("MisdetetW2");
        Object  misdetetUWB   = MisdetetMap.get("MisdetetUWB");
        Object  misdetetSCOND = MisdetetMap.get("MisdetetSCOND");

        Object yieldDFU  = YieldMap.get("YieldDFU");
        Object yieldFCT  =YieldMap.get("YieldFCT");
        Object yieldS1   =YieldMap.get("YieldS1");
        Object yieldS2   =YieldMap.get("YieldS2");
        Object yieldS3   =YieldMap.get("YieldS3");
        Object yieldCCT  =YieldMap.get("YieldCCT");
        Object yieldW1   =YieldMap.get("YieldW1");
        Object yieldW2   =YieldMap.get("YieldW2");
        Object yieldUWB  =YieldMap.get("YieldUWB");
        Object yieldSCOND=YieldMap.get("YieldSCOND");

        Map<String,Object> FPYMap = new HashMap<>();
        Map<String,Integer> FPYOrangeMap= new HashMap<>();
        Map<String,Integer> FPYRedMap= new HashMap<>();

        List<AutoFloor_Rate> TopList= new ArrayList<>();

        Double FPYDFU=0.0;
        if (yieldDFU!=null&&misdetetDFU!=null){
            FPYDFU= (Double)yieldDFU-(Double)misdetetDFU;
            FPYAllsum++;
        }
        if (FPYDFU<0){
            FPYDFU*=0;
        }
        if (FPYDFU!=null&&FPYDFU!=0){
            BigDecimal FPYDFUbig= new BigDecimal(FPYDFU);
            FPYDFU=FPYDFUbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        Double FPYDFUVlaue = FPYDFU-98.90;
        if (FPYDFUVlaue>-2&&FPYDFUVlaue<0&&FPYDFU!=0){
            FPYOrangeMap.put("FPYDFUVlaue",-1);
        }else if (FPYDFUVlaue<-2&&FPYDFU!=0){
            FPYRedMap.put("FPYDFUVlaue",-1);
        }
        if (FPYDFUVlaue<0){
            AutoFloor_Rate FPYDFUBean= new AutoFloor_Rate();
            FPYDFUBean.setStationName("DFU");
            FPYDFUBean.setRATE1(FPYDFUVlaue);
            TopList.add(FPYDFUBean);
        }
        FPYAll+=FPYDFU;
        FPYMap.put("FPYDFU",FPYDFU);

        Double FPYFCT=0.0;
        if (yieldFCT!=null&&misdetetFCT!=null){
            FPYFCT=(Double)yieldFCT-(Double)misdetetFCT;
            FPYAllsum++;
        }
        if (FPYFCT<0){
            FPYFCT*=0;
        }
        if (FPYFCT!=null&&FPYFCT!=0){
            BigDecimal FPYFCTbig= new BigDecimal(FPYFCT);
            FPYFCT=FPYFCTbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();


        }
        Double FPYFCTVlaue= FPYFCT-99.20;
        if (FPYFCTVlaue>-2&&FPYFCTVlaue<0&&FPYFCT!=0){
            FPYOrangeMap.put("FPYFCTVlaue",-1);
        }else if (FPYFCTVlaue<-2&&FPYFCT!=0){
            FPYRedMap.put("FPYFCTVlaue",-1);
        }
        if (FPYFCTVlaue<0){
            AutoFloor_Rate FPYFCTBean= new AutoFloor_Rate();
            FPYFCTBean.setStationName("FCT");
            FPYFCTBean.setRATE1(FPYFCTVlaue);
            TopList.add(FPYFCTBean);
        }
        FPYAll+=FPYFCT;
        FPYMap.put("FPYFCT",FPYFCT);

        Double FPYS1=0.0;
        if (yieldS1!=null&&misdetetS1!=null){
            FPYS1=(Double)yieldS1-(Double)misdetetS1;
            FPYAllsum++;
        }
        if (FPYS1<0){
            FPYS1*=0;
        }
        if (FPYS1!=null&&FPYS1!=0){
            BigDecimal FPYS1big= new BigDecimal(FPYS1);
            FPYS1=FPYS1big.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        }
        Double FPYS1Vlaue=FPYS1-97.35;
        if (FPYS1Vlaue>-2&&FPYS1Vlaue<0&&FPYS1!=0){
            FPYOrangeMap.put("FPYS1Vlaue",-1);
        }else if (FPYS1Vlaue<-2&&FPYS1!=0){
            FPYRedMap.put("FPYS1Vlaue",-1);
        }
        if (FPYS1Vlaue<0){
            AutoFloor_Rate FPYS1Bean= new AutoFloor_Rate();
            FPYS1Bean.setStationName("CELL_S1");
            FPYS1Bean.setRATE1(FPYS1Vlaue);
            TopList.add(FPYS1Bean);
        }
        FPYAll+=FPYS1;
        FPYMap.put("FPYS1",FPYS1);

        Double FPYS2= 0.0;
        if (yieldS2!=null&&misdetetS2!=null){
            FPYS2=(Double)yieldS2-(Double)misdetetS2;
            FPYAllsum++;
        }
        if (FPYS2<0){
            FPYS2*=0;
        }
        if (FPYS2!=null&&FPYS2!=0){
            BigDecimal FPYS2big= new BigDecimal(FPYS2);
            FPYS2=FPYS2big.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        }
        Double FPYS2Vlaue=FPYS2-96.40;
        if (FPYS2Vlaue>-2&&FPYS2Vlaue<0&&FPYS2!=0){
            FPYOrangeMap.put("FPYS2Vlaue",-1);
        }else if (FPYS2Vlaue<-2&&FPYS2!=0){
            FPYRedMap.put("FPYS2Vlaue",-1);
        }
        if (FPYS2Vlaue<0){
            AutoFloor_Rate FPYS2Bean= new AutoFloor_Rate();
            FPYS2Bean.setStationName("CELL_S2");
            FPYS2Bean.setRATE1(FPYS2Vlaue);
            TopList.add(FPYS2Bean);
        }
        FPYAll+=FPYS2;
        FPYMap.put("FPYS2",FPYS2);

        Double FPYS3= 0.0;
        if (yieldS3!=null&&misdetetS3!=null){
            FPYS3=(Double)yieldS3-(Double)misdetetS3;
            FPYAllsum++;
        }
        if (FPYS3<0){
            FPYS3*=0;
        }
        if (FPYS3!=null&&FPYS3!=0){
            BigDecimal FPYS3big= new BigDecimal(FPYS3);
            FPYS3=FPYS3big.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        }
        Double FPYS3Vlaue=FPYS3-98.45;
        if (FPYS3Vlaue>-2&&FPYS3Vlaue<0&&FPYS3!=0){
            FPYOrangeMap.put("FPYS3Vlaue",-1);
        }else if (FPYS3Vlaue<-2&&FPYS3!=0){
            FPYRedMap.put("FPYS3Vlaue",-1);
        }
        if (FPYS3Vlaue<0){
            AutoFloor_Rate FPYS3Bean= new AutoFloor_Rate();
            FPYS3Bean.setStationName("CELL_S3");
            FPYS3Bean.setRATE1(FPYS3Vlaue);
            TopList.add(FPYS3Bean);
        }
        FPYAll+=FPYS3;
        FPYMap.put("FPYS3",FPYS3);

        Double FPYCCT=0.0;
        if (yieldCCT!=null&&misdetetCCT!=null){
            FPYCCT= (Double)yieldCCT-(Double)misdetetCCT;
            FPYAllsum++;
        }
        if (FPYCCT<0){
            FPYCCT*=0;
        }
        if (FPYCCT!=null&&FPYCCT!=0){
            BigDecimal FPYCCTbig= new BigDecimal(FPYCCT);
            FPYCCT=FPYCCTbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        }
        Double FPYCCTVlaue=FPYCCT-99.49;
        if (FPYCCTVlaue>-2&&FPYCCTVlaue<0&&FPYCCT!=0){
            FPYOrangeMap.put("FPYCCTVlaue",-1);
        }else if (FPYCCTVlaue<-2&&FPYCCT!=0){
            FPYRedMap.put("FPYCCTVlaue",-1);
        }
        if (FPYCCTVlaue<0){
            AutoFloor_Rate FPYCCTBean= new AutoFloor_Rate();
            FPYCCTBean.setStationName(ProductnameCCT);
            FPYCCTBean.setRATE1(FPYCCTVlaue);
            TopList.add(FPYCCTBean);
        }
        FPYAll+=FPYCCT;
        FPYMap.put("FPYCCT",FPYCCT);

        Double FPYW1= 0.0;
        if (yieldW1!=null&&misdetetW1!=null){
            FPYW1=(Double)yieldW1-(Double)misdetetW1;
            FPYAllsum++;
        }
        if (FPYW1<0){
            FPYW1*=0;
        }
        if (FPYW1!=null&&FPYW1!=0){
            BigDecimal FPYW1big= new BigDecimal(FPYW1);
            FPYW1=FPYW1big.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        }
        Double FPYW1Vlaue =FPYW1-97.45;
        if (FPYW1Vlaue>-2&&FPYW1Vlaue<0&&FPYW1!=0){
            FPYOrangeMap.put("FPYW1Vlaue",-1);
        }else if (FPYW1Vlaue<-2&&FPYW1!=0){
            FPYRedMap.put("FPYW1Vlaue",-1);
        }
        if (FPYW1Vlaue<0){
            AutoFloor_Rate FPYW1Bean= new AutoFloor_Rate();
            FPYW1Bean.setStationName(ProductnameWIFIBT);
            FPYW1Bean.setRATE1(FPYW1Vlaue);
            TopList.add(FPYW1Bean);
        }
        FPYAll+=FPYW1;
        FPYMap.put("FPYW1",FPYW1);

        Double FPYW2= 0.0;
        if (yieldW2!=null&&misdetetW2!=null){
            FPYW2=(Double)yieldW2-(Double)misdetetW2;
            FPYAllsum++;
        }
        if (FPYW2<0){
            FPYW2*=0;
        }
        if (FPYW2!=null&&FPYW2!=0){
            BigDecimal FPYW2big= new BigDecimal(FPYW2);
            FPYW2=FPYW2big.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        }
        Double FPYW2Vlaue=FPYW2-98.99;
        if (FPYW2Vlaue>-2&&FPYW2Vlaue<0&&FPYW2!=0){

            FPYOrangeMap.put("FPYW2Vlaue",-1);
        }else if (FPYW2Vlaue<-2&&FPYW2!=0){
            FPYRedMap.put("FPYW2Vlaue",-1);
        }
        if (FPYW2Vlaue<0){
            AutoFloor_Rate FPYW2Bean= new AutoFloor_Rate();
            FPYW2Bean.setStationName(ProductnameWIFIBT2);
            FPYW2Bean.setRATE1(FPYW2Vlaue);
            TopList.add(FPYW2Bean);
        }
        FPYAll+=FPYW2;
        FPYMap.put("FPYW2",FPYW2);

        Double FPYUWB= 0.0;
        if (yieldUWB!=null&&misdetetUWB!=null){
            FPYUWB=(Double)yieldUWB-(Double)misdetetUWB;
            FPYAllsum++;
        }
        if (FPYUWB<0){
            FPYUWB*=0;
        }
        if (FPYUWB!=null&&FPYUWB!=0){
            BigDecimal FPYUWBbig= new BigDecimal(FPYUWB);
            FPYUWB=FPYUWBbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();


        }
        Double FPYUWBVlaue=FPYUWB-98.95;
        if (FPYUWBVlaue>-2&&FPYUWBVlaue<0&&FPYUWB!=0){
            FPYOrangeMap.put("FPYUWBVlaue",-1);
        }else if (FPYUWBVlaue<-2&&FPYUWB!=0){
            FPYRedMap.put("FPYUWBVlaue",-1);
        }
        if (FPYUWBVlaue<0){
            AutoFloor_Rate FPYUWBBean= new AutoFloor_Rate();
            FPYUWBBean.setStationName("UWB");
            FPYUWBBean.setRATE1(FPYUWBVlaue);
            TopList.add(FPYUWBBean);
        }
        FPYAll+=FPYUWB;
        FPYMap.put("FPYUWB",FPYUWB);

        Double FPYSCOND= 0.0;
        if (yieldSCOND!=null&&misdetetSCOND!=null){
            FPYSCOND=(Double)yieldSCOND-(Double)misdetetSCOND;
            FPYAllsum++;
        }
        if (FPYSCOND<0){
            FPYSCOND*=0;
        }
        if (FPYSCOND!=null&&FPYSCOND!=0){
            BigDecimal FPYSCONDbig= new BigDecimal(FPYSCOND);
            FPYSCOND=FPYSCONDbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();


        }
        Double FPYSCONDVlaue=FPYSCOND-98.47;
        if (FPYSCONDVlaue>-2&&FPYSCONDVlaue<0&&FPYSCOND!=0){
            FPYOrangeMap.put("FPYSCONDVlaue",-1);
        }else if (FPYSCONDVlaue<-2&&FPYSCOND!=0){
            FPYRedMap.put("FPYSCONDVlaue",-1);
        }
        if (FPYSCONDVlaue<0){
            AutoFloor_Rate FPYSCONDBean= new AutoFloor_Rate();
            FPYSCONDBean.setStationName("SCOND");
            FPYSCONDBean.setRATE1(FPYSCONDVlaue);
            TopList.add(FPYSCONDBean);
        }
        FPYAll+=FPYSCOND;
        FPYMap.put("FPYSCOND",FPYSCOND);


        List<String>  Top5Name= new ArrayList<>();
        Map<String,List<AutoFloor_Rate>> FPYTOP5No=new HashMap<>();
       Collections.sort(TopList);
            if (TopList.size()>0){
                AutoFloor_Rate autoFloor_rate = TopList.get(0);
                List<AutoFloor_Rate> autoFloor_rates = new ArrayList<>();
                if (TimeDay.equals("two")){
                    if (timeUtility.Timequantum0830_1029()){
                        autoFloor_rates  = autoFloor_rateService.MidetectFPY08(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYTwo(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Day")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYDay(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                }else if (TimeDay.equals("Night")){
                    if (timeUtility.Timequantum2030_2229()){
                        autoFloor_rates =autoFloor_rateService.MidetectFPY20(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYNight(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Yesterday")){
                        autoFloor_rates =autoFloor_rateService.MidetectFPYYesterday(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                }

                List<AutoFloor_Rate>  zty1List= new ArrayList<>();
                for (int i = 0; i < autoFloor_rates.size(); i++) {
                    AutoFloor_Rate floor_rate = autoFloor_rates.get(i);
                    zty1List.add(floor_rate);
                }
                Top5Name.add(0,autoFloor_rate.getStationName());
                FPYTOP5No.put("NO1Machine_no",zty1List);
            }
            if (TopList.size()>1){
                AutoFloor_Rate autoFloor_rate = TopList.get(1);
                List<AutoFloor_Rate> autoFloor_rates = new ArrayList<>();
                if (TimeDay.equals("two")){
                    if (timeUtility.Timequantum0830_1029()){
                        autoFloor_rates  = autoFloor_rateService.MidetectFPY08(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYTwo(actionDate, Floorname, autoFloor_rate.getStationName(), linename);

                    }

                }else if (TimeDay.equals("Day")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYDay(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                }else if (TimeDay.equals("Night")){
                    if (timeUtility.Timequantum2030_2229()){
                        autoFloor_rates =autoFloor_rateService.MidetectFPY20(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYNight(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Yesterday")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYYesterday(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                }
                List<AutoFloor_Rate>  zty2List= new ArrayList<>();
                for (int i = 0; i < autoFloor_rates.size(); i++) {
                    AutoFloor_Rate floor_rate = autoFloor_rates.get(i);
                    zty2List.add(floor_rate);
                }
                Top5Name.add(1,autoFloor_rate.getStationName());
                FPYTOP5No.put("NO2Machine_no",zty2List);
            }
            if (TopList.size()>2){
                AutoFloor_Rate autoFloor_rate = TopList.get(2);
                List<AutoFloor_Rate> autoFloor_rates = new ArrayList<>();
                if (TimeDay.equals("two")){
                    if (timeUtility.Timequantum0830_1029()){
                        autoFloor_rates  = autoFloor_rateService.MidetectFPY08(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYTwo(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Day")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYDay(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                }else if (TimeDay.equals("Night")){
                    if (timeUtility.Timequantum2030_2229()){
                        autoFloor_rates =autoFloor_rateService.MidetectFPY20(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYNight(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Yesterday")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYYesterday(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                }
                List<AutoFloor_Rate>  zty3List= new ArrayList<>();
                for (int i = 0; i < autoFloor_rates.size(); i++) {
                    AutoFloor_Rate floor_rate = autoFloor_rates.get(i);
                    zty3List.add(floor_rate);
                }
                Top5Name.add(2,autoFloor_rate.getStationName());
                FPYTOP5No.put("NO3Machine_no",zty3List);
            }
            if (TopList.size()>3){
                AutoFloor_Rate autoFloor_rate = TopList.get(3);
                List<AutoFloor_Rate> autoFloor_rates = new ArrayList<>();
                if (TimeDay.equals("two")){
                    if (timeUtility.Timequantum0830_1029()){
                        autoFloor_rates  = autoFloor_rateService.MidetectFPY08(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYTwo(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Day")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYDay(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                }else if (TimeDay.equals("Night")){
                    if (timeUtility.Timequantum2030_2229()){
                        autoFloor_rates =autoFloor_rateService.MidetectFPY20(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYNight(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Yesterday")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYYesterday(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                }
                List<AutoFloor_Rate>  zty4List= new ArrayList<>();
                for (int i = 0; i < autoFloor_rates.size(); i++) {
                    AutoFloor_Rate floor_rate = autoFloor_rates.get(i);
                    zty4List.add(floor_rate);
                }
                Top5Name.add(3,autoFloor_rate.getStationName());
                FPYTOP5No.put("NO4Machine_no",zty4List);


            }
            if (TopList.size()>4){
                AutoFloor_Rate autoFloor_rate = TopList.get(4);
                List<AutoFloor_Rate> autoFloor_rates = new ArrayList<>();
                if (TimeDay.equals("two")){
                    if (timeUtility.Timequantum0830_1029()){
                        autoFloor_rates  = autoFloor_rateService.MidetectFPY08(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYTwo(actionDate, Floorname, autoFloor_rate.getStationName(), linename);

                    }

                }else if (TimeDay.equals("Day")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYDay(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                }else if (TimeDay.equals("Night")){
                    if (timeUtility.Timequantum2030_2229()){
                        autoFloor_rates =autoFloor_rateService.MidetectFPY20(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYNight(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Yesterday")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYYesterday(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                }
                List<AutoFloor_Rate>  zty5List= new ArrayList<>();
                for (int i = 0; i < autoFloor_rates.size(); i++) {
                    AutoFloor_Rate floor_rate = autoFloor_rates.get(i);
                    zty5List.add(floor_rate);
                }
                Top5Name.add(4,autoFloor_rate.getStationName());
                FPYTOP5No.put("NO5Machine_no",zty5List);

            }
            if (TopList.size()>5){
                AutoFloor_Rate autoFloor_rate = TopList.get(5);
                List<AutoFloor_Rate> autoFloor_rates = new ArrayList<>();
                if (TimeDay.equals("two")){
                    if (timeUtility.Timequantum0830_1029()){
                        autoFloor_rates  = autoFloor_rateService.MidetectFPY08(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYTwo(actionDate, Floorname, autoFloor_rate.getStationName(), linename);

                    }

                }else if (TimeDay.equals("Day")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYDay(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                }else if (TimeDay.equals("Night")){
                    if (timeUtility.Timequantum2030_2229()){
                        autoFloor_rates =autoFloor_rateService.MidetectFPY20(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYNight(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Yesterday")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYYesterday(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                }
                List<AutoFloor_Rate>  zty6List= new ArrayList<>();
                for (int i = 0; i < autoFloor_rates.size(); i++) {
                    AutoFloor_Rate floor_rate = autoFloor_rates.get(i);
                    zty6List.add(floor_rate);
                }
                Top5Name.add(5,autoFloor_rate.getStationName());
                FPYTOP5No.put("NO6Machine_no",zty6List);

            }
            if (TopList.size()>6){
                AutoFloor_Rate autoFloor_rate = TopList.get(6);
                List<AutoFloor_Rate> autoFloor_rates = new ArrayList<>();
                if (TimeDay.equals("two")){
                    if (timeUtility.Timequantum0830_1029()){
                        autoFloor_rates  = autoFloor_rateService.MidetectFPY08(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYTwo(actionDate, Floorname, autoFloor_rate.getStationName(), linename);

                    }

                }else if (TimeDay.equals("Day")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYDay(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                }else if (TimeDay.equals("Night")){
                    if (timeUtility.Timequantum2030_2229()){
                        autoFloor_rates =autoFloor_rateService.MidetectFPY20(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYNight(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Yesterday")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYYesterday(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                }
                List<AutoFloor_Rate>  zty7List= new ArrayList<>();
                for (int i = 0; i < autoFloor_rates.size(); i++) {
                    AutoFloor_Rate floor_rate = autoFloor_rates.get(i);
                    zty7List.add(floor_rate);
                }
                Top5Name.add(6,autoFloor_rate.getStationName());
                FPYTOP5No.put("NO7Machine_no",zty7List);
            }
            if (TopList.size()>7){
                AutoFloor_Rate autoFloor_rate = TopList.get(7);
                List<AutoFloor_Rate> autoFloor_rates = new ArrayList<>();
                if (TimeDay.equals("two")){
                    if (timeUtility.Timequantum0830_1029()){
                        autoFloor_rates  = autoFloor_rateService.MidetectFPY08(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYTwo(actionDate, Floorname, autoFloor_rate.getStationName(), linename);

                    }

                }else if (TimeDay.equals("Day")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYDay(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                }else if (TimeDay.equals("Night")){
                    if (timeUtility.Timequantum2030_2229()){
                        autoFloor_rates =autoFloor_rateService.MidetectFPY20(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYNight(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Yesterday")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYYesterday(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                }
                List<AutoFloor_Rate>  zty8List= new ArrayList<>();
                for (int i = 0; i < autoFloor_rates.size(); i++) {
                    AutoFloor_Rate floor_rate = autoFloor_rates.get(i);
                    zty8List.add(floor_rate);
                }
                Top5Name.add(7,autoFloor_rate.getStationName());
                FPYTOP5No.put("NO8Machine_no",zty8List);
            }
            if (TopList.size()>8){
                AutoFloor_Rate autoFloor_rate = TopList.get(8);
                List<AutoFloor_Rate> autoFloor_rates = new ArrayList<>();
                if (TimeDay.equals("two")){
                    if (timeUtility.Timequantum0830_1029()){
                        autoFloor_rates  = autoFloor_rateService.MidetectFPY08(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYTwo(actionDate, Floorname, autoFloor_rate.getStationName(), linename);

                    }

                }else if (TimeDay.equals("Day")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYDay(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                }else if (TimeDay.equals("Night")){
                    if (timeUtility.Timequantum2030_2229()){
                        autoFloor_rates =autoFloor_rateService.MidetectFPY20(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYNight(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Yesterday")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYYesterday(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                }
                List<AutoFloor_Rate>  zty9List= new ArrayList<>();
                for (int i = 0; i < autoFloor_rates.size(); i++) {
                    AutoFloor_Rate floor_rate = autoFloor_rates.get(i);
                    zty9List.add(floor_rate);
                }
                Top5Name.add(8,autoFloor_rate.getStationName());
                FPYTOP5No.put("NO9Machine_no",zty9List);
            }
            if (TopList.size()>9){
                AutoFloor_Rate autoFloor_rate = TopList.get(9);
                List<AutoFloor_Rate> autoFloor_rates = new ArrayList<>();
                if (TimeDay.equals("two")){
                    if (timeUtility.Timequantum0830_1029()){
                        autoFloor_rates  = autoFloor_rateService.MidetectFPY08(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYTwo(actionDate, Floorname, autoFloor_rate.getStationName(), linename);

                    }

                }else if (TimeDay.equals("Day")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYDay(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                }else if (TimeDay.equals("Night")){
                    if (timeUtility.Timequantum2030_2229()){
                        autoFloor_rates =autoFloor_rateService.MidetectFPY20(actionDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }else {
                        autoFloor_rates =autoFloor_rateService.MidetectFPYNight(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                    }
                }else if (TimeDay.equals("Yesterday")){
                    autoFloor_rates =autoFloor_rateService.MidetectFPYYesterday(StartDate,EndDate, Floorname, autoFloor_rate.getStationName(), linename);
                }
                List<AutoFloor_Rate>  zty10List= new ArrayList<>();
                for (int i = 0; i < autoFloor_rates.size(); i++) {
                    AutoFloor_Rate floor_rate = autoFloor_rates.get(i);
                    zty10List.add(floor_rate);
                }
                Top5Name.add(9,autoFloor_rate.getStationName());
                FPYTOP5No.put("NO10Machine_no",zty10List);
            }
        map.put("Top5Name",Top5Name);
        map.put("FPYTOP5No",FPYTOP5No);
        map.put("FPYRedMap",FPYRedMap);
        map.put("FPYOrangeMap",FPYOrangeMap);
        map.put("UnknownMap",UnknownMap);
        map.put("MisdetetMap",MisdetetMap);
        map.put("YieldMap", YieldMap);
        map.put("FPYMap", FPYMap);
        map.put("FPYAll", FPYAll);
        map.put("InputAll",  InputAll   );
        map.put("PassAll",  PassAll    );
        map.put("YieldAll",  YieldAll   );
        map.put("MisdetetAll",  MisdetetAll);
        map.put("UnknownAll",  UnknownAll );
        map.put("Floorname", Floorname);
        map.put("InputAllsum",InputAllsum );
        map.put("PassAllsum",PassAllsum);
        map.put("YieldAllsum",YieldAllsum);
        map.put("MisdetetAllsum",MisdetetAllsum);
        map.put("UnknownAllsum",UnknownAllsum);
        map.put("FPYAllsum",FPYAllsum);

        return "realtime/AllFloorsmidetect";
    }





}
