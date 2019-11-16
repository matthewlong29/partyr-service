package com.partyrgame.socketservice.service;

import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.chatservice.model.ChatMessage;
import com.partyrgame.roomservice.model.Room;

public interface MessageService {
  public void sendChatMessage(ChatMessage chatMessage);

  public void sendRoomMessage(List<Room> rooms);

  public void sendBlackHandMessage(BlackHand blackHand);
}