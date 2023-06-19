package com.example.storeeverything.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Configuration;

@Service
@Configuration
public class RestNameService {

    @Autowired()
    private RestTemplate restTemplate;

    public boolean isCategoryNamePolish(String name)
    {
        Boolean response = restTemplate.getForObject("http://localhost:8090/" + name,Boolean.class);
        if(response == null) response = false;
        return response;
    }
}