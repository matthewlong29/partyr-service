package com.partyrgame.blackhandservice.model;

import lombok.Data;

@Data
public class BlackHandRole {
  private BlackHandFaction faction;
  private String name;
  private String dayAbilityDescription;
  private String nightAbilityDescription;
  private String attributeDescription;
  private String goalDescription;
  private String spritePath;
  private int rolePriority;
  private boolean canDayKill;
  private boolean canNightKill;
  private boolean canDayBlock;
  private boolean canNightBlock;
}