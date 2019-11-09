package com.partygames.partygamesservice.model.blackhand;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.partygames.partygamesservice.model.blackhand.BlackHandRole;

import lombok.Data;

@Data
public class BlackHand {
  private String gameRoomName;
  private Timestamp gameStartTime = new Timestamp(new Date().getTime());
  private String phase; // NOTE DAY or NIGHT
  private List<String> playersTurnRemaining; // list of players who have not completed their turn
                                             // once this is empty, or timer reaches 0. evaluate end of phase
  private BlackHandNote lastPlayerToDieWill = new BlackHandNote();
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
    private List<BlackHandNote> will = new ArrayList<>();
  }

  @Data
  public static class BlackHandTrial {
    private String displayName;
    private int votesToKill;
    private int votesToSpare;
  }
}