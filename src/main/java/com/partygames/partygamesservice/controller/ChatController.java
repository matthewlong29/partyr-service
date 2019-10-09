package com.partygames.partygamesservice.controller;

import java.util.List;

import com.partygames.partygamesservice.model.Message;
import com.partygames.partygamesservice.service.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
  @Autowired
  ChatService chatService;

  /**
   * onReceivedMessage.
   */
  @MessageMapping("/send/message")
  public void onReceivedMessage(Message chatMessage) {
    chatService.saveMessage(chatMessage);
    chatService.sendMessageToChat("/chat", chatMessage);
  }

  /**
   * getMessages.
   */
  @GetMapping(value = "/api/chat", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Message> getMessages() {
    return chatService.getMessages();
  }
}
