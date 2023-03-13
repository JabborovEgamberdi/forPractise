package com.example.forgetdatafromapi;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final DataRepository dataRepository;
    private final Config config;

    public RestController(DataRepository dataRepository, Config config) {
        this.dataRepository = dataRepository;
        this.config = config;
    }

    @GetMapping("/1")
    public HttpEntity<?> getDataFromAPI() {

//        String url = "https://jira.atlassian.com/rest/api/latest/issue/JRA-9";
        JSONObject jsonObject = config.jsonObject(Config.API_NETWORK_1);
        System.out.println("jsonObject.getString(\"key\") = " + jsonObject.getString("key"));

        System.out.println("jsonObject.getJSONObject(\"fields\").get(\"customfield_18232\") = "
                + jsonObject.getJSONObject("fields").get("customfield_18232"));

        System.out.println("jsonObject.getJSONObject(\"fields\").getJSONObject(\"fixVersions\").getMapType(\"releaseDate\") = "
                + jsonObject.getJSONObject("fields").getJSONObject("resolution").get("description"));

        System.out.println("jsonObject.getJSONObject(\"fields\").getJSONArray(\"fixVersions\").getJSONObject(0).getString(\"name\") = "
                + jsonObject.getJSONObject("fields").getJSONArray("fixVersions").getJSONObject(0).getString("name"));

        System.out.println("jsonObject.getJSONObject(\"key\") = " + jsonObject.get("key"));

        Data data = new Data();
        data.setExpand((String) jsonObject.get("expand"));
        data.setKey(jsonObject.getString("key"));
        data.setReleaseDate(jsonObject.getJSONObject("fields").getJSONArray("fixVersions").getJSONObject(0).getString("releaseDate"));
        dataRepository.save(data);

        return ResponseEntity.ok(jsonObject);
    }


    @GetMapping("/json")
    public HttpEntity<?> getAndSendJSON() {

        JSONObject jsonObject = config.jsonObject(Config.API_NETWORK_1);

//        DataDTO data = DataDTO
//                .builder()
//                .releaseDate(jsonObject.getJSONObject("fields").getJSONArray("fixVersions").getJSONObject(0).getString("releaseDate"))
//                .build();

//        data.setExpand((String) jsonObject.get("expand"));
//        data.setKey(jsonObject.getString("key"));
//        data.setReleaseDate(jsonObject.getJSONObject("fields").getJSONArray("fixVersions").getJSONObject(0).getString("releaseDate"));

        DataDTO data = new DataDTO();
        data.setReleaseDate(jsonObject.getJSONObject("fields").getJSONArray("fixVersions").getJSONObject(0).getString("releaseDate"));

        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }

}