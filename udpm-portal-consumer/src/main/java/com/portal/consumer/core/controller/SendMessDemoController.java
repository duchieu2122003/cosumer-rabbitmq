package com.portal.consumer.core.controller;

import com.portal.consumer.infrastructure.rabbit.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/send-message")
public class SendMessDemoController {

    @Autowired
    private LogService service;

    @PostMapping
    public boolean sendMessage(@RequestBody Map<String, String> data) {
        try {
            service.sendLogMessage(data);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
