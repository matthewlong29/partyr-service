package com.partygames.partygamesservice.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.partygames.partygamesservice.model.PartyrUser;

public interface AuthService {
  int googleSignIn(String token);

  public PartyrUser getLoggedInUser(String token) throws IOException, GeneralSecurityException;
}