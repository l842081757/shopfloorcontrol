package com.shop.shopfloorcontroller;

import com.shop.model.*;
import com.shop.services.AUTOFLOOR_TARGEService;
import com.shop.services.AutoFloor_RateService;
import com.shop.services.AutoFloor_RoBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author
 * @create 2019-08-17 14:32
 */
@Controller
public class AutoFloorTarGetDayCotller {

    @Autowired
    AUTOFLOOR_TARGEService autofloorService;

    @Autowired
    AutoFloor_RoBotService autoFloor_roBotService;
    @Autowired
    TaskService taskService;

    @Autowired
    TimeUtility timeUtility;

    @Autowired
    AutoFloor_RateService  autoFloor_rateService;


    private List<Integer> index = Arrays.asList(5000,3000,2000);

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
/*---------------****--------------楼层健康状况----------------****--------------------*/

    @RequestMapping(value = "/Allfloordaydata",method = RequestMethod.GET)
    public  String Allfloordaydata(@RequestParam(value = "FloorName",required = true,defaultValue = "D073F")String FloorName, Map map){
        /*获取当前时间*/
        List<java.sql.Date> sqlList = taskService.Schedule();
        /*当前日期*/
        Date schedule =sqlList.get(0);
        /*当前日期加1*/
        Date endschedule =sqlList.get(1);

        List<Integer> timeList = taskService.getTime(new Date());
        /*时*/
        Integer Htime = timeList.get(2);
        Integer Minute = timeList.get(3);
        /*数据开始时间*/
        String DataStartTime=schedule+" "+"08:30";
        /*数据结束时间*/
        String DataEndTime=null;
        /*工作时间*/
        Integer  actionMinuteD=0;
        Integer  actionMinute;
        if (Htime!=null&&Htime>=8){
            actionMinute=((Htime-8)*60+Minute)-30;
            actionMinuteD=actionMinute;
        }

        map.put("FloorName",FloorName);
        /*线体数据集合*/
        //浮动数据
        Integer FloatNum=200;
/*--------------***----------Lot-----------**---------------------*/
        List<AutoFloorGet> autoAll = new ArrayList<AutoFloorGet>();
        /*D073FARF线体数据*/
        List<AUTOFLOOR_TARGET> autoDayT0s = autofloorService.outPutTarGetF(FloorName,"Day",FloorName,schedule,"Lotus");
        /*TTL线体数据*/
        List<AUTOFLOOR_TARGET> autofloor_targets = autofloorService.outPutTarGetF(FloorName,"Day", "TTL",schedule,"Lotus");
        int autoDayTargetsize=0;
        for (AUTOFLOOR_TARGET autoDayT0 : autoDayT0s ) {
            if (autoDayT0.getdTarget()!=null){
            if (autoDayT0.getdTarget().intValue()>0){
                autoDayTargetsize++;
            }
            }
        }
        map.put("autoDayTargetsize", autoDayTargetsize);
        /* TLL天目标*/
        /*初始化实际输出值*/
        Integer LotA2=0;
        Double Lotdouble2=0.00;
        List<AutoFloorGet> NO3List =new ArrayList <AutoFloorGet>();
        //產能運行時間
        long StartdebugTime =System.currentTimeMillis();

        for (int i = 0; i < autoDayT0s.size() ; i++) {
    /*---------------------------- 產能狀況  --------------------------------------*/
            /*每个线体数据*/
            AutoFloorGet autoFloorGet= new AutoFloorGet();
            AUTOFLOOR_TARGET autoDayT0 = autoDayT0s.get(i);
            /*线体每天目标*/
            BigDecimal Target = autoDayT0.getdTarget();
            Integer TargetInteger=0;
            if (Target!=null){ TargetInteger =Integer.parseInt(Target.toString()); }
            /*线体名称*/
            String lineName = autoDayT0.getLineName();
            /*线体每小时目标*/
            BigDecimal hTarget = autoDayT0.gethTarget();
            Integer hTargetInteger=0;
            if (hTarget!=null){ hTargetInteger=Integer.parseInt(hTarget.toString()); }
            /*线体实际输出数量*/
            Integer actualOut = autofloorService.actualOut(FloorName,lineName,schedule,"Lotus");
            /*判断实际输出数量空值赋0*/
            if (actualOut != null){
                autoFloorGet.setActualOuts(actualOut);
                LotA2+=actualOut;
            }else {
                actualOut=0;
                autoFloorGet.setActualOuts(0);
            }
            /*线体达成率*/
            /*线体达成率*/
            if( TargetInteger!=null && TargetInteger!=0 ){
                Double actdataDouble = actualOut.doubleValue()/Double.parseDouble(Target.toString())*100;
                BigDecimal bigD = new BigDecimal(actdataDouble);
                /*线体达成率保留后两位*/
                Double actdata = bigD.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                /*线体数据传值*/
                /*实际数量*/
                autoFloorGet.setAchievingRate(actdata);
            }else {
                autoFloorGet.setActualData(0.00);
            }
        if (autoDayT0.getWorkingHours()!=null){
            if (autoDayT0.getWorkingHours().doubleValue()==9.5){
                if (timeUtility.Timequantum0830_0930()){
                    if(hTargetInteger!=0&&hTargetInteger!=null &&actionMinuteD!=0) {
                        Double actdataDouble = (actionMinuteD* (hTargetInteger.doubleValue()/60) )*0.5;
                        Lotdouble2+=(actionMinuteD* (hTargetInteger.doubleValue()/60) )*0.5;
                        Double actdata =  new BigDecimal(actdataDouble).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert= (actionMinuteD* (hTargetInteger.doubleValue()/60) )*0.5;
                        if (actionTargert>actualOut){
                            NO3List.add(autoFloorGet);
                        }
                    }else {
                        autoFloorGet.setActualData(0.00);
                    }
                }else if (timeUtility.Timequantum0930_1230()) {
                    Integer actionTimeh = actionMinuteD - 30;
                    if (actionTimeh >460){
                        actionTimeh =460;
                    }
                    if (hTargetInteger != 0  && actionMinuteD != 0) {
                        Double actdataDouble = actionTimeh * (hTargetInteger.doubleValue() / 60);
                        Lotdouble2+=actionTimeh * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert= actionTimeh * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>actualOut){
                            NO3List.add(autoFloorGet);
                        }
                    } else {
                        autoFloorGet.setActualData(0.00);
                    }
                }else if (timeUtility.Timequantum1230_1930()){
                    Integer actionTimeh =actionMinuteD-90;
                    if (actionTimeh >570){
                        actionTimeh =570;
                    }
                    if (hTargetInteger != 0 && hTargetInteger != null && actionMinuteD != 0) {
                        Double actdataDouble = ((actionTimeh* (hTargetInteger.doubleValue()/60) ));
                        Lotdouble2+=actionTimeh* (hTargetInteger.doubleValue()/60) ;
                        Double actdata =  new BigDecimal(actdataDouble).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert= actionTimeh* (hTargetInteger.doubleValue()/60) ;
                        if (actionTargert>actualOut){
                            NO3List.add(autoFloorGet);
                        }
                    }else {
                        autoFloorGet.setActualData(0.00);
                    }
                }else if (Htime>=19&&Minute>=30||Htime>19&&Minute>0) {
                    FloatNum=0;
                    if (hTargetInteger != 0 && hTargetInteger != null  && actionMinuteD != 0) {
                        Double actdataDouble =  ((570 * (hTargetInteger.doubleValue() / 60)));
                        Lotdouble2+= 570 * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert= 570 * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>actualOut){
                            NO3List.add(autoFloorGet);
                        }
                    } else {
                        autoFloorGet.setActualData(0.00);
                    }
                }else {
                    FloatNum=0;
                    if (hTargetInteger != 0 && hTargetInteger != null  && actionMinuteD != 0) {
                        Double actdataDouble =  ((570 * (hTargetInteger.doubleValue() / 60)));
                        Lotdouble2+= 570 * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert= 570 * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>actualOut){
                            NO3List.add(autoFloorGet);
                        }
                    } else {
                        autoFloorGet.setActualData(0.00);
                    }
                }
            }else if (autoDayT0.getWorkingHours().doubleValue()==7.67){
                if (timeUtility.Timequantum0830_0930()){
                    if(hTargetInteger!=0&&hTargetInteger!=null &&actionMinuteD!=0) {
                        Double actdataDouble = ((actionMinuteD* (hTargetInteger.doubleValue()/60) )*0.5) ;
                        Lotdouble2+=(actionMinuteD* (hTargetInteger.doubleValue()/60) )*0.5;
                        Double actdata =  new BigDecimal(actdataDouble).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert=(actionMinuteD* (hTargetInteger.doubleValue()/60) )*0.5;
                        if (actionTargert>actualOut){
                            NO3List.add(autoFloorGet);
                        }
                    }else {
                        autoFloorGet.setActualData(0.00);
                    }
                }else if (timeUtility.Timequantum0930_1230()) {
                    Integer actionTimeh = actionMinuteD - 20;
                    if (actionTimeh >460){
                        actionTimeh =460;
                    }
                    if (hTargetInteger != 0  &&actionMinuteD!=0) {
                        Double actdataDouble =  ((actionTimeh * (hTargetInteger.doubleValue() / 60)));
                        Lotdouble2+=actionTimeh * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert= (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>actualOut){
                            NO3List.add(autoFloorGet);
                        }
                    } else {
                        autoFloorGet.setActualData(0.00);
                    }
                }else if (timeUtility.Timequantum1230_1730()){
                    Integer actionTimeh =actionMinuteD-80;
                    if (actionTimeh >460){
                        actionTimeh =460;
                    }
                    if (hTargetInteger != 0 && hTargetInteger != null  && actionMinuteD != 0) {
                        Double actdataDouble = ((actionTimeh* (hTargetInteger.doubleValue()/60) )) ;
                        Lotdouble2+=actionTimeh* (hTargetInteger.doubleValue()/60);
                        Double actdata =  new BigDecimal(actdataDouble).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert= ((actionTimeh* (hTargetInteger.doubleValue()/60) ));
                        if (actionTargert>actualOut){
                            NO3List.add(autoFloorGet);
                        }
                    }else {
                        autoFloorGet.setActualData(0.00);
                    }
                }else if (Htime>=17&&Minute>=30||Htime>17&&Minute>0) {
                    if (hTargetInteger != 0 && hTargetInteger != null && actionMinuteD != 0) {
                        Double actdataDouble = ((460 * (hTargetInteger.doubleValue() / 60)));
                        Lotdouble2+=460 * (hTargetInteger.doubleValue() / 60) ;
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert= ((460 * (hTargetInteger.doubleValue() / 60)));
                        if (actionTargert>actualOut){
                            NO3List.add(autoFloorGet);
                        }
                    } else {
                        autoFloorGet.setActualData(0.00);
                    }
                }else {
                    FloatNum=0;
                    if (hTargetInteger != 0 && hTargetInteger != null && actionMinuteD != 0) {
                        Double actdataDouble = ((460 * (hTargetInteger.doubleValue() / 60)));
                        Lotdouble2+=460 * (hTargetInteger.doubleValue() / 60) ;
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert= ((460 * (hTargetInteger.doubleValue() / 60)));
                        if (actionTargert>actualOut){
                            NO3List.add(autoFloorGet);
                        }
                    } else {
                        autoFloorGet.setActualData(0.00);
                    }
                }
            }else if (autoDayT0.getWorkingHours().doubleValue()==8.67) {
                if (timeUtility.Timequantum0830_0930()) {
                    if (hTargetInteger != 0 && hTargetInteger != null  && actionMinuteD != 0) {
                        Double actdataDouble = ((actionMinuteD * (hTargetInteger.doubleValue() / 60)) * 0.5);
                        Lotdouble2 += (actionMinuteD * (hTargetInteger.doubleValue() / 60)) * 0.5;
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert = (actionMinuteD * (hTargetInteger.doubleValue() / 60)) * 0.5;
                        if (actionTargert > actualOut) {
                            NO3List.add(autoFloorGet);
                        }
                    } else {
                        autoFloorGet.setActualData(0.00);
                    }
                } else if (timeUtility.Timequantum0930_1230()) {
                    Integer actionTimeh = actionMinuteD - 20;
                    if (hTargetInteger != 0    && actionMinuteD != 0) {
                        Double actdataDouble = ((actionTimeh * (hTargetInteger.doubleValue() / 60)));
                        Lotdouble2 += ((actionTimeh * (hTargetInteger.doubleValue() / 60)));
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert = (hTargetInteger.doubleValue() / 60);
                        if (actionTargert > actualOut) {
                            NO3List.add(autoFloorGet);
                        }
                    } else {
                        autoFloorGet.setActualData(0.00);
                    }
                } else if (timeUtility.Timequantum1230_1830()) {
                    Integer actionTimeh = actionMinuteD - 80;
                    if (actionTimeh > 520) {
                        actionTimeh = 520;
                    }
                    if (hTargetInteger != 0 && hTargetInteger != null  && actionMinuteD != 0) {
                        Double actdataDouble = ((actionTimeh * (hTargetInteger.doubleValue() / 60)));
                        Lotdouble2 += ((actionTimeh * (hTargetInteger.doubleValue() / 60)));
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert = ((actionTimeh * (hTargetInteger.doubleValue() / 60)));
                        if (actionTargert > actualOut) {
                            NO3List.add(autoFloorGet);
                        }
                    } else {
                        autoFloorGet.setActualData(0.00);
                    }
                } else if (Htime >= 18 && Minute >= 30 || Htime > 18 && Minute > 0) {
                    FloatNum=0;
                    if (hTargetInteger != 0 && hTargetInteger != null  && actionMinuteD != 0) {
                        Double actdataDouble = (520  * (hTargetInteger.doubleValue() / 60));
                        Lotdouble2 += 520  * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert = 520  * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert > actualOut) {
                            NO3List.add(autoFloorGet);
                        }
                    } else {
                        autoFloorGet.setActualData(0.00);
                    }
                }else {
                    FloatNum=0;
                    if (hTargetInteger != 0 && hTargetInteger != null  && actionMinuteD != 0) {
                        Double actdataDouble = (520  * (hTargetInteger.doubleValue() / 60));
                        Lotdouble2 += 520  * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        autoFloorGet.setActualData(actdata);
                        Double actionTargert = 520  * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert > actualOut) {
                            NO3List.add(autoFloorGet);
                        }
                    } else {
                        autoFloorGet.setActualData(0.00);
                    }
                }
            }
        }
            /*线体名称*/
            autoFloorGet.setLineName(lineName );
            /*天目标*/
            autoFloorGet.setdTarget(TargetInteger);
            /*小时目标*/
            autoFloorGet.sethTarget(hTargetInteger);
            autoAll.add(autoFloorGet);
        }
        /*循环倒数三个不达标线体赋标记*/
        List<AutoFloorGet> WarinessList = new ArrayList<>();
        Integer index = 0;
        Collections.sort(NO3List);
        for (int i = 0; i < NO3List.size(); i++) {
            if (NO3List.size() <= 3) {
                int size = NO3List.size();
                if (size == 0) {
                    break;
                } else if (size == 1) {
                    AutoFloorGet autoFloorGet = NO3List.get(0);
                    autoFloorGet.setSign(989);
                    WarinessList.add(autoFloorGet);
                } else if (size == 2) {
                    AutoFloorGet autoFloorGet = NO3List.get(0);
                    autoFloorGet.setSign(989);
                    AutoFloorGet autoFloorGet2 = NO3List.get(1);
                    autoFloorGet2.setSign(989);
                    WarinessList.add(autoFloorGet);
                    WarinessList.add(autoFloorGet2);
                } else if (size == 3) {
                    AutoFloorGet autoFloorGet = NO3List.get(0);
                    autoFloorGet.setSign(989);
                    WarinessList.add(autoFloorGet);
                    AutoFloorGet autoFloorGet2 = NO3List.get(1);
                    autoFloorGet2.setSign(989);
                    WarinessList.add(autoFloorGet2);
                    AutoFloorGet autoFloorGet3 = NO3List.get(2);
                    autoFloorGet3.setSign(989);
                    WarinessList.add(autoFloorGet3);
                }
            } else if (NO3List.size() > 3) {
                AutoFloorGet autoFloorGet = NO3List.get(i);
                index++;
                if (index <= 3) {
                    autoFloorGet.setSign(989);
                }
                WarinessList.add(autoFloorGet);
            }
        }
        List<AutoFloorGet> LotNightautoFloorNo3List = new ArrayList<AutoFloorGet>();
        for (AutoFloorGet autoFloorGet : autoAll) {
            for (int i = 0; i < WarinessList.size(); i++) {
                AutoFloorGet autoFloorGet1 = WarinessList.get(i);
                if (autoFloorGet1.getSign() == 989) {
                    if (autoFloorGet.getLineName().equals(autoFloorGet1.getLineName())) {
                        autoFloorGet.setSign(989);
                    }
                }
            }
                LotNightautoFloorNo3List.add(autoFloorGet);
        }

        Double actdataDoublesum =0.00;
        Double LotachievingRatesum =0.00;
        /*TLL达成率*/
        Integer  Targetsum=0;
        Integer  HTargetsum=0;
        for (AUTOFLOOR_TARGET autofloor_target : autofloor_targets) {
            BigDecimal bigDecimal = autofloor_target.getdTarget();
            BigDecimal AllHTarget = autofloor_target.gethTarget();
            if (autofloor_target.getdTarget()!=null&&autofloor_target.gethTarget()!=null){
                HTargetsum=Integer.parseInt(AllHTarget.toString());
                Targetsum=Integer.parseInt(bigDecimal.toString());
            }
        }

        if ( actionMinuteD!=0 &&HTargetsum!=null&&HTargetsum!=0&&Lotdouble2!=0){
            Double actdataDoublesums = (LotA2.doubleValue()/Lotdouble2)*100;
            actdataDoublesum=actdataDoublesums;
        }

        BigDecimal bigDsum = new BigDecimal(actdataDoublesum);
        /*TLL达成率保留后两位*/
        Double actdatasum = bigDsum.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        if ( actionMinuteD!=0 &&LotA2!=0&&Targetsum!=null&&Targetsum!=0){
            Double actdataDoublesums = (LotA2.doubleValue()/Targetsum)*100;
            LotachievingRatesum=actdataDoublesums;
        }
        BigDecimal bigDsums = new BigDecimal(LotachievingRatesum);
        /*TLL达成率保留后两位*/
        Double actdatasums = bigDsums.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        AutoFloorGet floorGetsum = new AutoFloorGet();
        /*TLL天目标*/
        floorGetsum.setdTargetsum(Targetsum);
        /*TLL实际产出*/
        floorGetsum.setActualOutsum(LotA2);
        /*TLL达成率*/
        floorGetsum.setActualDatasum(actdatasum);
        floorGetsum.setAchievingRatesum(actdatasums);
        map.put("Lotdouble2",Lotdouble2);

