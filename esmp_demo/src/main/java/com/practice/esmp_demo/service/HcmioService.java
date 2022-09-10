package com.practice.esmp_demo.service;

import com.practice.esmp_demo.controller.dto.AddHcmioAndTcnud;
import com.practice.esmp_demo.model.HcmioRepository;
import com.practice.esmp_demo.model.entity.Hcmio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HcmioService {
    @Autowired
    HcmioRepository hcmioRepository;
    @Autowired
    Calculate calculate;


    public List<Hcmio> getHcmioAll() {
        List<Hcmio> response = hcmioRepository.findAll();
        return response;
    }

    public boolean createHcmio(AddHcmioAndTcnud request) {
        try {
            Hcmio createHcmio = new Hcmio();
            createHcmio.setTradeDate(request.getTradeDate());
            createHcmio.setBranchNo(request.getBranchNo());
            createHcmio.setCustSeq(request.getCustSeq());
            createHcmio.setDocSeq(request.getDocSeq());
            createHcmio.setStock(request.getStock());
            createHcmio.setBsType('B');
            createHcmio.setPrice(request.getPrice());
            createHcmio.setQty(request.getQty());

            calculate.set(createHcmio.getQty(), createHcmio.getPrice());
            createHcmio.setAmt(calculate.getAmt());
            createHcmio.setFee(calculate.getFee());

            if (createHcmio.getBsType() == 'S') {
                createHcmio.setTax(calculate.getTax());
            }

            createHcmio.setNetAmt(calculate.getNetAmt());
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
