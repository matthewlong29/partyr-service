package com.partygames.partygamesservice.controller;

import com.partygames.partygamesservice.model.Message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
  /**
   * sendMessage.
   */
  @MessageMapping("/chat.sendMessage")
  @SendTo("/topic/public") // TODO make dynamic rooms?
  public Message sendMessage(@Payload Message chatMessage) {
    return chatMessage;
  }

  /**
   * addUser: adds a user to the instance.
   */
  @MessageMapping("/chat.addUser")
  @SendTo("/topic/public") // TODO make dynamic rooms?
  public Message addUser(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    // add username in web socket session
    headerAccessor.getSessionAttributes().put("author", chatMessage.getSender());

    return chatMessage;
  }

  /**
   * removeUser: adds a user to the instance.
   */
  @MessageMapping("/chat.removeUser")
  @SendTo("/topic/public") // TODO make dynamic rooms?
  public Message removeUser(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    // Add username in web socket session
    headerAccessor.getSessionAttributes().put("author", chatMessage.getSender());

    return chatMessage;
  }
}