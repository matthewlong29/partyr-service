package com.partygames.partygamesservice.model.relationships;

import lombok.Data;

@Data
public class Relationship {
  private String relatingName;
  private String relatedName;
  private RelationshipStatus relationshipStatus;
}
