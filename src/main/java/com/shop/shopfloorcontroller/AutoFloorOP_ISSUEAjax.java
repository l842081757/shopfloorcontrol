package com.shop.shopfloorcontroller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.shop.model.AUTOFLOOR_OP_ISSUE;
import com.shop.model.AUTOFLOOR_TARGET;
import com.shop.model.PageInfo;
import com.shop.services.AUTOFLOOR_TARGEService;
import com.shop.services.AutoFloor_OP_ISSUEService;
import com.shop.services.AutoFloor_RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author
 * @create 2019-09-11 8:55
 */
@RestController
public class AutoFloorOP_ISSUEAjax {

        @Autowired
        AUTOFLOOR_TARGEService autofloorService;

        @Autowired
        AutoFloor_RateService autoFloor_rateService;

        @Autowired
        AutoFloor_OP_ISSUEService autoFloor_op_issueService;

        @Autowired
        TaskService taskService;

        @Autowired
        TimeUtility timeUtility;
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");

        @RequestMapping("/OPISSUEAjax")
        public Map<String,Object> OPISSUEAjax(@RequestParam(value = "Linename_M",required = true)String Linename_M,@RequestParam(value = "pagenum",required = true,defaultValue = "1") Integer pagenum){
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
           // PageHelper.startPage(pagenum, 5);
            List<AUTOFLOOR_OP_ISSUE> autofloor_op_issuesList = autoFloor_op_issueService.OPISSUE_detail(date, date, Linename_M);
            List<AUTOFLOOR_OP_ISSUE> autofloor_op_issues =new ArrayList<>();
            for (int i = 0; i < autofloor_op_issuesList.size(); i++) {
                AUTOFLOOR_OP_ISSUE autofloor_op_issue = autofloor_op_issuesList.get(i);

                if (autofloor_op_issue.getErrorType().intValue()==2){
                    autofloor_op_issue.setErrordetailed("投首漏掃描");

                }else if (autofloor_op_issue.getErrorType().intValue()==3){
                    autofloor_op_issue.setErrordetailed("漏測");

                }else if (autofloor_op_issue.getErrorType().intValue()==4){
                    autofloor_op_issue.setErrordetailed("混測");

                }else if (autofloor_op_issue.getErrorType().intValue()==5){
                    autofloor_op_issue.setErrordetailed("確認模式異常");

                }else if (autofloor_op_issue.getErrorType().intValue()==6){
                    autofloor_op_issue.setErrordetailed("工號無權限");

                }else if (autofloor_op_issue.getErrorType().intValue()==7){
                    autofloor_op_issue.setErrordetailed("機台號錯誤");

                }else if (autofloor_op_issue.getErrorType().intValue()==8){
                    autofloor_op_issue.setErrordetailed("SN掃描錯誤");
                }
                autofloor_op_issues.add(autofloor_op_issue);
            }
            PageInfo<AUTOFLOOR_OP_ISSUE> op_issuesPageInfo = new PageInfo<AUTOFLOOR_OP_ISSUE>(autofloor_op_issues);
            String OPISSUESJson = JSON.toJSONString(op_issuesPageInfo);
            Map<String,Object> OPISSUESMap = new HashMap();
            OPISSUESMap.put("OPISSUESJson",OPISSUESJson);
            OPISSUESMap.put("Linename_M",Linename_M);

            return OPISSUESMap;
        }





}
