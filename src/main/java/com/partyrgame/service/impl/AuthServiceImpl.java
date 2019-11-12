package com.partyrgame.service.impl;

import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.partyrgame.userservice.dao.UsersDao;
import com.partyrgame.userservice.model.PartyrUser;
import com.partyrgame.service.AuthService;
import com.partyrgame.util.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
  @Autowired
  UsersDao usersDao;

  NetHttpTransport transport = new NetHttpTransport();
  JsonFactory jsonFactory = new JacksonFactory();

  GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
      .setAudience(
          Collections.singletonList("276174427953-o7q6mv623adttteep82an71rs4bgge0r.apps.googleusercontent.com"))
      .build();

  /**
   * googleSignIn.
   */
  @Override
  public PartyrUser googleSignIn(String token) {
    PartyrUser user = null;

    try {
      if (token != null) {
        GoogleIdToken idToken = verifier.verify(token);
        user = getLoggedInUser(idToken);

        if (idToken != null && user != null) {
          Payload payload = idToken.getPayload();
          user.setEmail(payload.getEmail());
          user.setFirstName((String) payload.get("given_name"));
          user.setLastName((String) payload.get("family_name"));
          user.setProfileImageURL((String) payload.get("picture"));

          String uncodedUserHash = user.getEmail().concat(user.getFirstName()).concat(user.getLastName());
          user.setUserHash(SecurityUtils.encodeHashSha256(uncodedUserHash));

          usersDao.createUser(user);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    return user; // TODO throw exception instead?
  }

  /**
   * getLoggedInUser.
   * 
   * NOTE not thread safe.
   */
  public PartyrUser getLoggedInUser(GoogleIdToken idToken) {
    if (idToken != null) {
      Payload payload = idToken.getPayload();

      try {
        return usersDao.getUserByEmail(payload.getEmail());
      } catch (Exception e) {
        log.error("Could not find user with that email" + payload.getEmail() + "." + e.getMessage());
      }
    }

    return new PartyrUser(); // TODO throw exception instead?
  }

  @Override
  public Boolean checkAuthToken(String token) {
    try {
      GoogleIdToken idToken = verifier.verify(token);
      return idToken != null;
    } catch (Exception e) {
      log.error("Error verifying auth token");
    }
    return false;
  }
}
