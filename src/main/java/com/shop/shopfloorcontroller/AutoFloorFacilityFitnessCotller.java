package com.shop.shopfloorcontroller;


import com.alibaba.fastjson.JSON;
import com.shop.dao.AUTOFLOOR_HEALTH_MACHINEMapper;
import com.shop.model.*;
import com.shop.services.AUTOFLOOR_TARGEService;
import com.shop.services.AutoFloor_BYMWDMapperService;
import com.shop.services.AutoFloor_RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author
 * @create 2019-08-17 14:32
 */
@Controller
public class AutoFloorFacilityFitnessCotller {

    @Autowired
    AUTOFLOOR_TARGEService autofloorService;

    @Autowired
    AutoFloor_RateService autoFloor_rateService;

    @Autowired
    AutoFloor_BYMWDMapperService autoFloor_bymwdMapperService;

    @Autowired
    AUTOFLOOR_HEALTH_MACHINEMapper autofloor_health_machineMapper;

    @Autowired
    TaskService taskService;

    @Autowired
    TimeUtility timeUtility;


    DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");

/*---------------****--------------楼层设备健康追踪----------------****--------------------*/

    @RequestMapping(value = "/AllFloorsFacilityFitness",method = RequestMethod.GET)
    public  String Allfloordata(@RequestParam(value = "FloorName",required = true,defaultValue = "D073F")String FloorName, Map map){
        List<Date> dateList = taskService.Schedule();
        //获取当前时间
        Date Schedule = dateList.get(0);
        //昨天日期
        Date YesterdayDate= dateList.get(1);
        //上一周
        Date YesterWeek = dateList.get(3);
        //上一月
        Date YesterMonth = dateList.get(4);
        //机台名称
        String DFU="DFU";
        String FCT="FCT";
        String S1="CELL_S1";
        String S2="CELL_S2";
        String S3="CELL_S3";
        String CCT="CCT_COMBO";
        String W1="WIFI/BT_W1";
        String W2="WIFI/BT_W2";
        String UWB="UWB";
        String SCOND="SCOND";

        String DayType=null;
        if (timeUtility.Timequantum0830_2029()){
            DayType="Day";
        }else {
            DayType="Night";
        }
        if (timeUtility.Timequantum0000_0830()){
            Schedule=YesterdayDate;
        }
        int HealthM=2;
        int UNHealthM=5;

        Double DFUMisdetetRate  =0.8;
        Double FCTMisdetetRate  =0.70;
        Double S1MisdetetRate   =2.5;
        Double S2MisdetetRate   =3.5;
        Double S3MisdetetRate   =1.5;
        Double CCTMisdetetRate  =0.5;
        Double W1MisdetetRate   =2.5;
        Double W2MisdetetRate   =1.0;
        Double UWBMisdetetRate  =1.0;
        Double SCONDMisdetetRate=1.5;

        Map<String,Double> MisdetetMap=new HashMap<>();
        MisdetetMap.put("DFU",DFUMisdetetRate);
        MisdetetMap.put("FCT",FCTMisdetetRate);
        MisdetetMap.put("S1",S1MisdetetRate);
        MisdetetMap.put("S2",S2MisdetetRate);
        MisdetetMap.put("S3",S3MisdetetRate);
        MisdetetMap.put("CCT",CCTMisdetetRate);
        MisdetetMap.put("W1",W1MisdetetRate);
        MisdetetMap.put("W2",W2MisdetetRate);
        MisdetetMap.put("UWB",UWBMisdetetRate);
        MisdetetMap.put("SCOND",SCONDMisdetetRate);
        if (timeUtility.Timequantum0000_0830()){
            Schedule=YesterdayDate;
        }
        //机种名称
        String LinennameProduct="Macan";
        //Lotus
        List<AUTOFLOOR_TARGET> autofloor_targetAll =new ArrayList<>();
        autofloor_targetAll = autofloorService.LinenameGet(FloorName, FloorName, Schedule,DayType,"Lotus");
        //设备健康表格数据
        List<AutoFloorFacilityFitness> FacilityList = new ArrayList<>();
        for (int i = 0; i < autofloor_targetAll.size(); i++) {
            AUTOFLOOR_TARGET autofloorTarget = autofloor_targetAll.get(i);
            //New Facility
            AutoFloorFacilityFitness Facility = new AutoFloorFacilityFitness();
            //楼层所有线体名称
            String lineName = autofloorTarget.getLineName();
            // Facility 赋值线体名称
            Facility.setLinename(lineName);
            //楼层线体所属的机种
            String tProduct = autofloorTarget.gettProduct();
            //Facility 赋值机种类型
            Facility.setPRODUCT(tProduct);
            //判断线体是否开线
            BigDecimal dTarget = autofloorTarget.getdTarget();
            if (dTarget.intValue()>0){
                Facility.setArea(0);
            }else {
                Facility.setArea(1);
            }
            //楼层所有线体机种
            String Product = autofloorTarget.gettProduct();
            //线体误测率
            List<AutoFloor_Rate> autoFloor_rates = autoFloor_rateService.AutoFloorRate_Misdetet(FloorName,Product,lineName);
            for (AutoFloor_Rate floor_rate : autoFloor_rates) {
                //线体机台名称
                String stationName = floor_rate.getStationName();
                //根据机种更换机台名称
                if (Product.equals(LinennameProduct)){
                    W1="WIFI/BT";
                    W2="WIFI/BT2";
                }
                //取出机台名称相同的误测
                if (stationName.equals(DFU)){
                    Facility.setDFUStation(floor_rate.getRate());
                }else if (stationName.equals(FCT)){
                    Facility.setFCTStation(floor_rate.getRate());
                }else if(stationName.equals(S1)){
                    Facility.setS1Station(floor_rate.getRate());
                }else if(stationName.equals(S2)){
                    Facility.setS2Station(floor_rate.getRate());
                }else if(stationName.equals(S3)){
                    Facility.setS3Station(floor_rate.getRate());
                }else if(stationName.equals(CCT)){
                    Facility.setCCTStation(floor_rate.getRate());
                }else if(stationName.equals(W1)){
                    Facility.setW1Station(floor_rate.getRate());
                }else if(stationName.equals(W2)){
                    Facility.setW2Station(floor_rate.getRate());
                }else if(stationName.equals(UWB)){
                    Facility.setUWBStation(floor_rate.getRate());
                }else if(stationName.equals(SCOND)){
                    Facility.setSCONDStation(floor_rate.getRate());
                }
            }
            FacilityList.add(Facility);
        }
        //Macan
        List<AUTOFLOOR_TARGET> autofloor_targetAllMacan = autofloorService.LinenameGet(FloorName, FloorName, Schedule,DayType,"Macan");


        //设备健康表格数据
        List<AutoFloorFacilityFitness> FacilityListMacan = new ArrayList<>();
        for (int i = 0; i < autofloor_targetAllMacan.size(); i++) {
            AUTOFLOOR_TARGET autofloorTarget = autofloor_targetAllMacan.get(i);
            //New Facility
            AutoFloorFacilityFitness Facility = new AutoFloorFacilityFitness();
            //楼层所有线体名称
            String lineName = autofloorTarget.getLineName();
            // Facility 赋值线体名称
            Facility.setLinename(lineName);
            //楼层线体所属的机种
            String tProduct = autofloorTarget.gettProduct();
            //Facility 赋值机种类型
            Facility.setPRODUCT(tProduct);
            //判断线体是否开线
            BigDecimal dTarget = autofloorTarget.getdTarget();
            if (dTarget.intValue()>0){
                Facility.setArea(0);
            }else {
                Facility.setArea(1);
            }
            //楼层所有线体机种
            String Product = autofloorTarget.gettProduct();
            //线体误测率
            List<AutoFloor_Rate> autoFloor_rates = autoFloor_rateService.AutoFloorRate_Misdetet(FloorName,Product,lineName);
            for (AutoFloor_Rate floor_rate : autoFloor_rates) {
                //线体机台名称
                String stationName = floor_rate.getStationName();
                //根据机种更换机台名称
                if (Product.equals(LinennameProduct)){
                    W1="WIFI/BT";
                    W2="WIFI/BT2";
                }
                //取出机台名称相同的误测
                if (stationName.equals(DFU)){
                    Facility.setDFUStation(floor_rate.getRate());
                }else if (stationName.equals(FCT)){
                    Facility.setFCTStation(floor_rate.getRate());
                }else if(stationName.equals(S1)){
                    Facility.setS1Station(floor_rate.getRate());
                }else if(stationName.equals(S2)){
                    Facility.setS2Station(floor_rate.getRate());
                }else if(stationName.equals(S3)){
                    Facility.setS3Station(floor_rate.getRate());
                }else if(stationName.equals(CCT)){
                    Facility.setCCTStation(floor_rate.getRate());
                }else if(stationName.equals(W1)){
                    Facility.setW1Station(floor_rate.getRate());
                }else if(stationName.equals(W2)){
                    Facility.setW2Station(floor_rate.getRate());
                }else if(stationName.equals(UWB)){
                    Facility.setUWBStation(floor_rate.getRate());
                }else if(stationName.equals(SCOND)){
                    Facility.setSCONDStation(floor_rate.getRate());
                }
            }
            FacilityListMacan.add(Facility);
        }
        //设备追踪健康柱状图
        Map<String,Integer> OneDayMap= new HashMap<>();
        List<AutoFloor_Rate> AllStation= new ArrayList<>();
        //所有线体信息
        for (AUTOFLOOR_TARGET autofloorTarget : autofloor_targetAll) {
            //线体名称
            String lineName =null;
            //判断有排配的线体名称
            lineName=autofloorTarget.getLineName();
            if (autofloorTarget.getdTarget().intValue()>0){
            }
            //获取有排配的线体的所有机台
            List<AutoFloor_Rate> autoFloor_rates = autoFloor_rateService.AllStation(FloorName, lineName);
            AllStation.addAll(autoFloor_rates);
            if (lineName!=null){
            }
        }

        Map<String, List<AUTOFLOOR_BYMWD>> ALLBYMWDlist = autoFloor_bymwdMapperService.SelectBYMWDList(FloorName);

        //月
        List<AUTOFLOOR_BYMWD> autofloor_bymwdListM = ALLBYMWDlist.get("bymwdListM");
        //周
        List<AUTOFLOOR_BYMWD> autofloor_bymwdListW = ALLBYMWDlist.get("bymwdListW");
        //天
        List<AUTOFLOOR_BYMWD> autofloor_bymwdListD = ALLBYMWDlist.get("bymwdListD");
        //柱狀圖所有數據
        List<AUTOFLOOR_BYMWD> ALLBYMWDData=new ArrayList<>();


        //天類型數據
        for (AUTOFLOOR_BYMWD autofloor_bymwd : autofloor_bymwdListD) {
            String StartDate = df.format(autofloor_bymwd.getStartDate());
            String EndDate = df.format(autofloor_bymwd.getEndDate());
            AUTOFLOOR_BYMWD autofloorBymwd = new AUTOFLOOR_BYMWD();
            List<AUTOFLOOR_HEALTH_MACHINE> autofloor_health_machines = autofloor_health_machineMapper.SelectDayStationMidetect(FloorName, StartDate, EndDate);
            int DayHealth = 0;
            int DayUNHealth = 0;
            int DayWarning = 0;

            for (AutoFloor_Rate floor_rate : AllStation) {
                String AllstationName = floor_rate.getStationname();
                String product = floor_rate.getProduct();
                //判断机种 区分工站名称
                if (product.equals(LinennameProduct)) {
                    W1 = "WIFI/BT";
                    W2 = "WIFI/BT2";
                }
                for (AUTOFLOOR_HEALTH_MACHINE autoFloor_rate : autofloor_health_machines) {
                    String OneDaystationName = autoFloor_rate.getStationName();
                    if (OneDaystationName != null) {

                        if (OneDaystationName.contains(DFU)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    //判断小于机台目标二倍的Health机台
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    //判断大于机台目标二倍的小于目标五倍的UNHealth机台
                                    DayUNHealth += 1;
                                } else {
                                    //判断Warning机台
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(FCT)) {

                            if (OneDaystationName.equals(AllstationName)) {

                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(S1)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(S2)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(S3)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(CCT)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(W1)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(W2)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(UWB)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(SCOND)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        }
                    }
                }

            }
            DayWarning+=AllStation.size()-(DayHealth+DayUNHealth+DayWarning);
            autofloorBymwd.settMark(autofloor_bymwd.gettMark());
            autofloorBymwd.setId(autofloor_bymwd.getId());
            autofloorBymwd.setHealth(DayHealth);
            autofloorBymwd.setUNHealth(DayUNHealth);
            autofloorBymwd.setWarning(DayWarning);
            ALLBYMWDData.add(autofloorBymwd);
        }
            //周類型數據
            for (AUTOFLOOR_BYMWD autofloor_bymwd : autofloor_bymwdListW) {
                String StartDate = df.format(autofloor_bymwd.getStartDate());
                String EndDate = df.format(autofloor_bymwd.getEndDate());
                AUTOFLOOR_BYMWD autofloorBymwd=new AUTOFLOOR_BYMWD();
                List<AUTOFLOOR_HEALTH_MACHINE> autofloor_health_machines = autofloor_health_machineMapper.SelectWeekStationMidetect(FloorName, StartDate, EndDate);
                int  DayHealth   =0;
                int  DayUNHealth =0;
                int  DayWarning  =0;

                for (AutoFloor_Rate floor_rate : AllStation) {
                    String AllstationName = floor_rate.getStationname();

                    String product = floor_rate.getProduct();
                    //判断机种 区分工站名称
                    if (product.equals(LinennameProduct)) {
                        W1 = "WIFI/BT";
                        W2 = "WIFI/BT2";
                    }
                    for (AUTOFLOOR_HEALTH_MACHINE autoFloor_rate : autofloor_health_machines) {
                        String OneDaystationName = autoFloor_rate.getStationName();
                        if (OneDaystationName != null) {
                            if (OneDaystationName.contains(DFU)) {
                                if (OneDaystationName.equals(AllstationName)) {
                                    if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                        //判断小于机台目标二倍的Health机台
                                        DayHealth += 1;
                                    } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                        //判断大于机台目标二倍的小于目标五倍的UNHealth机台
                                        DayUNHealth += 1;
                                    } else {
                                        //判断Warning机台
                                        DayWarning += 1;
                                    }
                                }
                            } else if (OneDaystationName.contains(FCT)) {

                                if (OneDaystationName.equals(AllstationName)) {
                                    if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                        DayHealth += 1;
                                    } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                        DayUNHealth += 1;
                                    } else {
                                        DayWarning += 1;
                                    }
                                }
                            } else if (OneDaystationName.contains(S1)) {
                                if (OneDaystationName.equals(AllstationName)) {
                                    if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                        DayHealth += 1;
                                    } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                        DayUNHealth += 1;
                                    } else {
                                        DayWarning += 1;
                                    }
                                }
                            } else if (OneDaystationName.contains(S2)) {
                                if (OneDaystationName.equals(AllstationName)) {
                                    if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                        DayHealth += 1;
                                    } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                        DayUNHealth += 1;
                                    } else {
                                        DayWarning += 1;
                                    }
                                }
                            } else if (OneDaystationName.contains(S3)) {
                                if (OneDaystationName.equals(AllstationName)) {
                                    if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                        DayHealth += 1;
                                    } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                        DayUNHealth += 1;
                                    } else {
                                        DayWarning += 1;
                                    }
                                }
                            } else if (OneDaystationName.contains(CCT)) {
                                if (OneDaystationName.equals(AllstationName)) {
                                    if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                        DayHealth += 1;
                                    } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                        DayUNHealth += 1;
                                    } else {
                                        DayWarning += 1;
                                    }
                                }
                            } else if (OneDaystationName.contains(W1)) {
                                if (OneDaystationName.equals(AllstationName)) {
                                    if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                        DayHealth += 1;
                                    } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                        DayUNHealth += 1;
                                    } else {
                                        DayWarning += 1;
                                    }
                                }
                            } else if (OneDaystationName.contains(W2)) {
                                if (OneDaystationName.equals(AllstationName)) {
                                    if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                        DayHealth += 1;
                                    } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                        DayUNHealth += 1;
                                    } else {
                                        DayWarning += 1;
                                    }
                                }
                            } else if (OneDaystationName.contains(UWB)) {
                                if (OneDaystationName.equals(AllstationName)) {
                                    if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                        DayHealth += 1;
                                    } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                        DayUNHealth += 1;
                                    } else {
                                        DayWarning += 1;
                                    }
                                }
                            } else if (OneDaystationName.contains(SCOND)) {
                                if (OneDaystationName.equals(AllstationName)) {
                                    if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                        DayHealth += 1;
                                    } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                        DayUNHealth += 1;
                                    } else {
                                        DayWarning += 1;
                                    }
                                }
                            }
                        }
                    }

                }
            DayWarning+=AllStation.size()-(DayHealth+DayUNHealth+DayWarning);
            autofloorBymwd.setId(autofloor_bymwd.getId());
            autofloorBymwd.settMark(autofloor_bymwd.gettMark());
            autofloorBymwd.setHealth(DayHealth);
            autofloorBymwd.setUNHealth(DayUNHealth);
            autofloorBymwd.setWarning(DayWarning);
            ALLBYMWDData.add(autofloorBymwd);
        }
        //月類型數據
        for (AUTOFLOOR_BYMWD autofloor_bymwd : autofloor_bymwdListM) {
            String StartDate = df.format(autofloor_bymwd.getStartDate());
            String EndDate = df.format(autofloor_bymwd.getEndDate());
            AUTOFLOOR_BYMWD autofloorBymwd=new AUTOFLOOR_BYMWD();
            List<AUTOFLOOR_HEALTH_MACHINE> autofloor_health_machines = autofloor_health_machineMapper.SelectMonthStationMidetect(FloorName, StartDate, EndDate);
            int  DayHealth   =0;
            int  DayUNHealth =0;
            int  DayWarning  =0;

            for (AutoFloor_Rate floor_rate : AllStation) {
                String AllstationName = floor_rate.getStationname();
                String product = floor_rate.getProduct();
                //判断机种 区分工站名称
                if (product.equals(LinennameProduct)) {
                    W1 = "WIFI/BT";
                    W2 = "WIFI/BT2";
                }
                for (AUTOFLOOR_HEALTH_MACHINE autoFloor_rate : autofloor_health_machines) {
                    String OneDaystationName = autoFloor_rate.getStationName();
                    if (OneDaystationName != null) {
                        if (OneDaystationName.contains(DFU)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    //判断小于机台目标二倍的Health机台
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    //判断大于机台目标二倍的小于目标五倍的UNHealth机台
                                    DayUNHealth += 1;
                                } else {
                                    //判断Warning机台
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(FCT)) {

                            if (OneDaystationName.equals(AllstationName)) {

                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(S1)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(S2)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(S3)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(CCT)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(W1)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(W2)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(UWB)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        } else if (OneDaystationName.contains(SCOND)) {
                            if (OneDaystationName.equals(AllstationName)) {
                                if ((autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * HealthM)) {
                                    DayHealth += 1;
                                } else if ((autoFloor_rate.getMisdetet() * 100) > (DFUMisdetetRate * HealthM) && (autoFloor_rate.getMisdetet() * 100) <= (DFUMisdetetRate * UNHealthM)) {
                                    DayUNHealth += 1;
                                } else {
                                    DayWarning += 1;
                                }
                            }
                        }
                    }
                }

            }
            DayWarning+=AllStation.size()-(DayHealth+DayUNHealth+DayWarning);
            autofloorBymwd.setId(autofloor_bymwd.getId());
            autofloorBymwd.settMark(autofloor_bymwd.gettMark());
            autofloorBymwd.setHealth(DayHealth);
            autofloorBymwd.setUNHealth(DayUNHealth);
            autofloorBymwd.setWarning(DayWarning);
            ALLBYMWDData.add(autofloorBymwd);
        }
        Collections.sort(ALLBYMWDData);
        String ALLBYMWDDataJson = JSON.toJSONString(ALLBYMWDData);
        map.put("ALLBYMWDData",ALLBYMWDDataJson);
        map.put("OneDayMap", OneDayMap);
        map.put("MisdetetMap",MisdetetMap);
        map.put("FacilityListMacan",FacilityListMacan);
        map.put("FacilityList",FacilityList);
        return "realtime/AllFloorsFacilityFitness";

    }



}
