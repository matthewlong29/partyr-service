package com.partygames.partygamesservice.controller;

import java.util.List;

import com.partygames.partygamesservice.model.User;
import com.partygames.partygamesservice.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class UsersController {
  @Autowired
  UsersService usersService;

  /**
   * getAllUsers.
   */
  @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getAllUsers() {
    return usersService.getAllUsers();
  }

  /**
   * getOnlineUsers.
   */
  @GetMapping(value = "/online-users", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getOnlineUsers() {
    return usersService.getOnlineUsers();
  }

  /**
   * getFriendsList.
   */
  @GetMapping(value = "/friends-list/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getFriendsList(@PathVariable String userName) {
    return usersService.getFriendsList(userName);
  }

  /**
   * addUser.
   */
  @GetMapping(value = "/add-friend/{currentUser}/{userToAdd}")
  public int addUser(@PathVariable String currentUser, @PathVariable String userToAdd) {
    return usersService.addUser(currentUser, userToAdd);
  }

  /**
   * getBlockedList.
   */
  @GetMapping(value = "/blocked-list/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getBlockedList(@PathVariable String userName) {
    return usersService.getBlockedList(userName);
  }

  /**
   * blockUser.
   */
  @GetMapping(value = "/block-user/{currentUser}/{userToBlock}")
  public int blockUser(@PathVariable String currentUser, @PathVariable String userToBlock) {
    return usersService.blockUser(currentUser, userToBlock);
  }

  /**
   * searchForUsersByName.
   */
  @GetMapping(value = "/search-for-user/{text}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> searchForUsersByName(@PathVariable String text) {
    return usersService.searchForUsersByName(text);
  }

  /**
   * createUser.
   */
  @PostMapping(value = "create-user", consumes = MediaType.APPLICATION_JSON_VALUE)
  public int createUser(@RequestBody User user) {
    return usersService.createUser(user);
  }

  /**
   * chooseTheme.
   */
  @GetMapping(value = "choose-theme/{userToUpdate}/{themeID}")
  public int chooseTheme(@PathVariable String userToUpdate, @PathVariable int themeID) {
    return usersService.chooseTheme(userToUpdate, themeID);
  }

  /**
   * changePassword.
   */
  @GetMapping(value = "change-password/{userToUpdate}/{newPassword}")
  public int changePassword(@PathVariable String userToUpdate, @PathVariable String newPassword) {
    return usersService.changePassword(userToUpdate, newPassword);
  }
}
