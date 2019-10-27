package com.partygames.partygamesservice.service;

import java.util.List;

import com.partygames.partygamesservice.model.Room;

public interface LobbyService {
  public int createNewGameRoom(String partyrEmail, String roomName, String gameName);

  public int joinGameRoom(String partyrEmail, String roomName);

  public List<Room> getRooms();
}