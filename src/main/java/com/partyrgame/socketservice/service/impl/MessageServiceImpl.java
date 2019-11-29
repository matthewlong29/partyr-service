package com.partyrgame.socketservice.service.impl;

import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.chatservice.model.ChatMessage;
import com.partyrgame.roomservice.model.Room;
import com.partyrgame.socketservice.service.MessageService;
import com.partyrgame.socketservice.util.WebsocketConstants;

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
  public void sendChatMessage(ChatMessage chatMessage, String channel) {
    String destination = WebsocketConstants.CHAT_SUBSCRIBE + "/" + convertChannelName(channel);
    this.template.convertAndSend(destination, chatMessage);
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
  public void sendBlackHandMessage(BlackHand blackHand, String channel) {
    String destination = WebsocketConstants.BLACK_HAND_SUBSCRIBE + "/" + convertChannelName(channel);
    this.template.convertAndSend(destination, blackHand);
  }

  /**
   * sendOfferMessage: sends
   */
  public void sendOfferMessage(String offer, String channel) {
    String queue = WebsocketConstants.BLACK_HAND_BROKER + "/" + channel;
    log.info("sending offer to " + queue);

    this.template.convertAndSend(queue, offer);
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

  /**
   * convertChannelName: removes special characters, converts spaces to dashes,
   * and converts to lowercase.
   * 
   * @param channel input channel which may contain spaces and special characters.
   * @return lowercase alphanumeric version of a room name (channel) separated by
   *         dashes.
   */
  private String convertChannelName(String channel) {
    channel = channel.replaceAll("[^a-zA-Z0-9\\s\\-]", "");
    channel = channel.replaceAll(" ", "-").toLowerCase();

    log.info("altered channel: {}", channel);

    return channel;
  }
}