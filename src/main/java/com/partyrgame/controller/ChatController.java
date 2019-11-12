package com.partyrgame.controller;

import java.util.List;

import com.partyrgame.model.ChatMessage;
import com.partyrgame.service.ChatService;
import com.partyrgame.service.MessageService;
import com.partyrgame.util.WebsocketConstants;

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
   * 
   * @param body {"username": "timmy7", "content": "woo"}
   * 
   */
  @MessageMapping(WebsocketConstants.CHAT_SEND)
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
