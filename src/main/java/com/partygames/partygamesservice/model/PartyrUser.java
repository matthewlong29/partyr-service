package com.partygames.partygamesservice.model;

import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
public class PartyrUser {
  private String userName;
  private String email;
  private String firstName;
  private String lastName;
  private String pictureUrl;
  private String userHash;
  private Timestamp joinedDate;
  private OnlineStatus onlineStatus;
  private int themeID;
  private int age;
  private String country;
}