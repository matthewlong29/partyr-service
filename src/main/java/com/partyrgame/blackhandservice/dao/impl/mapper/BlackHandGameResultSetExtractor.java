package com.partyrgame.blackhandservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partyrgame.blackhandservice.model.BlackHand;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BlackHandGameResultSetExtractor implements ResultSetExtractor<BlackHand> {
  /**
   * extractData.
   */
  @Override
  public BlackHand extractData(ResultSet resultSet) throws SQLException {
    BlackHandGameRowMapper blackHandGameRowMapper = new BlackHandGameRowMapper();
    BlackHand blackHand = new BlackHand();

    while (resultSet.next()) {
      // TODO convert BlackHandGame to BlackHand
    }

    return blackHand;
  }
}