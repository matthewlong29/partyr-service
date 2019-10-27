package com.partygames.partygamesservice.dao.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.ChatDao;
import com.partygames.partygamesservice.dao.impl.mapper.ChatRowMapper;
import com.partygames.partygamesservice.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    String query = "CALL `partyrdb`.`save_chat_message`('" + message.getEmail() + "', '" + message.getContent() + "', '"
        + message.getTimeOfMessage() + "');";
    log.info(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to save message {}; error: {}", message.getContent(), e.getMessage());
    }

    return jdbcTemplate.update(query);
  }

  /**
   * getMessages.
   */
  public List<Message> getMessages() {
    String query = "CALL `partyrdb`.`get_all_messages`();";
    log.info(query);

    return jdbcTemplate.query(query, chatRowMapper);
  }
}
