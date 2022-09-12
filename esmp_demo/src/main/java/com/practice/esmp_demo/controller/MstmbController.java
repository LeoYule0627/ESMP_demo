package com.practice.esmp_demo.controller;

import com.practice.esmp_demo.controller.dto.UpdateMstmb;
import com.practice.esmp_demo.controller.dto.response.SearchStock;
import com.practice.esmp_demo.service.MstmbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RestController
@RequestMapping("/api/v1/mstmb")
public class MstmbController {

    @Autowired
    MstmbService mstmbService;

    @PostMapping()
    public Map getMstmbAll(@RequestBody SearchStock searchStock) {
        Map response = this.mstmbService.getMstmbCache(searchStock);
        return response;
    }

    @PutMapping("/update")
    public String updateMstmb(@RequestBody UpdateMstmb updateMstmb) {
        String response = this.mstmbService.updateMstmb(updateMstmb);
        return response;
    }
}
