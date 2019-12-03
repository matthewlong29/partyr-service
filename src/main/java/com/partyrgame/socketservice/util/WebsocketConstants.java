package com.partyrgame.socketservice.util;

import org.springframework.stereotype.Component;

@Component
public class WebsocketConstants {
  public final static String ENDPOINT = "/ws/socket";

  public final static String PREFIX = "/app";

  // ** chat constants

  public final static String CHAT_SUBSCRIBE = "/chat/room"; // TODO add a room id to have multiple chat instances
  public final static String CHAT_BROKER = "/chat";

  public final static String CHAT_SEND = "/send-chat/{channel}";

  // ** lobby and room constants

  public final static String LOBBY_SUBSCRIBE = "/lobby/rooms";
  public final static String LOBBY_BROKER = "/lobby";

  public final static String ROOM_CREATE_SEND = "/create-room/{channel}";
  public final static String ROOM_JOIN_SEND = "/join-room/{channel}";
  public final static String ROOM_LEAVE_SEND = "/leave-room/{channel}";
  public final static String ROOM_DELETE_SEND = "/delete-room/{channel}";
  public final static String ROOM_TOGGLE_READY_STATUS_SEND = "/toggle-ready-status/{channel}";

  // ** black hand constants

  // TODO: get working with one black hand game first! then add the room names to
  // have separate games going at once
  public final static String BLACK_HAND_SUBSCRIBE = "/game/black-hand";
  public final static String BLACK_HAND_BROKER = "/game";

  public final static String BLACK_HAND_SELECT_PREFERRED_FACTION = "/select-preferred-faction/{channel}";
  public final static String BLACK_HAND_SELECT_DISPLAY_NAME = "/select-display-name/{channel}";
  public final static String BLACK_HAND_START_SEND = "/start-black-hand/{channel}";
  public final static String BLACK_HAND_SUBMIT_TURN = "/submit-turn/{channel}";
  public final static String BLACK_HAND_EVALUATE_DAY = "/evaluate-day/{channel}";
  public final static String BLACK_HAND_SUBMIT_VOTE = "/submit-vote/{channel}";
  public final static String BLACK_HAND_EVALUATE_TRIAL = "/evaluate-trial/{channel}";
  public final static String BLACK_HAND_EVALUATE_NIGHT = "/evaluate-night/{channel}";
}