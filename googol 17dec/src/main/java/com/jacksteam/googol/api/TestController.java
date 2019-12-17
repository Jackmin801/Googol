package com.jacksteam.googol.api;

import com.jacksteam.googol.exception.ApiRequestException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("jacksteam/googol/api/test")
@RestController
public class TestController{

    @GetMapping
    public String test(){
        throw new ApiRequestException("hi me die!");
    }
}