package com.partyrgame.blackhandservice.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BlackHandSettings {
  private String gameRoomName;
  private int lengthOfDay; // minutes (defaulted to 5)
  private int lengthOfNight; // minutes (defaulted to 3)
  private boolean chatOnly; // disables audio and video feature for the game
  private List<BlackHandPlayerPreferences> playerPreferences = new ArrayList<>();

  @Data
  public static class BlackHandPlayerPreferences {
    private String username;
    private String displayName;
    private BlackHandFaction preferredFaction;
  }

  public BlackHandSettings() {
    this.lengthOfDay = 5;
    this.lengthOfNight = 3;
    this.chatOnly = false;
  }
}