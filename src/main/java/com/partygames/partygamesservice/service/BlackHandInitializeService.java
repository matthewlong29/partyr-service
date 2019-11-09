package com.partygames.partygamesservice.service;

import java.util.HashMap;
import java.util.List;

import com.partygames.partygamesservice.model.blackhand.BlackHand;
import com.partygames.partygamesservice.model.blackhand.BlackHandFaction;
import com.partygames.partygamesservice.model.blackhand.BlackHandNumberOfPlayers;
import com.partygames.partygamesservice.model.blackhand.BlackHandRole;
import com.partygames.partygamesservice.model.blackhand.BlackHandSettings;

public interface BlackHandInitializeService {
  public BlackHand startGame(BlackHandSettings blackHandSettings);

  public HashMap<BlackHandFaction, List<BlackHandRole>> getBlackHandRoles();

  public BlackHandNumberOfPlayers getBlackHandNumberOfPlayers(int playerTotal);

  public List<String> getPlayers(BlackHandSettings blackHandSettings);
}