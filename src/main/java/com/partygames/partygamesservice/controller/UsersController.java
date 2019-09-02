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
  @GetMapping(value = "/friends-of-{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getFriendsList(@PathVariable String userName) {
    return usersService.getFriendsList(userName);
  }

  /**
   * createUser.
   */
  @PostMapping(value = "create-user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public int createUser(@RequestBody User user) {
    return usersService.createUser(user);
  }
}