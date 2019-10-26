package com.partygames.partygamesservice.model.blackhand;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class BlackHandFactionRoles {
  private HashMap<BlackHandFaction, List<BlackHandRole>> factionRoles = new HashMap<>();
}