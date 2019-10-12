package com.partygames.partygamesservice.model;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
  private String author;
  private Timestamp timeOfMessage = new Timestamp(new Date().getTime());
  private String content;
}
