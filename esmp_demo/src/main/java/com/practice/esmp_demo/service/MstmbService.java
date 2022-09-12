package com.practice.esmp_demo.service;

import com.practice.esmp_demo.controller.dto.UpdateMstmb;
import com.practice.esmp_demo.controller.dto.response.SearchStock;
import com.practice.esmp_demo.model.MstmbRepository;
import com.practice.esmp_demo.model.entity.Mstmb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MstmbService {
    @Autowired
    MstmbRepository mstmbRepository;

    @Autowired
    Calculate calculate;


//    public List<Mstmb> getMstmbAll(Mstmb mstmb) {
//        List<Mstmb> stockList = mstmbRepository.findAll();
//        List<Map> response = new ArrayList<>();
//        for(Mstmb stock:stockList){
//            Map stockMap = new HashMap<>();
//            stockMap.put("stock",stock.getStock());
//            stockMap.put("nowPrice",stock.getCurPrice());
//            response.add(stockMap);
//        }
//        return stockList;
//    }


    @Cacheable(value = "stock",key = "#request.stock")
    public Map getMstmbCache(SearchStock request){
        simulateSlowService();
        Mstmb stock = this.mstmbRepository.findByStock(request.getStock());
        Map response = new HashMap();
        response.put("stock",stock.getStock());
        response.put("nowPrice",stock.getCurPrice());
        return response;
    }

    private void simulateSlowService() {
        try {
            long time = 1000;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }


    public String updateMstmb(UpdateMstmb request) {
        try {
            Mstmb updateMstmb = this.mstmbRepository.findByStock(request.getStock());
            if(updateMstmb==null){
                return "NOt FOUND STOCK.";
            }
            updateMstmb.setCurPrice(request.getCurPrice());
            updateMstmb.setModDate(calculate.getModDate());
            updateMstmb.setModTime(calculate.getModTime());
            updateMstmb.setModUser(request.getModUser());
            this.mstmbRepository.save(updateMstmb);
            return "OK";
        } catch (Exception e) {
            return "Fail";
        }
    }
}
