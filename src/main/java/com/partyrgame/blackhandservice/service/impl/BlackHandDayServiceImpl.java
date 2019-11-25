package com.partyrgame.blackhandservice.service.impl;

import com.partyrgame.blackhandservice.dao.BlackHandDao;
import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandPlayer;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandTrial;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;
import com.partyrgame.blackhandservice.model.BlackHandPhase;
import com.partyrgame.blackhandservice.model.PlayerTurn;
import com.partyrgame.blackhandservice.service.BlackHandDayService;
import com.partyrgame.blackhandservice.service.BlackHandService;
import com.partyrgame.blackhandservice.service.BlackHandTrialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlackHandDayServiceImpl implements BlackHandDayService {
  @Autowired
  BlackHandService blackHandService;

  @Autowired
  BlackHandTrialService trialService;

  @Autowired
  BlackHandDao dao;

  /**
   * evaluateDay.
   */
  public BlackHand evaluateDay(String roomName) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);
    BlackHandPlayer playerOnTrial = trialService.getPlayerOnTrial(blackHand);

    log.info("[{}] is on trial", playerOnTrial.getUsername());

    BlackHandTrial blackHandTrial = new BlackHandTrial();
    blackHandTrial.setDisplayName(playerOnTrial.getDisplayName());
    blackHandTrial.setGuiltyVotes(0);
    blackHandTrial.setNotGuiltyVotes(0);

    blackHand.setPlayerOnTrial(blackHandTrial);

    BlackHandNumberOfPlayers numOfPlayers = new BlackHandNumberOfPlayers();
    numOfPlayers.setBlackHandTotal(blackHand.getNumOfBlackHandRemaining());
    numOfPlayers.setMonstersTotal(blackHand.getNumOfMonsterRemaining());
    numOfPlayers.setTowniesTotal(blackHand.getNumOfTownieRemaining());

    for (BlackHandPlayer player : blackHand.getAlivePlayers()) {
      log.info("player: [{}]", player);
      if (player.getAttacksAgainst() > player.getBlocksAgainst()) {
        log.info("player [{}] has died", player.getUsername());
        dao.killPlayer(roomName, player.getUsername());
        blackHandService.decrementNumberOfPlayersPerFaction(player.getActualFaction(), numOfPlayers);
      }
    }

    dao.updateBlackHandGame(blackHand.getRoomName(), BlackHandPhase.TRIAL, numOfPlayers.getBlackHandTotal(),
        numOfPlayers.getMonstersTotal(), numOfPlayers.getTowniesTotal());

    return blackHandService.getBlackHandDetails(roomName);
  }

  /**
   * submitPlayerTurn.
   */
  public BlackHand submitPlayerTurn(PlayerTurn turn) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(turn.getRoomName());
    String username = turn.getUsername(); // player making the turn
    BlackHandPlayer player = blackHandService.getBlackHandPlayer(blackHand, username);

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

    dao.submitPlayerTurn(turn);

    return blackHandService.getBlackHandDetails(turn.getRoomName());
  }
}