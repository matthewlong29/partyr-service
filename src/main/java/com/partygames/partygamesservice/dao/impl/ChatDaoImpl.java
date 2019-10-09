package com.partygames.partygamesservice.dao.impl;

import com.partygames.partygamesservice.dao.ChatDao;
import com.partygames.partygamesservice.dao.impl.mapper.ChatRowMapper;
import com.partygames.partygamesservice.model.Message;
import com.partygames.partygamesservice.util.PartyLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDaoImpl implements ChatDao {
  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  ChatRowMapper chatRowMapper;

  /**
   * insertMessage.
   */
  public int insertMessage(Message message) {
    String query = "CALL `partyrdb`.`save_chat_message`('" + message.getAuthor() + "', '" + message.getContent() + "', '"
        + message.getTimeOfMessage() + "');";
    PartyLogger.query(query);

    return jdbcTemplate.update(query);
  }
}