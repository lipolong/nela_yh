package com.example.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lp
 * @date 2022-05-23 10:03
 */
@RestController
public class SentinelTestController {

    @GetMapping("/getString")
    public String getString(){
        return "SUCCESS";
    }


}
