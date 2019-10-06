package com.partygames.partygamesservice.service;

import com.partygames.partygamesservice.model.PartyrUser;

public interface AuthService {
  int googleSignIn(String token);

  public PartyrUser getLoggedInUser(String token);
}