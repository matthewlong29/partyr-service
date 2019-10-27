package com.partygames.partygamesservice.service;

public interface LobbyService {
  public int createNewGameLobby(String partyrEmail, String roomName, String gameName);

  public int joinGameLobby(String partyrEmail, String roomName);
}