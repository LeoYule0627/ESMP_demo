package com.practice.esmp_demo.controller;

import com.practice.esmp_demo.model.entity.Tcnud;
import com.practice.esmp_demo.service.TcnudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/tcnud")
public class TcnudController {

    @Autowired
    TcnudService tcnudService;

    @GetMapping()
    public List<Tcnud> getTcnudAll(){
        List<Tcnud> response = tcnudService.getTcnudAll();
        return response;
    }

}
