package com.redhat.offermanagement.routes;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapBean {

    public Map process(String body) {

        LinkedHashMap<String,String> mapValue = new Gson().fromJson(body,LinkedHashMap.class);
        System.out.println(mapValue);
        return mapValue;


    }
}
