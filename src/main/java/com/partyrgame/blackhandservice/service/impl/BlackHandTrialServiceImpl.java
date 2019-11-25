package com.partyrgame.blackhandservice.service.impl;

import java.util.Comparator;

import com.partyrgame.blackhandservice.dao.BlackHandDao;
import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHand.BlackHandPlayer;
import com.partyrgame.blackhandservice.service.BlackHandService;
import com.partyrgame.blackhandservice.service.BlackHandTrialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlackHandTrialServiceImpl implements BlackHandTrialService {
  @Autowired
  BlackHandService blackHandService;

  @Autowired
  BlackHandDao dao;

  /**
   * submitVote.
   */
  public BlackHand submitPlayerVote(String roomName, String username, String vote) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);
    BlackHandPlayer player = blackHandService.getBlackHandPlayer(blackHand, username);

    if (player.isHasVoted()) {
      log.info("player [{}] has already voted", username);

      return blackHand;
    }

    dao.submitPlayerVote(roomName, username, vote);

    return blackHand;
  }

  /**
   * evaluateTrial.
   */
  public BlackHand evaluateTrial(String roomName) {
    BlackHand blackHand = blackHandService.getBlackHandDetails(roomName);

    return blackHand;
  }

  /**
   * getPlayerOnTrial
   */
  public BlackHandPlayer getPlayerOnTrial(BlackHand blackHand) {
    BlackHandPlayer playerOnTrial = blackHand.getAlivePlayers().stream()
        .max(Comparator.comparing(player -> player.getTimesVotedToBePlacedOnTrial())).get();

    dao.putPlayerOnTrial(playerOnTrial.getUsername(), blackHand.getRoomName());

    return playerOnTrial;
  }

  /**
   * getVotes: gets number of votes to kill or number of votes to spare.
   */
  public int getVotes(BlackHand blackHand, String voteAction) {
    if (voteAction.equals("kill")) {
      Math.toIntExact(blackHand.getAlivePlayers().stream().filter(player -> player.isHasAttacked()).count());
    } else {
      Math.toIntExact(blackHand.getAlivePlayers().stream().filter(player -> !player.isHasAttacked()).count());
    }

    return 0;
  }
}