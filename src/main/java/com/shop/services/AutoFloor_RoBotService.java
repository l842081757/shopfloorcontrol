package com.shop.services;

import com.shop.dao.AutoFloor_Wait_TimeMapper;
import com.shop.model.AutoFloor_Wait_Time;
import com.shop.services.Impls.AutoFloor_RoBotServiceImpls;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2019-09-25 17:55
 */
@MapperScan("com.shop.dao")
@Service
public class AutoFloor_RoBotService implements AutoFloor_RoBotServiceImpls {
    @Autowired
    AutoFloor_Wait_TimeMapper autoFloor_wait_timeMapper;

    @Override
    public AutoFloor_Wait_Time RobotAnalyze(String floor_name, String product_name, String line_name) {
        return autoFloor_wait_timeMapper.RobotAnalyze(floor_name, product_name, line_name);
    }

    @Override
    public AutoFloor_Wait_Time RobotAnalyzeDay(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeDay(rate_Time, floor_name, product_name, line_name);
    }

    @Override
    public AutoFloor_Wait_Time RobotAnalyzeNight(Date start_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeNight(start_Time, end_Time, floor_name, product_name,line_name );
    }

    @Override
    public AutoFloor_Wait_Time RobotAnalyzeYesterday(Date start_Time, Date end_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeYesterday(start_Time,end_Time ,floor_name ,product_name,line_name);
    }

    @Override
    public AutoFloor_Wait_Time RobotAnalyze08(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_wait_timeMapper.RobotAnalyze08(rate_Time, floor_name, product_name, line_name);
    }

    @Override
    public AutoFloor_Wait_Time RobotAnalyze20(Date rate_Time, String floor_name, String product_name, String line_name) {
        return autoFloor_wait_timeMapper.RobotAnalyze20(rate_Time, floor_name, product_name, line_name);
    }

    @Override
    public List<AutoFloor_Wait_Time> RobotAnalyzeTOP5(String floor_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeTOP5(floor_name);
    }

    @Override
    public List<AutoFloor_Wait_Time> RobotAnalyzeTOP5Day(Date rate_Time, String floor_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeTOP5Day( rate_Time,floor_name);
    }

    @Override
    public List<AutoFloor_Wait_Time> RobotAnalyzeTOP5Night(Date start_Time, Date end_Time, String floor_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeTOP5Night(start_Time, end_Time, floor_name);
    }

    @Override
    public List<AutoFloor_Wait_Time> RobotAnalyzeTOP5Yesterday(Date start_Time, Date end_Time, String floor_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeTOP5Yesterday(start_Time,end_Time , floor_name);
    }

    @Override
    public List<AutoFloor_Wait_Time> RobotAnalyzeTOP508(Date rate_Time, String floor_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeTOP508(rate_Time, floor_name);
    }

    @Override
    public List<AutoFloor_Wait_Time> RobotAnalyzeTOP520(Date rate_Time, String floor_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeTOP520(rate_Time,floor_name );
    }

    @Override
    public List<AutoFloor_Wait_Time> RobotAnalyzeTOP5Site(String floor_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeTOP5Site(floor_name);
    }

    @Override
    public List<AutoFloor_Wait_Time> RobotAnalyzeTOP5SiteDay(Date rate_Time, String floor_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeTOP5SiteDay(rate_Time, floor_name);
    }

    @Override
    public List<AutoFloor_Wait_Time> RobotAnalyzeTOP5SiteNight(Date start_Time, Date end_Time, String floor_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeTOP5SiteNight(start_Time, end_Time, floor_name);
    }

    @Override
    public List<AutoFloor_Wait_Time> RobotAnalyzeTOP5SiteYesterday(Date start_Time, Date end_Time, String floor_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeTOP5SiteYesterday(start_Time,end_Time,floor_name);
    }

    @Override
    public List<AutoFloor_Wait_Time> RobotAnalyzeTOP5Site08(Date rate_Time, String floor_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeTOP5Site08(rate_Time,floor_name);
    }

    @Override
    public List<AutoFloor_Wait_Time> RobotAnalyzeTOP5Site20(Date rate_Time, String floor_name) {
        return autoFloor_wait_timeMapper.RobotAnalyzeTOP5Site20(rate_Time,floor_name);
    }
}
