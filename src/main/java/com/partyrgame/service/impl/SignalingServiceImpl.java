package com.partyrgame.service.impl;

import com.partyrgame.service.SignalingService;
import com.partyrgame.util.WebsocketConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SignalingServiceImpl implements SignalingService {
    private final SimpMessagingTemplate template;

    @Autowired
    SignalingServiceImpl(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void sendOffer(String offer, String channel) {
        String queue = WebsocketConstants.GAME_BROKER + "/" + channel;
        System.out.println("Sending offer to " + queue);
        this.template.convertAndSend(queue, offer);
    }
}