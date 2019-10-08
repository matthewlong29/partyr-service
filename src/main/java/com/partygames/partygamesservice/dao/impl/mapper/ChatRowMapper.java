package com.partygames.partygamesservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partygames.partygamesservice.model.Message;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ChatRowMapper implements RowMapper<Message> {
  /**
   * mapRow.
   */
  @Override
  public Message mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    Message message = new Message();
    message.setAuthor(resultSet.getString("player"));
    message.setContent(resultSet.getString("message"));
    message.setTimeOfMessage(resultSet.getTimestamp("time_of_message"));

    return message;
  }
}
