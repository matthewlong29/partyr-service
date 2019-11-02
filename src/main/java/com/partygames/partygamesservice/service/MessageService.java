package com.partygames.partygamesservice.service;

import java.util.List;

import com.partygames.partygamesservice.model.ChatMessage;
import com.partygames.partygamesservice.model.Room;

public interface MessageService {
  public void sendChatMessage(ChatMessage chatMessage);

  public void sendRoomMessage(List<Room> rooms);
}