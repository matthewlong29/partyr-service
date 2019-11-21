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
  private String roomName;
  private Timestamp gameStartTime; // TODO: set in startGame method of BlackHandInitializeService
  private String phase; // NOTE DAY or NIGHT (start with DAY phase)
  private List<String> playersTurnRemaining = new ArrayList<>(); // list of players who have not completed their turn
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
    private int blocksAgainst;
    private int attacksAgainst;
    private int turnPriority; // TODO: remove this as players can go in any order. the evaluate phases just check if block > attack
    private BlackHandRole role;
    private List<BlackHandNote> notes = new ArrayList<>();
  }

  /**
   * addPlayer: adds player to BlackHandPlayers list.
   */
  public void addPlayer(BlackHandPlayer player) {
    this.players.add(player);
  }

  /**
   * addPlayerNotCompletedTurn: if a player hasn't completed his turn yet they're
   * added to this list.
   */
  public void addPlayerNotCompletedTurn(String displayName) {
    playersTurnRemaining.add(displayName);
  }

  /**
   * removePlayerWhenCompletedTurn: when a player completes his turn remove him
   * from the list.
   */
  public void removePlayerWhenCompletedTurn(String displayName) {
    playersTurnRemaining.remove(displayName);
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