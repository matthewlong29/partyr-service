package com.partygames.partygamesservice.service.impl;

import java.util.List;

import com.partygames.partygamesservice.model.ChatMessage;
import com.partygames.partygamesservice.model.Room;
import com.partygames.partygamesservice.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
  private final SimpMessagingTemplate template;

  /**
   * MessageServiceImpl.
   */
  @Autowired
  MessageServiceImpl(SimpMessagingTemplate template) {
    this.template = template;
  }

  /**
   * sendChatMessage.
   */
  public void sendChatMessage(ChatMessage chatMessage) {
    this.template.convertAndSend("/chat/room", chatMessage);
  }

  /**
   * sendRoomMessage: sends a list of rooms to the client
   */
  public void sendRoomMessage(List<Room> rooms) {
    this.template.convertAndSend("/lobby/rooms", rooms);
  }

  /**
   * handleWebSocketConnectListener.
   */
  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    log.info("received a new web socket connection. time: {}; user: {}; message: {}; source: {}", event.getTimestamp(),
        event.getUser(), event.getMessage(), event.getSource());
  }

  /**
   * handleWebSocketDisconnectListener.
   */
  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    log.info("received a new web socket connection. time: {}; user: {}; message: {}; source: {}", event.getTimestamp(),
        event.getUser(), event.getMessage(), event.getSource());
  }
}