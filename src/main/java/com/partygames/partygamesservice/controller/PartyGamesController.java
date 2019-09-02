package com.partygames.partygamesservice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class PartyGamesController {
  /**
   * welcome.
   */
  @GetMapping(value = "/welcome", produces = MediaType.TEXT_PLAIN_VALUE)
  public String welcome() {
    return "welcome! party games service up";
  }
}