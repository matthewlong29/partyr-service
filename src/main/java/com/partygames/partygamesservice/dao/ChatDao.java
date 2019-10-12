package com.partygames.partygamesservice.dao;

import java.util.List;

import com.partygames.partygamesservice.model.Message;

public interface ChatDao {
  public int insertMessage(Message message);

  public List<Message> getMessages();
}