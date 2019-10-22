package com.partygames.partygamesservice.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.partygames.partygamesservice.model.BlackHandFactionRoles.BlackHandRole;

import lombok.Data;

@Data
public class BlackHandStartGame {
  private Timestamp gameStartTime = new Timestamp(new Date().getTime());
  private List<BlackHandPlayer> playerRoles = new ArrayList<>();

  @Data
  public static class BlackHandPlayer {
    private PartyrUser player;
    private BlackHandRole role;
  }
}