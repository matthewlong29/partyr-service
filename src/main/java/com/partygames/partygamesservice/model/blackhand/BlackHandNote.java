package com.partygames.partygamesservice.model.blackhand;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BlackHandNote {
  private String note;
  private Timestamp time;
}