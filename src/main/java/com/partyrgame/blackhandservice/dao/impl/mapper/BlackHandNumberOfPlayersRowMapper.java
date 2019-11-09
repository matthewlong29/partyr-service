package com.partyrgame.blackhandservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BlackHandNumberOfPlayersRowMapper implements RowMapper<BlackHandNumberOfPlayers> {
  /**
   * mapRow.
   */
  @Override
  public BlackHandNumberOfPlayers mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    BlackHandNumberOfPlayers blackHandRequiredNumberOfPlayers = new BlackHandNumberOfPlayers();
    blackHandRequiredNumberOfPlayers.setMonstersTotal(resultSet.getInt("monster_total"));
    blackHandRequiredNumberOfPlayers.setBlackHandTotal(resultSet.getInt("black_hand_total"));
    blackHandRequiredNumberOfPlayers.setTowniesTotal(resultSet.getInt("townie_total"));

    return blackHandRequiredNumberOfPlayers;
  }
}
