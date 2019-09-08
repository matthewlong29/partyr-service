package com.partygames.partygamesservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.partygames.partygamesservice.model.OnlineStatus;
import com.partygames.partygamesservice.model.PartyrUser;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UserRowMapper implements RowMapper<PartyrUser> {
  /**
   * mapRow.
   */
  @Override
  public PartyrUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    boolean enabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;
    Set<GrantedAuthority> authorities = new HashSet()<GrantedAuthority>();

    PartyrUser user = new PartyrUser(resultSet.getString("user_name"), resultSet.getString("password"), enabled,
        accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

    user.setEmail(resultSet.getString("email"));
    user.setJoinedDate(resultSet.getTimestamp("joined_date"));
    user.setOnlineStatus(OnlineStatus.valueOf(resultSet.getString("online_status")));
    user.setThemeID(resultSet.getInt("theme_id"));
    user.setAge(resultSet.getInt("age"));
    user.setCountry(resultSet.getString("country"));

    return user;
  }

  private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

    Set<GrantedAuthority> setAuthorities = new HashSet<GrantedAuthority>();

    // Build user's authorities
    for (UserRole userRole : userRoles) {
      setAuthorities.add(new SimpleGrantedAuthority(userRole.getRole()));
    }

    return new ArrayList<GrantedAuthority>(setAuthorities);
  }
}
