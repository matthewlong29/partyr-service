package com.partygames.partygamesservice.model;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class BlackHandFactionRoles {
  private HashMap<BlackHandFaction, List<BlackHandRole>> factionRoles = new HashMap<>();

  @Data
  public static class BlackHandRole {
    private BlackHandFaction faction;
    private String name;
    private String dayAbilityDescription;
    private String nightAbilityDescription;
    private String attributeDescription;
    private String goalDescription;
  }
}