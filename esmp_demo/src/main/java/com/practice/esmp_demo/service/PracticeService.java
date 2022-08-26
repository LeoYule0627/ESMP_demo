package com.practice.esmp_demo.service;

import com.practice.esmp_demo.model.HcmioRepository;
import com.practice.esmp_demo.model.MstmbRepository;
import com.practice.esmp_demo.model.TcnudRepository;
import com.practice.esmp_demo.model.TestRepository;
import com.practice.esmp_demo.model.entity.Hcmio;
import com.practice.esmp_demo.model.entity.Mstmb;
import com.practice.esmp_demo.model.entity.Tcnud;
import com.practice.esmp_demo.model.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PracticeService {
    @Autowired
    TestRepository testRepository;
    @Autowired
    TcnudRepository tcnudRepository;
    @Autowired
    MstmbRepository mstmbRepository;
    @Autowired
    HcmioRepository hcmioRepository;

    public List<Map<String, String>> getAll() {
        List<Map<String, String>> response = testRepository.getAll();
        return response;
    }

    public List<Tcnud> getTcnudAll(){
        List<Tcnud> response = tcnudRepository.findAll();
        return response;
    }

    public List<Mstmb> getMstmbAll(){
        List<Mstmb> response = mstmbRepository.findAll();
        return response;
    }
    public List<Hcmio> getHcmioAll(){
        List<Hcmio> response = hcmioRepository.findAll();
        return response;
    }

}
