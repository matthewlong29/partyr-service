package com.partyrgame.blackhandservice.service;

import java.util.HashMap;
import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHand;
import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandRole;

public interface BlackHandInitializeService {
  public BlackHand startGame(String roomName);

  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles();
}