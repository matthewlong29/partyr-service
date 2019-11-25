package com.partyrgame.blackhandservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHandPhase;
import com.partyrgame.blackhandservice.model.PlayerStatus;
import com.partyrgame.userservice.model.ReadyStatus;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BlackHandGameRowMapper implements RowMapper<BlackHandGame> {
  /**
   * mapRow.
   */
  @Override
  public BlackHandGame mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    BlackHandGame blackHandGame = new BlackHandGame();
    blackHandGame.setGameStartTime(resultSet.getTimestamp("game_start_time"));
    blackHandGame.setGameRoomName(resultSet.getString("room_name"));
    blackHandGame.setUsername(resultSet.getString("username"));
    blackHandGame.setPhase(BlackHandPhase.valueOf(resultSet.getString("phase")));
    blackHandGame.setDisplayName(resultSet.getString("display_name"));
    blackHandGame.setReadyStatus(ReadyStatus.valueOf(resultSet.getString("ready_status")));
    blackHandGame.setPreferredFaction(checkForFaction(resultSet.getString("preferred_faction")));
    blackHandGame.setActualFaction(checkForFaction(resultSet.getString("actual_faction")));
    blackHandGame.setRoleName(resultSet.getString("role_name"));
    blackHandGame.setPlayerStatus(PlayerStatus.valueOf(resultSet.getString("player_status")));
    blackHandGame.setNote(resultSet.getString("note"));
    blackHandGame.setBlocksAgainst(resultSet.getInt("blocks_against"));
    blackHandGame.setAttacksAgainst(resultSet.getInt("attacks_against"));
    blackHandGame.setTimesVotedToBePlacedOnTrial(resultSet.getInt("times_voted_to_be_placed_on_trial"));
    blackHandGame.setTurnPriority(resultSet.getInt("turn_priority"));
    blackHandGame.setTurnCompleted(convertToBoolean(resultSet.getInt("turn_completed")));
    blackHandGame.setVoteCompleted(convertToBoolean(resultSet.getInt("vote_completed")));
    blackHandGame.setGuiltyVotes(resultSet.getInt("guilty_votes"));
    blackHandGame.setNotGuiltyVotes(resultSet.getInt("not_guilty_votes"));
    blackHandGame.setHasAttacked(convertToBoolean(resultSet.getInt("has_attacked")));
    blackHandGame.setHasBlocked(convertToBoolean(resultSet.getInt("has_blocked")));

    return blackHandGame;
  }

  /**
   * checkForFactionPreference: setting preferred faction is optional so this may
   * be null in the database.
   */
  private BlackHandFaction checkForFaction(String preferredFaction) {
    if (null != preferredFaction) {
      return BlackHandFaction.valueOf(preferredFaction);
    }

    return null;
  }

  /**
   * convertToBoolean: converts 1 to true and 0 to false.
   */
  private boolean convertToBoolean(int value) {
    return value == 1 ? true : false;
  }
}