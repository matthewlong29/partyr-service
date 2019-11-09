package com.partyrgame.blackhandservice.model;

import lombok.Data;

@Data
public class BlackHandNumberOfPlayers {
  private int monstersTotal;
  private int blackHandTotal;
  private int towniesTotal;

  public BlackHandNumberOfPlayers() {
    this.monstersTotal = 0;
    this.blackHandTotal = 0;
    this.towniesTotal = 0;
  }

  public void incrementMonstersTotal() {
    this.monstersTotal++;
  }

  public void incrementBlackHandTotal() {
    this.blackHandTotal++;
  }

  public void incrementTowniesTotal() {
    this.towniesTotal++;
  }
}