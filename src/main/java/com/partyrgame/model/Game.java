package com.partyrgame.model;

import lombok.Data;

@Data
public class Game {
  private String gameName;
  private int minNumberOfPlayers;
  private int maxNumberOfPlayers;
  private int minAge;
  private int averageGameDuration;
}