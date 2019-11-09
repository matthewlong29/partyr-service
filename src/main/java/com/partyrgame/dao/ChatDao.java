package com.partyrgame.dao;

import java.util.List;

import com.partyrgame.model.ChatMessage;

public interface ChatDao {
  public int insertChatMessage(ChatMessage chatMessage);

  public List<ChatMessage> getChatMessages();
}