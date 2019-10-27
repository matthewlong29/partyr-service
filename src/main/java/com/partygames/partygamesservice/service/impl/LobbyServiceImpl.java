package com.partygames.partygamesservice.service.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.LobbyDao;
import com.partygames.partygamesservice.model.Room;
import com.partygames.partygamesservice.service.LobbyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LobbyServiceImpl implements LobbyService {
  @Autowired
  LobbyDao lobbyDao;

  /**
   * createNewGameRoom.
   */
  public int createNewGameRoom(String partyrEmail, String roomName, String gameName) {
    return lobbyDao.createNewGameRoom(partyrEmail, roomName, gameName);
  }

  /**
   * joinGameRoom.
   */
  public int joinGameRoom(String partyrEmail, String roomName) {
    return lobbyDao.joinGameRoom(partyrEmail, roomName);
  }

  /**
   * getRooms.
   */
  public List<Room> getRooms() {
    return lobbyDao.getRooms();
  }
}