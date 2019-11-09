package com.partygames.partygamesservice.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.partygames.partygamesservice.model.ChatMessage;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ChatRowMapper implements RowMapper<ChatMessage> {
  /**
   * mapRow.
   * 
   * TODO update chat stored proc....
   */
  @Override
  public ChatMessage mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    ChatMessage chat = new ChatMessage();
    chat.setUsername(resultSet.getString("username"));
    chat.setContent(resultSet.getString("chat_message"));
    chat.setTimeOfMessage(resultSet.getTimestamp("time_of_chat_message"));

    return chat;
  }
}
