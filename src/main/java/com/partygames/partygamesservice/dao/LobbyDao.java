package com.partygames.partygamesservice.dao;

public interface LobbyDao {
  public int createNewGameLobby(String partyrEmail, String roomName, String gameName);

  public int joinGameLobby(String partyrEmail, String roomName);
}