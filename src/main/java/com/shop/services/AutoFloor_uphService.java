package com.shop.services;

import com.shop.dao.AutoFloor_uphMapper;
import com.shop.model.AmountsUPH;
import com.shop.model.AutoFloorUphS;
import com.shop.services.Impls.AutoFloor_uphServiceImpls;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2019-08-19 15:56
 */
@MapperScan("com.shop.dao")
@Service
public class AutoFloor_uphService implements AutoFloor_uphServiceImpls {
    @Autowired
    private AutoFloor_uphMapper autoFloor_uphMapper;


    @Override
    public List<AutoFloorUphS> WorkstationOut(String linename, Date startdate , String floor_name, String product_name) {
        return autoFloor_uphMapper.WorkstationOut(linename,startdate,floor_name,product_name);
    }

    @Override
    public List<AutoFloorUphS> TTLWorkstationOut(Date startdate, String floor_name, String product_name) {
        return autoFloor_uphMapper.TTLWorkstationOut(startdate, floor_name, product_name);
    }

    @Override
    public List<AutoFloorUphS> WorkstationOutNight(String linename, Date startdate, Date enddate, String floor_name, String product_name) {
        return autoFloor_uphMapper.WorkstationOutNight(linename, startdate, enddate, floor_name,product_name);
    }

    @Override
    public List<AutoFloorUphS> WorkstationOutYesterDay(String linename, Date startdate, Date enddate, String floor_name, String product_name) {
        return autoFloor_uphMapper.WorkstationOutYesterDay(linename,startdate, enddate, floor_name,product_name);
    }

    @Override
    public List<AutoFloorUphS> TTLWorkstationOutNight(Date startdate, Date enddate, String floor_name, String product_name) {
        return autoFloor_uphMapper.TTLWorkstationOutNight( startdate, enddate, floor_name,product_name);
    }

    @Override
    public Integer Workstationamount(String Floorname,String linename, String statio_name, Date startdate,String Productname) {
        return autoFloor_uphMapper.Workstationamount(Floorname,linename,statio_name,startdate,Productname);
    }

    @Override
    public Integer MachinaeActionSum(String floor, String statio_name, Date startdate, String Productname, String linename) {
        return autoFloor_uphMapper.MachinaeActionSum(floor, statio_name,startdate ,Productname ,linename );
    }

    @Override
    public Integer MachinaeActionSumNight(String floor, String statio_name, Date startdate, Date endate, String Productname, String linename) {
        return autoFloor_uphMapper.MachinaeActionSumNight(floor, statio_name, startdate, endate, Productname, linename);
    }

    @Override
    public Integer MachinaeActionSumYesterday(String floor, String statio_name, Date startdate, Date enddate, String Productname, String linename) {
        return autoFloor_uphMapper.MachinaeActionSumYesterday(floor,statio_name, startdate, enddate, Productname,linename );
    }


    public List<AmountsUPH> MachinaeUPH( String statio_name, Date startdate,String line_name, String floor_name, String product_name) {

        return autoFloor_uphMapper.MachinaeUPH(statio_name,startdate,line_name, floor_name,product_name);
    }

    public List<AmountsUPH> MachinaeUPHNight( String statio_name, Date startdate, Date enddate, String line_name, String floor_name, String product_name) {
        return autoFloor_uphMapper.MachinaeUPHNight(statio_name,startdate,enddate,line_name,floor_name,product_name);
    }

    @Override
    public List<AmountsUPH> MachinaeUPHYesterday(String statio_name, Date startdate, Date enddate, String line_name, String floor_name, String product_name) {
        return autoFloor_uphMapper.MachinaeUPHYesterday(statio_name, startdate, enddate, line_name, floor_name,product_name);
    }

    @Override
    public List<AmountsUPH> MachinaeUPHFPYTop3Day(String statio_name, Date startdate, String line_name, String floor_name, String product_name) {
        return autoFloor_uphMapper.MachinaeUPHFPYTop3Day(statio_name,startdate,line_name,floor_name,product_name);
    }

    @Override
    public List<AmountsUPH> MachinaeUPHFPYTop3Night(String statio_name, Date startdate, Date enddate, String line_name, String floor_name, String product_name) {
        return autoFloor_uphMapper.MachinaeUPHFPYTop3Night(statio_name, startdate, enddate, line_name, floor_name, product_name);
    }

    @Override
    public List<AmountsUPH> MachinaeUPHFPYTop3Yesterday(String statio_name, Date startdate, Date enddate, String line_name, String floor_name, String product_name) {
        return autoFloor_uphMapper.MachinaeUPHFPYTop3Yesterday(statio_name,startdate, enddate,line_name, floor_name, product_name);
    }

    @Override
    public List<AmountsUPH> MachinaeDetailDay(String statio_name, Date startdate, String line_name, String floor_name, String product_name) {
        return autoFloor_uphMapper.MachinaeDetailDay( statio_name, startdate, line_name, floor_name,  product_name);
    }

    @Override
    public List<AmountsUPH> MachinaeDetailNight(String statio_name, Date startdate, Date enddate, String line_name, String floor_name, String product_name) {
        return autoFloor_uphMapper.MachinaeDetailNight(statio_name, startdate, enddate, line_name, floor_name,product_name);
    }

    @Override
    public List<AmountsUPH> MachinaeDetailYesterday(String statio_name, Date startdate, Date enddate, String line_name, String floor_name, String product_name) {
        return autoFloor_uphMapper.MachinaeDetailYesterday(statio_name,startdate ,enddate ,line_name ,floor_name ,product_name );
    }


}
