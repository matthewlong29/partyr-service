package com.partyrgame.blackhandservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandPlayer;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandTrial;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandGame;
import com.partyrgame.blackhandservice.model.BlackHandPhase;
import com.partyrgame.blackhandservice.model.BlackHandRole;
import com.partyrgame.blackhandservice.model.PlayerStatus;
import com.partyrgame.blackhandservice.service.BlackHandInitializeService;
import com.partyrgame.blackhandservice.service.BlackHandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BlackHandResultSetExtractor implements ResultSetExtractor<BlackHand> {
  @Autowired
  BlackHandInitializeService initializeService;

  @Autowired
  BlackHandService blackHandService;

  /**
   * extractData.
   */
  @Override
  public BlackHand extractData(final ResultSet resultSet) throws SQLException {
    final BlackHandGameRowMapper rowMapper = new BlackHandGameRowMapper();
    final HashMap<BlackHandFaction, List<BlackHandRole>> factionRoles = initializeService.getBlackHandRoles();

    final BlackHand blackHand = new BlackHand();
    blackHand.setNumOfBlackHandRemaining(0);
    blackHand.setNumOfTownieRemaining(0);
    blackHand.setNumOfMonsterRemaining(0);

    while (resultSet.next()) {
      final BlackHandGame gameRow = rowMapper.mapRow(resultSet, resultSet.getRow());
      blackHand.setPhase(gameRow.getPhase());
      blackHand.setRoomName(gameRow.getGameRoomName());

      final Optional<BlackHandPlayer> blackHandPlayer = isPlayerPresent(blackHand, gameRow.getPlayerStatus(),
          gameRow.getUsername());

      if (!blackHand.getPhase().equals(BlackHandPhase.SETUP)) {
        if (!blackHandPlayer.isPresent()) {
          BlackHandPlayer player = createPlayer(gameRow);

          if (!gameRow.isTurnCompleted() && gameRow.getPlayerStatus().equals(PlayerStatus.ALIVE)
              && gameRow.getPhase().equals(BlackHandPhase.DAY)) {
            blackHand.addPlayerNotCompletedTurn(
                player.getDisplayName() == null ? player.getUsername() : player.getDisplayName());
          } else if (!gameRow.isVoteCompleted() && gameRow.getPlayerStatus().equals(PlayerStatus.ALIVE)
              && gameRow.getPhase().equals(BlackHandPhase.TRIAL)) {
            blackHand.addPlayerNotCompletedVote(
                player.getDisplayName() == null ? player.getUsername() : player.getDisplayName());
          }

          final List<BlackHandRole> roles = factionRoles.get(player.getActualFaction());
          final BlackHandRole blackHandRole = roles.stream()
              .filter(role -> role.getName().equals(gameRow.getRoleName())).findFirst().get();
          player.setRole(blackHandRole);

          blackHand.setGameStartTime(gameRow.getGameStartTime());

          if (gameRow.getPlayerStatus().equals(PlayerStatus.ALIVE)) {
            updatePlayersRemainingPerFaction(blackHand, gameRow.getActualFaction());
            blackHand.addPlayer(player);
          } else {
            blackHand.addDeadPlayer(player);
          }

        } else {
          updatePlayersRemainingPerFaction(blackHand, gameRow.getActualFaction());
          blackHandPlayer.get().addNote(gameRow.getNote());
        }
      } else {
        if (!blackHandPlayer.isPresent()) {
          BlackHandPlayer player = createPlayer(gameRow);

          blackHand.addPlayer(player);
        }
      }
    }

    if (!blackHand.getPhase().equals(BlackHandPhase.SETUP)) {
      checkForWinningFaction(blackHand);
    }

    if (blackHand.getPhase().equals(BlackHandPhase.TRIAL)) {
      BlackHandPlayer playerOnTrial = blackHandService.getPlayerOnTrial(blackHand);

      BlackHandTrial blackHandTrial = new BlackHandTrial();
      blackHandTrial.setDisplayName(playerOnTrial.getDisplayName());
      blackHandTrial.setVotesToKill(blackHandService.getVotes(blackHand, "kill"));
      blackHandTrial.setVotesToSpare(blackHandService.getVotes(blackHand, "spare"));

      blackHand.removePlayerWhenCompletedVote(playerOnTrial.getUsername());
      blackHand.setPlayerOnTrial(blackHandTrial);
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

  /**
   * createPlayer: builds a player object with data obtained from database.
   */
  private BlackHandPlayer createPlayer(BlackHandGame gameRow) {
    BlackHandPlayer player = new BlackHandPlayer();
    player.setUsername(gameRow.getUsername());
    player.setDisplayName(gameRow.getDisplayName());
    player.setPreferredFaction(gameRow.getPreferredFaction());
    player.setBlocksAgainst(gameRow.getBlocksAgainst());
    player.setAttacksAgainst(gameRow.getAttacksAgainst());
    player.setTimesVotedToBePlacedOnTrial(gameRow.getTimesVotedToBePlacedOnTrial());
    player.setTurnPriority(gameRow.getTurnPriority());
    player.setActualFaction(gameRow.getActualFaction());
    player.setHasAttacked(gameRow.isHasAttacked());
    player.setHasBlocked(gameRow.isHasBlocked());
    player.addNote(gameRow.getNote());

    return player;
  }

  /**
   * updatePlayersRemainingPerFaction: count number of players alive per faction.
   */
  private void updatePlayersRemainingPerFaction(BlackHand blackHand, BlackHandFaction faction) {
    switch (faction) {
    case BlackHand:
      blackHand.setNumOfBlackHandRemaining(blackHand.getNumOfBlackHandRemaining() + 1);
      break;
    case Monster:
      blackHand.setNumOfMonsterRemaining(blackHand.getNumOfMonsterRemaining() + 1);
      break;
    case Townie:
      blackHand.setNumOfTownieRemaining(blackHand.getNumOfTownieRemaining() + 1);
      break;
    default:
      break;
    }
  }

  /**
   * checkForWinningFaction: if only one faction remains then they are the winning
   * faction.
   */
  private void checkForWinningFaction(BlackHand blackHand) {
    int monsters = blackHand.getNumOfMonsterRemaining();
    int townies = blackHand.getNumOfTownieRemaining();
    int blackHands = blackHand.getNumOfBlackHandRemaining();

    if (monsters > 0 && townies == 0 && blackHands == 0) {
      log.info("monsters win!");
      blackHand.setWinningFaction(BlackHandFaction.Monster);
    } else if (monsters == 0 && townies > 0 && blackHands == 0) {
      log.info("townies win!");
      blackHand.setWinningFaction(BlackHandFaction.Townie);
    } else if (monsters == 0 && townies > 0 && blackHands == 0) {
      log.info("black hand wins!");
      blackHand.setWinningFaction(BlackHandFaction.BlackHand);
    }
  }
}