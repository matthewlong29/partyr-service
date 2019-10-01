package com.partygames.partygamesservice.websocketeventlistener;

import com.partygames.partygamesservice.model.Message;
import com.partygames.partygamesservice.util.PartyLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

  /**
   * handleWebSocketConnectListener.
   */
  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    PartyLogger.info("Received a new web socket connection");
  }

  /**
   * handleWebSocketDisconnectListener.
   */
  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    String username = (String) headerAccessor.getSessionAttributes().get("username");

    if (username != null) {
      PartyLogger.info("User Disconnected : " + username);

      Message chatMessage = new Message();
      chatMessage.setType(Message.MessageType.LEAVE);
      chatMessage.setSender(username);

      messagingTemplate.convertAndSend("/topic/public", chatMessage);
    }
  }
}