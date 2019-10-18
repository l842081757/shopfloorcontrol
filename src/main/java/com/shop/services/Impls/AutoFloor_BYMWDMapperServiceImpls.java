package com.shop.services.Impls;

import com.github.pagehelper.Page;
import com.shop.model.AUTOFLOOR_BYMWD;
import com.shop.model.AUTOFLOOR_OP_ISSUE;
import com.shop.model.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2019-10-14 10:21
 */
public interface AutoFloor_BYMWDMapperServiceImpls {

    //日期查詢ID
    Map<String,List<AUTOFLOOR_BYMWD>>  SelectBYMWDList (@Param("T_MARK") String T_MARK);
}
