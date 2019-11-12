package com.partyrgame.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ThemeStatus {
  DARK(1), LIGHT(2), RETRO(3), LEMON_IN_WATER(4);

  private int themeIndex;
}
