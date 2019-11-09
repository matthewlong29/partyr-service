package com.partyrgame.service;

import java.util.List;

import com.partyrgame.model.ChatMessage;
import com.partyrgame.roomservice.model.Room;

public interface MessageService {
  public void sendChatMessage(ChatMessage chatMessage);

  public void sendRoomMessage(List<Room> rooms);
}