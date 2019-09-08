package com.partygames.partygamesservice.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Relationships {
  private List<PartyrUser> friendsList = new ArrayList<>();
  private List<PartyrUser> blockedList = new ArrayList<>();
}
