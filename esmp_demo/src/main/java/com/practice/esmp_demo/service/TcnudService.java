package com.practice.esmp_demo.service;

import com.practice.esmp_demo.model.TcnudRepository;
import com.practice.esmp_demo.model.entity.Tcnud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TcnudService {

    @Autowired
    TcnudRepository tcnudRepository;

    public List<Tcnud> getTcnudAll() {
        List<Tcnud> response = tcnudRepository.findAll();
        return response;
    }
}
