package com.partygames.partygamesservice.dao.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.GamesDao;
import com.partygames.partygamesservice.dao.impl.mapper.GameRowMapper;
import com.partygames.partygamesservice.model.Game;

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
    String query = "select * from games";
    List<Game> games = jdbcTemplate.query(query, gameRowMapper);

    return games;
  }
}