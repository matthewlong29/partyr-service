package com.partygames.partygamesservice.model;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PartyrUser extends User {
  public PartyrUser(String username, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
  }

  private static final long serialVersionUID = 1L;

  private String userName;
  private String email;
  private String password;
  private Timestamp joinedDate;
  private OnlineStatus onlineStatus;
  private int themeID;
  private int age;
  private String country;
}