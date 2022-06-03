package com.flab.fire_inform.global.security;

import org.springframework.stereotype.Component;

@Component
public class RedisProperties {
    private final String host = "localhost";
    private final int port = 6379;
}
