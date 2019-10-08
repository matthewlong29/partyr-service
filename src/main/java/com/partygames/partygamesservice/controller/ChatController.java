package com.partygames.partygamesservice.controller;

import com.partygames.partygamesservice.model.Message;
import com.partygames.partygamesservice.service.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
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
}
