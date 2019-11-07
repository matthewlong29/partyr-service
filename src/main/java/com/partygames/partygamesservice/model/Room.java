package com.partygames.partygamesservice.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.partygames.partygamesservice.model.ReadyStatus;

import lombok.Data;

@Data
public class Room {
  private String gameRoomName;
  private String gameName;
  private String hostEmail;
  private List<RoomReadyStatus> playersReady = new ArrayList<>();
  private List<RoomReadyStatus> playersNotReady = new ArrayList<>();
  private int numberOfPlayers;
  private boolean gameStarted;
  private Timestamp gameStartTime;
  private Timestamp gameEndTime;

  @Data
  public static class RoomReadyStatus {
    private String userName;
    private ReadyStatus readyStatus;
  }
}