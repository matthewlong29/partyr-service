package com.partygames.partygamesservice.controller;

import java.util.List;

import com.partygames.partygamesservice.model.ChatMessage;
import com.partygames.partygamesservice.service.ChatService;
import com.partygames.partygamesservice.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ChatController {
  @Autowired
  ChatService chatService;

  @Autowired
  MessageService messageService;

  /**
   * onReceivedMessage.
   */
  @MessageMapping("/lobby")
  public void onReceivedChatMessage(ChatMessage chatMessage) {
    log.info(chatMessage.toString());
    chatService.saveChatMessage(chatMessage);
    messageService.sendChatMessage(chatMessage);
  }

  /**
   * getMessages.
   */
  @GetMapping(value = "/api/chat", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<ChatMessage> getChatMessages() {
    return chatService.getChatMessages();
  }
}
