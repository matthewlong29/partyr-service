package com.partyrgame.roomservice.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import com.partyrgame.roomservice.dao.LobbyDao;
import com.partyrgame.roomservice.dao.impl.mapper.RoomRowMapper;
import com.partyrgame.roomservice.model.Room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class LobbyDaoImpl implements LobbyDao {
  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  RoomRowMapper roomRowMapper;

  /**
   * createNewGameRoom.
   */
  public int createNewGameRoom(String username, String roomName, String gameName) {
    String query = "CALL `partyrdb`.`create_room`('" + roomName + "', '" + username + "', '" + gameName + "');";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to create game lobby {}; error: {}", roomName, e.getMessage());
    }

    return 0;
  }

  /**
   * joinGameRoom.
   */
  public int joinGameRoom(String username, String roomName) {
    String query = "CALL `partyrdb`.`join_black_hand_room`('" + roomName + "', '" + username + "');";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to join game lobby {}; error: {}", roomName, e.getMessage());
    }

    return 0;
  }

  /**
   * leaveGameRoom.
   */
  public int leaveGameRoom(String username, String roomName) {
    String query = "CALL `partyrdb`.`leave_black_hand_room`('" + roomName + "', '" + username + "');";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to leave game lobby {}; error: {}", roomName, e.getMessage());
    }

    return 0;
  }

  /**
   * deleteGameRoom.
   */
  public int deleteGameRoom(String roomName) {
    String query = "CALL `partyrdb`.`delete_black_hand_room`('" + roomName + "');";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to delete game lobby {}; error: {}", roomName, e.getMessage());
    }

    return 0;
  }

  /**
   * toggleReadyStatus.
   */
  public int toggleReadyStatus(String username, String roomName) {
    String query = "CALL `partyrdb`.`toggle_ready_status`('" + roomName + "', '" + username + "');";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to leave game lobby {}; error: {}", roomName, e.getMessage());
    }

    return 0;
  }

  /**
   * startGame
   */
  public int startGame(String roomName) {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    String query = "CALL `partyrdb`.`start_game`('" + roomName + "', '" + timestamp + "');";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to leave game lobby {}; error: {}", roomName, e.getMessage());
    }

    return 0;
  }

  /**
   * getRooms.
   */
  public List<Room> getRooms() {
    String query = "CALL `partyrdb`.`get_lobby`();";
    log.debug(query);

    return jdbcTemplate.query(query, roomRowMapper);
  }
}
