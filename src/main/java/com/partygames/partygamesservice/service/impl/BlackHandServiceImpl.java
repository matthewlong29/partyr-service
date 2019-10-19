package com.partygames.partygamesservice.service.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.BlackHandDao;
import com.partygames.partygamesservice.model.BlackHandRole;
import com.partygames.partygamesservice.model.BlackHandSettings;
import com.partygames.partygamesservice.model.BlackHandStartGame;
import com.partygames.partygamesservice.service.BlackHandService;
import com.partygames.partygamesservice.util.PartyLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackHandServiceImpl implements BlackHandService {
  @Autowired
  BlackHandDao blackHandDao;

  /**
   * startGame.
   */
  public BlackHandStartGame startGame(BlackHandSettings blackHandSettings) {
    PartyLogger.info(blackHandSettings.toString());
    BlackHandStartGame blackHandStartGame = new BlackHandStartGame();

    return blackHandStartGame;
  }

  /**
   * getBlackHandRoles.
   */
  public List<BlackHandRole> getBlackHandRoles() {
    return blackHandDao.getBlackHandRoles();
  }
}