package com.partyrgame.chatservice.dao.impl;

import java.util.List;

import com.partyrgame.chatservice.dao.ChatDao;
import com.partyrgame.chatservice.dao.impl.mapper.ChatRowMapper;
import com.partyrgame.chatservice.model.ChatMessage;

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
  public int insertChatMessage(ChatMessage chatMessage) {
    String query = "CALL `partyrdb`.`save_chat_message`('" + chatMessage.getUsername() + "', '" + chatMessage.getContent()
        + "', '" + chatMessage.getTimeOfMessage() + "');";
    log.debug(query);

    try {
      return jdbcTemplate.update(query);
    } catch (Exception e) {
      log.error("unable to save chat message {}; error: {}", chatMessage.getContent(), e.getMessage());
    }

    return jdbcTemplate.update(query);
  }

  /**
   * getMessages.
   */
  public List<ChatMessage> getChatMessages() {
    String query = "CALL `partyrdb`.`get_all_chat_messages`();";
    log.debug(query);

    return jdbcTemplate.query(query, chatRowMapper);
  }
}
