package com.shop.dao;

import com.shop.model.AUTOFLOOR_BYMWD;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface AUTOFLOOR_BYMWDMapper {

  //日期查詢ID
  Integer  SelectBYMWDByID (@Param("T_MARK") String T_MARK);
  //Id查詢所有月數據
  List<AUTOFLOOR_BYMWD> SelectBYMWDMList (@Param("T_Id") Integer T_Id);
  //Id查詢周日數據
  List<AUTOFLOOR_BYMWD> SelectBYMWDWList (@Param("W_Id") Integer W_Id,@Param("D_Id") Integer D_Id);
}