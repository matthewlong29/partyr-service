package com.partygames.partygamesservice.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
  private final SimpMessagingTemplate template;

  @Autowired
  ChatController(SimpMessagingTemplate template) {
    this.template = template;
  }

  @MessageMapping("/send/message")
  public void onReceivedMessage(String message) {
    this.template.convertAndSend("/chat", new SimpleDateFormat("HH:mm:ss").format(new Date()) + " - " + message);
  }

  /**
   * sendMessage.
   */
  // @MessageMapping("/chat.sendMessage")
  // @SendTo("/topic/public") // TODO make dynamic rooms?
  // public Message sendMessage(@Payload Message chatMessage) {
  // return chatMessage;
  // }

  /**
   * addUser: adds a user to the instance.
   */
  // @MessageMapping("/chat.addUser")
  // @SendTo("/topic/public") // TODO make dynamic rooms?
  // public Message addUser(@Payload Message chatMessage,
  // SimpMessageHeaderAccessor headerAccessor) {
  // // add username in web socket session
  // headerAccessor.getSessionAttributes().put("author", chatMessage.getSender());

  // return chatMessage;
  // }

  /**
   * removeUser: adds a user to the instance.
   */
  // @MessageMapping("/chat.removeUser")
  // @SendTo("/topic/public") // TODO make dynamic rooms?
  // public Message removeUser(@Payload Message chatMessage,
  // SimpMessageHeaderAccessor headerAccessor) {
  // // Add username in web socket session
  // headerAccessor.getSessionAttributes().put("author", chatMessage.getSender());

  // return chatMessage;
  // }
}