package com.shop.shopfloorcontroller;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSON;
import com.shop.model.*;
import com.shop.services.*;
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
 * @create 2019-09-25 10:55
 */
@Controller
public class AutoFloorOPIssueContller {

    @Autowired
    AUTOFLOOR_TARGEService autofloorService;

    @Autowired
    AutoFloor_RateService autoFloor_rateService;

    @Autowired
    AutoFloor_OP_ISSUEService autoFloor_op_issueService;
    @Autowired
    AutoFloor_BYMWDMapperService autoFloor_bymwdMapperService;

    @Autowired
    TaskService taskService;

    @Autowired
    TimeUtility timeUtility;

    DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @RequestMapping(value = "/AllfloorOPissue",method = RequestMethod.GET)
    public String AutoFloorRobot(@RequestParam(value = "FloorName",required = true,defaultValue = "D073F")String FloorName,Map map){
        List<Date> dateList = taskService.Schedule();
        //获取当前时间
        java.sql.Date Schedule = dateList.get(0);
        //昨天日期
        java.sql.Date YesterdayDate= dateList.get(1);
        //上一周
        java.sql.Date YesterWeek = dateList.get(3);

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

        //机种名称
        String LinennameProduct="Macan";
        //班次天类别
        String DayType=null;
        if (timeUtility.Timequantum0830_2029()){
            DayType="Day";
        }else {
            DayType="Night";
        }
        if (timeUtility.Timequantum0000_0830()){
            Schedule=YesterdayDate;
        }

        List<AUTOFLOOR_TARGET> autofloor_targetAll = autofloorService.LinenameGet(FloorName, FloorName, Schedule,DayType,"Lotus");

        List<AUTOFLOOR_OP_ISSUE> OPIssueList=new ArrayList<>();



        Integer errorTypeTTL=0;
        Integer errorTypeTTL2=0;
        Integer errorTypeTTL3=0;
        Integer errorTypeTTL4=0;
        Integer errorTypeTTL5=0;
        Integer errorTypeTTL6=0;
        Integer errorTypeTTL7=0;
        Integer errorTypeTTL8=0;
        Map<String,Integer> error_TTLMap=new HashMap<>();


        for (int i = 0; i < autofloor_targetAll.size(); i++) {
            Integer error_TypeTTL=0;
            Integer error_Type2=0;
            Integer error_Type3=0;
            Integer error_Type4=0;
            Integer error_Type5=0;
            Integer error_Type6=0;
            Integer error_Type7=0;
            Integer error_Type8=0;
            AUTOFLOOR_TARGET autofloorTarget = autofloor_targetAll.get(i);
            //New Facility
            AUTOFLOOR_OP_ISSUE OPIssue = new AUTOFLOOR_OP_ISSUE();
            //楼层所有线体名称
            String lineName = autofloorTarget.getLineName();
            // Facility 赋值线体名称
            OPIssue.setLineName(lineName);
            //楼层线体所属的机种
            String tProduct = autofloorTarget.gettProduct();
            //Facility 赋值机种类型
            OPIssue.setProduct(tProduct);
            //判断线体是否开线
            BigDecimal dTarget = autofloorTarget.getdTarget();
            if (dTarget.intValue()>0){
                OPIssue.setAreaint(0);
            }else {
                OPIssue.setAreaint(1);
            }
            //投首漏扫描
            AUTOFLOOR_OP_ISSUE autofloor_op_issues2 = autoFloor_op_issueService.OPISSUE_ERROR_TYPE2(Schedule, Schedule,lineName);
            if (autofloor_op_issues2!=null){
                error_Type2=autofloor_op_issues2.getError_TypeNo();
            }
            errorTypeTTL2+=error_Type2;
            OPIssue.setError_TypeNo2(error_Type2);

            //漏测.混测.确认模式异常.工号无权限.機台號错误.SN扫描错误
            AUTOFLOOR_OP_ISSUE autofloor_op_issues3_8 = autoFloor_op_issueService.OPISSUE_ERROR_TYPE3_8(Schedule, Schedule,lineName);
            if (autofloor_op_issues3_8!=null){
                if (autofloor_op_issues3_8.getErrorType().intValue()==3){
                //漏测
                    error_Type3= autofloor_op_issues3_8.getError_TypeNo();


                }else if (autofloor_op_issues3_8.getErrorType().intValue()==4){
                //混测
                    error_Type4= autofloor_op_issues3_8.getError_TypeNo();
                }else if (autofloor_op_issues3_8.getErrorType().intValue()==5){
                //确认模式异常
                    error_Type5= autofloor_op_issues3_8.getError_TypeNo();
                }else if (autofloor_op_issues3_8.getErrorType().intValue()==6){
                //工号无权限
                    error_Type6= autofloor_op_issues3_8.getError_TypeNo();
                }else if (autofloor_op_issues3_8.getErrorType().intValue()==7){
                //機台號错误
                    error_Type7= autofloor_op_issues3_8.getError_TypeNo();
                }else if (autofloor_op_issues3_8.getErrorType().intValue()==8){
                //SN扫描错误
                    error_Type8= autofloor_op_issues3_8.getError_TypeNo();
                }
            }
            //漏测
            errorTypeTTL3+=error_Type3;
            OPIssue.setError_TypeNo3(error_Type3);
            //混测
            errorTypeTTL4+=error_Type4;
            OPIssue.setError_TypeNo4(error_Type4);
            //确认模式异常
            errorTypeTTL5+=error_Type5;
            OPIssue.setError_TypeNo5(error_Type5);
            //工号无权限
            errorTypeTTL6+=error_Type6;
            OPIssue.setError_TypeNo6(error_Type6);
            //機台號错误
            errorTypeTTL7+=error_Type7;
            OPIssue.setError_TypeNo7(error_Type7);
            //SN扫描错误
            errorTypeTTL8+=error_Type8;
            OPIssue.setError_TypeNo8(error_Type8);
            ///TTL
            errorTypeTTL+=error_TypeTTL;
            error_TypeTTL=error_Type2+error_Type3+error_Type4+error_Type5+error_Type6+error_Type7+error_Type8;
            OPIssue.setError_TypeTTL(error_TypeTTL);
            if (dTarget.intValue()>0){
            }else {
            }
            //线体误测率
            OPIssueList.add(OPIssue);
        }
        //Macan
        List<AUTOFLOOR_TARGET> autofloor_targetAllMacan = autofloorService.LinenameGet(FloorName, FloorName, Schedule,DayType,"Macan");
        List<AUTOFLOOR_OP_ISSUE> OPIssueListMacan=new ArrayList<>();
        for (int i = 0; i < autofloor_targetAllMacan.size(); i++) {
            Integer error_TypeTTL=0;
            Integer error_Type2=0;
            Integer error_Type3=0;
            Integer error_Type4=0;
            Integer error_Type5=0;
            Integer error_Type6=0;
            Integer error_Type7=0;
            Integer error_Type8=0;
            AUTOFLOOR_TARGET autofloorTarget = autofloor_targetAllMacan.get(i);
            //New Facility
            AUTOFLOOR_OP_ISSUE OPIssue = new AUTOFLOOR_OP_ISSUE();
            //楼层所有线体名称
            String lineName = autofloorTarget.getLineName();
            // Facility 赋值线体名称
            OPIssue.setLineName(lineName);
            //楼层线体所属的机种
            String tProduct = autofloorTarget.gettProduct();
            //Facility 赋值机种类型
            OPIssue.setProduct(tProduct);
            //判断线体是否开线
            BigDecimal dTarget = autofloorTarget.getdTarget();
            if (dTarget.intValue()>0){
                OPIssue.setAreaint(0);
            }else {
                OPIssue.setAreaint(1);
            }
            //投首漏扫描
            AUTOFLOOR_OP_ISSUE autofloor_op_issues2 = autoFloor_op_issueService.OPISSUE_ERROR_TYPE2(Schedule, Schedule,lineName);
            if (autofloor_op_issues2!=null){
                error_Type2=autofloor_op_issues2.getError_TypeNo();
            }
            errorTypeTTL2+=error_Type2;
            OPIssue.setError_TypeNo2(error_Type2);

            //漏测.混测.确认模式异常.工号无权限.機台號错误.SN扫描错误
            AUTOFLOOR_OP_ISSUE autofloor_op_issues3_8 = autoFloor_op_issueService.OPISSUE_ERROR_TYPE3_8(Schedule, Schedule,lineName);
            if (autofloor_op_issues3_8!=null){
                if (autofloor_op_issues3_8.getErrorType().intValue()==3){
                    //漏测
                    error_Type3= autofloor_op_issues3_8.getError_TypeNo();


                }else if (autofloor_op_issues3_8.getErrorType().intValue()==4){
                    //混测
                    error_Type4= autofloor_op_issues3_8.getError_TypeNo();
                }else if (autofloor_op_issues3_8.getErrorType().intValue()==5){
                    //确认模式异常
                    error_Type5= autofloor_op_issues3_8.getError_TypeNo();
                }else if (autofloor_op_issues3_8.getErrorType().intValue()==6){
                    //工号无权限
                    error_Type6= autofloor_op_issues3_8.getError_TypeNo();
                }else if (autofloor_op_issues3_8.getErrorType().intValue()==7){
                    //機台號错误
                    error_Type7= autofloor_op_issues3_8.getError_TypeNo();
                }else if (autofloor_op_issues3_8.getErrorType().intValue()==8){
                    //SN扫描错误
                    error_Type8= autofloor_op_issues3_8.getError_TypeNo();
                }
            }
            //漏测
            errorTypeTTL3+=error_Type3;
            OPIssue.setError_TypeNo3(error_Type3);
            //混测
            errorTypeTTL4+=error_Type4;
            OPIssue.setError_TypeNo4(error_Type4);
            //确认模式异常
            errorTypeTTL5+=error_Type5;
            OPIssue.setError_TypeNo5(error_Type5);
            //工号无权限
            errorTypeTTL6+=error_Type6;
            OPIssue.setError_TypeNo6(error_Type6);
            //機台號错误
            errorTypeTTL7+=error_Type7;
            OPIssue.setError_TypeNo7(error_Type7);
            //SN扫描错误
            errorTypeTTL8+=error_Type8;
            OPIssue.setError_TypeNo8(error_Type8);
            ///TTL
            errorTypeTTL+=error_TypeTTL;
            error_TypeTTL=error_Type2+error_Type3+error_Type4+error_Type5+error_Type6+error_Type7+error_Type8;
            OPIssue.setError_TypeTTL(error_TypeTTL);
            if (dTarget.intValue()>0){
            }else {
            }
            //线体误测率
            OPIssueListMacan.add(OPIssue);
        }
        errorTypeTTL+=errorTypeTTL2+errorTypeTTL3+errorTypeTTL4+errorTypeTTL5+errorTypeTTL6+errorTypeTTL7+errorTypeTTL8;

        error_TTLMap.put("errorTypeTTL", errorTypeTTL);
        error_TTLMap.put("errorTypeTTL2",errorTypeTTL2);
        error_TTLMap.put("errorTypeTTL3", errorTypeTTL3);
        error_TTLMap.put("errorTypeTTL4", errorTypeTTL4);
        error_TTLMap.put("errorTypeTTL5", errorTypeTTL5);
        error_TTLMap.put("errorTypeTTL6", errorTypeTTL6);
        error_TTLMap.put("errorTypeTTL7", errorTypeTTL7);
        error_TTLMap.put("errorTypeTTL8", errorTypeTTL8);
        //OP柱状图数据


        Map<String, List<AUTOFLOOR_BYMWD>> ALLBYMWDlist = autoFloor_bymwdMapperService.SelectBYMWDList(FloorName);
        //月
        List<AUTOFLOOR_BYMWD> autofloor_bymwdListM = ALLBYMWDlist.get("bymwdListM");
        //周
        List<AUTOFLOOR_BYMWD> autofloor_bymwdListW = ALLBYMWDlist.get("bymwdListW");
        //天
        List<AUTOFLOOR_BYMWD> autofloor_bymwdListD = ALLBYMWDlist.get("bymwdListD");
        //柱狀圖所有數據
        List<AUTOFLOOR_BYMWD> ALLBYMWDData=new ArrayList<>();
        //天数据
        for (AUTOFLOOR_BYMWD autofloorBymwd : autofloor_bymwdListD) {
            String StartDate = df.format(autofloorBymwd.getStartDate());
            String EndDate = df.format(autofloorBymwd.getEndDate());
            AUTOFLOOR_BYMWD autofloor_bymwd=new AUTOFLOOR_BYMWD();
            autofloor_bymwd.setId(autofloorBymwd.getId());
            autofloor_bymwd.settMark(autofloorBymwd.gettMark());
            AUTOFLOOR_OP_ISSUE autofloor_op_issueOneDay = autoFloor_op_issueService.OPISSUE_ERROR_TYPEOneDay(StartDate, EndDate, FloorName);
            Integer errortypeOneDaysum=0;
            if (autofloor_op_issueOneDay!=null){
                errortypeOneDaysum=autofloor_op_issueOneDay.getERRORTYPESUM();
            }
            autofloor_bymwd.setOPIssue(errortypeOneDaysum);

            ALLBYMWDData.add(autofloor_bymwd);
        }
        //周数据
        for (AUTOFLOOR_BYMWD autofloorBymwd : autofloor_bymwdListW) {
            String StartDate = df.format(autofloorBymwd.getStartDate());
            String EndDate = df.format(autofloorBymwd.getEndDate());
            AUTOFLOOR_BYMWD autofloor_bymwd=new AUTOFLOOR_BYMWD();
            autofloor_bymwd.setId(autofloorBymwd.getId());
            autofloor_bymwd.settMark(autofloorBymwd.gettMark());
            AUTOFLOOR_OP_ISSUE autofloor_op_issueOneDay = autoFloor_op_issueService.OPISSUE_ERROR_TYPEOneDay(StartDate, EndDate, FloorName);
            Integer errortypeOneDaysum=0;
            if (autofloor_op_issueOneDay!=null){
                errortypeOneDaysum=autofloor_op_issueOneDay.getERRORTYPESUM();
            }
            autofloor_bymwd.setOPIssue(errortypeOneDaysum);

            ALLBYMWDData.add(autofloor_bymwd);
        }
        //月数据
        for (AUTOFLOOR_BYMWD autofloorBymwd : autofloor_bymwdListM) {
            String StartDate = df.format(autofloorBymwd.getStartDate());
            String EndDate = df.format(autofloorBymwd.getEndDate());
            AUTOFLOOR_BYMWD autofloor_bymwd=new AUTOFLOOR_BYMWD();
            autofloor_bymwd.setId(autofloorBymwd.getId());
            autofloor_bymwd.settMark(autofloorBymwd.gettMark());
            AUTOFLOOR_OP_ISSUE autofloor_op_issueOneDay = autoFloor_op_issueService.OPISSUE_ERROR_TYPEOneDay(StartDate, EndDate, FloorName);
            Integer errortypeOneDaysum=0;
            if (autofloor_op_issueOneDay!=null){
                errortypeOneDaysum=autofloor_op_issueOneDay.getERRORTYPESUM();
            }
            autofloor_bymwd.setOPIssue(errortypeOneDaysum);

            ALLBYMWDData.add(autofloor_bymwd);
        }
        Collections.sort(ALLBYMWDData);
        String ALLBYMWDDataJson = JSON.toJSONString(ALLBYMWDData);

        map.put("ALLBYMWDData",ALLBYMWDDataJson);
        map.put("Scheduledate",Schedule.toString());
        map.put("YesterWeekdate", YesterWeek.toString());
        map.put("OPIssueList",OPIssueList);
        map.put("OPIssueListMacan",OPIssueListMacan);
        map.put("error_TTLMap",error_TTLMap);
        map.put("Start", Schedule);
        return "realtime/AllFloorsOPIssueTracking";
    }

}
