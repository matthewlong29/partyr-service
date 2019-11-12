package com.partyrgame.blackhandservice.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BlackHandNote {
  private String username;
  private String note;
  private Timestamp time;
}