package com.partyrgame.blackhandservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandPlayer;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHandRole;
import com.partyrgame.blackhandservice.service.BlackHandInitializeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BlackHandResultSetExtractor implements ResultSetExtractor<BlackHand> {
  @Autowired
  BlackHandInitializeService blackHandInitializeService;

  /**
   * extractData.
   */
  @Override
  public BlackHand extractData(final ResultSet resultSet) throws SQLException {
    final BlackHandGameRowMapper rowMapper = new BlackHandGameRowMapper();
    final BlackHand blackHand = new BlackHand();
    final HashMap<BlackHandFaction, List<BlackHandRole>> factionRoles = blackHandInitializeService.getBlackHandRoles();

    log.info("|||| factionRoles: {}", factionRoles);

    while (resultSet.next()) {
      final BlackHandGame blackHandGameRawDetails = rowMapper.mapRow(resultSet, resultSet.getRow());
      log.info("row: {}", blackHandGameRawDetails.toString());

      final BlackHandPlayer player = new BlackHandPlayer();
      player.setUsername(blackHandGameRawDetails.getUsername());
      player.setDisplayName(blackHandGameRawDetails.getDisplayName());
      player.setPreferredFaction(blackHandGameRawDetails.getPreferredFaction());
      player.setPlayerStatus(blackHandGameRawDetails.getPlayerStatus());
      player.setBlocksAgainst(blackHandGameRawDetails.getBlocksAgainst());
      player.setAttacksAgainst(blackHandGameRawDetails.getAttacksAgainst());
      player.setTurnPriority(blackHandGameRawDetails.getTurnPriority());
      player.setActualFaction(blackHandGameRawDetails.getActualFaction());

      if (!blackHandGameRawDetails.isTurnCompleted()) {
        blackHand.addPlayerNotCompletedTurn(player.getDisplayName());
      }

      log.info("\n||||| faction: {}; \n||||| role name: {}; \n||||| roles: {}", player.getActualFaction(),
          blackHandGameRawDetails.getRoleName(), factionRoles.get(player.getActualFaction()));

      if (null != player.getActualFaction() && null != blackHandGameRawDetails.getRoleName()) {
        final List<BlackHandRole> roles = factionRoles.get(player.getActualFaction());

        final BlackHandRole blackHandRole = roles.stream()
            .filter(role -> role.getName().equals(blackHandGameRawDetails.getRoleName())).findFirst().get();

        log.info("!!!role: {}", blackHandRole);

        player.setRole(blackHandRole);
      }

      blackHand.setGameStartTime(blackHandGameRawDetails.getGameStartTime());
      blackHand.setRoomName(blackHandGameRawDetails.getGameRoomName());
      blackHand.setPhase(blackHandGameRawDetails.getPhase());
      blackHand.addPlayer(player);
    }

    return blackHand;
  }
}