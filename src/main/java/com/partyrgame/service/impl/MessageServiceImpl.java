package com.partyrgame.service.impl;

import java.util.List;

import com.partyrgame.model.ChatMessage;
import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.service.MessageService;
import com.partyrgame.roomservice.model.Room;
import com.partyrgame.util.WebsocketConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
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
    this.template.convertAndSend(WebsocketConstants.CHAT_SUBSCRIBE, chatMessage);
  }

  /**
   * sendRoomMessage: sends a list of rooms to the client
   */
  public void sendRoomMessage(List<Room> rooms) {
    this.template.convertAndSend(WebsocketConstants.LOBBY_SUBSCRIBE, rooms);
  }

  /**
   * sendBlackHandMessage: sends a BlackHand object to the current game.
   */
  public void sendBlackHandMessage(BlackHand blackHand) {
    this.template.convertAndSend(WebsocketConstants.BLACK_HAND_SUBSCRIBE, blackHand);
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