package com.partygames.partygamesservice.dao;

import java.util.List;

import com.partygames.partygamesservice.model.ChatMessage;

public interface ChatDao {
  public int insertChatMessage(ChatMessage chatMessage);

  public List<ChatMessage> getChatMessages();
}