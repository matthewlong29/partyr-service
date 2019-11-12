package com.partyrgame.userservice.model;

import lombok.Data;

@Data
public class Relationship {
  private String relatingUsername;
  private String relatedUsername;
  private RelationshipStatus relationshipStatus;
}
