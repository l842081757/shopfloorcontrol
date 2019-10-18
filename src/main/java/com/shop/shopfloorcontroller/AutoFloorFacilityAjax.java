package com.shop.shopfloorcontroller;
import com.alibaba.fastjson.JSON;
import com.shop.model.AutoFloor_Rate;
import com.shop.services.AutoFloor_RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
/**
 * @author
 * @create 2019-09-11 8:55
 */
@RestController
public class AutoFloorFacilityAjax {

        @Autowired
        AutoFloor_RateService autoFloor_rateService;
        @Autowired
        TaskService taskService;
        @Autowired
        TimeUtility timeUtility;
        @RequestMapping("/FacilityAjax")
        public Map<String,Object> MidetectFailQ(String Linename_M,String Station,String product_name){
            String Floor_Name="D073F";
            String LinennameProduct="Macan";
            if (Station.equals("S1")){
                Station="CELL_S1";
            }else if (Station.equals("S2")){
                Station="CELL_S2";
            }else if (Station.equals("S3")){
                Station="CELL_S3";
            }else if (Station.equals("CCT")){
                Station="CCT_COMBO";
            }else if (Station.equals("W1")){
                Station="WIFI/BT_W1";
                if (product_name.equals(LinennameProduct)){
                    Station="WIFI/BT";
                }
            }else if (Station.equals("W2")){
                Station="WIFI/BT_W2";
                if (product_name.equals(LinennameProduct)){
                    Station="WIFI/BT2";
                }

            }

            List<AutoFloor_Rate> FacilityTop = autoFloor_rateService.AllStationTop5(Floor_Name, Linename_M, Station);

            List<AutoFloor_Rate> FacilityTopList= new ArrayList<>();
            for (int i = 0; i < FacilityTop.size(); i++) {
                AutoFloor_Rate Facility=new AutoFloor_Rate();
                AutoFloor_Rate floor_rate = FacilityTop.get(i);
                Integer rownum = floor_rate.getROWNUM();
                String machine_no = floor_rate.getMACHINE_NO();
                Double rate = floor_rate.getRate();
                Facility.setROWNUM(rownum);
                Facility.setMACHINE_NO(machine_no);
                Double FacilityD=0.0;
                if (rate!=0){
                    BigDecimal ratelbig = new BigDecimal(rate*100);
                    Double ratedouble = ratelbig.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    FacilityD=ratedouble;
                }
                Facility.setRate(FacilityD);
                FacilityTopList.add(Facility);
            }


            Object FacilityTopJson = JSON.toJSON(FacilityTopList);
            Map<String,Object> FacilityMap = new HashMap();
            FacilityMap.put("FacilityTopJson",FacilityTopJson.toString());
            return FacilityMap;
        }




}
