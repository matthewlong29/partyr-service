package com.partygames.partygamesservice.controller;

import java.util.List;
import java.util.Map;

import com.partygames.partygamesservice.model.relationships.Relationship;
import com.partygames.partygamesservice.model.relationships.Relationships;
import com.partygames.partygamesservice.model.users.PartyrEmail;
import com.partygames.partygamesservice.model.users.PartyrUser;
import com.partygames.partygamesservice.model.users.ThemeSelect;
import com.partygames.partygamesservice.model.users.UserNameSelect;
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
    String email = body.get("partyrEmail");
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
      @RequestParam(value = "ready", required = false, defaultValue = "false") boolean readyToPlay,
      @RequestParam(value = "query", required = false, defaultValue = "") String queryString) {
    return usersService.getAllUsers(onlineOnly, readyToPlay, queryString);
  }

  /**
   * getRelationships: gets all relationships associated with a user, or only
   * friends (and optionally only online friends), or only blocked users.
   */
  @PostMapping(value = "/all-relationships")
  public Relationships getRelationships(@RequestBody Map<String, String> body,
      @RequestParam(value = "type", required = false, defaultValue = "both") String relationshipStatus,
      @RequestParam(value = "online", required = false, defaultValue = "false") boolean onlineOnly) {
    String email = body.get("partyrEmail");
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
   * createUser.
   * 
   * TODO: do we need this endpoint still???
   */
  @PostMapping(value = "/create-user", consumes = MediaType.APPLICATION_JSON_VALUE)
  public int createUser(@RequestBody PartyrUser user) {
    return usersService.createUser(user);
  }

  /**
   * selectTheme: allows the user to select a theme
   */
  @PostMapping(value = "/select-theme")
  public int selectTheme(@RequestBody ThemeSelect themeSelect) {
    return usersService.selectTheme(themeSelect.getEmail(), themeSelect.getThemeStatus().getThemeIndex());
  }

  /**
   * selectUsername: allows the user to select a username
   */
  @PostMapping(value = "/select-username")
  public int selectUsername(@RequestBody Map<String, String> body) {
    return usersService.selectUsername(body.get("email"), body.get("username"));
  }
}
