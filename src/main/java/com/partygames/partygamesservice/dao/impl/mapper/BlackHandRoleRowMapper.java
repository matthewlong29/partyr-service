package com.partygames.partygamesservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partygames.partygamesservice.model.BlackHandFaction;
import com.partygames.partygamesservice.model.BlackHandFactionRoles.BlackHandRole;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BlackHandRoleRowMapper implements RowMapper<BlackHandRole> {
  /**
   * mapRow.
   */
  @Override
  public BlackHandRole mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    BlackHandRole blackHandRole = new BlackHandRole();
    blackHandRole.setFaction(BlackHandFaction.valueOf(resultSet.getString("faction")));
    blackHandRole.setName(resultSet.getString("role_name"));
    blackHandRole.setDayAbilityDescription(resultSet.getString("day_ability_description"));
    blackHandRole.setNightAbilityDescription(resultSet.getString("night_ability_description"));
    blackHandRole.setAttributeDescription(resultSet.getString("attribute_description"));
    blackHandRole.setGoalDescription(resultSet.getString("goal_description"));

    return blackHandRole;
  }
}
