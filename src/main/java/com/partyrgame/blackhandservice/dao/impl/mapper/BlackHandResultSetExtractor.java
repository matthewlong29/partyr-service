package com.partyrgame.blackhandservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandPlayer;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BlackHandResultSetExtractor implements ResultSetExtractor<BlackHand> {
  /**
   * extractData.
   */
  @Override
  public BlackHand extractData(ResultSet resultSet) throws SQLException {
    BlackHandGameRowMapper rowMapper = new BlackHandGameRowMapper();
    BlackHand blackHand = new BlackHand();

    while (resultSet.next()) {
      BlackHandGame blackHandGameRawDetails = rowMapper.mapRow(resultSet, resultSet.getRow());
      log.info("row: {}", blackHandGameRawDetails.toString());

      BlackHandPlayer player = new BlackHandPlayer();
      player.setUsername(blackHandGameRawDetails.getUsername());
      player.setDisplayName(blackHandGameRawDetails.getDisplayName());
      player.setPreferredFaction(blackHandGameRawDetails.getPreferredFaction());
      player.setPlayerStatus(blackHandGameRawDetails.getPlayerStatus());
      player.setBlocksAgainst(blackHandGameRawDetails.getBlocksAgainst());
      player.setAttacksAgainst(blackHandGameRawDetails.getAttacksAgainst());
      player.setTurnPriority(blackHandGameRawDetails.getTurnPriority());

      blackHand.setGameStartTime(blackHandGameRawDetails.getGameStartTime());
      blackHand.addPlayer(player);
    }

    return blackHand;
  }
}