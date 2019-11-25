package com.partyrgame.blackhandservice.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.partyrgame.blackhandservice.util.BlackHandConstants;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlackHand {
  private String roomName;
  private Timestamp gameStartTime;
  private String phase; // NOTE DAY, NIGHT, or SETUP (SETUP -> DAY -> NIGHT -> DAY -> NIGHT -> ...)
  private List<String> playersTurnRemaining = new ArrayList<>(); // list of players who have not completed their turn
  private int numOfBlackHandRemaining;
  private int numOfTownieRemaining;
  private int numOfMonsterRemaining;
  private List<String> lastPlayerToDie;
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
    private BlackHandFaction actualFaction;
    private PlayerStatus playerStatus; // ALIVE or DEAD
    private int blocksAgainst;
    private int attacksAgainst;
    private int timesVotedToBePlacedOnTrial;
    private boolean hasAttacked;
    private boolean hasBlocked;
    private int turnPriority;
    private BlackHandRole role;
    private List<String> notes = new ArrayList<>();

    /**
     * addNote.
     */
    public void addNote(String note) {
      this.notes.add(note);
    }
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
  public void addPlayerNotCompletedTurn(String username) {
    playersTurnRemaining.add(username);
  }

  /**
   * removePlayerWhenCompletedTurn: when a player completes his turn remove him
   * from the list.
   */
  public void removePlayerWhenCompletedTurn(String username) {
    playersTurnRemaining.remove(username);
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

  /**
   * BlackHand: initialize various black hand values.
   */
  public BlackHand() {
    this.phase = BlackHandConstants.SETUP; // game starts in setup phase
  }
}