package com.talismanov.apiparsers;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class testHHVacancies {
    private static final String USER_AGENT = "Mozilla/5.0";
    private Map map = new HashMap();

    public void sendGet() throws Exception {
        URL url = new URL("https://api.hh.ru/vacancies?text=java&area=1&per_page=500&page=0");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int response = con.getResponseCode();
        System.out.println("response = " + response);
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();

        String line = "";
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
        }
        br.close();
        String json = stringBuilder.toString();

        Map<String, Object> retMap = new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());
        System.out.println("retMap.size()" + retMap.size());



        System.out.println(retMap);
        @SuppressWarnings("unchecked")
        List<Object> items = (ArrayList<Object>) retMap.get("items");
        System.out.println(items.size());
        System.out.println(items);

        AtomicInteger atomicInteger = new AtomicInteger();

        items.stream().forEach(elem -> {

            System.out.println(elem);
            @SuppressWarnings("unchecked")
            Map map = (LinkedTreeMap<String,Object>) elem;
            //System.out.println(map.size());
            @SuppressWarnings("unchecked")
            Map map1 = (LinkedTreeMap<String,Object>) map.get("snippet");
            String str = (String) map1.get("requirement");
            //  System.out.println(str);
            if (str != null) {
                if (str.toLowerCase().contains("tomcat")) {
                    atomicInteger.incrementAndGet();
                }
            }
        });
        System.out.println("Tomcat count" + atomicInteger.get());
    }
}