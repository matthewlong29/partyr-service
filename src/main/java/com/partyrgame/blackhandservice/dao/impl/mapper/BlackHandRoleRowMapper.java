package com.partyrgame.blackhandservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandRole;

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
    blackHandRole.setRolePriority(resultSet.getInt("role_priority"));
    blackHandRole.setCanDayKill(convertToBoolean(resultSet.getInt("day_kill")));
    blackHandRole.setCanNightKill(convertToBoolean(resultSet.getInt("night_kill")));
    blackHandRole.setCanDayBlock(convertToBoolean(resultSet.getInt("day_block")));
    blackHandRole.setCanNightBlock(convertToBoolean(resultSet.getInt("night_block")));

    return blackHandRole;
  }

  /**
   * convertToBoolean: converts 1 to true and 0 to false.
   */
  private boolean convertToBoolean(int value) {
    return value == 1 ? true : false;
  }
}
