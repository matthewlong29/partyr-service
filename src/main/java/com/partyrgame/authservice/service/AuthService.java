package com.partyrgame.authservice.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.partyrgame.userservice.model.PartyrUser;

public interface AuthService {
  public PartyrUser googleSignIn(String token);

  public PartyrUser getLoggedInUser(GoogleIdToken idToken);

  public Boolean checkAuthToken(String token);
}