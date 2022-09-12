package com.practice.esmp_demo.service;

import com.practice.esmp_demo.controller.dto.AddHcmioAndTcnud;
import com.practice.esmp_demo.model.HcmioRepository;
import com.practice.esmp_demo.model.entity.Hcmio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class HcmioService {
    @Autowired
    HcmioRepository hcmioRepository;
    @Autowired
    Calculate calculate;

    public boolean createHcmio(AddHcmioAndTcnud request) {
        try {
            BigDecimal price = request.getPrice();
            BigDecimal qty = BigDecimal.valueOf(request.getQty());
            Hcmio createHcmio = new Hcmio();
            createHcmio.setTradeDate(request.getTradeDate());
            createHcmio.setBranchNo(request.getBranchNo());
            createHcmio.setCustSeq(request.getCustSeq());
            createHcmio.setDocSeq(request.getDocSeq());
            createHcmio.setStock(request.getStock());
            createHcmio.setBsType('B');
            createHcmio.setPrice(price);
            createHcmio.setQty(request.getQty());

            createHcmio.setAmt(calculate.getAmt(qty, price));
            createHcmio.setFee(calculate.getFee(qty, price));

            if (createHcmio.getBsType() == 'S') {
                createHcmio.setTax(calculate.getTax(qty, price));
            } else {
                createHcmio.setTax(BigDecimal.valueOf(0));
            }

            createHcmio.setNetAmt(calculate.getNetAmt(qty, price, createHcmio.getBsType()));
            createHcmio.setModDate(calculate.getModDate());
            createHcmio.setModTime(calculate.getModTime());
            createHcmio.setModUser(request.getModUser());
            this.hcmioRepository.save(createHcmio);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
