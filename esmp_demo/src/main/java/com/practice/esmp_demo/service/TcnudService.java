package com.practice.esmp_demo.service;

import com.practice.esmp_demo.controller.dto.AddHcmioAndTcnud;
import com.practice.esmp_demo.model.TcnudRepository;
import com.practice.esmp_demo.model.entity.Tcnud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TcnudService {

    @Autowired
    TcnudRepository tcnudRepository;
    @Autowired
    Calculate calculate;

    public List<Tcnud> getTcnudAll() {
        List<Tcnud> response = tcnudRepository.findAll();
        return response;
    }

    public boolean createTcnud(AddHcmioAndTcnud request) {
        try {
            Tcnud createTcnud = new Tcnud();
            createTcnud.setTradeDate(request.getTradeDate());
            createTcnud.setBranchNo(request.getBranchNo());
            createTcnud.setCustSeq(request.getCustSeq());
            createTcnud.setDocSeq(request.getDocSeq());
            createTcnud.setStock(request.getStock());
            createTcnud.setPrice(request.getPrice());
            createTcnud.setQty(request.getQty());

            Tcnud check = this.tcnudRepository.findByBranchNoAndCustSeqAndStock(
                    createTcnud.getBranchNo(), createTcnud.getCustSeq(), createTcnud.getStock());
            if (check == null) {
                createTcnud.setRemainQty(0 + createTcnud.getQty());
            } else {
                createTcnud.setRemainQty(check.getRemainQty() + createTcnud.getQty());
            }

            calculate.set(createTcnud.getQty(), createTcnud.getPrice());
            createTcnud.setFee(calculate.getFee());
            createTcnud.setCost(Math.abs(calculate.getNetAmt()));
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
