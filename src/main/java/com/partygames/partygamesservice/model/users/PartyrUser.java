package com.partygames.partygamesservice.model.users;

import java.sql.Timestamp;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PartyrUser {
  private String userName;
  private String email;
  private String firstName;
  private String lastName;
  private String profileImageURL;
  private String userHash;
  private Timestamp joinedDate;
  private OnlineStatus onlineStatus;
  private ReadyStatus readyStatus;
  private int themeID;
  private int age;
  private String country;
}