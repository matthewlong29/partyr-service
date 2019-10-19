package com.partygames.partygamesservice.model;

import lombok.Data;

@Data
public class BlackHandRole {
  private BlackHandFaction faction;
  private String name;
  private String dayAbilityDescription;
  private String nightAbilityDescription;
  private String attributeDescription;
  private String goalDescription;
}