package com.partyrgame.chatservice.service;

import java.util.List;

import com.partyrgame.chatservice.model.ChatMessage;


public interface ChatService {
  public List<ChatMessage> getChatMessages();

  public int saveChatMessage(ChatMessage chatMessage);
}