package com.shop.servicesimpls;

import com.shop.model.AutoFloor_Slant;
import com.shop.model.AutoFloor_Wait_Time;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2019-09-25 17:55
 */
public interface AutoFloor_RoBotServiceImpls {

    // 偏位次數 偏位率 待板率 最近倆小時
    AutoFloor_Wait_Time RobotAnalyze(@Param("floor_name")String floor_name,@Param("product_name")String product_name,@Param("line_name")String line_name);
    // 偏位次數 偏位率 待板率 白班
    AutoFloor_Wait_Time RobotAnalyzeDay(@Param("rate_Time") Date rate_Time,@Param("floor_name")String floor_name, @Param("product_name")String product_name, @Param("line_name")String line_name);
    // 偏位次數 偏位率 待板率 夜班
    AutoFloor_Wait_Time RobotAnalyzeNight(@Param("start_Time")Date start_Time,@Param("end_Time")Date end_Time,@Param("floor_name")String floor_name,@Param("product_name")String product_name,@Param("line_name")String line_name);
    // 偏位次數 偏位率 待板率 昨日
    AutoFloor_Wait_Time RobotAnalyzeYesterday(@Param("start_Time")Date start_Time,@Param("end_Time")Date end_Time,@Param("floor_name")String floor_name,@Param("product_name")String product_name,@Param("line_name")String line_name);
    // 偏位次數 偏位率 待板率 08
    AutoFloor_Wait_Time RobotAnalyze08(@Param("rate_Time")Date rate_Time,@Param("floor_name")String floor_name,@Param("product_name")String product_name,@Param("line_name")String line_name);
    // 偏位次數 偏位率 待板率 20
    AutoFloor_Wait_Time RobotAnalyze20(@Param("rate_Time")Date rate_Time,@Param("floor_name")String floor_name,@Param("product_name")String product_name,@Param("line_name")String line_name);

    //  偏位機故率高TOP  5 Cell 分析
    List<AutoFloor_Wait_Time> RobotAnalyzeTOP5(@Param("floor_name")String floor_name);
    //  偏位機故率高TOP  5 白班Cell 分析
    List<AutoFloor_Wait_Time> RobotAnalyzeTOP5Day(@Param("rate_Time") Date rate_Time,@Param("floor_name")String floor_name);
    //  偏位機故率高TOP  5 夜班 分析
    List<AutoFloor_Wait_Time> RobotAnalyzeTOP5Night(@Param("start_Time")Date start_Time,@Param("end_Time")Date end_Time,@Param("floor_name")String floor_name);
    //  偏位機故率高TOP  5 夜班 分析
    List<AutoFloor_Wait_Time> RobotAnalyzeTOP5Yesterday(@Param("start_Time")Date start_Time,@Param("end_Time")Date end_Time,@Param("floor_name")String floor_name);
    //  偏位機故率高TOP  5 08Cell 分析
    List<AutoFloor_Wait_Time> RobotAnalyzeTOP508(@Param("rate_Time") Date rate_Time,@Param("floor_name")String floor_name);
    //  偏位機故率高TOP  5 20Cell 分析
    List<AutoFloor_Wait_Time> RobotAnalyzeTOP520(@Param("rate_Time") Date rate_Time,@Param("floor_name")String floor_name);


    //  偏位機故率高TOP  5 Cell 位置
    List<AutoFloor_Wait_Time> RobotAnalyzeTOP5Site (@Param("floor_name")String floor_name);
    //  偏位機故率高TOP  5 白班位置說明
    List<AutoFloor_Wait_Time> RobotAnalyzeTOP5SiteDay (@Param("rate_Time") Date rate_Time,@Param("floor_name")String floor_name);
    //  偏位機故率高TOP  5 夜班位置說明
    List<AutoFloor_Wait_Time> RobotAnalyzeTOP5SiteNight (@Param("start_Time")Date start_Time,@Param("end_Time")Date end_Time,@Param("floor_name")String floor_name);
    //  偏位機故率高TOP  5 夜班位置說明
    List<AutoFloor_Wait_Time> RobotAnalyzeTOP5SiteYesterday (@Param("start_Time")Date start_Time,@Param("end_Time")Date end_Time,@Param("floor_name")String floor_name);
    //  偏位機故率高TOP  5 08位置說明
    List<AutoFloor_Wait_Time> RobotAnalyzeTOP5Site08 (@Param("rate_Time") Date rate_Time,@Param("floor_name")String floor_name);
    //  偏位機故率高TOP  5 20位置說明
    List<AutoFloor_Wait_Time> RobotAnalyzeTOP5Site20 (@Param("rate_Time") Date rate_Time,@Param("floor_name")String floor_name);


}
