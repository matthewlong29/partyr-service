package com.partygames.partygamesservice.websocketeventlistener;

import com.partygames.partygamesservice.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSocketEventListener {
  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

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

      Message chatMessage = new Message();
      messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
  }
}