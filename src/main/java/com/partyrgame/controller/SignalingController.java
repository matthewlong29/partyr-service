package com.partyrgame.controller;

import java.util.Map;

import com.partyrgame.service.SignalingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
class SignalingController {
    @Autowired
    SignalingService signalingService;

    @MessageMapping("/signal-rtc")
    public void signalRTC(Map<String, String> body) {
        log.info("body: {}", body.toString());

        String signal = body.get("signal");
        String channel = body.get("channel");

        signalingService.sendOffer(signal, channel);
    }
}