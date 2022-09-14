package com.practice.esmp_demo.service;

import com.practice.esmp_demo.controller.dto.request.UpdateMstmb;
import com.practice.esmp_demo.controller.dto.request.SearchMstmb;
import com.practice.esmp_demo.model.MstmbRepository;
import com.practice.esmp_demo.model.entity.Mstmb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MstmbService {
    @Autowired
    MstmbRepository mstmbRepository;

    @Autowired
    Calculate calculate;


    @Cacheable(value = "stock", key = "#request.stock")
    public Map getMstmbCache(SearchMstmb request) {
        Map response = new HashMap();
        try {
            Mstmb stock = this.mstmbRepository.findByStock(request.getStock());
            if(stock==null){
                response.put("message", "NOT FOUND STOCK.");
                return response;
            }

            response.put("stock", stock.getStock());
            response.put("nowPrice", calculate.getNowPrice(stock.getCurPrice()));

            return response;
        }catch (Exception e){
            response.put("message", "FAIL.");
            return response;
        }

    }


    @CachePut(value = "stock", key = "#request.stock")
    public Map updateMstmb(UpdateMstmb request) {
        Map response = new HashMap();
        try {
            Mstmb updateMstmb = this.mstmbRepository.findByStock(request.getStock());

            if (updateMstmb == null) {
                response.put("message", "NOT FOUND STOCK.");
                return response;
            }

            response.put("stock", request.getStock());
            response.put("nowPrice", calculate.getNowPrice(request.getCurPrice()));

            updateMstmb.setCurPrice(calculate.getNowPrice(request.getCurPrice()));
            updateMstmb.setModDate(calculate.getModDate());
            updateMstmb.setModTime(calculate.getModTime());
            updateMstmb.setModUser("LEO");  // 暫定
            this.mstmbRepository.save(updateMstmb);
            return response;
        } catch (Exception e) {
            response.put("message", "UPDATE FAIL.");
            return response;
        }
    }
}
