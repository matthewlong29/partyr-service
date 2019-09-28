package com.partygames.partygamesservice.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
  private PartyrUser author;
  private String sender; // TODO: use PartyrUser
  private String content;
  private LocalDateTime timeOfMessage = LocalDateTime.now();
  private MessageType type;

  public enum MessageType {
    CHAT, JOIN, LEAVE
  }
}
