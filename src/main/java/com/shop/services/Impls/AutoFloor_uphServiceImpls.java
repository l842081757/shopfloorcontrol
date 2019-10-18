package com.shop.services.Impls;

import com.shop.model.AmountsUPH;
import com.shop.model.AutoFloorUphS;
import com.shop.model.STATION_UPH;
import com.shop.model.WorkstationOut_Wip;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @create 2019-08-19 15:56
 */
public interface AutoFloor_uphServiceImpls {
    //線體，每個工站的小時產能 （變量：樓層，機種，線體）
    List<AutoFloorUphS> WorkstationOut(@Param("linename") String linename,@Param("startdate") Date startdate ,@Param("floor_name") String floor_name, @Param("product_name") String product_name);
    //TTL，每個工站的小時產能 （變量：樓層，機種，線體
    List<AutoFloorUphS> TTLWorkstationOut(@Param("startdate") Date startdate ,@Param("floor_name") String floor_name, @Param("product_name") String product_name);

    //線體，每個工站的小時產能 （變量：樓層，機種，線體）
    List<AutoFloorUphS> WorkstationOutNight(@Param("linename") String linename,@Param("startdate") Date startdate ,@Param("enddate") Date enddate ,@Param("floor_name") String floor_name, @Param("product_name") String product_name);
    //某個線體，每個工站的小時產能 （變量：樓層，機種，線體）昨日
    List<AutoFloorUphS> WorkstationOutYesterDay(@Param("linename") String linename,@Param("startdate") Date startdate ,@Param("enddate") Date enddate ,@Param("floor_name") String floor_name, @Param("product_name") String product_name);


    //TTL，每個工站的小時產能 （變量：樓層，機種，線體）夜班
    List<AutoFloorUphS> TTLWorkstationOutNight(@Param("startdate") Date startdate ,@Param("enddate") Date enddate ,@Param("floor_name") String floor_name, @Param("product_name") String product_name);
//Wip個線體，每個工站的小時產能 （變量：樓層，機種，線體）！暂不使用
    //List<WorkstationOut_Wip> WorkstationOut_wip(@Param("startdate")String startdate,@Param("enddate")Date enddate,@Param("linename") String linename);

//列表版 個線體，每個工站的小時產能 （變量：樓層，機種，線體）！暂不使用
    //List<STATION_UPH> WorkstationOuttext(@Param("linename") String linename,@Param("startdate") Date startdata);

    //某個線體的工站的機台數量( 變量：樓層，機種，線體，工站)
    Integer Workstationamount(@Param("Floorname")String Floorname,@Param("linename") String linename,@Param("statio_name") String statio_name,@Param("startdate") Date startdate,@Param("Productname")String Productname);
    //统计现机台工作数量
    Integer MachinaeActionSum(@Param("floor")String floor,@Param("statio_name") String statio_name,@Param("startdate") Date startdate,@Param("Productname")String Productname,@Param("linename")String linename);
    //统计夜班现机台工作数量
    Integer MachinaeActionSumNight(@Param("floor")String floor,@Param("statio_name") String statio_name,@Param("startdate") Date startdate,@Param("enddate") Date enddate,@Param("Productname")String Productname,@Param("linename")String linename);
    //统计昨日现机台工作数量
    Integer MachinaeActionSumYesterday(@Param("floor")String floor,@Param("statio_name") String statio_name,@Param("startdate") Date startdate,@Param("enddate") Date enddate,@Param("Productname")String Productname,@Param("linename")String linename);

    //某個時間段內，某個工站 所有幾台的產能  (變量：時間， 樓層，機種，線體，時間段，工站）
    List<AmountsUPH>MachinaeUPH(@Param("statio_name") String statio_name,@Param("startdate") Date startdate ,@Param("line_name") String line_name,@Param("floor_name") String floor_name, @Param("product_name") String product_name);
    //夜班，某個工站 所有幾台的產能  (變量：時間， 樓層，機種，線體，時間段，工站）
    List<AmountsUPH>MachinaeUPHNight( @Param("statio_name") String statio_name,@Param("startdate") Date startdate ,@Param("enddate") Date enddate ,@Param("line_name") String line_name,@Param("floor_name") String floor_name, @Param("product_name") String product_name);
    //昨日，某個工站 所有幾台的產能  (變量：時間， 樓層，機種，線體，時間段，工站）
    List<AmountsUPH>MachinaeUPHYesterday( @Param("statio_name") String statio_name,@Param("startdate") Date startdate ,@Param("enddate") Date enddate ,@Param("line_name") String line_name,@Param("floor_name") String floor_name, @Param("product_name") String product_name);

    //白班 机台top3直通率产能状况
    List<AmountsUPH>MachinaeUPHFPYTop3Day( @Param("statio_name") String statio_name,@Param("startdate") Date startdate ,@Param("line_name") String line_name,@Param("floor_name") String floor_name, @Param("product_name") String product_name);
    //夜班 机台top3直通率产能状况
    List<AmountsUPH>MachinaeUPHFPYTop3Night( @Param("statio_name") String statio_name,@Param("startdate") Date startdate ,@Param("enddate") Date enddate ,@Param("line_name") String line_name,@Param("floor_name") String floor_name, @Param("product_name") String product_name);
    //昨日 机台top3直通率产能状况
    List<AmountsUPH>MachinaeUPHFPYTop3Yesterday( @Param("statio_name") String statio_name,@Param("startdate") Date startdate ,@Param("enddate") Date enddate ,@Param("line_name") String line_name,@Param("floor_name") String floor_name, @Param("product_name") String product_name);

    //統計沒有产出的幾台的詳細 白班内，某個工站  (變量：時間， 樓層，機種，線體，時間段，工站）
    List<AmountsUPH> MachinaeDetailDay( @Param("statio_name") String statio_name,@Param("startdate") Date startdate ,@Param("line_name") String line_name,@Param("floor_name") String floor_name, @Param("product_name") String product_name);
    //統計沒有产出的幾台的詳細 夜班內，某個工站  (變量：時間， 樓層，機種，線體，時間段，工站）
    List<AmountsUPH>MachinaeDetailNight( @Param("statio_name") String statio_name,@Param("startdate") Date startdate ,@Param("enddate") Date enddate ,@Param("line_name") String line_name,@Param("floor_name") String floor_name, @Param("product_name") String product_name);
    //統計沒有产出的幾台的詳細 昨日，某個工站  (變量：時間， 樓層，機種，線體，時間段，工站）
    List<AmountsUPH>MachinaeDetailYesterday( @Param("statio_name") String statio_name,@Param("startdate") Date startdate ,@Param("enddate") Date enddate ,@Param("line_name") String line_name,@Param("floor_name") String floor_name, @Param("product_name") String product_name);

}
