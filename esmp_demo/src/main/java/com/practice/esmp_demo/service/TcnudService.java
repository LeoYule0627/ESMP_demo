package com.practice.esmp_demo.service;

import com.practice.esmp_demo.controller.dto.AddHcmioAndTcnud;
import com.practice.esmp_demo.model.TcnudRepository;
import com.practice.esmp_demo.model.entity.Tcnud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TcnudService {

    @Autowired
    TcnudRepository tcnudRepository;
    @Autowired
    Calculate calculate;

    public boolean createTcnud(AddHcmioAndTcnud request) {
        try {
            BigDecimal price = request.getPrice();
            BigDecimal qty = BigDecimal.valueOf(request.getQty());
            Tcnud createTcnud = new Tcnud();
            createTcnud.setTradeDate(request.getTradeDate());
            createTcnud.setBranchNo(request.getBranchNo());
            createTcnud.setCustSeq(request.getCustSeq());
            createTcnud.setDocSeq(request.getDocSeq());
            createTcnud.setStock(request.getStock());
            createTcnud.setPrice(price);
            createTcnud.setQty(request.getQty());

            Tcnud lastOne = this.tcnudRepository.findLastOne(
                    createTcnud.getBranchNo(), createTcnud.getCustSeq(), createTcnud.getStock());
            if (lastOne == null) {
                createTcnud.setRemainQty(0 + createTcnud.getQty());
            } else {
                createTcnud.setRemainQty(lastOne.getRemainQty() + createTcnud.getQty());
            }

            createTcnud.setFee(calculate.getFee(qty, price));
            createTcnud.setCost(calculate.getCost(qty, price));
            createTcnud.setModDate(calculate.getModDate());
            createTcnud.setModTime(calculate.getModTime());
            createTcnud.setModUser(request.getModUser());
            this.tcnudRepository.save(createTcnud);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
