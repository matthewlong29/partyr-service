package com.partygames.partygamesservice.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.partygames.partygamesservice.dao.BlackHandDao;
import com.partygames.partygamesservice.dao.impl.mapper.BlackHandNumberOfPlayersRowMapper;
import com.partygames.partygamesservice.dao.impl.mapper.BlackHandRoleResultSetExtractor;
import com.partygames.partygamesservice.model.Lobby;
import com.partygames.partygamesservice.model.blackhand.BlackHandFaction;
import com.partygames.partygamesservice.model.blackhand.BlackHandRole;
import com.partygames.partygamesservice.model.blackhand.BlackHandNumberOfPlayers;

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
  BlackHandRoleResultSetExtractor blackHandRoleResultSetExtractor;

  @Autowired
  BlackHandNumberOfPlayersRowMapper blackHandRequiredNumberOfPlayersRowMapper;

  /**
   * createNewGameLobby.
   */
  public int createNewGameLobby(Lobby lobby) {
    String query = "CALL `partyrdb`.`create_black_hand_lobby`('" + lobby.getLobbyName() + "', '" + lobby.getPlayerEmail()
    + "');";
    log.info(query);

    return jdbcTemplate.update(query);
  }

  /**
   * joinGameLobby.
   */
  public int joinGameLobby(Lobby lobby) {
    String query = "CALL `partyrdb`.`join_black_hand_lobby`('" + lobby.getLobbyName() + "', '" + lobby.getPlayerEmail()
        + "');";
    log.info(query);

    return jdbcTemplate.update(query);
  }

  /**
   * getBlackHandRoles.
   */
  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles() {
    String query = "CALL `partyrdb`.`get_black_hand_roles`();";
    log.info(query);

    return jdbcTemplate.query(query, blackHandRoleResultSetExtractor);
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