package com.example.forgetdatafromapi;

import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

//    private final RestTemplate restTemplate;
//
//    public Config(RestTemplateBuilder restTemplateBuilder) {
//        this.restTemplate = restTemplateBuilder.build();
//    }

    public static final String API_NETWORK_1 = "https://jira.atlassian.com/rest/api/latest/issue/JRA-9";

    private static final RestTemplate restTemplate = new RestTemplate();

    public JSONObject jsonObject(String apiUrl) {
        String apiResponse = restTemplate.getForObject(apiUrl, String.class);
        JSONObject jsonObject = new JSONObject(apiResponse);
        return jsonObject;
    }

}