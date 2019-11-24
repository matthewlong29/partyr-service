package com.partyrgame.blackhandservice.model;

import java.sql.Timestamp;

import com.partyrgame.userservice.model.ReadyStatus;

import lombok.Data;

@Data
public class BlackHandGame {
  private Timestamp gameStartTime;
  private String gameRoomName;
  private String username;
  private String displayName;
  private String phase;
  private BlackHandFaction preferredFaction;
  private BlackHandFaction actualFaction;
  private String roleName;
  private PlayerStatus playerStatus;
  private ReadyStatus readyStatus;
  private String note;
  private int blocksAgainst;
  private int attacksAgainst;
  private boolean hasAttacked;
  private boolean hasBlocked;
  private boolean turnCompleted;
  private String attackingPlayer;
  private String blockingPlayer;
  private int turnPriority;
}