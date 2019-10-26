package com.partygames.partygamesservice.model.relationships;

import lombok.Data;

@Data
public class Relationship {
  private String relatingEmail;
  private String relatedEmail;
  private RelationshipStatus relationshipStatus;
}
