package com.partygames.partygamesservice.dao.impl;

import com.partygames.partygamesservice.dao.ChatDao;
import com.partygames.partygamesservice.dao.impl.mapper.ChatRowMapper;
import com.partygames.partygamesservice.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDaoImpl implements ChatDao {
  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  ChatRowMapper chatRowMapper;
  
  public int insertMessage(Message message) {
    return 0;
  }
}