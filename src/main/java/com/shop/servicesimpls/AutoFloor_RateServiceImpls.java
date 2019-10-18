package com.shop.servicesimpls;

import com.shop.model.AutoFloor_Rate;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2019-09-04 17:21
 */
public interface AutoFloor_RateServiceImpls {
    int insertSelective(AutoFloor_Rate record);
    /*良率(相乘)  10:30:00-20:29:59 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_Yield(@Param("floor_name") String floor_name,@Param("product_name")String product_name,@Param("line_name") String line_name);
    /*良率(相乘) 8:30:00-10:30:00 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_Yield08aciton (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*良率(相乘) 20:30:00-22:30:00 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_Yield20aciton (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*良率(相乘) 白班使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_YieldDay (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*良率(相乘) 夜班使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_YieldNight (@Param("start_Time")Date start_Time,@Param("end_Time")Date end_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*良率(相乘) 昨日 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_YieldYesterday (@Param("start_Time")Date start_Time,@Param("end_Time")Date end_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);

    /*误测率(相加) 10:30:00-20:29:00 使用此sql*/
    List<AutoFloor_Rate>AutoFloorRate_Misdetet(@Param("floor_name") String floor_name,@Param("product_name")String product_name,@Param("line_name") String line_name);
    /*误测率(相加) 8:30:00-10:30:00 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_Misdetet08aciton (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*误测率(相加) 20:30:00-22:30:00 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_Misdetet20aciton (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*误测率(相加) 白班 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_MisdetetDay (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*误测率(相加) 夜班 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_MisdetetNight (@Param("start_Time")Date start_Time,@Param("end_Time")Date end_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*误测率(相加) 昨日 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_MisdetetYesterday (@Param("start_Time")Date start_Time,@Param("end_Time")Date end_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /* Unknown Rate （相加）10:30:00-20:29:00 使用此sql*/
    List<AutoFloor_Rate>AutoFloorRate_Unknown(@Param("floor_name") String floor_name,@Param("product_name")String product_name,@Param("line_name") String line_name);
    /* Unknown Rate （相加） 8:30:00-10:30:00 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_Unknown08aciton (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /* Unknown Rate （相加） 20:30:00-22:30:00 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_Unknown20aciton (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /* Unknown Rate （相加） 白班 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_UnknownDay (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /* Unknown Rate （相加） 夜班 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_UnknownNight (@Param("start_Time")Date start_Time,@Param("end_Time")Date end_Time,@Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /* Unknown Rate （相加） 昨日 使用此sql*/
    List<AutoFloor_Rate> AutoFloorRate_UnknownYesterday (@Param("start_Time")Date start_Time,@Param("end_Time")Date end_Time,@Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);

