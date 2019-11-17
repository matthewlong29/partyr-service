package com.partyrgame.blackhandservice.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.partyrgame.blackhandservice.util.BlackHandConstants;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlackHand {
  private String gameRoomName;
  private Timestamp gameStartTime; // TODO: set in startGame method of BlackHandInitializeService
  private String phase; // NOTE DAY or NIGHT (start with DAY phase)
  private List<String> playersTurnRemaining; // list of players who have not completed their turn
                                             // once this is empty, or timer reaches 0 (frontend). evaluate end of phase
  private List<PlayerTurn> playerTurns = new ArrayList<>();
  private int numOfBlackHandRemaining;
  private int numOfTownieRemaining;
  private int numOfMonsterRemaining;
  private BlackHandNote lastPlayerToDie;
  private BlackHandFaction winningFaction;
  private BlackHandTrial playerOnTrial;
  private BlackHandSettings settings = new BlackHandSettings();
  private List<BlackHandPlayer> players = new ArrayList<>();

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class BlackHandPlayer {
    private String username;
    private String displayName;
    private BlackHandFaction preferredFaction;
    private PlayerStatus playerStatus; // ALIVE or DEAD
    private int numberOfBlocksAgainst;
    private int numberOfKillStrikesAgainst;
    private int turnPriority;
    private BlackHandRole role;
    private List<BlackHandNote> notes = new ArrayList<>();
  }

  /**
   * addPlayer: adds player to BlackHandPlayers list.
   */
  public void addPlayer(BlackHandPlayer player) {
    this.players.add(player);
  }

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class BlackHandTrial {
    private String displayName;
    private int votesToKill;
    private int votesToSpare;
  }

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class BlackHandSettings {
    private String gameRoomName;
    private int lengthOfDay; // minutes (defaulted to 5)
    private int lengthOfNight; // minutes (defaulted to 3)
    private boolean chatOnly; // disables audio and video feature for the game

    /**
     * BlackHandSettings: initialize various setting values (defaults).
     */
    public BlackHandSettings() {
      this.lengthOfDay = 5;
      this.lengthOfNight = 3;
      this.chatOnly = false;
    }
  }

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class BlackHandNote {
    private String username;
    private String note;
    private Timestamp time = new Timestamp(new Date().getTime());
  }

  /**
   * BlackHand: initialize various black hand values.
   */
  public BlackHand() {
    this.phase = BlackHandConstants.SETUP; // game starts in setup phase
  }
}