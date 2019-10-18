package com.shop.services;

import com.shop.dao.AUTOFLOOR_TARGETMapper;
import com.shop.model.AUTOFLOOR_TARGET;
import com.shop.model.AutoFloor_Rate;
import com.shop.services.Impls.AUTOFLOOR_TARGETServiceImpls;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2019-08-17 14:25
 */

@MapperScan("com.shop.dao")
@Service
public class AUTOFLOOR_TARGEService implements AUTOFLOOR_TARGETServiceImpls {

    @Autowired
    AUTOFLOOR_TARGETMapper  autofloorTargetMapper;

    @Override
    public int deleteByPrimaryKey(BigDecimal id) {
        return autofloorTargetMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(AUTOFLOOR_TARGET record) {
        return autofloorTargetMapper.insert(record);
    }

    @Override
    public int insertSelective(AUTOFLOOR_TARGET record) {
        return autofloorTargetMapper.insertSelective(record);
    }

    @Override
    public AUTOFLOOR_TARGET selectByPrimaryKey(BigDecimal id) {
        return autofloorTargetMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(AUTOFLOOR_TARGET record) {
        return autofloorTargetMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(AUTOFLOOR_TARGET record) {
        return autofloorTargetMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<AUTOFLOOR_TARGET> outPutTarGetF(String floorname,String tClass, String linename,Date startdate,String product) {
        return autofloorTargetMapper.outPutTarGetF(floorname,tClass, linename,startdate,product);
    }

    @Override
    public AUTOFLOOR_TARGET LinenameTarGet(String linename,String Class, Date startdate ,String Productname) {
        return autofloorTargetMapper.LinenameTarGet(linename,Class, startdate,Productname);
    }

    @Override
    public AUTOFLOOR_TARGET YesterDayLinenameTarGet(String floorname, String linename, Date startdate, String Productname) {
        return autofloorTargetMapper.YesterDayLinenameTarGet(floorname,linename ,startdate ,Productname);
    }

    @Override
    public List<AUTOFLOOR_TARGET> RobotLinenameTarGet(String tClass, String floorname, String linename, Date startdate) {
        return autofloorTargetMapper.RobotLinenameTarGet(tClass,floorname ,linename, startdate);
    }

    @Override
    public List<AUTOFLOOR_TARGET> RobotLinenameTarGetYesterday(String floorname, String linename, Date startdate,String tClass) {
        return autofloorTargetMapper.RobotLinenameTarGetYesterday(floorname,linename ,startdate,tClass);
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_cunchu() {
        return autofloorTargetMapper.AutoFloorRate_cunchu();
    }

    @Override
    public Integer  actualOut(String floorname,String linename,Date startdate,String product) {
        return autofloorTargetMapper.actualOut(floorname,linename,startdate,product);
    }

    @Override
    public Integer actualOutYesterDay(String floorname, String linename, Date startdate, Date endstartdate, String product) {
        return autofloorTargetMapper.actualOutYesterDay(floorname,linename,startdate,endstartdate,product);
    }


    @Override
    public Integer HouractualOut(String floorname,String linename, Date startdate, Date enddate, String product) {
        return autofloorTargetMapper.HouractualOut(floorname,linename, startdate, enddate, product);
    }



    @Override
    public Integer NightactualOut(String floorname,String linename, Date startdate, Date endstartdate,String product) {
        return autofloorTargetMapper.NightactualOut(floorname,linename, startdate, endstartdate,product);
    }

    @Override
    public List<AUTOFLOOR_TARGET> LinenameGet(String floorname, String linename, Date startdate, String tClass, String Productname) {
        return autofloorTargetMapper.LinenameGet(floorname,linename,startdate,tClass,Productname);
    }


}
