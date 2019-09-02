package com.partygames.partygamesservice.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ThemeStatus {
  DARK(1), LIGHT(2), RETRO(3), LEMON_IN_WATER(4);
  private int themeIndex;
}