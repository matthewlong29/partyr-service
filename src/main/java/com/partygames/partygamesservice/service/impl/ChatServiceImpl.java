package com.partygames.partygamesservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.partygames.partygamesservice.dao.ChatDao;
import com.partygames.partygamesservice.model.Message;
import com.partygames.partygamesservice.service.ChatService;
import com.partygames.partygamesservice.util.PartyLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
  private final SimpMessagingTemplate template;

  @Autowired
  ChatDao chatDao;

  /**
   * ChatServiceImpl.
   */
  @Autowired
  ChatServiceImpl(SimpMessagingTemplate template) {
    this.template = template;
  }

  /**
   * sendMessageToChat.
   */
  public void sendMessageToChat(String destination, Message message) {
    this.template.convertAndSend("/chat", message.getContent());
  }

  /**
   * saveMessage.
   */
  public int saveMessage(Message message) {
    PartyLogger.info("new chat message: [" + message.toString() + "]");

    return chatDao.insertMessage(message);
  }

  /**
   * getMessages.
   */
  public List<Message> getMessages() {
    return new ArrayList<>();
  }
}