package com.partyrgame.util;

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
  public final static String ROOM_LEAVE_SEND = "/leave-room";
  public final static String ROOM_DELETE_SEND = "/delete-room";
  public final static String ROOM_TOGGLE_READY_STATUS_SEND = "/toggle-ready-status";

  public final static String BLACK_HAND_SUBSCRIBE = "/game/black-hand/"; // + {gameRoomName}
  public final static String BLACK_HAND_BROKER = "/game/black-hand";

  public final static String BLACK_HAND_START_SEND = "/start-black-hand";
}