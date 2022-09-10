package com.practice.esmp_demo.service;

import com.practice.esmp_demo.controller.dto.UpdateMstmb;
import com.practice.esmp_demo.model.MstmbRepository;
import com.practice.esmp_demo.model.entity.Mstmb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MstmbService {
    @Autowired
    MstmbRepository mstmbRepository;

    @Autowired
    Calculate calculate;

    public List<Mstmb> getMstmbAll() {
        List<Mstmb> response = mstmbRepository.findAll();
        return response;
    }

    public String updateMstmb(String stock, UpdateMstmb request) {
        try {
            Mstmb updateMstmb = this.mstmbRepository.findAllByStock(stock);
            updateMstmb.setCurPrice(request.getCurPrice());
            updateMstmb.setModDate(calculate.getModDate());
            updateMstmb.setModTime(calculate.getModTime());
            this.mstmbRepository.save(updateMstmb);
            return "OK";
        } catch (Exception e) {
            return "Fail";
        }
    }
}
