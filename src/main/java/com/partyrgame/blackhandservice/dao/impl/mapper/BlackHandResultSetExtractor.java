package com.partyrgame.blackhandservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandPlayer;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHandRole;
import com.partyrgame.blackhandservice.model.PlayerStatus;
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

    log.debug("faction roles: {}", factionRoles);

    while (resultSet.next()) {
      final BlackHandGame blackHandGameRawDetails = rowMapper.mapRow(resultSet, resultSet.getRow());
      log.debug("black hand game row: {}", blackHandGameRawDetails.toString());

      final Optional<BlackHandPlayer> blackHandPlayer = isPlayerPresent(blackHand,
          blackHandGameRawDetails.getPlayerStatus(), blackHandGameRawDetails.getUsername());

      if (!blackHandPlayer.isPresent()) {
        BlackHandPlayer player = new BlackHandPlayer();
        player.setUsername(blackHandGameRawDetails.getUsername());
        player.setDisplayName(blackHandGameRawDetails.getDisplayName());
        player.setPreferredFaction(blackHandGameRawDetails.getPreferredFaction());
        player.setBlocksAgainst(blackHandGameRawDetails.getBlocksAgainst());
        player.setAttacksAgainst(blackHandGameRawDetails.getAttacksAgainst());
        player.setTimesVotedToBePlacedOnTrial(blackHandGameRawDetails.getTimesVotedToBePlacedOnTrial());
        player.setTurnPriority(blackHandGameRawDetails.getTurnPriority());
        player.setActualFaction(blackHandGameRawDetails.getActualFaction());
        player.setHasAttacked(blackHandGameRawDetails.isHasAttacked());
        player.setHasBlocked(blackHandGameRawDetails.isHasBlocked());
        player.addNote(blackHandGameRawDetails.getNote());

        if (!blackHandGameRawDetails.isTurnCompleted()
            && blackHandGameRawDetails.getPlayerStatus().equals(PlayerStatus.ALIVE)) {
          blackHand.addPlayerNotCompletedTurn(
              player.getDisplayName() == null ? player.getUsername() : player.getDisplayName());
        }

        if (null != player.getActualFaction() && null != blackHandGameRawDetails.getRoleName()) {
          final List<BlackHandRole> roles = factionRoles.get(player.getActualFaction());
          final BlackHandRole blackHandRole = roles.stream()
              .filter(role -> role.getName().equals(blackHandGameRawDetails.getRoleName())).findFirst().get();

          player.setRole(blackHandRole);
        }

        blackHand.setGameStartTime(blackHandGameRawDetails.getGameStartTime());
        blackHand.setRoomName(blackHandGameRawDetails.getGameRoomName());
        blackHand.setPhase(blackHandGameRawDetails.getPhase());

        if (blackHandGameRawDetails.getPlayerStatus().equals(PlayerStatus.ALIVE)) {
          blackHand.addPlayer(player);
        } else {
          blackHand.addDeadPlayer(player);
        }

      } else {
        blackHandPlayer.get().addNote(blackHandGameRawDetails.getNote());
      }
    }

    return blackHand;
  }

  /**
   * isPlayerPresent: checks incoming player. if the player has already been added
   * to the Black Hand game object, then do not add redundant player, but add note
   * if exists.
   */
  private Optional<BlackHandPlayer> isPlayerPresent(BlackHand blackHand, PlayerStatus playerStatus, String username) {
    if (playerStatus.equals(PlayerStatus.ALIVE)) {
      return blackHand.getAlivePlayers().stream().filter(player -> player.getUsername().equals(username)).findFirst();
    } else {
      return blackHand.getDeadPlayers().stream().filter(player -> player.getUsername().equals(username)).findFirst();
    }
  }
}