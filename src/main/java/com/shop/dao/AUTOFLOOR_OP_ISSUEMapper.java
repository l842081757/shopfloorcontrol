package com.shop.dao;


import com.github.pagehelper.Page;
import com.shop.model.AUTOFLOOR_OP_ISSUE;
import com.shop.model.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AUTOFLOOR_OP_ISSUEMapper {

//投首漏扫描 使用此sql
AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPE2(@Param("start_date")Date start_date,@Param("end_date")Date end_date,@Param("line_name") String line_name);
//投首漏扫描 使用此sql
AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPE2OneDay(@Param("start_date")String start_date,@Param("end_date")String end_date,@Param("line_name") String line_name);
//漏测.混测.确认模式异常.工号无权限.機台號错误.SN扫描错误  使用此sql
AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPE3_8(@Param("start_date")Date start_date,@Param("end_date")Date end_date,@Param("line_name") String line_name);
//漏测.混测.确认模式异常.工号无权限.機台號错误.SN扫描错误  使用此sql
AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPE3_8OneDay(@Param("start_date")String start_date,@Param("end_date")String end_date,@Param("line_name") String line_name);

//OPISSUE详细  使用此sql
List<AUTOFLOOR_OP_ISSUE> OPISSUE_detail(@Param("start_date")Date start_date, @Param("end_date")Date end_date, @Param("line_name") String line_name);

//OPISSUEPage详细  使用此sql
Page<PageInfo> OPISSUE_detailPage(@Param("start_date")Date start_date, @Param("end_date")Date end_date, @Param("line_name") String line_name);

AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPEOneDay(@Param("start_date")Date start_date,@Param("end_date")Date end_date,@Param("floor_name") String floor_name);

AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPE7Day(@Param("floor_name") String floor_name);

AUTOFLOOR_OP_ISSUE OPISSUE_ERROR_TYPE30Day(@Param("floor_name") String floor_name);




}