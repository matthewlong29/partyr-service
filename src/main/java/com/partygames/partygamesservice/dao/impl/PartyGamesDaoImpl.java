package com.partygames.partygamesservice.dao.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.PartyGamesDao;
import com.partygames.partygamesservice.dao.impl.mapper.UserRowMapper;
import com.partygames.partygamesservice.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PartyGamesDaoImpl implements PartyGamesDao {
  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  UserRowMapper userRowMapper;

  /**
   * getAllUsers.
   */
  public List<User> getAllUsers() {
    String query = "select * from Users";
    List<User> users = jdbcTemplate.query(query, userRowMapper);

    return users;
  }
}