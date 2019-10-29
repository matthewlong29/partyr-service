package com.partygames.partygamesservice.service;

import com.partygames.partygamesservice.model.ChatMessage;

public interface MessageService {
  public void sendMessage(String destination, ChatMessage chatMessage);

  // public void sendMessage(String destination, );
}