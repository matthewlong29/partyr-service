package com.partyrgame.blackhandservice.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.partyrgame.blackhandservice.util.BlackHandConstants;

import lombok.Data;

@Data
public class BlackHand {
  private String gameRoomName;
  private Timestamp gameStartTime = new Timestamp(new Date().getTime());
  private int lengthOfDay; // minutes (defaulted to 5)
  private int lengthOfNight; // minutes (defaulted to 3)
  private boolean chatOnly;
  private String phase; // NOTE DAY or NIGHT (start with DAY phase)
  private List<String> playersTurnRemaining; // list of players who have not completed their turn
                                             // once this is empty, or timer reaches 0 (frontend). evaluate end of phase
  private List<PlayerTurn> playerTurns = new ArrayList<>();
  private int numOfBlackHandRemaining;
  private int numOfTownieRemaining;
  private int numOfMonsterRemaining;
  private List<BlackHandNote> lastPlayerToDie = new ArrayList<>();
  private BlackHandFaction winningFaction;
  private BlackHandTrial playerOnTrial = new BlackHandTrial();
  private List<BlackHandPlayer> players = new ArrayList<>();

  @Data
  public static class BlackHandPlayer {
    private String username;
    private String displayName;
    private boolean isAlive;
    private int numberOfBlocks;
    private int numberOfKillStrikes;
    private int turnPriority;
    private BlackHandRole role;
    private List<BlackHandNote> notes = new ArrayList<>();
  }

  @Data
  public static class BlackHandTrial {
    private String displayName;
    private int votesToKill;
    private int votesToSpare;
  }

  /**
   * 
   */
  public BlackHand() {
    this.lengthOfDay = 5;
    this.lengthOfNight = 3;
    this.phase = BlackHandConstants.DAY;
  }
}