package com.practice.esmp_demo.controller.dto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class setResponse {
    public static String setResponse(List<Map<String, String>> response) {
        JSONObject object = new JSONObject();
        if (response != null) {
            JSONArray list = new JSONArray();
            object.put("user", list);
        }
        return object.toString();
    }
}
