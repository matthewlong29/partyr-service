package com.partyrgame.blackhandservice.model;

import lombok.Data;

@Data
public class PlayerTurn {
  private String roomName;
  private String username;
  private String attacking;
  private String blocking;
  private String note;
}