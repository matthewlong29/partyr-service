package com.partyrgame.blackhandservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.partyrgame.blackhandservice.model.BlackHandFaction;
import com.partyrgame.blackhandservice.model.BlackHandRole;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class BlackHandRoleResultSetExtractor
    implements ResultSetExtractor<HashMap<BlackHandFaction, List<BlackHandRole>>> {
  /**
   * extractData: maps factions to a HashMap containing the black hand role with
   * the key being the faction.
   */
  @Override
  public HashMap<BlackHandFaction, List<BlackHandRole>> extractData(ResultSet resultSet) throws SQLException {
    BlackHandRoleRowMapper rowMapper = new BlackHandRoleRowMapper();
    HashMap<BlackHandFaction, List<BlackHandRole>> blackHandFactionRoles = new HashMap<>();

    while (resultSet.next()) {
      BlackHandRole blackHandRole = rowMapper.mapRow(resultSet, resultSet.getRow());
      blackHandFactionRoles.computeIfAbsent(blackHandRole.getFaction(), f -> new ArrayList<>()).add(blackHandRole);
    }

    return blackHandFactionRoles;
  }
}