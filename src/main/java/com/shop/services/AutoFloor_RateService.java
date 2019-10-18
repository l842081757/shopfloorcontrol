package com.shop.services;

import com.shop.dao.AutoFloor_RateMapper;
import com.shop.model.AutoFloor_Rate;
import com.shop.servicesimpls.AutoFloor_RateServiceImpls;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2019-09-04 17:21
 */
@MapperScan("com.shop.dao")
@Service
public class AutoFloor_RateService implements AutoFloor_RateServiceImpls {

    @Autowired
    AutoFloor_RateMapper autoFloor_rateMapper;

    @Override
    public int insertSelective(AutoFloor_Rate record) {
        return 0;
    }

    /*良率(相乘)  10:30:00-20:29:59 使用此sql*/
    @Override
    public List<AutoFloor_Rate> AutoFloorRate_Yield(String floor_name, String product_name, String line_name) {
        return  autoFloor_rateMapper.AutoFloorRate_Yield(floor_name, product_name, line_name);
    }

    /*良率(相乘) 8:30:00-10:30:00 使用此sql*/
    @Override
    public List<AutoFloor_Rate> AutoFloorRate_Yield08aciton(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_Yield08aciton(rate_Time,floor_name,product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_Yield20aciton(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_Yield20aciton(rate_Time, floor_name, product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_YieldDay(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_YieldDay(rate_Time,floor_name,product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_YieldNight(Date start_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_YieldNight(start_Time,end_Time,floor_name,product_name,line_name );
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_YieldYesterday(Date start_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_YieldYesterday(start_Time, end_Time, floor_name,product_name,line_name);
    }

    /*误测率(相加) 10:30:00-20:29:00 使用此sql*/
    @Override
    public List<AutoFloor_Rate> AutoFloorRate_Misdetet(String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_Misdetet(floor_name,product_name,line_name);
    }
    /*误测率(相加) 8:30:00-10:30:00 使用此sql*/
    @Override
    public List<AutoFloor_Rate> AutoFloorRate_Misdetet08aciton(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_Misdetet08aciton(rate_Time,floor_name,product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_Misdetet20aciton(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_Misdetet20aciton( rate_Time,floor_name, product_name, line_name);
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_MisdetetDay(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_MisdetetDay(rate_Time,floor_name,product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_MisdetetNight(Date start_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_MisdetetNight(start_Time,end_Time,floor_name,product_name,line_name );
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_MisdetetYesterday(Date start_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_MisdetetYesterday(start_Time,end_Time ,floor_name,product_name,line_name);
    }

    /* Unknown Rate （相加）10:30:00-20:29:00 使用此sql*/
    @Override
    public List<AutoFloor_Rate> AutoFloorRate_Unknown(String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_Unknown(floor_name, product_name,line_name);
    }
    /* Unknown Rate （相加） 8:30:00-10:30:00 使用此sql*/
    @Override
    public List<AutoFloor_Rate> AutoFloorRate_Unknown08aciton(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_Unknown08aciton(rate_Time, floor_name, product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_Unknown20aciton(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_Unknown20aciton(rate_Time, floor_name, product_name, line_name);
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_UnknownDay(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_UnknownDay(rate_Time,floor_name,product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_UnknownNight(Date start_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_UnknownNight(start_Time,end_Time,floor_name,product_name,line_name );
    }

    @Override
    public List<AutoFloor_Rate> AutoFloorRate_UnknownYesterday(Date start_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.AutoFloorRate_UnknownYesterday(start_Time,end_Time,floor_name,product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> FPYTop5Machine08action(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.FPYTop5Machine08action(rate_Time,floor_name, product_name, line_name);
    }

    @Override
    public List<AutoFloor_Rate> FPYTop5Machine20action(Date rate_Time, String floor_name, String product_name, String line_name) {
        return FPYTop5Machine20action(rate_Time, floor_name, product_name, line_name);
    }

    @Override
    public List<AutoFloor_Rate> FPYTop5Machine(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.FPYTop5Machine(rate_Time, floor_name, product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> FPYTop5Machinetwo(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.FPYTop5Machinetwo(rate_Time, floor_name, product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> FPYTop5MachineNight(Date rate_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.FPYTop5MachineNight(rate_Time, end_Time, floor_name,product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> FPYTop5MachineYesterday(Date rate_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.FPYTop5MachineYesterday(rate_Time,end_Time ,floor_name,product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> MidetectFail(Date rate_Time,String line_name,String machine_no) {
        return autoFloor_rateMapper.MidetectFail(rate_Time,line_name,machine_no);
    }

    @Override
    public List<AutoFloor_Rate> MidetectFail08action(Date rate_Time,String line_name, String machine_no) {
        return autoFloor_rateMapper.MidetectFail08action(rate_Time,line_name, machine_no);
    }

    @Override
    public List<AutoFloor_Rate> MidetectFail20action(Date rate_Time,  String line_name, String machine_no) {
        return autoFloor_rateMapper.MidetectFail20action( rate_Time,line_name, machine_no);
    }

    @Override
    public Integer MidetectFailsum(Date rate_Time,String line_name,String machine_no) {
        return autoFloor_rateMapper.MidetectFailsum(rate_Time,  line_name,machine_no);
    }

    @Override
    public List<AutoFloor_Rate> MidetectFailtwo(String line_name, String machine_no) {
        return autoFloor_rateMapper.MidetectFailtwo(line_name, machine_no);
    }

    @Override
    public List<AutoFloor_Rate> MidetectFailNight(Date rate_Time, Date end_Time,String line_name, String machine_no) {
        return autoFloor_rateMapper.MidetectFailNight(rate_Time,end_Time,line_name,machine_no);
    }

    @Override
    public List<AutoFloor_Rate> MidetectFailYesterday(Date rate_Time, Date end_Time, String line_name, String machine_no) {
        return autoFloor_rateMapper.MidetectFailYesterday(rate_Time,end_Time,line_name,machine_no);
    }

    @Override
    public Integer MidetectFailsumtwo(String line_name, String machine_no) {
        return autoFloor_rateMapper.MidetectFailsumtwo(line_name, machine_no);
    }

    @Override
    public Integer MidetectFailsumNight(Date rate_Time, Date end_Time,String line_name, String machine_no) {
        return autoFloor_rateMapper.MidetectFailsumNight(rate_Time,end_Time,line_name,machine_no);
    }

    @Override
    public Integer MidetectFailsumYesterday(Date rate_Time, Date end_Time, String line_name, String machine_no) {
        return autoFloor_rateMapper.MidetectFailsumYesterday(rate_Time,end_Time,line_name,machine_no);
    }

    @Override
    public Integer MidetectFailsum08action(Date rate_Time,String line_name, String machine_no) {
        return autoFloor_rateMapper.MidetectFailsum08action(rate_Time,line_name, machine_no);
    }

    @Override
    public Integer MidetectFailsum20action(Date rate_Time, String floor_name, String product_name, String line_name, String machine_no) {
        return autoFloor_rateMapper.MidetectFailsum20action(rate_Time, floor_name, product_name, line_name, machine_no);
    }

    @Override
    public  List<AutoFloor_Rate> MidetectAutoDay(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.MidetectAutoDay(rate_Time, floor_name, product_name, line_name);
    }

    @Override
    public  List<AutoFloor_Rate> MidetectAutoTwo(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.MidetectAutoTwo(rate_Time, floor_name, product_name,line_name );
    }

    @Override
    public  List<AutoFloor_Rate> MidetectAutoNight(Date rate_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.MidetectAutoNight(rate_Time, end_Time, floor_name, product_name, line_name);
    }

    @Override
    public List<AutoFloor_Rate> MidetectAutoYesterday(Date rate_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper. MidetectAutoYesterday(rate_Time,end_Time ,floor_name ,product_name ,line_name);
    }

    @Override
    public  List<AutoFloor_Rate> MidetectAuto08(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.MidetectAuto08(rate_Time, floor_name, product_name, line_name);
    }

    @Override
    public  List<AutoFloor_Rate> MidetectAuto20(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.MidetectAuto20(rate_Time, floor_name, product_name, line_name);
    }

    @Override
    public List<AutoFloor_Rate> MidetectFPYTwo(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.MidetectFPYTwo(rate_Time, floor_name, product_name, line_name);
    }

    @Override
    public List<AutoFloor_Rate> MidetectFPYDay(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.MidetectFPYDay(rate_Time, floor_name, product_name, line_name);
    }

    @Override
    public List<AutoFloor_Rate> MidetectFPYNight(Date start_Time, Date end_Time,String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.MidetectFPYNight(start_Time,end_Time, floor_name, product_name, line_name);
    }

    @Override
    public List<AutoFloor_Rate> MidetectFPYYesterday(Date start_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.MidetectFPYYesterday(start_Time, end_Time, floor_name, product_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> MidetectFPY08(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.MidetectFPY08(rate_Time, floor_name, product_name, line_name);
    }

    @Override
    public List<AutoFloor_Rate> MidetectFPY20(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_rateMapper.MidetectFPY20(rate_Time, floor_name, product_name, line_name);
    }

    @Override
    public List<AutoFloor_Rate> AllDayStationMidetect(Date rate_Time, String floor_name ,Date end_Time) {
        return autoFloor_rateMapper.AllDayStationMidetect(rate_Time,floor_name,end_Time);
    }

    @Override
    public List<AutoFloor_Rate> AllWeekStationMidetect(String floor_name) {
        return autoFloor_rateMapper.AllWeekStationMidetect(floor_name);
    }

    @Override
    public List<AutoFloor_Rate> AllMonthStationMidetect(String floor_name) {
        return autoFloor_rateMapper.AllMonthStationMidetect(floor_name);
    }

    @Override
    public List<AutoFloor_Rate> AllStation(String floor_name, String line_name) {
        return autoFloor_rateMapper.AllStation(floor_name,line_name);
    }

    @Override
    public List<AutoFloor_Rate> AllStationTop5(String floor_name, String line_name,String product_name) {
        return autoFloor_rateMapper.AllStationTop5(floor_name,line_name,product_name);
    }

}
