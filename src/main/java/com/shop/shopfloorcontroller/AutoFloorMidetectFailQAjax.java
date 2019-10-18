package com.shop.shopfloorcontroller;

import com.alibaba.fastjson.JSON;
import com.shop.model.AutoFloor_Rate;
import com.shop.services.AutoFloor_RateService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2019-09-11 8:55
 */
@RestController
public class AutoFloorMidetectFailQAjax {

        @Autowired
        AutoFloor_RateService autoFloor_rateService;
        @Autowired
        TaskService taskService;
        @Autowired
        TimeUtility timeUtility;
        @RequestMapping("/MidetectFailQ")
        public Map<String,Object> MidetectFailQ(String Linename_M,String MachineNo, String Floorname, String Linename,String TimeDayFail){
            List<java.sql.Date> schedule = taskService.Schedule();
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
            Integer MidetectFailsum=0;
            List<AutoFloor_Rate> MidetectFail = new ArrayList<>();
            if (TimeDayFail.equals("two")){
                if (timeUtility.Timequantum0830_1029()){
                    MidetectFailsum = autoFloor_rateService.MidetectFailsum08action(date,Linename_M,MachineNo);
                    MidetectFail=autoFloor_rateService.MidetectFail08action(date,Linename_M,MachineNo);
                }else {
                    MidetectFailsum = autoFloor_rateService.MidetectFailsumtwo(Linename_M,MachineNo);
                    MidetectFail=autoFloor_rateService.MidetectFailtwo(Linename_M,MachineNo);
                }
            }else if (TimeDayFail.equals("Day")){
                MidetectFailsum =autoFloor_rateService.MidetectFailsum(date,Linename_M,MachineNo);
                MidetectFail= autoFloor_rateService.MidetectFail(date,Linename_M,MachineNo);
            }else if (TimeDayFail.equals("Night")){
                if (timeUtility.Timequantum2030_2229()){
                    MidetectFailsum = autoFloor_rateService.MidetectFailsum20action(date, Floorname, MachineNo, Linename_M,MachineNo);
                    MidetectFail=autoFloor_rateService.MidetectFail20action(date,Linename_M,MachineNo);
                }else {
                    MidetectFailsum =autoFloor_rateService.MidetectFailsumNight(StartDate,EndDate,Linename_M,MachineNo);
                    MidetectFail= autoFloor_rateService.MidetectFailNight(StartDate,EndDate,Linename_M,MachineNo);
                }
            }else if (TimeDayFail.equals("Yesterday")){
                    MidetectFailsum =autoFloor_rateService.MidetectFailsumYesterday(StartDate,EndDate,Linename_M,MachineNo);
                    MidetectFail= autoFloor_rateService.MidetectFailYesterday(StartDate,EndDate,Linename_M,MachineNo);
            }
            Map<String,Object> MidetectFailMap= new HashMap<>();

            MidetectFailMap.put("MidetectFailsum",MidetectFailsum);
            MidetectFailMap.put("MachineNo",MachineNo);
            MidetectFailMap.put("MidetectFailList",MidetectFail);
            Object MidetectJson = JSON.toJSON(MidetectFail);
            MidetectFailMap.put("MidetectJson",MidetectJson.toString());
            return MidetectFailMap;
        }




}
