package com.shop.dao;

import com.shop.model.AUTOFLOOR_BYMWD;
import java.math.BigDecimal;

public interface AUTOFLOOR_BYMWDMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(AUTOFLOOR_BYMWD record);

    int insertSelective(AUTOFLOOR_BYMWD record);

    AUTOFLOOR_BYMWD selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(AUTOFLOOR_BYMWD record);

    int updateByPrimaryKey(AUTOFLOOR_BYMWD record);
}