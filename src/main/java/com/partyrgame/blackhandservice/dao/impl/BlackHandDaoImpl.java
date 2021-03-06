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
import com.partyrgame.blackhandservice.model.BlackHandPhase;
import com.partyrgame.blackhandservice.model.BlackHandRole;
import com.partyrgame.blackhandservice.model.PlayerStatus;
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
  BlackHandRoleResultSetExtractor roleResultSetExtractor;

  @Autowired
  BlackHandNumberOfPlayersRowMapper requiredNumberOfPlayersRowMapper;

  @Autowired
  BlackHandGameRowMapper gameRowMapper;

  @Autowired
  BlackHandResultSetExtractor resultSetExtractor;

  /**
   * getBlackHandRawDetails;
   */
  public List<BlackHandGame> getBlackHandRawDetails(String roomName) {
    String query = "CALL `partyrdb`.get_black_hand_game('" + roomName + "')";
    log.debug(query);

    return jdbcTemplate.query(query, gameRowMapper);
  }

  /**
   * getBlackHandDetails
   */
  public BlackHand getBlackHandDetails(String roomName) {
    String query = "CALL `partyrdb`.get_black_hand_game('" + roomName + "')";
    log.debug(query);

    return jdbcTemplate.query(query, resultSetExtractor);
  }

  /**
   * updateBlackHandGameForPlayer.
   */
  public int updateBlackHandGameForPlayer(String username, String roomName, String roleName, String faction,
      int turnPriority) {
    String query = "CALL `partyrdb`.`update_black_hand_game_start_for_player`('" + roomName + "', '" + username + "', '"
        + roleName + "', '" + faction + "', '" + turnPriority + "')";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to update black hand game data for user {}; error: {}", username, e.getMessage());
    }

    return 0;
  }

  /**
   * setPreferredFaction.
   */
  public int setPreferredFaction(String username, String roomName, String preferredFaction) {
    String query = "CALL `partyrdb`.`set_black_hand_preferred_faction`('" + roomName + "', '" + username + "', '"
        + preferredFaction + "')";
    log.debug(query);

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
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to update display name for user {}; error: {}", username, e.getMessage());
    }

    return 0;
  }

  /**
   * updateBlackHandGame.
   */
  public int updateBlackHandGame(String roomName, BlackHandPhase phase, int numOfBlackHand, int numOfMonster,
      int numOfTownie) {
    String query = "CALL `partyrdb`.`update_black_hand_game`('" + roomName + "', '" + phase + "', '" + numOfBlackHand
        + "', '" + numOfMonster + "', '" + numOfTownie + "')";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to update black hand game for room {}; error: {}", roomName, e.getMessage());
    }

    return 0;
  }

  /**
   * submitPlayerTurn.
   */
  public int submitPlayerTurn(PlayerTurn turn) {
    log.debug("turn: {}", turn);

    String query = "CALL `partyrdb`.`submit_black_hand_player_turn`('" + turn.getRoomName() + "', '"
        + turn.getUsername() + "', '" + turn.getAttacking() + "', '" + turn.getBlocking() + "', '"
        + turn.getPlaceOnTrial() + "', '" + turn.getNote() + "')";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to submit turn for user {}; error: {}", turn.getUsername(), e.getMessage());
    }

    return 0;
  }

  /**
   * submitPlayerVote.
   */
  public int submitPlayerVote(String roomName, String username, String vote) {
    log.debug("vote: {}", vote);

    String query = "CALL `partyrdb`.`submit_black_hand_player_vote`('" + roomName + "', '" + username + "', '" + vote
        + "')";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to submit vote for user {}; error: {}", username, e.getMessage());
    }

    return 0;
  }

  /**
   * putPlayerOnTrial
   */
  public int putPlayerOnTrial(String username, String roomName) {
    String query = "CALL `partyrdb`.`put_player_on_trial`('" + roomName + "', '" + username + "')";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to put player on trial for user {}; error: {}", username, e.getMessage());
    }

    return 0;
  }

  /**
   * killPlayer.
   */
  public int killPlayer(String roomName, String username) {
    String query = "CALL `partyrdb`.`kill_player`('" + roomName + "', '" + username + "', '" + PlayerStatus.DEAD + "')";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to submit update player status for user {}; error: {}", username, e.getMessage());
    }

    return 0;
  }

  /**
   * getBlackHandRoles.
   */
  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles() {
    String query = "CALL `partyrdb`.`get_black_hand_roles`();";
    log.debug(query);

    return jdbcTemplate.query(query, roleResultSetExtractor);
  }

  /**
   * resetGameCycle.
   */
  public int resetGameCycle(String roomName) {
    String query = "CALL `partyrdb`.`reset_black_hand_game_cycle`('" + roomName + "')";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to reset black hand game cycle for room {}; error: {}", roomName, e.getMessage());
    }

    return 0;
  }

  /**
   * getBlackHandNumberOfPlayers.
   */
  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int totalNumberOfPlayers) {
    String query = "CALL `partyrdb`.`get_black_hand_required_number_of_players`('" + totalNumberOfPlayers + "');";
    log.debug(query);

    return jdbcTemplate.query(query, requiredNumberOfPlayersRowMapper).get(0);
  }
}