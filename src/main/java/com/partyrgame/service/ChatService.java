package com.partyrgame.service;

import java.util.List;

import com.partyrgame.model.ChatMessage;

public interface ChatService {
  public List<ChatMessage> getChatMessages();

  public int saveChatMessage(ChatMessage chatMessage);
}