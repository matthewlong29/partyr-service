package com.partyrgame.roomservice.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Room {
  private String gameRoomName;
  private String gameName;
  private String hostUsername;
  private List<String> playersReady = new ArrayList<>();
  private List<String> playersNotReady = new ArrayList<>();
  private int numberOfPlayers;
  private boolean gameStarted;
  private Timestamp gameStartTime;
  private Timestamp gameEndTime;
}