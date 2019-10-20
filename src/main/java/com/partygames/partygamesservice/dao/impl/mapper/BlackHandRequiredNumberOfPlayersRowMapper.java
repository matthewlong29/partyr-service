package com.partygames.partygamesservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partygames.partygamesservice.model.BlackHandRequiredNumberOfPlayers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BlackHandRequiredNumberOfPlayersRowMapper implements RowMapper<BlackHandRequiredNumberOfPlayers> {
  /**
   * mapRow.
   */
  @Override
  public BlackHandRequiredNumberOfPlayers mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    BlackHandRequiredNumberOfPlayers blackHandRequiredNumberOfPlayers = new BlackHandRequiredNumberOfPlayers();
    blackHandRequiredNumberOfPlayers.setMonstersTotal(resultSet.getInt("monster_total"));
    blackHandRequiredNumberOfPlayers.setBlackHandTotal(resultSet.getInt("black_hand_total"));
    blackHandRequiredNumberOfPlayers.setTowniesTotal(resultSet.getInt("townie_total"));

    return blackHandRequiredNumberOfPlayers;
  }
}
