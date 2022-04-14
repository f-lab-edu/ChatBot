package com.flab.fire_inform.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FireController {

    @PostMapping("/health")
    public ResponseEntity healthCheck(){
        return new ResponseEntity(HttpStatus.OK);
    }
}
