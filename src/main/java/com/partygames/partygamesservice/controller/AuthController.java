package com.partygames.partygamesservice.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.partygames.partygamesservice.model.PartyrUser;
import com.partygames.partygamesservice.service.AuthService;
import com.partygames.partygamesservice.util.PartyLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class AuthController {
  @Autowired
  AuthService authService;

  /**
   * googleSignIn.
   */
  @PostMapping(value = "/google-authenticate")
  public ResponseEntity<Boolean> googleSignIn(@RequestBody Map<String, String> body, HttpServletResponse res)
      throws Exception {
    try {
      String idToken = body.get("idToken");
      int signInSuccess = authService.googleSignIn(idToken);

      if (signInSuccess > 0) {
        Cookie authCookie = new Cookie("AUTH_ID_TOKEN", idToken);
        authCookie.setPath("/");
        authCookie.setHttpOnly(true);

        Cookie loggedInCookie = new Cookie("LOGGED_IN", "TRUE");
        loggedInCookie.setHttpOnly(true);
        loggedInCookie.setPath("/");

        res.addCookie(authCookie);
        res.addCookie(loggedInCookie);

        return ResponseEntity.ok(true);
      }
    } catch (Exception e) {
      PartyLogger.error(e.getMessage());
    }

    return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
  }

  /**
   * getLoggedInUser.
   */
  @GetMapping(value = "/current-user")
  public PartyrUser getLoggedInUser() {

    return new PartyrUser();
  }
}