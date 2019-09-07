package com.partygames.partygamesservice.controller;

import java.util.List;

import com.partygames.partygamesservice.model.Relationship;
import com.partygames.partygamesservice.model.Relationships;
import com.partygames.partygamesservice.model.User;
import com.partygames.partygamesservice.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class UsersController {
  @Autowired
  UsersService usersService;

  /**
   * getAllUsers: returns all users, and optionally only all users that are
   * online. Username and email cannot contain spaces.
   */
  @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getAllUsers(
      @RequestParam(value = "online", required = false, defaultValue = "false") boolean onlineOnly,
      @RequestParam(value = "ready", required = false, defaultValue = "false") boolean readyToPlay,
      @RequestParam(value = "query", required = false, defaultValue = "") String queryString) {
    return usersService.getAllUsers(onlineOnly, readyToPlay, queryString);
  }

  /**
   * getRelationships: gets all relationships associated with a user, or only
   * friends (and optionally only online friends), or only blocked users.
   */
  @GetMapping(value = "/relationships/{userName}")
  public Relationships getRelationships(@PathVariable String userName,
      @RequestParam(value = "type", required = false, defaultValue = "both") String relationshipStatus,
      @RequestParam(value = "online", required = false, defaultValue = "false") boolean onlineOnly) {
    return usersService.getRelationships(userName, relationshipStatus, onlineOnly);
  }

  /**
   * createRelationship: either add a user as a friend or block them.
   */
  @PostMapping(value = "/create-relationship", consumes = MediaType.APPLICATION_JSON_VALUE)
  public int createRelationship(@RequestBody Relationship newRelationship) {
    return usersService.createRelationship(newRelationship);
  }

  /**
   * createUser.
   */
  @PostMapping(value = "/create-user", consumes = MediaType.APPLICATION_JSON_VALUE)
  public int createUser(@RequestBody User user) {
    return usersService.createUser(user);
  }

  /**
   * chooseTheme.
   */
  @GetMapping(value = "/choose-theme/{userToUpdate}/{themeID}")
  public int chooseTheme(@PathVariable String userToUpdate, @PathVariable int themeID) {
    return usersService.chooseTheme(userToUpdate, themeID);
  }

  /**
   * changePassword.
   */
  @GetMapping(value = "/change-password/{userToUpdate}/{newPassword}")
  public int changePassword(@PathVariable String userToUpdate, @PathVariable String newPassword) {
    return usersService.changePassword(userToUpdate, newPassword);
  }
}
