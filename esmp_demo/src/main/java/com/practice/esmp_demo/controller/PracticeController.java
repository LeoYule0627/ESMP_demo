package com.practice.esmp_demo.controller;

import com.practice.esmp_demo.model.entity.Hcmio;
import com.practice.esmp_demo.model.entity.Mstmb;
import com.practice.esmp_demo.model.entity.Tcnud;
import com.practice.esmp_demo.model.entity.Test;
import com.practice.esmp_demo.service.PracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/api/v1/")
public class PracticeController {

    @Autowired
    PracticeService practiceService;

//    @GetMapping()
//    public ResponseEntity<String> getAll(@PathVariable int stock){
//        try{
//            String response = practiceService.getAll(stock);
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body(response);
//        }catch (Exception e) {
//            System.out.println(e);
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
//        }
//    }

    @GetMapping()
    public List<Map<String, String>> getAll(){
        List<Map<String, String>> response = practiceService.getAll();
            return response;
    }

}
