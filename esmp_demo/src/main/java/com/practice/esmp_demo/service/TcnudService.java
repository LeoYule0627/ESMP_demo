package com.practice.esmp_demo.service;

import com.practice.esmp_demo.controller.dto.CreateHcmio;
import com.practice.esmp_demo.model.TcnudRepository;
import com.practice.esmp_demo.model.entity.Hcmio;
import com.practice.esmp_demo.model.entity.Tcnud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TcnudService {

    @Autowired
    TcnudRepository tcnudRepository;

    public List<Tcnud> getTcnudAll() {
        List<Tcnud> response = tcnudRepository.findAll();
        return response;
    }

    public String createTcnud(Hcmio requset) {
        try {
            Tcnud createTcnud = new Tcnud();
            createTcnud.setTradeDate(requset.getTradeDate());
            createTcnud.setBranchNo(requset.getBranchNo());
            createTcnud.setCustSeq(requset.getCustSeq());
            createTcnud.setDocSeq(requset.getDocSeq());
            createTcnud.setStock(requset.getStock());
            createTcnud.setPrice(requset.getPrice());
            createTcnud.setQty(requset.getQty());
            int remainQty = this.tcnudRepository.findByRemainQty(
                    createTcnud.getTradeDate(), createTcnud.getBranchNo(), createTcnud.getCustSeq(), createTcnud.getStock());
            createTcnud.setRemainQty(remainQty + createTcnud.getQty());
            createTcnud.setFee(requset.getFee());
            createTcnud.setCost(Math.abs(requset.getNetAmt()));
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar c1 = Calendar.getInstance();
            createTcnud.setModDate(dateFormat.format(c1.getTime()));
            Format timeFormat = new SimpleDateFormat("HHmmss");
            createTcnud.setModTime(timeFormat.format(new Date()));
            createTcnud.setModUser(requset.getModUser());
            this.tcnudRepository.save(createTcnud);
            return "OK";
        } catch (Exception e) {
            return "FAIL";
        }
    }
}
