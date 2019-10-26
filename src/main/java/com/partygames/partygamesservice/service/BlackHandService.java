package com.partygames.partygamesservice.service;

import java.util.HashMap;
import java.util.List;

import com.partygames.partygamesservice.model.blackhand.BlackHandFaction;
import com.partygames.partygamesservice.model.blackhand.BlackHandRole;
import com.partygames.partygamesservice.model.blackhand.BlackHandNumberOfPlayers;
import com.partygames.partygamesservice.model.blackhand.BlackHandSettings;
import com.partygames.partygamesservice.model.blackhand.BlackHandStartGame;

public interface BlackHandService {
  public BlackHandStartGame startGame(BlackHandSettings blackHandSettings);

  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles();

  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int playerTotal);
}