package com.partyrgame.userservice.model;

import java.sql.Timestamp;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PartyrUser {
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String profileImageURL;
  private String userHash;
  private Timestamp joinedDate;
  private OnlineStatus onlineStatus;
  private String themeName;
  private int age;
  private String country;
}