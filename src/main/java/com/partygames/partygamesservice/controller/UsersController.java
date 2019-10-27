package com.partygames.partygamesservice.controller;

import java.util.List;

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
  @GetMapping(value = "/current-user")
  public PartyrUser getLoggedInUser(@RequestParam() String partyrEmail) {
    log.info("email: " + partyrEmail);

    return usersService.getCurrentUser(partyrEmail);
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
  @GetMapping(value = "/all-relationships")
  public Relationships getRelationships(@RequestBody PartyrEmail partyrEmail,
      @RequestParam(value = "type", required = false, defaultValue = "both") String relationshipStatus,
      @RequestParam(value = "online", required = false, defaultValue = "false") boolean onlineOnly) {
    return usersService.getRelationships(partyrEmail.getEmail(), relationshipStatus, onlineOnly);
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
  @GetMapping(value = "/select-theme")
  public int selectTheme(@RequestBody ThemeSelect themeSelect) {
    return usersService.selectTheme(themeSelect.getEmail(), themeSelect.getThemeStatus().getThemeIndex());
  }

  /**
   * selectUsername: allows the user to select a username
   */
  @GetMapping(value = "/select-username")
  public int selectUsername(@RequestBody UserNameSelect userNameSelect) {
    return usersService.selectUsername(userNameSelect.getEmail(), userNameSelect.getUsername());
  }
}
