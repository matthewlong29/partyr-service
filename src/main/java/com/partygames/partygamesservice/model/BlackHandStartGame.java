package com.partygames.partygamesservice.model;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class BlackHandStartGame {
  private Timestamp gameStartTime = new Timestamp(new Date().getTime());
}