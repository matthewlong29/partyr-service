package com.partyrgame.socketservice.util;

import org.springframework.stereotype.Component;

@Component
public class WebsocketConstants {
  public final static String ENDPOINT = "/ws/socket";

  public final static String PREFIX = "/app";

  // ** chat constants

  public final static String CHAT_SUBSCRIBE = "/chat/room"; // TODO add a room id to have multiple chat instances
  public final static String CHAT_BROKER = "/chat";

  public final static String CHAT_SEND = "/send-chat";

  // ** lobby and room constants

  public final static String LOBBY_SUBSCRIBE = "/lobby/rooms";
  public final static String LOBBY_BROKER = "/lobby";

  public final static String ROOM_CREATE_SEND = "/create-room";
  public final static String ROOM_JOIN_SEND = "/join-room";
  public final static String ROOM_LEAVE_SEND = "/leave-room";
  public final static String ROOM_DELETE_SEND = "/delete-room";
  public final static String ROOM_TOGGLE_READY_STATUS_SEND = "/toggle-ready-status";

  // ** black hand constants

  // TODO: get working with one black hand game first! then add the room names to
  // have separate games going at once
  public final static String BLACK_HAND_SUBSCRIBE = "/game/black-hand"; // + {gameRoomName}
  public final static String BLACK_HAND_BROKER = "/game";

  public final static String BLACK_HAND_SELECT_PREFERRED_FACTION = "/select-preferred-faction";
  public final static String BLACK_HAND_START_SEND = "/start-black-hand";
}