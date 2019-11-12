package com.partyrgame.userservice.model;

import java.util.ArrayList;
import java.util.List;

import com.partyrgame.userservice.model.PartyrUser;

import lombok.Data;

@Data
public class Relationships {
  private List<PartyrUser> friendsList = new ArrayList<>();
  private List<PartyrUser> blockedList = new ArrayList<>();
}
