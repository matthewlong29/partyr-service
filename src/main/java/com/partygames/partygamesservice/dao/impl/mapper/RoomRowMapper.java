package com.partygames.partygamesservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import com.partygames.partygamesservice.model.Room;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class RoomRowMapper implements RowMapper<Room> {
  @Override
  public Room mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    Room room = new Room();
    room.setGameRoomName(resultSet.getString("game_room_name"));
    room.setGameName(resultSet.getString("game_name"));
    room.setHostEmail(resultSet.getString("host_email"));
    // room.setPlayers(Arrays.asList(resultSet.getString("partyr_users").split(",")));
    // room.setPlayersReady(Arrays.asList(resultSet.getString("partyr_users").split(",")));
    // room.setPlayersNotReady(Arrays.asList(resultSet.getString("partyr_users").split(",")));
    room.setNumberOfPlayers(resultSet.getInt("number_of_players"));
    room.setGameStarted(resultSet.getInt("game_started") == 0 ? true : false);
    room.setGameStartTime(resultSet.getTimestamp("game_start_time"));
    room.setGameEndTime(resultSet.getTimestamp("game_end_time"));

    return room;
  }
}