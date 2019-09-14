package com.partygames.partygamesservice.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PartyrUser {
  private String userName;
  private String email;
  private String password;
  private Timestamp joinedDate;
  private OnlineStatus onlineStatus;
  private int themeID;
  private int age;
  private String country;
}