package com.partyrgame.blackhandservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandGame;
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
    blackHandGame.setGameRoomName(resultSet.getString("game_room_name"));
    blackHandGame.setUsername(resultSet.getString("username"));
    blackHandGame.setDisplayName(resultSet.getString("display_name"));
    blackHandGame.setReadyStatus(ReadyStatus.valueOf(resultSet.getString("ready_status")));
    blackHandGame.setPreferredFaction(checkForFactionPreference(resultSet.getString("preferred_faction")));
    blackHandGame.setRoleName(resultSet.getString("role_name"));
    blackHandGame.setPlayerStatus(PlayerStatus.valueOf(resultSet.getString("player_status")));
    blackHandGame.setNote(resultSet.getString("note"));
    blackHandGame.setNumberOfBlocksAgainst(resultSet.getInt("number_of_blocks_against"));
    blackHandGame.setNumberOfKillStrikesAgainst(resultSet.getInt("number_of_kill_strikes_against"));
    blackHandGame.setTurnPriority(resultSet.getInt("turn_priority"));

    return blackHandGame;
  }

  /**
   * checkForFactionPreference: setting preferred faction is optional so this may
   * be null in the database.
   */
  private BlackHandFaction checkForFactionPreference(String preferredFaction) {
    if (null != preferredFaction) {
      return BlackHandFaction.valueOf(preferredFaction);
    }

    return null;
  }
}