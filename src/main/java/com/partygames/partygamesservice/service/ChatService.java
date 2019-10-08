package com.partygames.partygamesservice.service;

import java.util.List;

import com.partygames.partygamesservice.model.Message;

public interface ChatService {
  public List<Message> getMessages();

  public int saveMessage(Message message);

  public void sendMessageToChat(String destination, Message message);
}