package com.partyrgame.blackhandservice.model;

import com.partyrgame.userservice.model.ReadyStatus;

import lombok.Data;

@Data
public class BlackHandGame {
  private String gameRoomName;
  private String username;
  private String displayName;
  private BlackHandFaction preferredFaction;
  private String roleName;
  private PlayerStatus playerStatus;
  private ReadyStatus readyStatus;
  private String note;
  private int blocksAgainst;
  private int attacksAgainst;
  private boolean turnCompleted;
  private String attackingPlayer;
  private String blockingPlayer;
  private int turnPriority;
}