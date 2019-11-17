let stompClient = null;
let subscribeTo = "";
let sendTo = "";
let sendToOptions = [
  {
    send: "/app/send-chat",
    example: '{"username": "coty", "content": "woo"}'
  },
  {
    send: "/app/create-room",
    example:
      '{"roomName": "ziploc bags box tablet stand", "gameName": "Black Hand", "username": "timmy7"}'
  },
  {
    send: "/app/join-room",
    example:
      '{"roomName": "ziploc bags box tablet stand", "username": "cheesecake"}'
  },
  {
    send: "/app/leave-room",
    example:
      '{"roomName": "ziploc bags box tablet stand", "username": "obtrusivemonks"}'
  },
  {
    send: "/app/delete-room",
    example: '{"roomName": "ziploc bags box tablet stand"}'
  },
  {
    send: "/app/toggle-ready-status",
    example:
      '{"roomName": "ziploc bags box tablet stand", "username": "lanawood"}'
  },
  {
    send: "/app/start-black-hand",
    example: '{ "roomName": "ziploc bags box tablet stand"}'
  },
  {
    send: "/app/select-preferred-faction",
    example:
      '{"roomName": "ziploc bags box tablet stand", "username": "cheesecake", "preferredFaction": "Townie"}'
  }
];
let subscribeToOptions = ["/chat/room", "/lobby/rooms", "/game/black-hand"];

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
  let buttons = "";
  let togglePanels = "";
  sendToOptions.forEach(button => {
    buttons += `<button class="btn btn--stripe" onclick="setSendTo('${button.send}')">${button.send}</button>`;
    togglePanels += `<button class="accordion btn btn--stripe btn--large">${
      button.send
    }</button><pre class="panel">${syntaxHighlight(button.example)}</pre>`;
  });
  document.querySelector("#sendToOptions").innerHTML = buttons;
  document.querySelector("#accordion").innerHTML = togglePanels;
};

/**
 * setSendTo.
 */
const setSendTo = selectedSendTo => {
  sendTo = selectedSendTo;
  console.log(`selected to send to: ${sendTo}`);
  document.querySelector(
    ".send-message"
  ).innerHTML = `select where to send <span>[${sendTo}]</span>`;
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
  ).innerHTML = `select what to subscribe to <span>[${subscribeTo}]</span>`;
};

/**
 * onPageLoad.
 */
document.addEventListener("DOMContentLoaded", () => {
  getSendTo();
  getSubscribeTo();

  document.querySelector(
    ".subscribe-message"
  ).innerHTML = `select what to subscribe to`;
  document.querySelector(".send-message").innerHTML = "select where to send to";

  document.querySelector("#connect").addEventListener("click", () => {
    connect();
  });

  document.querySelector("#disconnect").addEventListener("click", () => {
    document.querySelector(".send-message").innerHTML =
      "select where to send to";
    document.querySelector(".subscribe-message").innerHTML =
      "select what to subscribe to";
    disconnect();
  });

  document.querySelector("#send").addEventListener("click", e => {
    e.preventDefault();
    sendJSON();
  });

  const accordion = document.getElementsByClassName("accordion");

  for (let i = 0; i < accordion.length; i++) {
    accordion[i].onclick = function() {
      this.classList.toggle("active");
      let panel = this.nextElementSibling;
      if (panel.style.maxHeight) {
        panel.style.maxHeight = null;
      } else {
        panel.style.maxHeight = `${panel.scrollHeight}px`;
      }
    };
  }
});
