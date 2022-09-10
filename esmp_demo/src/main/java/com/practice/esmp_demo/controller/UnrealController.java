package com.practice.esmp_demo.controller;

import com.practice.esmp_demo.controller.dto.AddHcmioAndTcnud;
import com.practice.esmp_demo.controller.dto.Search;
import com.practice.esmp_demo.controller.dto.response.UnrealResponse;
import com.practice.esmp_demo.service.UnrealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Controller
@RestController
@Validated
@RequestMapping("api/v1/unreal")
public class UnrealController {

    @Autowired
    UnrealService unrealService;

    @PostMapping("/detail")
    public ResponseEntity<LinkedHashMap> unrealDetail(@RequestBody @Valid Search search) {
        try {
            LinkedHashMap response = unrealService.unrealDetail(search);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
        }
    }


    @PostMapping("/sum")
    public ResponseEntity<LinkedHashMap> unrealSum(@RequestBody @Validated Search search) {
        try {
            LinkedHashMap response = unrealService.unrealSum(search);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found ^^");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<LinkedHashMap> unrealAdd(@RequestBody AddHcmioAndTcnud request) {
        try {
            LinkedHashMap response = unrealService.unrealAdd(request);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } catch (Exception e) {
            System.out.println(e);
            LinkedHashMap response = UnrealResponse.setUnrealResponse(null, "001", "gg");
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<LinkedHashMap> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        StringBuilder sb = new StringBuilder();
        exception.getBindingResult().getAllErrors().stream().forEach(objectError -> sb.append(objectError.getDefaultMessage()).append(", "));
        LinkedHashMap response = UnrealResponse.setUnrealResponse(null, "002", sb.toString());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
