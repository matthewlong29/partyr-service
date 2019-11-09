package com.partyrgame.dao.impl;

import java.util.List;

import com.partyrgame.dao.GamesDao;
import com.partyrgame.dao.impl.mapper.GameRowMapper;
import com.partyrgame.model.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GamesDaoImpl implements GamesDao {
  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  GameRowMapper gameRowMapper;

  /**
   * getAllGames.
   */
  public List<Game> getAllGames() {
    String query = "CALL `partyrdb`.`get_games`();";
    List<Game> games = jdbcTemplate.query(query, gameRowMapper);

    return games;
  }

  /**
   * getGameByName.
   */
  public Game getGameByName(String gameName) {
    String query = "CALL `partyrdb`.`get_game`('" + gameName + "');";

    return jdbcTemplate.query(query, gameRowMapper).get(0);
  }
}