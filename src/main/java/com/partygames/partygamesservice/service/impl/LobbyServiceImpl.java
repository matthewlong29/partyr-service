package com.partygames.partygamesservice.service.impl;

import com.partygames.partygamesservice.dao.LobbyDao;
import com.partygames.partygamesservice.service.LobbyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LobbyServiceImpl implements LobbyService {
  @Autowired
  LobbyDao lobbyDao;

  /**
   * createNewGameLobby.
   */
  public int createNewGameLobby(String partyrEmail, String roomName, String gameName) {
    return lobbyDao.createNewGameLobby(partyrEmail, roomName, gameName);
  }

  /**
   * joinGameLobby.
   */
  public int joinGameLobby(String partyrEmail, String roomName) {
    return lobbyDao.joinGameLobby(partyrEmail, roomName);
  }
}