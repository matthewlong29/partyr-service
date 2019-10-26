package com.partygames.partygamesservice.model.blackhand;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.partygames.partygamesservice.model.blackhand.BlackHandRole;
import com.partygames.partygamesservice.model.users.PartyrUser;

import lombok.Data;

@Data
public class BlackHand {
  private int gameInstanceID; // unique to each game
  private Timestamp gameStartTime = new Timestamp(new Date().getTime());
  private List<BlackHandPlayer> playerRoles = new ArrayList<>();

  @Data
  public static class BlackHandPlayer {
    private PartyrUser player;
    private BlackHandRole role;
    private List<BlackHandNote> will = new ArrayList<>();
  }
}