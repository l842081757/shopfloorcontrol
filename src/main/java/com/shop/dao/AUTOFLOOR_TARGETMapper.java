package com.shop.dao;

import com.shop.model.AUTOFLOOR_TARGET;
import com.shop.model.AutoFloor_Rate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/*@SuppressWarnings("ALL")*/
@Mapper
public interface AUTOFLOOR_TARGETMapper {

    int deleteByPrimaryKey(BigDecimal id);

    int insert(AUTOFLOOR_TARGET record);

    int insertSelective(AUTOFLOOR_TARGET record);

    AUTOFLOOR_TARGET selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(AUTOFLOOR_TARGET record);

    int updateByPrimaryKey(AUTOFLOOR_TARGET record);

    /*线体目标*/
    List<AUTOFLOOR_TARGET> outPutTarGetF(@Param("floorname")String floorname,@Param("tClass") String tClass, @Param("linename") String linename, @Param("startdate") Date startdate,@Param("product") String product);
    /* 條線的名称获取所有目标  D073F  Lotus  （變量：時間）*/
    AUTOFLOOR_TARGET LinenameTarGet(@Param("linename") String linename,@Param("tClass") String tClass, @Param("startdate") Date startdate, @Param("Productname")String Productname);

    /*  昨天條線的名称获取所有目标  D073F  Lotus  （變量：時間）*/
    AUTOFLOOR_TARGET YesterDayLinenameTarGet(@Param("floorname")String floorname,@Param("linename") String linename,@Param("startdate") Date startdate, @Param("Productname")String Productname);

    /*   <!-- RobotARF條線的名称获取所有目标  D073F  Lotus  （變量：時間）-->）*/
    List<AUTOFLOOR_TARGET> RobotLinenameTarGet(@Param("tClass") String tClass, @Param("floorname")String floorname,@Param("linename") String linename,@Param("startdate") Date startdate);
    /*  昨日 RobotARF條線的名称获取所有目标  D073F  Lotus  （變量：時間）-->）*/
    List<AUTOFLOOR_TARGET> RobotLinenameTarGetYesterday(@Param("floorname")String floorname,@Param("linename") String linename,@Param("startdate") Date startdate,@Param("tClass") String tClass);
    /*  按楼层机种获取條線的名称获取所有目标  */
    List<AUTOFLOOR_TARGET> LinenameGet(@Param("floorname")String floorname,@Param("linename") String linename,@Param("startdate") Date startdate,@Param("tClass") String tClass,@Param("Productname") String Productname);
    /*存储过程*/
    List<AutoFloor_Rate> AutoFloorRate_cunchu() ;

    /*day线体产能*/
    Integer  actualOut(@Param("floorname")String floorname,@Param("linename") String linename , @Param("startdate") Date startdate,@Param("product") String product);

    /*Lot YesterDay每條線的產能*/
    Integer  actualOutYesterDay(@Param("floorname")String floorname,@Param("linename") String linename ,  @Param("startdate") Date startdate,@Param("endstartdate") Date endstartdate,@Param("product") String product);


    /*小时day线体产能*/
    Integer  HouractualOut(@Param("floorname")String floorname,@Param("linename") String linename , @Param("startdate") Date startdate, @Param("enddate") Date enddate,@Param("product") String product);


    /*Night线体产能*/
    Integer  NightactualOut(@Param("floorname")String floorname,@Param("linename") String linename , @Param("startdate") Date startdate,@Param("endstartdate") Date endstartdate,@Param("product") String product);



}