/*--------------------**------------------Mac------------**--------------------*/

        List<AutoFloorGet> MacautoAll = new ArrayList<AutoFloorGet>();
        /*D073FARF线体数据*/
        List<AUTOFLOOR_TARGET> MacautoDayT0s = autofloorService.outPutTarGetF(FloorName,"Day",FloorName,schedule,"Macan");
        /*TTL线体数据*/
        List<AUTOFLOOR_TARGET> Macautofloor_targets = autofloorService.outPutTarGetF(FloorName,"Day", "TTL",schedule,"Macan");
        /* TLL天目标*/
        int MacautoDaysize=0;
        for (AUTOFLOOR_TARGET autoDayT0 : MacautoDayT0s) {
            if (autoDayT0.getdTarget()!=null){
                if (autoDayT0.getdTarget().intValue()>0){
                    MacautoDaysize++;
                }
            }
        }
        map.put("MacautoDaysize", MacautoDaysize);
        /*初始化实际输出值*/
        Integer Macints2=0;
        Double  Macdouble2=0.00;
        List<AutoFloorGet> MacNO3List =new ArrayList <AutoFloorGet>();
        for (int i = 0; i < MacautoDayT0s.size() ; i++) {
            /*每个线体数据*/
            AutoFloorGet MacautoFloorGet= new AutoFloorGet();
            AUTOFLOOR_TARGET MacautoDayT0 = MacautoDayT0s.get(i);
            /*线体每天目标*/
            BigDecimal Target = MacautoDayT0.getdTarget();
            Integer TargetInteger=0;
            if (Target!=null){
                TargetInteger=Integer.parseInt(Target.toString());

            }
            /*线体名称*/
            String lineName = MacautoDayT0.getLineName();
            /*线体每小时目标*/
            BigDecimal hTarget = MacautoDayT0.gethTarget();
            Integer hTargetInteger=0;
            if (hTarget!=null){
                hTargetInteger=Integer.parseInt(hTarget.toString());

            }
            /*线体实际输出数量*/
            Integer MacactualOut = autofloorService.actualOut(FloorName,lineName,schedule,"Macan");
            /*判断实际输出数量空值赋0*/
            if (MacactualOut != null){
                MacautoFloorGet.setActualOuts(MacactualOut);
                Macints2+=MacactualOut;
            }else {
                MacactualOut=0;
                MacautoFloorGet.setActualOuts(0);
            }
            /*Mac线体达成率*/
            if( TargetInteger!=null && TargetInteger!=0 ){
                Double actdataDouble = MacactualOut.doubleValue()/Double.parseDouble(Target.toString())*100;
                BigDecimal bigD = new BigDecimal(actdataDouble);
                /*线体达成率保留后两位*/
                Double actdata = bigD.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                /*线体数据传值*/
                /*实际数量*/
                MacautoFloorGet.setAchievingRate(actdata);
            }else {
                MacautoFloorGet.setActualData(0.00);
            }
        if (MacautoDayT0.getWorkingHours()!=null){
            if (MacautoDayT0.getWorkingHours().doubleValue()==9.5){
                if (timeUtility.Timequantum0830_0930()) {
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble =  ((actionMinuteD * (hTargetInteger.doubleValue() / 60)) * 0.5);
                        Macdouble2+=(actionMinuteD * (hTargetInteger.doubleValue() / 60)) * 0.5;
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= (actionMinuteD * (hTargetInteger.doubleValue() / 60)) * 0.5;
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                } else if (timeUtility.Timequantum0930_1230()) {
                    Integer actionTimeh = actionMinuteD - 30;
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble = ((actionTimeh * (hTargetInteger.doubleValue() / 60)));
                        Macdouble2+= actionTimeh * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= actionTimeh * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                } else if (timeUtility.Timequantum1230_1930()) {
                    Integer actionTimeh = actionMinuteD - 90;
                    if (actionTimeh >570){
                        actionTimeh =570;
                    }
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble =  ((actionTimeh * (hTargetInteger.doubleValue() / 60)));
                        Macdouble2+=actionTimeh * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= actionTimeh * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                } else if (Htime>=19&&Minute>=30||Htime>19&&Minute>0) {
                    FloatNum=0;
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble =  ((570 * (hTargetInteger.doubleValue() / 60)));
                        Macdouble2+=570 * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= 570 * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                }else {
                    FloatNum=0;
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble =  ((570 * (hTargetInteger.doubleValue() / 60)));
                        Macdouble2+=570 * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= 570 * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                }
            }else if (MacautoDayT0.getWorkingHours().doubleValue()==7.67){
                if (timeUtility.Timequantum0830_0930()) {
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble = ((actionMinuteD * (hTargetInteger.doubleValue() / 60)) * 0.5);
                        Macdouble2+=(actionMinuteD * (hTargetInteger.doubleValue() / 60)) * 0.5;
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= (actionMinuteD * (hTargetInteger.doubleValue() / 60)) * 0.5;
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                } else if (timeUtility.Timequantum0930_1230()) {
                    Integer actionTimeh = actionMinuteD - 20;
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble =  ((actionTimeh * (hTargetInteger.doubleValue() / 60)));
                        Macdouble2+=actionTimeh * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= actionTimeh * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                } else if (timeUtility.Timequantum1230_1730()) {
                    Integer actionTimeh = actionMinuteD - 80;
                    if (actionTimeh >460){
                        actionTimeh =460;
                    }
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble =  ((actionTimeh * (hTargetInteger.doubleValue() / 60)));
                        Macdouble2+=actionTimeh * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= actionTimeh * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                } else if (Htime>=17&&Minute>=30||Htime>17&&Minute>=0) {
                    FloatNum=0;
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble =  ((460 * (hTargetInteger.doubleValue() / 60)));
                        Macdouble2+= 460 * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= 460 * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                }else {
                    FloatNum=0;
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble =  ((460 * (hTargetInteger.doubleValue() / 60)));
                        Macdouble2+= 460 * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= 460 * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                }
            } else  if (MacautoDayT0.getWorkingHours().doubleValue()==8.67){
                if (timeUtility.Timequantum0830_0930()) {
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble =  ((actionMinuteD * (hTargetInteger.doubleValue() / 60)) * 0.5);
                        Macdouble2+=(actionMinuteD * (hTargetInteger.doubleValue() / 60)) * 0.5;
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= (actionMinuteD * (hTargetInteger.doubleValue() / 60)) * 0.5;
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                } else if (timeUtility.Timequantum0930_1230()) {
                    Integer actionTimeh = actionMinuteD - 20;
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble = ((actionTimeh * (hTargetInteger.doubleValue() / 60)));
                        Macdouble2+=actionTimeh * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= actionTimeh * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                } else if (timeUtility.Timequantum1230_1830()) {
                    Integer actionTimeh = actionMinuteD - 80;
                    if (actionTimeh >520){
                        actionTimeh =520;
                    }
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble =  ((actionTimeh * (hTargetInteger.doubleValue() / 60)));
                        Macdouble2+=actionTimeh * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= actionTimeh * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                } else if (Htime>=18&&Minute>=30||Htime>18&&Minute>=0) {
                    FloatNum=0;
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble = ((520 * (hTargetInteger.doubleValue() / 60)));
                        Macdouble2+= 520 * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= 520 * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                }else {
                    FloatNum=0;
                    if (hTargetInteger!=null&&hTargetInteger!=0&&actionMinuteD!=0){
                        Double actdataDouble = ((520 * (hTargetInteger.doubleValue() / 60)));
                        Macdouble2+= 520 * (hTargetInteger.doubleValue() / 60);
                        Double actdata = new BigDecimal(actdataDouble).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        MacautoFloorGet.setActualData(actdata);
                        Double actionTargert= 520 * (hTargetInteger.doubleValue() / 60);
                        if (actionTargert>MacactualOut){
                            MacNO3List.add(MacautoFloorGet);
                        }
                    }else {
                        MacautoFloorGet.setActualData(0.00);
                    }
                }
            }
        }
            /*线体名称*/
            MacautoFloorGet.setLineName(lineName );
            /*天目标*/
            MacautoFloorGet.setdTarget(TargetInteger);
            /*小时目标*/
            MacautoFloorGet.sethTarget(hTargetInteger);

            MacautoAll.add(MacautoFloorGet);
        }
        /*取Mac倒数3个线体*/
        List<AutoFloorGet> MacWarinessList = new ArrayList<>();
        /*取倒数3条*/
        Collections.sort(MacNO3List);
        Integer Macindex = 0;
        for (int i = 0; i < MacNO3List.size(); i++) {
            if (MacNO3List.size() <= 3) {
                int size = MacNO3List.size();
                if (size == 0) {
                    break;
                } else if (size == 1) {
                    AutoFloorGet autoFloorGet = MacNO3List.get(0);
                    autoFloorGet.setSign(989);
                    MacWarinessList.add(autoFloorGet);
                } else if (size == 2) {
                    AutoFloorGet autoFloorGet = MacNO3List.get(0);
                    autoFloorGet.setSign(989);
                    AutoFloorGet autoFloorGet2 = MacNO3List.get(1);
                    autoFloorGet2.setSign(989);
                    MacWarinessList.add(autoFloorGet);
                    MacWarinessList.add(autoFloorGet2);
                } else if (size == 3) {
                    AutoFloorGet autoFloorGet = MacNO3List.get(0);
                    autoFloorGet.setSign(989);
                    MacWarinessList.add(autoFloorGet);
                    AutoFloorGet autoFloorGet2 = MacNO3List.get(1);
                    autoFloorGet2.setSign(989);
                    MacWarinessList.add(autoFloorGet2);
                    AutoFloorGet autoFloorGet3 = MacNO3List.get(2);
                    autoFloorGet3.setSign(989);
                    MacWarinessList.add(autoFloorGet3);
                }
            } else if (MacNO3List.size() > 3) {
                AutoFloorGet autoFloorGet = MacNO3List.get(i);
                Macindex++;
                if (Macindex <= 3) {
                    autoFloorGet.setSign(989);
                }
                MacWarinessList.add(autoFloorGet);
            }
        }
        List<AutoFloorGet> MacNightAutoFloorNo3List = new ArrayList<>();
        for (AutoFloorGet autoFloorGet : MacautoAll) {
            for (int i = 0; i < MacWarinessList.size(); i++) {
                AutoFloorGet autoFloorGet1 = MacWarinessList.get(i);
                if (autoFloorGet1.getSign() == 989) {
                    if (autoFloorGet.getLineName().equals(autoFloorGet1.getLineName())) {
                        autoFloorGet.setSign(989);
                    }
                }
            }
            MacNightAutoFloorNo3List.add(autoFloorGet);
        }

        Integer MacTargetsum =0;
        Integer MacHTargetsum =0;
        for (AUTOFLOOR_TARGET macautofloor_target : Macautofloor_targets) {
            BigDecimal bigDecimal = macautofloor_target.getdTarget();
            BigDecimal HTearget = macautofloor_target.gethTarget();
            MacHTargetsum=Integer.parseInt(HTearget.toString());
            MacTargetsum=Integer.parseInt(bigDecimal.toString());
        }
        /*TLL达成率*/
        Double MacactdataDoublesum =0.00;
        if (MacTargetsum !=0&& MacTargetsum!=null&&actionMinuteD!=0&&Macdouble2!=0){
        Double MacactdataDoublesums = (Macints2.doubleValue()/Macdouble2)*100;
            MacactdataDoublesum=MacactdataDoublesums;
        }
        BigDecimal MacbigDsum = new BigDecimal(MacactdataDoublesum );
        /*TLL达成率保留后两位*/
        Double Macactdatasum = MacbigDsum.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        /*TLL达成率*/
        Double MacachievingRatesum =0.00;
        if (MacTargetsum !=0&& MacTargetsum!=null&&actionMinuteD!=0){
            Double MacactdataDoublesums = (Macints2.doubleValue()/MacTargetsum)*100;
            MacachievingRatesum =MacactdataDoublesums;
        }
        BigDecimal MacbigDsums = new BigDecimal(MacachievingRatesum);
        /*TLL达成率保留后两位*/
        Double MacachievingRatesum_double = MacbigDsums.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        AutoFloorGet MacfloorGetsum = new AutoFloorGet();
        /*TLL天目标*/
        MacfloorGetsum.setdTargetsum(MacTargetsum);
        /*TLL实际产出*/
        MacfloorGetsum.setActualOutsum(Macints2);
        /*TLL达成率*/
        MacfloorGetsum.setActualDatasum(Macactdatasum);
        MacfloorGetsum.setAchievingRatesum(MacachievingRatesum_double);


        map.put("Macdouble2",Macdouble2 );
        map.put("LotDayautoFloorNo3List",LotNightautoFloorNo3List);
        map.put("MacDayAutoFloorNo3List",MacNightAutoFloorNo3List);
        map.put("MacautoAll",  MacautoAll);
        map.put("MacautoAllsum", MacfloorGetsum);
        map.put("autoAll", autoAll);
        map.put("autoAllsum", floorGetsum);
        map.put("FloatNum",FloatNum);
        long EnddebugTime =System.currentTimeMillis();
        System.out.println("產能程序运行时间："+(EnddebugTime-StartdebugTime)+"ms");
