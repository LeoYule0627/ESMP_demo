package com.practice.esmp_demo.controller;

import com.practice.esmp_demo.controller.dto.request.UpdateMstmb;
import com.practice.esmp_demo.controller.dto.request.SearchMstmb;
import com.practice.esmp_demo.controller.dto.response.UnrealResponse;
import com.practice.esmp_demo.service.MstmbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@Validated
@Controller
@RestController
@RequestMapping("/api/v1/mstmb")
public class MstmbController {

    @Autowired
    MstmbService mstmbService;

    @PostMapping()
    public Map getMstmb(@RequestBody @Valid SearchMstmb searchStock) {
        Map response = this.mstmbService.getMstmbCache(searchStock);
        return response;
    }

    @PutMapping("/update")
    public Map updateMstmb(@RequestBody @Valid UpdateMstmb updateMstmb) {
        Map response = this.mstmbService.updateMstmb(updateMstmb);
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StringBuilder> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        exception.getBindingResult().getAllErrors().stream().forEach(error -> stringBuilder.append(error.getDefaultMessage()).append(", "));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(stringBuilder);
    }
}
