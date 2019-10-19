package com.partygames.partygamesservice.model;

import java.util.List;

import lombok.Data;

@Data
public class BlackHandSettings {
  private int lengthOfDay;
  private int lengthOfNight;
  private boolean chatOnly; // disables audio and video feature for the game 
  private List<BlackHandPlayerPreferredFaction> playerPreferredFactions;

  @Data
  private static class BlackHandPlayerPreferredFaction {
    private String email; // TODO use PartyrUser instead of just email?
    private BlackHandFaction preferredFaction;
  }
}