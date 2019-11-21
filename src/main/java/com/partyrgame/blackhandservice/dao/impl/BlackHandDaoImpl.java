package com.partyrgame.blackhandservice.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.partyrgame.blackhandservice.dao.BlackHandDao;
import com.partyrgame.blackhandservice.dao.impl.mapper.BlackHandGameRowMapper;
import com.partyrgame.blackhandservice.dao.impl.mapper.BlackHandNumberOfPlayersRowMapper;
import com.partyrgame.blackhandservice.dao.impl.mapper.BlackHandResultSetExtractor;
import com.partyrgame.blackhandservice.dao.impl.mapper.BlackHandRoleResultSetExtractor;
import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;
import com.partyrgame.blackhandservice.model.BlackHandRole;
import com.partyrgame.blackhandservice.model.PlayerTurn;

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

  @Autowired
  BlackHandGameRowMapper blackHandGameRowMapper;

  @Autowired
  BlackHandResultSetExtractor blackHandResultSetExtractor;

  /**
   * getBlackHandRawDetails;
   */
  public List<BlackHandGame> getBlackHandRawDetails(String roomName) {
    String query = "CALL `partyrdb`.get_black_hand_game('" + roomName + "')";
    log.info(query);

    return jdbcTemplate.query(query, blackHandGameRowMapper);
  }

  /**
   * getBlackHandDetails
   */
  public BlackHand getBlackHandDetails(String roomName) {
    String query = "CALL `partyrdb`.get_black_hand_game('" + roomName + "')";
    log.info(query);

    return jdbcTemplate.query(query, blackHandResultSetExtractor);
  }

  /**
   * setRoleForPlayer.
   */
  public int setRoleForPlayer(String username, String roomName, String roleName) {
    String query = "CALL `partyrdb`.`set_black_hand_role_name`('" + roomName + "', '" + username + "', '" + roleName
        + "')";
    log.info(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to update role name for user {}; error: {}", username, e.getMessage());
    }

    return 0;
  }

  /**
   * setPreferredFaction.
   */
  public int setPreferredFaction(String username, String roomName, String preferredFaction) {
    String query = "CALL `partyrdb`.`set_black_hand_preferred_faction`('" + roomName + "', '" + username + "', '"
        + preferredFaction + "')";
    log.info(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to update faction preference for user {}; error: {}", username, e.getMessage());
    }

    return 0;
  }

  /**
   * selectDisplayName.
   */
  public int selectDisplayName(String username, String roomName, String displayName) {
    String query = "CALL `partyrdb`.`set_black_hand_display_name`('" + roomName + "', '" + username + "', '"
        + displayName + "')";
    log.info(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to update display name for user {}; error: {}", username, e.getMessage());
    }

    return 0;
  }

  /**
   * submitPlayerTurn.
   */
  public int submitPlayerTurn(PlayerTurn turn) {
    String query = "CALL `partyrdb`.`submit_black_hand_player_turn`('" + turn.getRoomName() + "', '"
        + turn.getUsername() + "', '" + turn.getAttacking() + "', '" + turn.getBlocking() + "', '" + turn.getNote()
        + "')";
    log.info(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to submit turn for user {}; error: {}", turn.getUsername(), e.getMessage());
    }

    return 0;
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