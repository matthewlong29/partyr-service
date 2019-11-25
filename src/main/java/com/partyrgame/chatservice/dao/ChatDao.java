package com.partyrgame.chatservice.dao;

import java.util.List;

import com.partyrgame.chatservice.model.ChatMessage;

public interface ChatDao {
  public int insertChatMessage(ChatMessage chatMessage);

  public List<ChatMessage> getChatMessages();
}