package com.shop.dao;

import com.shop.model.Autofloor_Wip;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Autofloor_WipMapper {
    int insert(Autofloor_Wip record);

    int insertSelective(Autofloor_Wip record);
}