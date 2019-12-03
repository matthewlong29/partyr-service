let stompClient = null;
let roomName = "";
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
      '{"roomName": "ziploc bags box tablet stand 1", "gameName": "Black Hand", "username": "timmy7"}'
  },
  {
    send: "/app/join-room",
    example:
      '{"roomName": "ziploc bags box tablet stand 1", "username": "twobyfour"}'
  },
  {
    send: "/app/leave-room",
    example:
      '{"roomName": "ziploc bags box tablet stand 1", "username": "obtrusivemonks"}'
  },
  {
    send: "/app/delete-room",
    example: '{"roomName": "ziploc bags box tablet stand 1"}'
  },
  {
    send: "/app/toggle-ready-status",
    example:
      '{"roomName": "ziploc bags box tablet stand 1", "username": "lanawood"}'
  },
  {
    send: "/app/select-display-name",
    example:
      '{"roomName": "ziploc bags box tablet stand", "username": "cheesecake", "displayName": "cheese"}'
  },
  {
    send: "/app/select-preferred-faction",
    example:
      '{"roomName": "ziploc bags box tablet stand", "username": "cheesecake", "preferredFaction": "Townie"}'
  },
  {
    send: "/app/start-black-hand",
    example: '{"roomName": "ziploc bags box tablet stand"}'
  },
  {
    send: "/app/submit-turn",
    example:
      '{"roomName": "ziploc bags box tablet stand", "username": "cheesecake", "attacking": "coty", "blocking": "", "placeOnTrial": "lanawood", "note": "on the first night i attacked cody."}'
  },
  {
    send: "/app/evaluate-day",
    example: '{"roomName": "ziploc bags box tablet stand"}'
  },
  {
    send: "/app/submit-vote",
    example:
      '{"roomName": "ziploc bags box tablet stand", "username": "cheesecake", "vote": "GUILTY"}'
  },
  {
    send: "/app/evaluate-trial",
    example: '{"roomName": "ziploc bags box tablet stand"}'
  },
  {
    send: "/app/evaluate-night",
    example: '{"roomName": "ziploc bags box tablet stand"}'
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
 * selectRoom.
 */
const selectRoom = () => {
  roomName = document.querySelector("#room-name").value;
  roomName = roomName.replace(/[^a-zA-Z0-9 ]/g, "");
  roomName = roomName.replace(/\s+/g, "-").toLowerCase();

  console.log(`room name: "${roomName}"`);
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
  let togglePanels = "";
  sendToOptions.forEach(button => {
    togglePanels += `<button class="accordion btn btn--stripe btn--large">${
      button.send
    }</button><pre class="panel">${syntaxHighlight(button.example)}</pre>`;
  });
  document.querySelector("#accordion").innerHTML = togglePanels;
};

/**
 * setSendTo.
 */
const setSendTo = selectedSendTo => {
  sendTo = selectedSendTo;
  sendTo = `${sendTo}/${roomName}`;
  console.log(`selected to send to: ${sendTo}`);
  document.querySelector(".send-message").innerHTML = `[${sendTo}]`;
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
  subscribeTo = `${selectedSubscribeTo}/${roomName}`;
  console.log(`selected to subscribe to: ${subscribeTo}`);
  document.querySelector(".subscribe-message").innerHTML = `[${subscribeTo}]`;
};

/**
 * onPageLoad.
 */
document.addEventListener("DOMContentLoaded", () => {
  getSendTo();
  getSubscribeTo();

  document.querySelector(".subscribe-message").innerHTML = "";
  document.querySelector(".send-message").innerHTML = "";

  document.querySelector("#connect").addEventListener("click", () => {
    connect();
  });

  document.querySelector("#disconnect").addEventListener("click", () => {
    document.querySelector(".send-message").innerHTML = "";
    document.querySelector(".subscribe-message").innerHTML = "";
    disconnect();
  });

  document.querySelector("#send").addEventListener("click", e => {
    e.preventDefault();
    sendJSON();
  });

  document.querySelector("#submit-room").addEventListener("click", e => {
    e.preventDefault();
    selectRoom();
  });

  const accordion = document.getElementsByClassName("accordion");

  for (let i = 0; i < accordion.length; i++) {
    accordion[i].onclick = function() {
      this.classList.toggle("active");
      setSendTo(sendToOptions[i].send);
      let panel = this.nextElementSibling;
      if (panel.style.maxHeight) {
        panel.style.maxHeight = null;
      } else {
        panel.style.maxHeight = `${panel.scrollHeight}px`;
      }
    };
  }
});
