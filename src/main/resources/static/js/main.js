"use strict";

let usernamePage = document.querySelector("#username-page");
let chatPage = document.querySelector("#chat-page");
let usernameForm = document.querySelector("#usernameForm");
let messageForm = document.querySelector("#messageForm");
let messageInput = document.querySelector("#message");
let messageArea = document.querySelector("#messageArea");
let connectingElement = document.querySelector(".connecting");
let disconnectButton = document.querySelector("#disconnect");

let stompClient = null;
let username = null;

let colors = [
  "#2196F3",
  "#32c787",
  "#00BCD4",
  "#ff5652",
  "#ffc107",
  "#ff85af",
  "#FF9800",
  "#39bbb0"
];

/**
 * connect.
 */
const connect = event => {
  username = document.querySelector("#name").value.trim();

  if (username) {
    usernamePage.classList.add("hidden");
    chatPage.classList.remove("hidden");

    let socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
  }

  event.preventDefault();
};

/**
 * onConnected.
 */
const onConnected = () => {
  // subscribe to the Public Topic
  stompClient.subscribe("/topic/public", onMessageReceived);

  // tell your username to the server
  // TODO: use logged in user
  stompClient.send(
    "/app/chat.addUser",
    {},
    JSON.stringify({ sender: username, type: "JOIN" })
  );

  connectingElement.classList.add("hidden");
};

/**
 * disconnect.
 */
const disconnect = event => {
  console.log(`disconnecting user`);
  let socket = new SockJS("/ws"); // TODO make dynamic rooms?
  stompClient.disconnect(onDisconnect);
};

/**
 * onDisconnect.
 */
const onDisconnect = () => {
  // subscribe to the Public Topic
  stompClient.subscribe("/topic/public", onMessageReceived);

  stompClient.send(
    "/app/chat.removeUser",
    {},
    JSON.stringify({ sender: username, type: "LEAVE" })
  );

  usernamePage.classList.remove("hidden");
  chatPage.classList.add("hidden");
};

/**
 * onError.
 */
const onError = error => {
  connectingElement.textContent =
    "Could not connect to WebSocket server. Please try again!";
  connectingElement.style.color = "red";
};

/**
 * sendMessage.
 */
const sendMessage = event => {
  console.log(event);
  let messageContent = messageInput.value.trim();
  if (messageContent && stompClient) {
    let chatMessage = {
      sender: username,
      content: messageInput.value,
      type: "CHAT"
    };
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    messageInput.value = "";
  }
  event.preventDefault();
};

/**
 * onMessageReceived.
 */
const onMessageReceived = payload => {
  let message = JSON.parse(payload.body);

  let messageElement = document.createElement("li");

  if (message.type === "JOIN") {
    messageElement.classList.add("event-message");
    message.content = `${message.sender} joined!`;
  } else if (message.type === "LEAVE") {
    console.log("leaving.. :(");
    messageElement.classList.add("event-message");
    message.content = `${message.sender} left!`;
  } else {
    messageElement.classList.add("chat-message");

    let avatarElement = document.createElement("i");
    let avatarText = document.createTextNode(message.sender[0]);
    avatarElement.appendChild(avatarText);
    avatarElement.style["background-color"] = getAvatarColor(message.sender);

    messageElement.appendChild(avatarElement);

    let usernameElement = document.createElement("span");
    let usernameText = document.createTextNode(message.sender);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);
  }

  let textElement = document.createElement("p");
  let messageText = document.createTextNode(message.content);

  textElement.appendChild(messageText);

  messageElement.appendChild(textElement);

  messageArea.appendChild(messageElement);
  messageArea.scrollTop = messageArea.scrollHeight;
};

/**
 * getAvatarColor.
 * TODO: use googles image if it exists.
 */
const getAvatarColor = messageSender => {
  let hash = 0;
  for (let i = 0; i < messageSender.length; i++) {
    hash = 31 * hash + messageSender.charCodeAt(i);
  }
  let index = Math.abs(hash % colors.length);
  return colors[index];
};

usernameForm.addEventListener("submit", connect, true);
messageForm.addEventListener("submit", sendMessage, true);
disconnectButton.addEventListener("click", disconnect);
