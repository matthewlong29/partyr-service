package com.partygames.partygamesservice.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.partygames.partygamesservice.model.users.PartyrUser;

public interface AuthService {
  public PartyrUser googleSignIn(String token);

  public PartyrUser getLoggedInUser(GoogleIdToken idToken);

  public Boolean checkAuthToken(String token);
}