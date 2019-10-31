package com.partygames.partygamesservice.util;

import org.springframework.stereotype.Component;

@Component
public class WebsocketConstants {
  public final static String ENDPOINT = "/ws/socket";

  public final static String PREFIX = "/app";

  public final static String CHAT_SUBSCRIBE = "/chat/room"; // TODO add a room id to have multiple chat instances
  public final static String CHAT_BROKER = "/chat";
  public final static String CHAT_SEND = "/send-chat";

  public final static String LOBBY_SUBSCRIBE = "/lobby/rooms";
  public final static String LOBBY_BROKER = "/lobby";
  public final static String ROOM_CREATE_SEND = "/create-room";
  public final static String ROOM_JOIN_SEND = "/join-room";
}