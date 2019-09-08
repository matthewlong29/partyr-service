package com.partygames.partygamesservice.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.partygames.partygamesservice.model.PartyrUser;
import com.partygames.partygamesservice.util.PartyLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
  @Autowired
  PartyLogger log;

  /**
   * home.
   */
  @GetMapping("/welcome")
  public Map<String, Object> home() {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "Welcome to Partyr!");

    log.info(model.toString());

    return model;
  }

  /**
   * user.
   */
  @GetMapping("/user")
  public PartyrUser user(Principal user) {
    return (PartyrUser) user;
  }
}