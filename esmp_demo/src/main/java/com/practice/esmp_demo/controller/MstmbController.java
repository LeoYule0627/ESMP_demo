package com.practice.esmp_demo.controller;

import com.practice.esmp_demo.controller.dto.UpdateMstmb;
import com.practice.esmp_demo.model.entity.Mstmb;
import com.practice.esmp_demo.service.MstmbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1/mstmb")
public class MstmbController {

    @Autowired
    MstmbService mstmbService;

    @GetMapping()
    public List<Mstmb> getMstmbAll() {
        List<Mstmb> response = this.mstmbService.getMstmbAll();
        return response;
    }

    @PutMapping("/update/{stock}")
    public String updateMstmb(@PathVariable String stock, @RequestBody UpdateMstmb updateMstmb) {
        String response = this.mstmbService.updateMstmb(stock, updateMstmb);
        return response;
    }
}
