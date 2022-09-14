package com.practice.esmp_demo.controller;

import com.practice.esmp_demo.controller.dto.request.AddHcmioAndTcnud;
import com.practice.esmp_demo.controller.dto.request.Search;
import com.practice.esmp_demo.controller.dto.request.SearchDeliveryFee;
import com.practice.esmp_demo.controller.dto.response.UnrealResponse;
import com.practice.esmp_demo.service.UnrealService;
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

@Controller
@RestController
@Validated
@RequestMapping("api/v1/unreal")
public class UnrealController {

    @Autowired
    UnrealService unrealService;

    @PostMapping("/detail")
    public ResponseEntity<LinkedHashMap> unrealDetail(@RequestBody @Valid Search search) {
        LinkedHashMap response;
        try {
            response = unrealService.unrealDetail(search);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } catch (Exception e) {
            System.out.println(e);
            response = UnrealResponse.unrealResponse(null, "005", "伺服器忙碌中，請稍後嘗試");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }


    @PostMapping("/sum")
    public ResponseEntity<LinkedHashMap> unrealSum(@RequestBody @Valid Search search) {
        LinkedHashMap response;
        try {
            response = unrealService.unrealSum(search);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } catch (Exception e) {
            System.out.println(e);
            response = UnrealResponse.unrealResponse(null, "005", "伺服器忙碌中，請稍後嘗試");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<LinkedHashMap> unrealAdd(@RequestBody @Valid AddHcmioAndTcnud request) {
        LinkedHashMap response;
        try {
            response = unrealService.unrealAdd(request);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } catch (Exception e) {
            System.out.println(e);
            response = UnrealResponse.unrealResponse(null, "005", "伺服器忙碌中，請稍後嘗試");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @PostMapping("/delivery")
    public ResponseEntity<LinkedHashMap> getDeliveryFee(@RequestBody @Valid SearchDeliveryFee request){
        LinkedHashMap response = this.unrealService.getDeliveryFee(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<LinkedHashMap> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        exception.getBindingResult().getAllErrors().stream().forEach(error -> stringBuilder.append(error.getDefaultMessage()).append(", "));
        LinkedHashMap response = UnrealResponse.unrealResponse(null, "002", stringBuilder.toString());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

}
