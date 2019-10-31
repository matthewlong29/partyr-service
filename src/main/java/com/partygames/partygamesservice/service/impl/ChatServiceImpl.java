package com.partygames.partygamesservice.service.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.ChatDao;
import com.partygames.partygamesservice.model.ChatMessage;
import com.partygames.partygamesservice.service.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {
  @Autowired
  ChatDao chatDao;

  /**
   * saveMessage.
   */
  public int saveChatMessage(ChatMessage chatMessage) {
    log.info("new chat chatMessage: [" + chatMessage.toString() + "]");

    return chatDao.insertChatMessage(chatMessage);
  }

  /**
   * getMessages.
   */
  public List<ChatMessage> getChatMessages() {
    return chatDao.getChatMessages();
  }
}
