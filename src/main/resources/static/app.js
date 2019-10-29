let stompClient = null;

let sendTo = "";
let subscribeTo = "";

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
      showMessage(message.body);
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
  const message = {
    email: "long.matthew29@gmail.com",
    content: $("#message").val()
  };
  stompClient.send(sendTo, {}, JSON.stringify(message));
}

function showMessage(message) {
  $("#jsonMessage").append("<tr><td>" + message + "</td></tr>");
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
