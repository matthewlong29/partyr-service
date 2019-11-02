let stompClient = null;

let sendTo = "/app/join-room";
let subscribeTo = "/lobby/rooms";

function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  $("#jsonMessage").html("");
}

function connect() {
  let socket = new SockJS("/ws/socket");
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function(frame) {
    setConnected(true);
    console.log("Connected: " + frame);
    stompClient.subscribe(subscribeTo, function(message) {
      showMessage(message);
    });
  });
}

function disconnect() {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("Disconnected");
}

function sendJSON() {
  stompClient.send(sendTo, {}, $("#message").val());
}

function showMessage(message) {
  $("#jsonMessage").append("<tr><td>" + JSON.stringify(message.body) + "</td></tr>");
}

$(() => {
  $("form").on("submit", e => {
    e.preventDefault();
  });
  $("#connect").click(() => {
    connect();
  });
  $("#disconnect").click(() => {
    disconnect();
  });
  $("#send").click(() => {
    sendJSON();
  });
  $("#subscribeTo").on("submit", () => {
    subscribeTo = $("#subscribeTo input").val();
    console.log(subscribeTo);
  });
  $("#sendTo").on("submit", () => {
    sendTo = $("#sendTo input").val();
    console.log(sendTo);
  });
});
