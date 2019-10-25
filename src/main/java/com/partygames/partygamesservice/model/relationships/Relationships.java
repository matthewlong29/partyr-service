package com.partygames.partygamesservice.model.relationships;

import java.util.ArrayList;
import java.util.List;

import com.partygames.partygamesservice.model.users.PartyrUser;

import lombok.Data;

@Data
public class Relationships {
  private List<PartyrUser> friendsList = new ArrayList<>();
  private List<PartyrUser> blockedList = new ArrayList<>();
}
