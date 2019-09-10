package com.partygames.partygamesservice.controller;

import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.partygames.partygamesservice.model.PartyrUser;
import com.partygames.partygamesservice.util.PartyLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
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
   * login.
   */
  @RequestMapping("/login")
  public boolean login(@RequestBody PartyrUser partyrUser) {
    return partyrUser.getUserName().equals("user") & partyrUser.getPassword().equals("password");
  }

  /**
   * user.
   */
  @RequestMapping("/user")
  public Principal user(HttpServletRequest request) {
    String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();

    return () -> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
  }
}
