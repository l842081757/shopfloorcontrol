package com.shop.dao;

import com.shop.model.AUTOFLOOR_HEALTH_MACHINE;
import com.shop.model.AutoFloor_Rate;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AUTOFLOOR_HEALTH_MACHINEMapper {

    //天柱狀圖生產機台
    List<AUTOFLOOR_HEALTH_MACHINE> SelectDayStationMidetect(@Param("floor_name") String floor_name,@Param("start_date")String start_date,@Param("end_date") String end_date);
    //周柱狀圖生產機台
    List<AUTOFLOOR_HEALTH_MACHINE> SelectWeekStationMidetect(@Param("floor_name") String floor_name,@Param("start_date")String start_date,@Param("end_date") String end_date);
    //月柱狀圖生產機台
    List<AUTOFLOOR_HEALTH_MACHINE> SelectMonthStationMidetect(@Param("floor_name") String floor_name,@Param("start_date")String start_date,@Param("end_date") String end_date);

}