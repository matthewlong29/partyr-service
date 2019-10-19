package com.partygames.partygamesservice.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class BlackHandStartGame {
  private Timestamp gameStartTime = new Timestamp(new Date().getTime());
  private List<HashMap<PartyrUser, BlackHandRole>> playerRoles = new ArrayList<HashMap<PartyrUser, BlackHandRole>>();
}