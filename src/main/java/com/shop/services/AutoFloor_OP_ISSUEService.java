package com.shop.services;

import com.github.pagehelper.Page;
import com.shop.dao.AUTOFLOOR_OP_ISSUEMapper;
import com.shop.model.AUTOFLOOR_OP_ISSUE;
import com.shop.model.PageInfo;
import com.shop.services.Impls.AutoFloor_OP_ISSUEServiceImpls;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2019-10-14 10:21
 */
@MapperScan("com.shop.dao")
@Service
public class AutoFloor_OP_ISSUEService implements AutoFloor_OP_ISSUEServiceImpls {

    @Autowired
    AUTOFLOOR_OP_ISSUEMapper autofloor_op_issueMapper;


    @Override
    public AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPE2(Date start_date, Date end_date,String line_name) {
        return autofloor_op_issueMapper.OPISSUE_ERROR_TYPE2(start_date,end_date,line_name);
    }

    @Override
    public AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPE2OneDay(String start_date, String end_date, String line_name) {
        return autofloor_op_issueMapper.OPISSUE_ERROR_TYPE2OneDay(start_date, end_date,line_name);
    }

    @Override
    public AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPE3_8(Date start_date, Date end_date,String line_name) {
        return autofloor_op_issueMapper.OPISSUE_ERROR_TYPE3_8(start_date,end_date,line_name);
    }

    @Override
    public AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPE3_8OneDay(String start_date, String end_date, String line_name) {
        return autofloor_op_issueMapper.OPISSUE_ERROR_TYPE3_8OneDay(start_date,end_date,line_name);
    }

    @Override
    public List<AUTOFLOOR_OP_ISSUE> OPISSUE_detail(Date start_date, Date end_date, String line_name) {
        return autofloor_op_issueMapper.OPISSUE_detail(start_date, end_date,line_name);
    }

    @Override
    public Page<PageInfo> OPISSUE_detailPage(Date start_date, Date end_date, String line_name) {
        return autofloor_op_issueMapper.OPISSUE_detailPage(start_date,end_date,line_name);
    }

    @Override
    public AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPEOneDay(String start_date, String end_date, String floor_name) {
        return autofloor_op_issueMapper.OPISSUE_ERROR_TYPEOneDay(start_date,end_date,floor_name);
    }

    @Override
    public AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPE7Day(String floor_name) {
        return autofloor_op_issueMapper.OPISSUE_ERROR_TYPE7Day(floor_name);
    }

    @Override
    public AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPE30Day(String floor_name) {
        return autofloor_op_issueMapper.OPISSUE_ERROR_TYPE30Day(floor_name);
    }
}
