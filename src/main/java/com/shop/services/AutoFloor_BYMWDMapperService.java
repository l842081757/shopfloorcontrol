package com.shop.services;
import com.shop.dao.AUTOFLOOR_BYMWDMapper;
import com.shop.model.AUTOFLOOR_BYMWD;
import com.shop.services.Impls.AutoFloor_BYMWDMapperServiceImpls;
import com.shop.shopfloorcontroller.TaskService;
import com.shop.shopfloorcontroller.TimeUtility;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
    TaskService taskService;

    @Autowired
    TimeUtility timeUtility;

    @Override
    public Map<String,Object> SelectBYMWDList(String FloorName) {
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
        System.out.println(BYMWDId );
        //ID以前所有數據
        List<AUTOFLOOR_BYMWD> autofloor_bymwds = autofloorBymwdMapper.SelectBYMWDList(BYMWDId);
        for (AUTOFLOOR_BYMWD autofloor_bymwd : autofloor_bymwds) {
            System.out.println(autofloor_bymwd.gettMonth());
        }
        Map ALLlist=new HashMap();
        return ALLlist;
    }
}
