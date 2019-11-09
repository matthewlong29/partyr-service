let stompClient = null;
let subscribeTo = "";
let sendTo = "";
let sendToOptions = [
  "/app/send-chat",
  "/app/create-room",
  "/app/join-room",
  "/app/leave-room",
  "/app/delete-room",
  "/app/toggle-ready-status"
];
let subscribeToOptions = ["/chat/room", "/lobby/rooms"];

/**
 * setConnected.
 */
const setConnected = connected => {
  document.querySelector("#connect").disabled = connected;
  document.querySelector("#disconnect").disabled = !connected;
  document.querySelector("#jsonMessage").innerHTML = "";
};

/**
 * connect.
 */
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

/**
 * disconnect.
 */
const disconnect = () => {
  if (stompClient !== null) {
    stompClient.disconnect();
  }
  setConnected(false);
  console.log("Disconnected");
};

/**
 * sendJSON.
 */
const sendJSON = () => {
  stompClient.send(sendTo, {}, document.querySelector("#message").value);
};

/**
 * showMessage.
 */
const showMessage = message => {
  let newMessages =
    `<pre>${syntaxHighlight(message.body)}</pre>` +
    document.querySelector("#jsonMessage").innerHTML;
  document.querySelector("#jsonMessage").innerHTML = newMessages;
};

/**
 * syntaxHighlight.
 */
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

/**
 * getSendTo.
 */
const getSendTo = () => {
  console.log(sendToOptions);
  let html = "";
  sendToOptions.forEach(button => {
    html += `<button class="btn btn--stripe" onclick="setSendTo('${button}')">${button}</button>`;
  });
  document.querySelector("#sendToOptions").innerHTML = html;
};

/**
 * setSendTo.
 */
const setSendTo = selectedSendTo => {
  sendTo = selectedSendTo;
  console.log(`selected to send to: ${sendTo}`);
  document.querySelector(
    ".send-message"
  ).innerHTML = `select where to send [${sendTo}]`;
};

/**
 * getSubscribeTo.
 */
const getSubscribeTo = () => {
  console.log(subscribeToOptions);
  let html = "";
  subscribeToOptions.forEach(button => {
    html += `<button class="btn btn--stripe" onclick="setSubscribeTo('${button}')">${button}</button>`;
  });
  document.querySelector("#subscribeToOptions").innerHTML = html;
};

/**
 * setSubscribeTo.
 */
const setSubscribeTo = selectedSubscribeTo => {
  subscribeTo = selectedSubscribeTo;
  console.log(`selected to subscribe to: ${subscribeTo}`);
  document.querySelector(
    ".subscribe-message"
  ).innerHTML = `select what to subscribe [${subscribeTo}]`;
};

/**
 * onPageLoad.
 */
document.addEventListener("DOMContentLoaded", () => {
  getSendTo();
  getSubscribeTo();

  document.querySelector(
    ".subscribe-message"
  ).innerHTML = `select what to subscribe`;
  document.querySelector(".send-message").innerHTML = "select where to send";

  document.querySelector("#connect").addEventListener("click", () => {
    connect();
  });

  document.querySelector("#disconnect").addEventListener("click", () => {
    document.querySelector(".send-message").innerHTML = "select what to send";
    document.querySelector(".subscribe-message").innerHTML =
      "select what to subscribe";
    disconnect();
  });

  document.querySelector("#send").addEventListener("click", e => {
    e.preventDefault();
    sendJSON();
  });
});