/*-----*-----测试机台-----*-----------*/
        //测试机台運行時間
        long CStartdebugTime =System.currentTimeMillis();

        /*-------------*******------------ Lot -------------*******--------------*/
        List<AmountsUPH> autoFloor_reteList= new ArrayList<>();
        Double rateYieldsAll=0.0;
        Integer rateYieldsAllSum=0;
        Double rateMisdetetAll=0.0;
        Integer rateMisdetetAllSum=0;
        Double rateUnknownAll=0.0;
        Integer rateUnknownAllSum=0;
        AmountsUPH amountsUPHAll= new AmountsUPH();

        for (int i = 0; i < autoAll.size(); i++) {
            AmountsUPH amountsUPH= new AmountsUPH();
            AutoFloorGet autoFloorGet = autoAll.get(i);
            /*-----***----良率----***-----*/
            List<AutoFloor_Rate> autoFloor_ratesARFs = autoFloor_rateService.AutoFloorRate_YieldDay(schedule,FloorName, "Lotus",autoFloorGet.getLineName());
            Double YieldsARF=1.0;
            for (AutoFloor_Rate autoFloor_ratesARF : autoFloor_ratesARFs) {
                YieldsARF*=autoFloor_ratesARF.getRate();
            }
            if (YieldsARF!=1.0&&YieldsARF!=0.0){
                rateYieldsAll+=YieldsARF;
                rateYieldsAllSum++;
            }
            /*TLL良率保留后4位*/
            Double YieldsARFDoubles = YieldsARF*100;
            BigDecimal YieldsARFbig = new BigDecimal(YieldsARFDoubles);
            Double YieldsARFdouble = YieldsARFbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            amountsUPH.setYIELD(YieldsARFdouble);
            /*-----****---------- 误测率  ----------***------*/
            List<AutoFloor_Rate> autoFloor_ratesMisdetetARFs = autoFloor_rateService.AutoFloorRate_MisdetetDay(schedule,FloorName, "Lotus",autoFloorGet.getLineName());
            Double MisdetetARF=0.0;
            for (AutoFloor_Rate autoFloor_ratesMisdetetARF : autoFloor_ratesMisdetetARFs) {
                MisdetetARF+= autoFloor_ratesMisdetetARF.getRate();
            }
            if (MisdetetARF!=0){
                rateMisdetetAll+=MisdetetARF;
                rateMisdetetAllSum++;
            }
            /*TLL良率保留后4位*/
            Double MisdetetARFDoubles = MisdetetARF*100;
            BigDecimal MisdetetARFbig = new BigDecimal(MisdetetARFDoubles);
            Double MisdetetARFdouble = MisdetetARFbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            amountsUPH.setMISDETET(MisdetetARFdouble);
            /*-----****---------- 直通率  ----------***------*/
            if (YieldsARFdouble==100){
                YieldsARFdouble*=0;
            }
            Double FPYARFdoubles = YieldsARFdouble - MisdetetARFdouble;
            BigDecimal FPYARFbig = new BigDecimal(FPYARFdoubles);
            Double FPYARFdouble = FPYARFbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            amountsUPH.setFPY(FPYARFdouble);
            /*-----***-------Unknown-------***--------------*/
            List<AutoFloor_Rate> autoFloor_ratesUnknownARFs = autoFloor_rateService.AutoFloorRate_UnknownDay(schedule,FloorName, "Lotus",autoFloorGet.getLineName());
            Double UnknownARF=0.0;
            for (AutoFloor_Rate autoFloor_ratesUnknownARF : autoFloor_ratesUnknownARFs) {
                UnknownARF+=autoFloor_ratesUnknownARF.getRate();
            }
            if (UnknownARF!=0){
                rateUnknownAll+=UnknownARF;
                rateUnknownAllSum++;

            }
            /*TLL良率保留后4位*/
            Double UnknownARFDoubles = UnknownARF*100;
            BigDecimal UnknownARFlbig = new BigDecimal(UnknownARFDoubles);
            Double UnknownARFdouble = UnknownARFlbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            amountsUPH.setUnknown(UnknownARFdouble);
            amountsUPH.setLINE_NAME(autoFloorGet.getLineName());
            autoFloor_reteList.add(amountsUPH);
        }
        /*-----------全部线体良率-----------------*/
        if (rateYieldsAllSum!=0){
            rateYieldsAll/=rateYieldsAllSum;
        }
        /*-----------TLL全部良率保留后4位-----------*/
        Double rateYieldsAllDoubles= rateYieldsAll*100;
        BigDecimal rateYieldsAllbig = new BigDecimal(rateYieldsAllDoubles);
        Double rateYieldsAlldouble = rateYieldsAllbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        /*-----****---------- 误测率  ----------***------*/
        /*-----------全部线体误测率-----------*/
        if (rateMisdetetAllSum!=0){
            rateMisdetetAll/=rateMisdetetAllSum;
        }
        /*-----------TLL全部误测率保留后4位-----------*/
        Double rateMisdetetAllDoubles= rateMisdetetAll*100;
        BigDecimal rateMisdetetAllbig = new BigDecimal(rateMisdetetAllDoubles);
        Double rateMisdetetAlldouble = rateMisdetetAllbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        /*-----****---------- 直通率  ----------***------*/
        /*-----------全部Lot线体直通率-----------------*/
        Double rateFPYAllDouble= rateYieldsAlldouble-rateMisdetetAlldouble;
        /*-----------TLL全部直通率保留后4位-----------*/
        BigDecimal rateFPYAllbig = new BigDecimal(rateFPYAllDouble);
        Double rateFPYAlldouble = rateFPYAllbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        /*-----***-------Unknown-------***--------------*/
        /*-----------全部线体Unknown-----------*/
        if (rateUnknownAllSum!=0){
            rateUnknownAll/=rateUnknownAllSum;
        }
        /*TLLUnknown保留后4位*/
        Double rateUnknownAllDoubles = rateUnknownAll*100;
        BigDecimal rateUnknownAllbig = new BigDecimal(rateUnknownAllDoubles);
        Double rateUnknownAlldouble = rateUnknownAllbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        amountsUPHAll.setYIELD(rateYieldsAlldouble);
        amountsUPHAll.setMISDETET(rateMisdetetAlldouble);
        amountsUPHAll.setUnknown(rateUnknownAlldouble);
        amountsUPHAll.setFPY( rateFPYAlldouble);

        map.put("autoFloor_LotreteList",autoFloor_reteList);
        map.put("amountsLotUPHAll",amountsUPHAll);


        /*---------------***---------- Mac-------------***---------------*/
        /*-----***----良率----***-----*/
        List<AmountsUPH> autoFloor_MacreteList= new ArrayList<>();
        Double rateMacYieldsAll=0.0;
        Integer rateMacYieldsAllSum=0;
        Double rateMacMisdetetAll=0.0;
        Integer rateMacMisdetetAllSum=0;
        Double rateMacUnknownAll=0.0;
        Integer rateMacUnknownAllSum=0;
        AmountsUPH amountsMacUPHAll= new AmountsUPH();

        for (int i = 0; i < MacautoAll.size(); i++) {
            AmountsUPH amountsMacUPH= new AmountsUPH();
            AutoFloorGet autoFloorMacGet = MacautoAll.get(i);
            /*-----***----良率----***-----*/
            List<AutoFloor_Rate> autoFloor_ratesMacARFs = autoFloor_rateService.AutoFloorRate_YieldDay(schedule,FloorName, "Macan",autoFloorMacGet.getLineName());
            Double YieldsMacARF=1.0;
            for (AutoFloor_Rate autoFloor_ratesMacARF : autoFloor_ratesMacARFs) {
                YieldsMacARF*=autoFloor_ratesMacARF.getRate();
            }
            if (YieldsMacARF!=1.0&&YieldsMacARF!=0.0){
                rateMacYieldsAll+=YieldsMacARF;
                rateMacYieldsAllSum++;
            }
            /*TLL良率保留后4位*/
            Double YieldsARFMacDoubles = YieldsMacARF*100;
            BigDecimal YieldsARFMacbig = new BigDecimal(YieldsARFMacDoubles);
            Double YieldsARFMacdouble = YieldsARFMacbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            amountsMacUPH.setYIELD(YieldsARFMacdouble);
            /*-----****---------- 误测率  ----------***------*/
            List<AutoFloor_Rate> autoFloor_ratesMisdetetMacARFs = autoFloor_rateService.AutoFloorRate_MisdetetDay(schedule,FloorName, "Macan",autoFloorMacGet.getLineName());
            Double MisdetetMacARF=0.0;
            for (AutoFloor_Rate autoFloor_ratesMisdetetMacARF : autoFloor_ratesMisdetetMacARFs) {
                MisdetetMacARF+= autoFloor_ratesMisdetetMacARF.getRate();
            }
            if (MisdetetMacARF!=0){
                rateMacMisdetetAll+=MisdetetMacARF;
                rateMacMisdetetAllSum++;
            }
            /*TLL良率保留后4位*/
            Double MisdetetMacARFDoubles = MisdetetMacARF*100;
            BigDecimal MisdetetMacARFbig = new BigDecimal(MisdetetMacARFDoubles);
            Double MisdetetMacARFdouble = MisdetetMacARFbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            amountsMacUPH.setMISDETET(MisdetetMacARFdouble);
            /*-----****---------- 直通率  ----------***------*/
            if (YieldsARFMacdouble==100){
                YieldsARFMacdouble*=0;
            }
            Double FPYARFMacdoubles = YieldsARFMacdouble - MisdetetMacARFdouble;
            BigDecimal FPYARFMacbig = new BigDecimal(FPYARFMacdoubles);
            Double FPYARFMacdouble = FPYARFMacbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            amountsMacUPH.setFPY(FPYARFMacdouble);
            /*-----***-------Unknown-------***--------------*/
            List<AutoFloor_Rate> autoFloor_ratesUnknownMacARFs = autoFloor_rateService.AutoFloorRate_UnknownDay(schedule,FloorName, "Macan",autoFloorMacGet.getLineName());
            Double UnknownMacARF=0.0;
            for (AutoFloor_Rate autoFloor_ratesUnknownMacARF : autoFloor_ratesUnknownMacARFs) {
                UnknownMacARF+=autoFloor_ratesUnknownMacARF.getRate();
            }
            if (UnknownMacARF!=0){
                rateMacUnknownAll+=UnknownMacARF;
                rateMacUnknownAllSum++;

            }
            /*TLL良率保留后4位*/
            Double UnknownMacARFDoubles = UnknownMacARF*100;
            BigDecimal UnknownMacARFlbig = new BigDecimal(UnknownMacARFDoubles);
            Double UnknownMacARFdouble = UnknownMacARFlbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            amountsMacUPH.setUnknown(UnknownMacARFdouble);
            amountsMacUPH.setLINE_NAME(autoFloorMacGet.getLineName());
            autoFloor_MacreteList.add(amountsMacUPH);
        }

        /*-----------全部线体良率-----------------*/
        if (rateMacYieldsAllSum!=0){
            rateMacYieldsAll/=rateMacYieldsAllSum;
        }
        /*-----------TLL全部良率保留后4位-----------*/
        Double rateMacYieldsAllDoubles= rateMacYieldsAll*100;
        BigDecimal rateMacYieldsAllbig = new BigDecimal(rateMacYieldsAllDoubles);
        Double rateMacYieldsAlldouble = rateMacYieldsAllbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        /*-----****---------- 误测率  ----------***------*/
        /*-----------全部线体误测率-----------*/
        if (rateMacMisdetetAllSum!=0){
            rateMacMisdetetAll/=rateMacMisdetetAllSum;
        }

        /*-----------TLL全部误测率保留后4位-----------*/
        Double rateMacMisdetetAllDoubles= rateMacMisdetetAll*100;
        BigDecimal rateMacMisdetetAllbig = new BigDecimal(rateMacMisdetetAllDoubles);
        Double rateMacMisdetetAlldouble = rateMacMisdetetAllbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        /*-----****---------- 直通率  ----------***------*/
        /*-----------全部Lot线体直通率-----------------*/
        Double rateMacFPYAllDouble= rateMacYieldsAlldouble - rateMacMisdetetAlldouble;
        /*-----------TLL全部直通率保留后4位-----------*/
        BigDecimal rateMacFPYAllbig = new BigDecimal(rateMacFPYAllDouble);
        Double rateMacFPYAlldouble = rateMacFPYAllbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

        /*-----***-------Unknown-------***--------------*/
        /*-----------全部线体Unknown-----------*/
        if (rateMacUnknownAllSum!=0){
            rateMacUnknownAll/=rateMacUnknownAllSum;
        }
        /*TLL良率保留后4位*/
        Double rateMacUnknownAllDoubles = rateMacUnknownAll*100;
        BigDecimal rateMacUnknownAllbig = new BigDecimal(rateMacUnknownAllDoubles);
        Double rateMacUnknownAlldouble = rateMacUnknownAllbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        amountsMacUPHAll.setYIELD(rateMacYieldsAlldouble);
        amountsMacUPHAll.setMISDETET(rateMacMisdetetAlldouble);
        amountsMacUPHAll.setUnknown(rateMacUnknownAlldouble);
        amountsMacUPHAll.setFPY( rateMacFPYAlldouble);

        Long currentEndTime= System.currentTimeMillis();
        Date dateEnd = new Date(currentEndTime);
        DataEndTime= df.format(dateEnd);

        map.put("autoFloor_MacreteList",autoFloor_MacreteList);
        map.put("amountsMacUPHAll",amountsMacUPHAll);
        long CEnddebugTime =System.currentTimeMillis();
        System.out.println("測試機台程序运行时间："+(CEnddebugTime-CStartdebugTime)+"ms");
        /*-------------------------Robot-------------------------------*/
        //Robot運行時間
        long RStartdebugTime =System.currentTimeMillis();


        List<AutoFloor_Wait_Time> AutoFloor_Wait_TimeList= new ArrayList<>();
        int LotlineSum=0;
        Double LotRobotNoNormalRate=0.0;
        Double LotRobotPlateRate   =0.0;
        for (int i = 0; i < autoDayT0s.size(); i++) {

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
            AUTOFLOOR_TARGET autofloorTarget = autoDayT0s.get(i);
            AutoFloor_Wait_Time autoFloor_wait_times= new AutoFloor_Wait_Time();
            autoFloor_wait_times.setLine_name(autofloorTarget.getLineName());

            AutoFloor_Wait_Time autoFloor_wait_timeA = null;
                autoFloor_wait_timeA = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-A");
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


            if (autoFloor_wait_timeA!=null){
                one++;
                Integer awaitNoNormalSum = autoFloor_wait_timeA.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setANoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeA.getAwaitNoNormalRate();
                LineTTLNoNormalRateOne+=awaitNoNormalRate;
                autoFloor_wait_times.setANoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeA.getAwaitPlateRate();
                LineTTLPlateRateOne+=awaitPlateRate;
                autoFloor_wait_times.setAPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setANoNormalSum(0);
                autoFloor_wait_times.setANoNormalRate(0.0);
                autoFloor_wait_times.setAPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeB = null;

                autoFloor_wait_timeB = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-B");
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

            if (autoFloor_wait_timeB!=null){
                two++;
                Integer awaitNoNormalSum = autoFloor_wait_timeB.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setBNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeB.getAwaitNoNormalRate();
                LineTTLNoNormalRateTwo+=awaitNoNormalRate;
                autoFloor_wait_times.setBNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeB.getAwaitPlateRate();
                LineTTLPlateRateTwo+=awaitPlateRate;
                autoFloor_wait_times.setBPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setBNoNormalSum(0);
                autoFloor_wait_times.setBNoNormalRate(0.0);
                autoFloor_wait_times.setBPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeC = null;

                autoFloor_wait_timeC = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-C");
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

            if (autoFloor_wait_timeC!=null){
                two++;
                Integer awaitNoNormalSum = autoFloor_wait_timeC.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setCNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeC.getAwaitNoNormalRate();
                LineTTLNoNormalRateTwo+=awaitNoNormalRate;
                autoFloor_wait_times.setCNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeC.getAwaitPlateRate();
                LineTTLPlateRateTwo+=awaitPlateRate;
                autoFloor_wait_times.setCPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setCNoNormalSum(0);
                autoFloor_wait_times.setCNoNormalRate(0.0);
                autoFloor_wait_times.setCPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeD = null;

                autoFloor_wait_timeD = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-D");
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

            if (autoFloor_wait_timeD!=null){
                two++;
                Integer awaitNoNormalSum = autoFloor_wait_timeD.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setDNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeD.getAwaitNoNormalRate();
                LineTTLNoNormalRateTwo+=awaitNoNormalRate;
                autoFloor_wait_times.setDNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeD.getAwaitPlateRate();
                LineTTLPlateRateTwo+=awaitPlateRate;
                autoFloor_wait_times.setDPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setDNoNormalSum(0);
                autoFloor_wait_times.setDNoNormalRate(0.0);
                autoFloor_wait_times.setDPlateRate(0.0);
            }


            AutoFloor_Wait_Time autoFloor_wait_timeE = null;

                autoFloor_wait_timeE = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-E");
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

            if (autoFloor_wait_timeE!=null){
                three++;
                Integer awaitNoNormalSum = autoFloor_wait_timeE.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setENoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeE.getAwaitNoNormalRate();
                LineTTLNoNormalRateThree+=awaitNoNormalRate;
                autoFloor_wait_times.setENoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeE.getAwaitPlateRate();
                LineTTLPlateRateThree+=awaitPlateRate;
                autoFloor_wait_times.setEPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setENoNormalSum(0);
                autoFloor_wait_times.setENoNormalRate(0.0);
                autoFloor_wait_times.setEPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeF = null;

                autoFloor_wait_timeF = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-F");
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

            if (autoFloor_wait_timeF!=null){
                three++;
                Integer awaitNoNormalSum = autoFloor_wait_timeF.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setFNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeF.getAwaitNoNormalRate();
                LineTTLNoNormalRateThree+=awaitNoNormalRate;
                autoFloor_wait_times.setFNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeF.getAwaitPlateRate();
                LineTTLPlateRateThree+=awaitPlateRate;
                autoFloor_wait_times.setFPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setFNoNormalSum(0);
                autoFloor_wait_times.setFNoNormalRate(0.0);
                autoFloor_wait_times.setFPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeG = null;

                autoFloor_wait_timeG = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-G");
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

            if (autoFloor_wait_timeG!=null){
                three++;
                Integer awaitNoNormalSum = autoFloor_wait_timeG.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setGNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeG.getAwaitNoNormalRate();
                LineTTLNoNormalRateThree+=awaitNoNormalRate;
                autoFloor_wait_times.setGNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeG.getAwaitPlateRate();
                LineTTLPlateRateThree+=awaitPlateRate;
                autoFloor_wait_times.setGPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setGNoNormalSum(0);
                autoFloor_wait_times.setGNoNormalRate(0.0);
                autoFloor_wait_times.setGPlateRate(0.0);
            }
            AutoFloor_Wait_Time autoFloor_wait_timeH = null;

                autoFloor_wait_timeH = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-H");
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

            if (autoFloor_wait_timeH!=null){
                three++;
                Integer awaitNoNormalSum = autoFloor_wait_timeH.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setHNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeH.getAwaitNoNormalRate();
                LineTTLNoNormalRateThree+=awaitNoNormalRate;
                autoFloor_wait_times.setHNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeH.getAwaitPlateRate();
                LineTTLPlateRateThree+=awaitPlateRate;
                autoFloor_wait_times.setHPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setHNoNormalSum(0);
                autoFloor_wait_times.setHNoNormalRate(0.0);
                autoFloor_wait_times.setHPlateRate(0.0);
            }
            Double LinenameNoNormalRate=0.0;
            Double LinenamePlateRate= 0.0;

            LinenameNoNormalRate= LineTTLNoNormalRateOne+(LineTTLNoNormalRateTwo/3)+(LineTTLNoNormalRateThree/4)/3;
            LinenamePlateRate= LineTTLPlateRateOne+(LineTTLPlateRateTwo/3)+(LineTTLPlateRateThree/4)/3;

            if ( LinenameNoNormalRate!=0){
                LotlineSum++;
            }
            LotRobotNoNormalRate+=LinenameNoNormalRate;
            LotRobotPlateRate+=LinenamePlateRate;
            autoFloor_wait_times.setLineNoNormalRate(LinenameNoNormalRate);
            autoFloor_wait_times.setLinePlateRate(LinenamePlateRate);
            autoFloor_wait_times.setLineNoNormalSum(LinenameNoNormalSum);
            //*---------------A---------------*//*
            // 待板時間 By Line  By cell
            // 偏位時間 By Line  By cell
            // 待板率 By Line  By cell
            AutoFloor_Wait_TimeList.add(autoFloor_wait_times);
        }


        List<AutoFloor_Wait_Time> AutoFloor_Wait_TimeListMac= new ArrayList<>();
        int MaclineSum=0;
        Double MacRobotNoNormalRate=0.0;
        Double MacRobotPlateRate=0.0;
        for (int i = 0; i < MacautoDayT0s.size(); i++) {

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
            AUTOFLOOR_TARGET autofloorTarget = MacautoDayT0s.get(i);
            AutoFloor_Wait_Time autoFloor_wait_times= new AutoFloor_Wait_Time();
            autoFloor_wait_times.setLine_name(autofloorTarget.getLineName());

            AutoFloor_Wait_Time autoFloor_wait_timeA = null;
            autoFloor_wait_timeA = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-A");
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


            if (autoFloor_wait_timeA!=null){
                one++;
                Integer awaitNoNormalSum = autoFloor_wait_timeA.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setANoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeA.getAwaitNoNormalRate();
                LineTTLNoNormalRateOne+=awaitNoNormalRate;
                autoFloor_wait_times.setANoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeA.getAwaitPlateRate();
                LineTTLPlateRateOne+=awaitPlateRate;
                autoFloor_wait_times.setAPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setANoNormalSum(0);
                autoFloor_wait_times.setANoNormalRate(0.0);
                autoFloor_wait_times.setAPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeB = null;

            autoFloor_wait_timeB = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-B");
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

            if (autoFloor_wait_timeB!=null){
                two++;
                Integer awaitNoNormalSum = autoFloor_wait_timeB.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setBNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeB.getAwaitNoNormalRate();
                LineTTLNoNormalRateTwo+=awaitNoNormalRate;
                autoFloor_wait_times.setBNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeB.getAwaitPlateRate();
                LineTTLPlateRateTwo+=awaitPlateRate;
                autoFloor_wait_times.setBPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setBNoNormalSum(0);
                autoFloor_wait_times.setBNoNormalRate(0.0);
                autoFloor_wait_times.setBPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeC = null;

            autoFloor_wait_timeC = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-C");
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

            if (autoFloor_wait_timeC!=null){
                two++;
                Integer awaitNoNormalSum = autoFloor_wait_timeC.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setCNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeC.getAwaitNoNormalRate();
                LineTTLNoNormalRateTwo+=awaitNoNormalRate;
                autoFloor_wait_times.setCNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeC.getAwaitPlateRate();
                LineTTLPlateRateTwo+=awaitPlateRate;
                autoFloor_wait_times.setCPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setCNoNormalSum(0);
                autoFloor_wait_times.setCNoNormalRate(0.0);
                autoFloor_wait_times.setCPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeD = null;

            autoFloor_wait_timeD = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-D");
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

            if (autoFloor_wait_timeD!=null){
                two++;
                Integer awaitNoNormalSum = autoFloor_wait_timeD.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setDNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeD.getAwaitNoNormalRate();
                LineTTLNoNormalRateTwo+=awaitNoNormalRate;
                autoFloor_wait_times.setDNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeD.getAwaitPlateRate();
                LineTTLPlateRateTwo+=awaitPlateRate;
                autoFloor_wait_times.setDPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setDNoNormalSum(0);
                autoFloor_wait_times.setDNoNormalRate(0.0);
                autoFloor_wait_times.setDPlateRate(0.0);
            }


            AutoFloor_Wait_Time autoFloor_wait_timeE = null;

            autoFloor_wait_timeE = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-E");
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

            if (autoFloor_wait_timeE!=null){
                three++;
                Integer awaitNoNormalSum = autoFloor_wait_timeE.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setENoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeE.getAwaitNoNormalRate();
                LineTTLNoNormalRateThree+=awaitNoNormalRate;
                autoFloor_wait_times.setENoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeE.getAwaitPlateRate();
                LineTTLPlateRateThree+=awaitPlateRate;
                autoFloor_wait_times.setEPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setENoNormalSum(0);
                autoFloor_wait_times.setENoNormalRate(0.0);
                autoFloor_wait_times.setEPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeF = null;

            autoFloor_wait_timeF = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-F");
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

            if (autoFloor_wait_timeF!=null){
                three++;
                Integer awaitNoNormalSum = autoFloor_wait_timeF.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setFNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeF.getAwaitNoNormalRate();
                LineTTLNoNormalRateThree+=awaitNoNormalRate;
                autoFloor_wait_times.setFNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeF.getAwaitPlateRate();
                LineTTLPlateRateThree+=awaitPlateRate;
                autoFloor_wait_times.setFPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setFNoNormalSum(0);
                autoFloor_wait_times.setFNoNormalRate(0.0);
                autoFloor_wait_times.setFPlateRate(0.0);
            }

            AutoFloor_Wait_Time autoFloor_wait_timeG = null;

            autoFloor_wait_timeG = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-G");
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

            if (autoFloor_wait_timeG!=null){
                three++;
                Integer awaitNoNormalSum = autoFloor_wait_timeG.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setGNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeG.getAwaitNoNormalRate();
                LineTTLNoNormalRateThree+=awaitNoNormalRate;
                autoFloor_wait_times.setGNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeG.getAwaitPlateRate();
                LineTTLPlateRateThree+=awaitPlateRate;
                autoFloor_wait_times.setGPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setGNoNormalSum(0);
                autoFloor_wait_times.setGNoNormalRate(0.0);
                autoFloor_wait_times.setGPlateRate(0.0);
            }
            AutoFloor_Wait_Time autoFloor_wait_timeH = null;

            autoFloor_wait_timeH = autoFloor_roBotService.RobotAnalyzeDay(schedule,FloorName, autofloorTarget.gettProduct(), autofloorTarget.getLineName()+"-H");
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

            if (autoFloor_wait_timeH!=null){
                three++;
                Integer awaitNoNormalSum = autoFloor_wait_timeH.getAwaitNoNormalSum();
                LinenameNoNormalSum+=awaitNoNormalSum;
                autoFloor_wait_times.setHNoNormalSum(awaitNoNormalSum);

                Double awaitNoNormalRate = autoFloor_wait_timeH.getAwaitNoNormalRate();
                LineTTLNoNormalRateThree+=awaitNoNormalRate;
                autoFloor_wait_times.setHNoNormalRate(awaitNoNormalRate);

                Double awaitPlateRate = autoFloor_wait_timeH.getAwaitPlateRate();
                LineTTLPlateRateThree+=awaitPlateRate;
                autoFloor_wait_times.setHPlateRate(awaitPlateRate);
            }else {
                autoFloor_wait_times.setHNoNormalSum(0);
                autoFloor_wait_times.setHNoNormalRate(0.0);
                autoFloor_wait_times.setHPlateRate(0.0);
            }
            Double LinenameNoNormalRate=0.0;
            Double LinenamePlateRate= 0.0;

            LinenameNoNormalRate= LineTTLNoNormalRateOne+(LineTTLNoNormalRateTwo/3)+(LineTTLNoNormalRateThree/4)/3;
            LinenamePlateRate= LineTTLPlateRateOne+(LineTTLPlateRateTwo/3)+(LineTTLPlateRateThree/4)/3;

            if (LinenameNoNormalRate!=0){
                MaclineSum++;
            }

            MacRobotNoNormalRate+=LinenameNoNormalRate;
            MacRobotPlateRate+=LinenamePlateRate;
            autoFloor_wait_times.setLineNoNormalRate(LinenameNoNormalRate);
            autoFloor_wait_times.setLinePlateRate(LinenamePlateRate);
            autoFloor_wait_times.setLineNoNormalSum(LinenameNoNormalSum);
            //*---------------A---------------*//*
            // 待板時間 By Line  By cell
            // 偏位時間 By Line  By cell
            // 待板率 By Line  By cell
            AutoFloor_Wait_TimeListMac.add(autoFloor_wait_times);
        }
        if (MaclineSum!=0){
            MacRobotNoNormalRate/=MaclineSum;
            MacRobotPlateRate/=MaclineSum;
        }
        if (LotlineSum!=0){
            LotRobotNoNormalRate/=LotlineSum;
            LotRobotPlateRate/=LotlineSum;
        }
        long REnddebugTime =System.currentTimeMillis();
        System.out.println("程序运行时间："+(REnddebugTime-RStartdebugTime)+"ms");

        map.put("MacRobotNoNormalRate",MacRobotNoNormalRate);
        map.put("MacRobotPlateRate", MacRobotPlateRate);
        map.put("LotRobotNoNormalRate",LotRobotNoNormalRate);
        map.put("LotRobotPlateRate",LotRobotPlateRate);

        map.put("AutoFloor_Wait_TimeList", AutoFloor_Wait_TimeList);
        map.put("AutoFloor_Wait_TimeListMac",AutoFloor_Wait_TimeListMac);

        map.put("DataStartTime",DataStartTime);
        map.put("DataEndTime",DataEndTime);

        return "realtime/AllFloorsDayData";
    }



}
