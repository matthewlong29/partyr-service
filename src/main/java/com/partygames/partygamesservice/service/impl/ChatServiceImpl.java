package com.partygames.partygamesservice.service.impl;

import java.util.List;

import com.partygames.partygamesservice.dao.ChatDao;
import com.partygames.partygamesservice.model.Message;
import com.partygames.partygamesservice.service.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    log.info("new chat message: [" + message.toString() + "]");

    return chatDao.insertMessage(message);
  }

  /**
   * getMessages.
   */
  public List<Message> getMessages() {
    return chatDao.getMessages();
  }
}
