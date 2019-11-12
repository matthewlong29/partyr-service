package com.partyrgame.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.partyrgame.userservice.model.PartyrUser;
import com.partyrgame.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
      PartyrUser user = authService.googleSignIn(idToken);

      log.info(user.toString()); // TODO: change this method return back to int or bool!!

      if (user != null) {
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
      log.error(e.getMessage());
    }

    return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
  }

  /**
   * checkAuthToken.
   */
  @GetMapping(value = "check-auth")
  public ResponseEntity<Boolean> checkAuthToken(HttpServletRequest req) throws Exception {
    Cookie[] cookies = req.getCookies();
    Boolean authorized = false;

    try {
      for (Cookie cookie : cookies)
        if (cookie.getName().equalsIgnoreCase("AUTH_ID_TOKEN"))
          authorized = authService.checkAuthToken(cookie.getValue());
    } catch (Exception e) {
      log.error("Error getting id token");
    }
    return ResponseEntity.ok(authorized);
  }
}