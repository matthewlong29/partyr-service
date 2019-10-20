package com.partygames.partygamesservice.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import lombok.Data;

@Data
public class BlackHandStartGame {
  private Timestamp gameStartTime = new Timestamp(new Date().getTime());
  private HashMap<PartyrUser, BlackHandRole> playerRoles = new HashMap<PartyrUser, BlackHandRole>();
}