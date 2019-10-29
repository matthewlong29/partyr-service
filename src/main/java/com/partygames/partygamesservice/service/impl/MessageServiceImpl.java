package com.partygames.partygamesservice.service.impl;

import com.partygames.partygamesservice.model.ChatMessage;
import com.partygames.partygamesservice.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
  private final SimpMessagingTemplate template;

  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

  /**
   * MessageServiceImpl.
   */
  @Autowired
  MessageServiceImpl(SimpMessagingTemplate template) {
    this.template = template;
  }

  /**
   * sendMessage.
   */
  public void sendMessage(String destination, ChatMessage chatMessage) {
    this.template.convertAndSend(destination, chatMessage);
  }

  /**
   * handleWebSocketConnectListener.
   */
  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    log.info("Received a new web socket connection");
  }

  /**
   * handleWebSocketDisconnectListener.
   */
  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    String username = (String) headerAccessor.getSessionAttributes().get("username");

    if (username != null) {
      log.info("User Disconnected : " + username);

      ChatMessage chatMessage = new ChatMessage();
      messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
  }
}