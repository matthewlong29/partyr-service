package com.partygames.partygamesservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.partygames.partygamesservice.model.Room;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RoomRowMapper implements RowMapper<Room> {
  /**
   * mapRow.
   */
  @Override
  public Room mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    log.info(resultSet.toString());

    Room room = new Room();
    room.setGameRoomName(resultSet.getString("game_room_name"));
    room.setGameName(resultSet.getString("game_name"));
    room.setHostUsername(resultSet.getString("host_username"));
    room.setPlayersReady(extractPlayerList(resultSet.getString("players_ready")));
    room.setPlayersNotReady(extractPlayerList(resultSet.getString("players_not_ready")));
    room.setNumberOfPlayers(resultSet.getInt("number_of_players"));
    room.setGameStarted(resultSet.getInt("game_started") == 0 ? true : false);
    room.setGameStartTime(resultSet.getTimestamp("game_start_time"));
    room.setGameEndTime(resultSet.getTimestamp("game_end_time"));

    return room;
  }

  /**
   * extractPlayerList: extracts a comma separated list of players into an array.
   * If players string is null then return empty list.
   */
  private List<String> extractPlayerList(String players) {
    log.info("extracting: {}", players);

    if (players != null) {
      return Arrays.asList(players.split(","));
    }

    return new ArrayList<>();
  }
}