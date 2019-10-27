package com.partygames.partygamesservice.dao.impl;

import com.partygames.partygamesservice.dao.LobbyDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class LobbyDaoImpl implements LobbyDao {
  @Autowired
  JdbcTemplate jdbcTemplate;

  /**
   * createNewGameLobby.
   */
  public int createNewGameLobby(String partyrEmail, String roomName, String gameName) {
    String query = "CALL `partyrdb`.`create_room`('" + roomName + "', '" + partyrEmail + "', '" + gameName
        + "');";
    log.info(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to create game lobby {}; error: {}", roomName, e.getMessage());
    }

    return 0;
  }

  /**
   * joinGameLobby.
   */
  public int joinGameLobby(String partyrEmail, String roomName) {
    String query = "CALL `partyrdb`.`join_black_hand_room`('" + roomName + "', '" + partyrEmail + "');";
    log.info(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to join game lobby {}; error: {}", roomName, e.getMessage());
    }

    return 0;
  }
}
