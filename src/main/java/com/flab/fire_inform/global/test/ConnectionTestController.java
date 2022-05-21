package com.flab.fire_inform.global.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionTestController {

    @GetMapping("/test")
    public String connectionTest() {
        return "success";
    }
}
