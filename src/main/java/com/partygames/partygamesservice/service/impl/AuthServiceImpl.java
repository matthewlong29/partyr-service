package com.partygames.partygamesservice.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.partygames.partygamesservice.dao.UsersDao;
import com.partygames.partygamesservice.model.PartyrUser;
import com.partygames.partygamesservice.service.AuthService;
import com.partygames.partygamesservice.util.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UsersDao usersDao;

    NetHttpTransport transport = new NetHttpTransport();
    JsonFactory jsonFactory = new JacksonFactory();

    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory).setAudience(
            Collections.singletonList("276174427953-o7q6mv623adttteep82an71rs4bgge0r.apps.googleusercontent.com"))
            .build();

    @Override
    public int googleSignIn(String token) {
        try {
            if (token != null) {
                GoogleIdToken idToken = verifier.verify(token);
                if (idToken != null) {
                    Payload payload = idToken.getPayload();
                    PartyrUser user = new PartyrUser();
                    user.setEmail(payload.getEmail());
                    user.setFirstName((String) payload.get("given_name"));
                    user.setLastName((String) payload.get("family_name"));
                    user.setPictureUrl((String) payload.get("picture"));
                    String uncodedUserHash = user.getEmail().concat(user.getFirstName()).concat(user.getLastName());
                    user.setUserHash(SecurityUtils.encodeHashSha256(uncodedUserHash));
                    return usersDao.createUserIfNotExist(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}