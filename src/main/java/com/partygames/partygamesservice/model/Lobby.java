package com.partygames.partygamesservice.model;

import java.util.ArrayList;
import java.util.List;

import io.opencensus.common.Timestamp;
import lombok.Data;

@Data
public class Lobby {
  private String gameRoomName;
  private String gameName;
  private String hostEmail;
  private List<String> players = new ArrayList<>();
  private int numberOfPlayers;
  private boolean gameStarted;
  private Timestamp gameStartTime;
  private Timestamp gameEndTime;
}