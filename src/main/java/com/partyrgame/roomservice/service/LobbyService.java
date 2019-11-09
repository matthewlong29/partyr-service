package com.partyrgame.roomservice.service;

import java.util.List;

import com.partyrgame.roomservice.model.Room;

public interface LobbyService {
  public int createNewGameRoom(String username, String roomName, String gameName);

  public int joinGameRoom(String username, String roomName);

  public int leaveGameRoom(String username, String roomName);

  public int deleteGameRoom(String roomName);

  public int toggleReadyStatus(String username, String roomName);

  public List<Room> getRooms();
}