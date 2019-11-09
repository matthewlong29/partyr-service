package com.partygames.partygamesservice.controller;

import java.util.List;
import java.util.Map;

import com.partygames.partygamesservice.model.relationships.Relationship;
import com.partygames.partygamesservice.model.relationships.Relationships;
import com.partygames.partygamesservice.model.users.PartyrUser;
import com.partygames.partygamesservice.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/users")
public class UsersController {
  @Autowired
  UsersService usersService;

  /**
   * getLoggedInUser: if all you have is an email address use this endpoint to get
   * that PartyrUser using that email address.
   */
  @PostMapping(path = "/current-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public PartyrUser getLoggedInUser(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    log.info("email: " + email);

    return usersService.getCurrentUser(email);
  }

  /**
   * getAllUsers: returns all users, and optionally only all users that are
   * online. Username and email cannot contain spaces.
   */
  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PartyrUser> getAllUsers(
      @RequestParam(value = "online", required = false, defaultValue = "false") boolean onlineOnly,
      @RequestParam(value = "query", required = false, defaultValue = "") String queryString) {
    return usersService.getAllUsers(onlineOnly, queryString);
  }

  /**
   * getRelationships: gets all relationships associated with a user, or only
   * friends (and optionally only online friends), or only blocked users.
   */
  @PostMapping(value = "/all-relationships", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Relationships getRelationships(@RequestBody Map<String, String> body,
      @RequestParam(value = "type", required = false, defaultValue = "both") String relationshipStatus,
      @RequestParam(value = "online", required = false, defaultValue = "false") boolean onlineOnly) {
    String email = body.get("email");

    return usersService.getRelationships(email, relationshipStatus, onlineOnly);
  }

  /**
   * createRelationship: either add a user as a friend or block them
   */
  @PostMapping(value = "/create-relationship", consumes = MediaType.APPLICATION_JSON_VALUE)
  public int createRelationship(@RequestBody Relationship newRelationship) {
    return usersService.createRelationship(newRelationship);
  }

  /**
   * selectTheme: allows the user to select a theme
   */
  @PostMapping(value = "/select-theme", consumes = MediaType.APPLICATION_JSON_VALUE)
  public int selectTheme(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String chosenTheme = body.get("themeName");

    return usersService.selectTheme(email, chosenTheme);
  }

  /**
   * 
   */
  @GetMapping(value = "/themes", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<String> getThemes() {
    return usersService.getThemes();
  }

  /**
   * selectUsername: allows the user to select a username
   */
  @PostMapping(value = "/select-username", consumes = MediaType.APPLICATION_JSON_VALUE)
  public int selectUsername(@RequestBody Map<String, String> body) {
    return usersService.selectUsername(body.get("email"), body.get("username"));
  }
}
