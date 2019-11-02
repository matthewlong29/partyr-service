package com.partygames.partygamesservice.service;

import java.util.List;

import com.partygames.partygamesservice.model.ChatMessage;

public interface ChatService {
  public List<ChatMessage> getChatMessages();

  public int saveChatMessage(ChatMessage chatMessage);
}