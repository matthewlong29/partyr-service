package com.partygames.partygamesservice.dao.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.LobbyDao;
import com.partygames.partygamesservice.dao.impl.mapper.RoomRowMapper;
import com.partygames.partygamesservice.model.Room;

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
  public int createNewGameRoom(String partyrEmail, String roomName, String gameName) {
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
   * joinGameRoom.
   */
  public int joinGameRoom(String partyrEmail, String roomName) {
    String query = "CALL `partyrdb`.`join_black_hand_room`('" + roomName + "', '" + partyrEmail + "');";
    log.info(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to join game lobby {}; error: {}", roomName, e.getMessage());
    }

    return 0;
  }

  /**
   * getRooms.
   */
  public List<Room> getRooms() {
    String query = "CALL `partyrdb`.`get_lobby`();";
    log.info(query);

    return jdbcTemplate.query(query, roomRowMapper);
  }
}