    /*FPYTop5直通率白班 0830使用此sql*/
    List<AutoFloor_Rate> FPYTop5Machine08action (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*FPYTop5直通率白班 2030使用此sql*/
    List<AutoFloor_Rate> FPYTop5Machine20action (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*FPYTop5直通率白班 使用此sql*/
    List<AutoFloor_Rate> FPYTop5Machine (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*FPYTop5直通率最近俩小时 使用此sql*/
    List<AutoFloor_Rate> FPYTop5Machinetwo (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*FPYTop5直通率夜班 使用此sql*/
    List<AutoFloor_Rate> FPYTop5MachineNight (@Param("rate_Time")Date rate_Time,@Param("end_Time")Date end_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*FPYTop5直通率夜班 使用此sql*/
    List<AutoFloor_Rate> FPYTop5MachineYesterday (@Param("rate_Time")Date rate_Time,@Param("end_Time")Date end_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);

    /*FPYFail机台白班 使用此sql*/
    List<AutoFloor_Rate> MidetectFail (@Param("rate_Time")Date rate_Time,@Param("line_name") String line_name ,@Param("machine_no")String machine_no);
    /*FPYFail机台白班0830 使用此sql*/
    List<AutoFloor_Rate> MidetectFail08action (@Param("rate_Time")Date rate_Time, @Param("line_name") String line_name ,@Param("machine_no")String machine_no);
    /*FPYFail机台白班 2030使用此sql*/
    List<AutoFloor_Rate> MidetectFail20action (@Param("rate_Time")Date rate_Time,@Param("line_name") String line_name ,@Param("machine_no")String machine_no);
    /*FPYFailsum机台白班 使用此sql*/
    Integer MidetectFailsum (@Param("rate_Time")Date rate_Time,@Param("line_name") String line_name,@Param("machine_no")String machine_no);
    /*FPYFail最近两小时机台 使用此sql*/
    List<AutoFloor_Rate> MidetectFailtwo (@Param("line_name") String line_name ,@Param("machine_no")String machine_no);
    /*FPYFail夜班机台 使用此sql*/
    List<AutoFloor_Rate> MidetectFailNight (@Param("rate_Time")Date rate_Time,@Param("end_Time")Date end_Time,@Param("line_name") String line_name ,@Param("machine_no")String machine_no);
    /*Fail机台 使用此sql 昨日*/
    List<AutoFloor_Rate> MidetectFailYesterday (@Param("rate_Time")Date rate_Time,@Param("end_Time")Date end_Time,@Param("line_name") String line_name ,@Param("machine_no")String machine_no);
    /*FPYFailsum最近两小时机台 使用此sql*/
    Integer MidetectFailsumtwo (@Param("line_name") String line_name,@Param("machine_no")String machine_no);
    /*FPYFailsum夜班机台 使用此sql*/
    Integer MidetectFailsumNight (@Param("rate_Time")Date rate_Time,@Param("end_Time")Date end_Time, @Param("line_name") String line_name,@Param("machine_no")String machine_no);
    /*FPYFailsum昨日机台 使用此sql*/
    Integer MidetectFailsumYesterday (@Param("rate_Time")Date rate_Time,@Param("end_Time")Date end_Time, @Param("line_name") String line_name,@Param("machine_no")String machine_no);
    /*FPYFailsum0830-1030机台 使用此sql*/
    Integer MidetectFailsum08action (@Param("rate_Time")Date rate_Time,@Param("line_name") String line_name,@Param("machine_no")String machine_no);
    /*FPYFailsum2030-2230机台 使用此sql*/
    Integer MidetectFailsum20action (@Param("rate_Time")Date rate_Time,@Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name,@Param("machine_no")String machine_no);

    /*机台input pass白班 使用此sql*/
    List<AutoFloor_Rate> MidetectAutoDay (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name );
    /*机台input pass最近两小时机台 使用此sql*/
    List<AutoFloor_Rate> MidetectAutoTwo (@Param("rate_Time")Date rate_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*机台input pass夜班机台 使用此sql*/
    List<AutoFloor_Rate> MidetectAutoNight (@Param("rate_Time")Date rate_Time,@Param("end_Time")Date end_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*机台input pass夜班机台 使用此sql*/
    List<AutoFloor_Rate> MidetectAutoYesterday (@Param("rate_Time")Date rate_Time,@Param("end_Time")Date end_Time, @Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*机台input pass0830-1030机台 使用此sql*/
    List<AutoFloor_Rate> MidetectAuto08 (@Param("rate_Time")Date rate_Time,@Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*机台input pass2030-2230机台 使用此sql*/
    List<AutoFloor_Rate> MidetectAuto20 (@Param("rate_Time")Date rate_Time,@Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*机台直通率top5 俩小时使用此sql*/
    List<AutoFloor_Rate> MidetectFPYTwo (@Param("rate_Time")Date rate_Time,@Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*机台直通率top5 白班使用此sql*/
    List<AutoFloor_Rate> MidetectFPYDay (@Param("rate_Time")Date rate_Time,@Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*机台直通率top5 夜班使用此sql*/
    List<AutoFloor_Rate> MidetectFPYNight (@Param("start_Time")Date start_Time,@Param("end_Time") Date end_Time,@Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*机台直通率top5 夜班使用此sql*/
    List<AutoFloor_Rate> MidetectFPYYesterday (@Param("start_Time")Date start_Time,@Param("end_Time") Date end_Time,@Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*机台直通率top5 08使用此sql*/
    List<AutoFloor_Rate> MidetectFPY08 (@Param("rate_Time")Date rate_Time,@Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*机台直通率top5 20使用此sql*/
    List<AutoFloor_Rate> MidetectFPY20 (@Param("rate_Time")Date rate_Time,@Param("floor_name") String floor_name, @Param("product_name")String product_name, @Param("line_name") String line_name);
    /*一天的机台实时误测率使用此sql*/
    List<AutoFloor_Rate> AllDayStationMidetect(@Param("rate_Time")Date rate_Time,@Param("floor_name") String floor_name,@Param("end_Time")Date end_Time );
    /*一周的机台实时误测率使用此sql*/
    List<AutoFloor_Rate> AllWeekStationMidetect(@Param("floor_name") String floor_name);
    /*一月的机台实时误测率使用此sql*/
    List<AutoFloor_Rate> AllMonthStationMidetect(@Param("floor_name") String floor_name);
    /*所有机台使用此sql*/
    List<AutoFloor_Rate> AllStation(@Param("floor_name") String floor_name,@Param("line_name") String line_name);
    /*设备健康机台Top5sql*/
    List<AutoFloor_Rate> AllStationTop5(@Param("floor_name") String floor_name,@Param("line_name") String line_name,@Param("product_name") String product_name);
}
