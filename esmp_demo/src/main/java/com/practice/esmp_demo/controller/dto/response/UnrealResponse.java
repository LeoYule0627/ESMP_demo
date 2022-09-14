package com.practice.esmp_demo.controller.dto.response;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class UnrealResponse {


    public static LinkedHashMap unrealResponse(List<LinkedHashMap> response, String responseCode, String message) {
        LinkedHashMap object = new LinkedHashMap<>();
        if (response == null) {
            response = new ArrayList<>();
        }
        object.put("resultList", response);
        object.put("responseCode", responseCode);
        object.put("message", message);
        return object;
    }

    public static LinkedHashMap deliveryFeeResponse(Object sum, List<LinkedHashMap> response, String responseCode, String message) {
        LinkedHashMap object = new LinkedHashMap<>();
        if (response == null) {
            response = new ArrayList<>();
        }
        object.put("sumDeliveryFee",sum);
        object.put("detailList", response);
        object.put("responseCode", responseCode);
        object.put("message", message);
        return object;
    }
}
