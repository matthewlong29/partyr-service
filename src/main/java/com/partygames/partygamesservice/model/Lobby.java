package com.partygames.partygamesservice.model;

import lombok.Data;

// TODO consider changing this to "Room" and make "Lobby" something else
@Data
public class Lobby {
  private String lobbyName;
  private String playerEmail;
}