package com.partygames.partygamesservice.service;

import java.util.HashMap;
import java.util.List;

import com.partygames.partygamesservice.model.BlackHandFaction;
import com.partygames.partygamesservice.model.BlackHandFactionRoles.BlackHandRole;
import com.partygames.partygamesservice.model.BlackHandNumberOfPlayers;
import com.partygames.partygamesservice.model.BlackHandSettings;
import com.partygames.partygamesservice.model.BlackHandStartGame;

public interface BlackHandService {
  public BlackHandStartGame startGame(BlackHandSettings blackHandSettings);

  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles();

  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int playerTotal);
}