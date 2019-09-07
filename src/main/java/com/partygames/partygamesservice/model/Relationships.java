package com.partygames.partygamesservice.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Relationships {
  private List<User> friendsList = new ArrayList<>();
  private List<User> blockedList = new ArrayList<>();
}
