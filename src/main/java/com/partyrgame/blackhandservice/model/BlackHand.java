package com.partyrgame.blackhandservice.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlackHand {
  private String roomName;
  private Timestamp gameStartTime;
  private BlackHandPhase phase; // NOTE DAY, NIGHT, or SETUP (SETUP -> DAY -> NIGHT -> DAY -> NIGHT -> ...)
  private List<String> playersTurnRemaining = new ArrayList<>(); // list of players who have not completed their turn
  private List<String> playersVoteRemaining = new ArrayList<>(); // list of players who have not completed their turn
  private int numOfBlackHandRemaining;
  private int numOfTownieRemaining;
  private int numOfMonsterRemaining;
  private BlackHandFaction winningFaction;
  private BlackHandTrial playerOnTrial;
  private BlackHandSettings settings = new BlackHandSettings();
  private List<BlackHandPlayer> deadPlayers = new ArrayList<>();
  private List<BlackHandPlayer> alivePlayers = new ArrayList<>();

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class BlackHandPlayer {
    private String username;
    private String displayName;
    private BlackHandFaction preferredFaction;
    private BlackHandFaction actualFaction;
    private int blocksAgainst;
    private int attacksAgainst;
    private int timesVotedToBePlacedOnTrial;
    private boolean hasVoted;
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
   * addPlayer: adds player to alive BlackHandPlayers list.
   */
  public void addAlivePlayer(BlackHandPlayer player) {
    this.alivePlayers.add(player);
  }

  /**
   * addPlayer: adds player to dead BlackHandPlayers list.
   */
  public void addDeadPlayer(BlackHandPlayer player) {
    this.deadPlayers.add(player);
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

  /**
   * addPlayerNotCompletedTurn: if a player hasn't completed his vote yet they're
   * added to this list.
   */
  public void addPlayerNotCompletedVote(String username) {
    playersVoteRemaining.add(username);
  }

  /**
   * removePlayerWhenCompletedVote: when a player completes his vote remove him
   * from the list.
   */
  public void removePlayerWhenCompletedVote(String username) {
    playersVoteRemaining.remove(username);
  }

  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class BlackHandTrial {
    private String displayName;
    private String username;
    private int guiltyVotes;
    private int notGuiltyVotes;
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
    this.phase = BlackHandPhase.SETUP; // game starts in setup phase
  }
}