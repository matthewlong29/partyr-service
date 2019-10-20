package com.partygames.partygamesservice.service.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.BlackHandDao;
import com.partygames.partygamesservice.model.BlackHandRole;
import com.partygames.partygamesservice.model.BlackHandSettings;
import com.partygames.partygamesservice.model.BlackHandSettings.BlackHandPlayerPreferences;
import com.partygames.partygamesservice.model.BlackHandStartGame;
import com.partygames.partygamesservice.service.BlackHandService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlackHandServiceImpl implements BlackHandService {
  @Autowired
  BlackHandDao blackHandDao;

  /**
   * startGame.
   */
  public BlackHandStartGame startGame(BlackHandSettings blackHandSettings) {
    log.info(blackHandSettings.toString());
    List<BlackHandRole> availableRoles = getBlackHandRoles();
    BlackHandStartGame blackHandStartGame = new BlackHandStartGame();

    List<BlackHandPlayerPreferences> preferences = blackHandSettings.getPlayerPreferences();
    log.info(preferences.toString());

    for (BlackHandPlayerPreferences preference : preferences) {
      log.info(preferences.toString());
    }

    return blackHandStartGame;
  }

  /**
   * getBlackHandRoles.
   */
  public List<BlackHandRole> getBlackHandRoles() {
    return blackHandDao.getBlackHandRoles();
  }
}