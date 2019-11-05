let stompClient = null;

let subscribeTo = "/lobby/rooms";
let sendTo = "/app/join-room";

const setConnected = connected => {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  $("#jsonMessage").html("");
};

const connect = () => {
  let socket = new SockJS("/ws/socket");
  stompClient = Stomp.over(socket);
  stompClient.connect({}, frame => {
    setConnected(true);
    console.log("Connected: " + frame);
    stompClient.subscribe(subscribeTo, message => {
      showMessage(message);
    });
  });
};

const disconnect = () => {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("Disconnected");
};

const sendJSON = () => {
  stompClient.send(sendTo, {}, $("#message").val());
};

const showMessage = message => {
  $("#jsonMessage").append("<pre>" + syntaxHighlight(message.body) + "</pre>");
};

const syntaxHighlight = json => {
  json = JSON.parse(json);
  if (typeof json != "string") {
    json = JSON.stringify(json, null, 2);
  }
  json = json
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;");
  return json.replace(
    /("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g,
    match => {
      var cls = "number";
      if (/^"/.test(match)) {
        if (/:$/.test(match)) {
          cls = "key";
        } else {
          cls = "string";
        }
      } else if (/true|false/.test(match)) {
        cls = "boolean";
      } else if (/null/.test(match)) {
        cls = "null";
      }
      return '<span class="' + cls + '">' + match + "</span>";
    }
  );
};

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
