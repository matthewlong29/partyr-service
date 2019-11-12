package com.partyrgame.blackhandservice.service;

import java.util.HashMap;
import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandNumberOfPlayers;
import com.partyrgame.blackhandservice.model.BlackHandRole;
import com.partyrgame.blackhandservice.model.BlackHandSettings;

public interface BlackHandInitializeService {
  public BlackHand startGame(BlackHandSettings blackHandSettings);

  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles();

  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int playerTotal);

  public List<String> getPlayers(BlackHandSettings blackHandSettings);
}