package com.partygames.partygamesservice.model;

import java.util.List;

import lombok.Data;

@Data
public class BlackHandSettings {
  private int lengthOfDay;
  private int lengthOfNight;
  private boolean chatOnly; // disables audio and video feature for the game 
  private List<BlackHandPlayerPreferences> playerPreferences;

  @Data
  public static class BlackHandPlayerPreferences {
    private PartyrUser player;
    private BlackHandFaction preferredFaction;
  }
}