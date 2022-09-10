package com.practice.esmp_demo.controller;

import com.practice.esmp_demo.model.entity.Hcmio;
import com.practice.esmp_demo.service.HcmioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/hcmio")
public class HcmioController {
    @Autowired
    HcmioService hcmioService;

    @GetMapping()
    public List<Hcmio> getHcmioAll() {
        List<Hcmio> response = hcmioService.getHcmioAll();
        return response;
    }
}
