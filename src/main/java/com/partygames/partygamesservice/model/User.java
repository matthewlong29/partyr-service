package com.partygames.partygamesservice.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class User {
  private String userName;
  private String email;
  private String password; // TODO: hash
  private Timestamp joinedDate;
  private int themeID;
  private int age;
}