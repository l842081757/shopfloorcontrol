package com.shop.services;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.shop.dao.AUTOFLOOR_BYMWDMapper;
import com.shop.dao.AUTOFLOOR_HEALTH_MACHINEMapper;
import com.shop.dao.AutoFloor_RateMapper;
import com.shop.model.AUTOFLOOR_BYMWD;
import com.shop.model.AUTOFLOOR_HEALTH_MACHINE;
import com.shop.services.Impls.AutoFloor_BYMWDMapperServiceImpls;
import com.shop.shopfloorcontroller.TaskService;
import com.shop.shopfloorcontroller.TimeUtility;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2019-10-14 10:21
 */
@MapperScan("com.shop.dao")
@Service
public class AutoFloor_BYMWDMapperService implements AutoFloor_BYMWDMapperServiceImpls {

    @Autowired
    AUTOFLOOR_BYMWDMapper autofloorBymwdMapper;

    @Autowired
    AutoFloor_RateMapper  autoFloor_rateMapper;

    @Autowired
    AUTOFLOOR_HEALTH_MACHINEMapper autofloor_health_machineMapper;

    @Autowired
    TaskService taskService;

    @Autowired
    TimeUtility timeUtility;

    DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Override
    public Map<String,List<AUTOFLOOR_BYMWD>> SelectBYMWDList(String FloorName) {
        List<Integer> timeList= taskService.getTime(new java.util.Date());
        List<Date> dateList = taskService.Schedule();
        //获取当前时间
        Date Schedule = dateList.get(0);
        //昨天日期
        Date YesterdayDate= dateList.get(1);
        //上一周
        Date YesterWeek = dateList.get(3);
        //上一月
        Date YesterMonth = dateList.get(4);
        //月
        Integer Month = timeList.get(0);
        //日
        Integer DAY = timeList.get(1);
        //時
        Integer HOUR = timeList.get(2);
        //分
        Integer MINUTE = timeList.get(3);
        //秒
        Integer SECOND = timeList.get(4);

        //當前日期獲取ID
        Integer BYMWDId = autofloorBymwdMapper.SelectBYMWDByID(Month+"/"+DAY);
        //ID以前所有月的數據
        List<AUTOFLOOR_BYMWD> autofloor_bymwds = autofloorBymwdMapper.SelectBYMWDMList(BYMWDId);
        //月List
        List<AUTOFLOOR_BYMWD> autofloor_bymwdListM = new ArrayList<>();
        //周List
        List<AUTOFLOOR_BYMWD> autofloor_bymwdListW = new ArrayList<>();
        //日List
        List<AUTOFLOOR_BYMWD> autofloor_bymwdListD = new ArrayList<>();

        for (AUTOFLOOR_BYMWD autofloor_bymwd : autofloor_bymwds) {
            if (autofloor_bymwd.gettRemark().equals("M")){
                autofloor_bymwdListM.add(autofloor_bymwd);
            }
        }
        //獲取月的Id
        AUTOFLOOR_BYMWD bymwdM = autofloor_bymwdListM.get(autofloor_bymwdListM.size() - 1);
        BigDecimal bymwdMbyID = bymwdM.getId();
        //獲取周的數據
        List<AUTOFLOOR_BYMWD> autofloor_bymwdWList = autofloorBymwdMapper.SelectBYMWDWList(bymwdMbyID.intValue(), BYMWDId);
        for (AUTOFLOOR_BYMWD autofloor_bymwd : autofloor_bymwdWList) {
            if (autofloor_bymwd.gettRemark().equals("W")){
                autofloor_bymwdListW.add(autofloor_bymwd);
            }
        }
        //獲取周的Id
        AUTOFLOOR_BYMWD bymwdW = autofloor_bymwdListW.get(autofloor_bymwdListW.size() - 1);
        BigDecimal bymwdWbyID = bymwdW.getId();
        //獲取周的數據
        List<AUTOFLOOR_BYMWD> autofloor_bymwdDList = autofloorBymwdMapper.SelectBYMWDWList(bymwdWbyID.intValue(), BYMWDId);
        for (AUTOFLOOR_BYMWD autofloor_bymwd : autofloor_bymwdDList) {
            if (autofloor_bymwd.gettRemark().equals("D")){
                autofloor_bymwdListD.add(autofloor_bymwd);
            }
        }



        Map<String,List<AUTOFLOOR_BYMWD>> ALLBYMWDlist=new HashMap();
        ALLBYMWDlist.put("bymwdListM",autofloor_bymwdListM);
        ALLBYMWDlist.put("bymwdListW",autofloor_bymwdListW);
        ALLBYMWDlist.put("bymwdListD",autofloor_bymwdListD);

        return ALLBYMWDlist;
    }
}
