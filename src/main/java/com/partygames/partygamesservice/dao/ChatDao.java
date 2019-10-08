package com.partygames.partygamesservice.dao;

import com.partygames.partygamesservice.model.Message;

public interface ChatDao {
  public int insertMessage(Message message);
}