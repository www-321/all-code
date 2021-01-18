package com.example.estemplate.controller;

import com.example.estemplate.config.Condition;
import com.example.estemplate.entity.IndexUser;
import com.example.estemplate.reposity.IndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class IndexController {

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    IndexRepository indexRepository;
    @Autowired
    RestTemplate restTemplate;


    public void index() {
        IndexUser indexUser = new IndexUser();
        indexUser.setAge(20);
        indexUser.setId("121");
        indexUser.setName("wuquan");

        indexRepository.save(indexUser);
    }


    @GetMapping("get")
    public String test(Condition condition) {
       return condition.getName();
    }







}
