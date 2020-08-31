package com.hug.controller;

import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/")
@Api(value = "HealthController", tags = "HealthController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HealthController {

    @Resource
    Environment evn;

    @GetMapping(value = "status")
    @ApiOperation(value = "health", notes = "health")
    public String health() {
        log.info("-{}", evn.getProperty("catalina.base"));
        return "ok";
    }

    @GetMapping(value = "test")
    @ApiOperation(value = "test", notes = "test")
    public String test() {
        log.info("-{}", evn.getProperty("catalina.base"));
        Map source = ImmutableMap.of("order_id", "1", "order_state", "i'm create!");
        return "ok";
    }

}
