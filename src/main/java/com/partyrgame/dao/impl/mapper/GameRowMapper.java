package com.partyrgame.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partyrgame.model.Game;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class GameRowMapper implements RowMapper<Game> {
  @Override
  public Game mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    Game game = new Game();
    game.setGameName(resultSet.getString("game_name"));
    game.setMinNumberOfPlayers(resultSet.getInt("min_players_num"));
    game.setMaxNumberOfPlayers(resultSet.getInt("max_players_num"));
    game.setMinAge(resultSet.getInt("min_age"));
    game.setAverageGameDuration(resultSet.getInt("average_game_duration"));

    return game;
  }
}