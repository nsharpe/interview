package org.example.test.data;

import java.util.HashMap;
import java.util.Map;

import static org.example.test.data.TestUtil.FAKER;

public class PostPayloadGenerator {

    public static Map<String,Object> createSeriesPojo(String title){
        Map<String,Object> seriesPojo = new HashMap<>();

        seriesPojo.put("title",title);
        seriesPojo.put("description","A rousing story");
        seriesPojo.put("locale","en");

        return seriesPojo;
    }

    public static Map<String,Object> createSeriesPojo(){
        return createSeriesPojo(FAKER.lorem().sentence(2,4));
    }

    public static Map<String,Object> createSeasonPojo(String title, int order){
        Map<String,Object> seasonPojo = new HashMap<>();

        seasonPojo.put("title",title);
        seasonPojo.put("order",order);

        return seasonPojo;
    }
}
