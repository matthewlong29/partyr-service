package com.partyrgame.blackhandservice.service.impl;

import java.util.Comparator;

import com.partyrgame.blackhandservice.dao.BlackHandDao;
import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandPlayer;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandTrial;
import com.partyrgame.blackhandservice.model.BlackHandPhase;
import com.partyrgame.blackhandservice.model.PlayerTurn;
import com.partyrgame.blackhandservice.service.BlackHandDayService;
import com.partyrgame.blackhandservice.service.BlackHandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlackHandDayServiceImpl implements BlackHandDayService {
  @Autowired
  BlackHandService blackHandService;

  @Autowired
  BlackHandDao blackHandDao;

  /**
   * evaluateDay.
   */
  public BlackHand evaluateDay(String roomName) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);
    BlackHandPlayer playerOnTrial = getPlayerOnTrial(blackHand);

    log.info("[{}] is on trial", playerOnTrial.getUsername());

    BlackHandTrial blackHandTrial = new BlackHandTrial();
    blackHandTrial.setDisplayName(playerOnTrial.getDisplayName());
    blackHandTrial.setVotesToKill(0);
    blackHandTrial.setVotesToSpare(0);

    blackHand.setPlayerOnTrial(blackHandTrial);

    for (BlackHandPlayer player : blackHand.getAlivePlayers()) {
      log.info("player: [{}]", player);
      if (player.getAttacksAgainst() > player.getBlocksAgainst()) {
        log.info("player [{}] has died", player.getUsername());
        blackHandDao.killPlayer(roomName, player.getUsername());
      }
    }

    blackHandDao.updateBlackHandGame(blackHand.getRoomName(), BlackHandPhase.TRIAL);

    return blackHandService.getBlackHandDetails(roomName);
  }

  /**
   * submitPlayerTurn.
   */
  public BlackHand submitPlayerTurn(PlayerTurn turn) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(turn.getRoomName());
    String username = turn.getUsername(); // player making the turn
    BlackHandPlayer player = getBlackHandPlayer(blackHand, username);

    if (player.isHasAttacked()) {
      log.info("player [{}] has already attack", username);
      turn.setAttacking(null); // player has already attacked
    } else if (!player.getRole().isCanAttack()) {
      log.info("player [{}] cannot attack as a [{}]", username, player.getRole().getName());
      turn.setAttacking(null); // players cant attack with this role
    }

    if (player.isHasBlocked()) {
      log.info("player [{}] has already blocked", username);
      turn.setBlocking(null); // player has already blocked
    } else if (!player.getRole().isCanBlock()) {
      log.info("player [{}] cannot block as a [{}]", username, player.getRole().getName());
      turn.setBlocking(null); // player cant block with this role
    }

    blackHandDao.submitPlayerTurn(turn);

    return blackHandService.getBlackHandDetails(turn.getRoomName());
  }

  /**
   * getBlackHandPlayer.
   */
  private BlackHandPlayer getBlackHandPlayer(BlackHand blackHand, String username) {
    return blackHand.getAlivePlayers().stream().filter(player -> player.getUsername().equals(username)).findFirst()
        .get();
  }

  /**
   * getPlayerOnTrial
   */
  private BlackHandPlayer getPlayerOnTrial(BlackHand blackHand) {
    BlackHandPlayer playerOnTrial = blackHand.getAlivePlayers().stream()
        .max(Comparator.comparing(player -> player.getTimesVotedToBePlacedOnTrial())).get();

    return playerOnTrial;
  }
}