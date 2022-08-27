package com.practice.esmp_demo.service;

import com.practice.esmp_demo.controller.dto.CreateHcmio;
import com.practice.esmp_demo.model.HcmioRepository;
import com.practice.esmp_demo.model.entity.Hcmio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HcmioService {
    @Autowired
    HcmioRepository hcmioRepository;

    public List<Hcmio> getHcmioAll() {
        List<Hcmio> response = hcmioRepository.findAll();
        return response;
    }

    public String createHcmio(CreateHcmio requset) {
        try {
            Hcmio createHcmio = new Hcmio();
            createHcmio.setTradeDate(requset.getTradeDate());
            createHcmio.setBranchNo(requset.getBranchNo());
            createHcmio.setCustSeq(requset.getCustSeq());
            createHcmio.setDocSeq(requset.getDocSeq());
            createHcmio.setStock(requset.getStock());
            createHcmio.setBsType(requset.getBsType());
            createHcmio.setPrice(requset.getPrice());
            createHcmio.setQty(requset.getQty());
            createHcmio.setAmt(requset.getPrice() * requset.getQty());
            createHcmio.setFee((int) (createHcmio.getAmt() * 0.001425));
            if (createHcmio.getBsType() == 'S') {
                createHcmio.setTax((int) (createHcmio.getAmt() * 0.003));
            }
            createHcmio.setNetAmt(createHcmio.getAmt() - createHcmio.getFee() - createHcmio.getTax());
            this.hcmioRepository.save(createHcmio);
            return "OK";
        } catch (Exception e) {
            System.out.println(e);
            return "Fail";
        }
    }
}
