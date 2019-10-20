package com.partygames.partygamesservice.dao.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.BlackHandDao;
import com.partygames.partygamesservice.dao.impl.mapper.BlackHandNumberOfPlayersRowMapper;
import com.partygames.partygamesservice.dao.impl.mapper.BlackHandRoleRowMapper;
import com.partygames.partygamesservice.model.BlackHandNumberOfPlayers;
import com.partygames.partygamesservice.model.BlackHandRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BlackHandDaoImpl implements BlackHandDao {
  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  BlackHandRoleRowMapper blackHandRoleRowMapper;

  @Autowired
  BlackHandNumberOfPlayersRowMapper blackHandRequiredNumberOfPlayersRowMapper;

  /**
   * getBlackHandRoles.
   */
  public List<BlackHandRole> getBlackHandRoles() {
    String query = "CALL `partyrdb`.`get_black_hand_roles`();";
    log.info(query);

    return jdbcTemplate.query(query, blackHandRoleRowMapper);
  }

  /**
   * getBlackHandNumberOfPlayers.
   */
  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int totalNumberOfPlayers) {
    String query = "CALL `partyrdb`.`get_black_hand_required_number_of_players`('" + totalNumberOfPlayers + "');";
    log.info(query);

    return jdbcTemplate.query(query, blackHandRequiredNumberOfPlayersRowMapper).get(0);
  }
}