package com.practice.esmp_demo.service;

import com.practice.esmp_demo.controller.dto.CreateHcmio;
import com.practice.esmp_demo.model.HcmioRepository;
import com.practice.esmp_demo.model.entity.Hcmio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
public class HcmioService {
    @Autowired
    HcmioRepository hcmioRepository;
    @Autowired
    TcnudService tcnudService;

    public List<Hcmio> getHcmioAll() {
        List<Hcmio> response = hcmioRepository.findAll();
        return response;
    }

    public String createHcmio(CreateHcmio request) {
        try {
            Hcmio createHcmio = new Hcmio();
            createHcmio.setTradeDate(request.getTradeDate());
            createHcmio.setBranchNo(request.getBranchNo());
            createHcmio.setCustSeq(request.getCustSeq());
            createHcmio.setDocSeq(request.getDocSeq());
            createHcmio.setStock(request.getStock());
            createHcmio.setBsType(request.getBsType());
            createHcmio.setPrice(request.getPrice());
            createHcmio.setQty(request.getQty());
            createHcmio.setAmt(request.getPrice() * request.getQty());
            createHcmio.setFee((int) (createHcmio.getAmt() * 0.001425));
            if (createHcmio.getBsType() == 'S') {
                createHcmio.setTax((int) (createHcmio.getAmt() * 0.003));
            }
            createHcmio.setNetAmt(createHcmio.getAmt() - createHcmio.getFee() - createHcmio.getTax());
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Calendar c1 = Calendar.getInstance();
            createHcmio.setModDate(dateFormat.format(c1.getTime()));
            Format timeFormat = new SimpleDateFormat("HHmmss");
            createHcmio.setModTime(timeFormat.format(new Date()));
            createHcmio.setModUser(request.getModUser());
            if (createHcmio.getBsType() == 'B') {
                String TcnudResponse = this.tcnudService.createTcnud(createHcmio);
            }
            this.hcmioRepository.save(createHcmio);
            return "OK";
        } catch (Exception e) {
            System.out.println(e);
            return "Fail";
        }
    }
}
