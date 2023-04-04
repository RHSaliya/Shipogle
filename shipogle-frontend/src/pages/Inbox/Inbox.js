import React, { useState, useEffect, useRef } from "react";
import axios from "../../utils/MyAxios";
import { w3cwebsocket as WebSocket } from "websocket";
import Constants from "../../Constants";
import "./inbox.css";
import DeleteIcon from '@mui/icons-material/Delete';
import chatProfileImg from "../../assets/profile.png";
import { Button, Typography } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";

const getUniqueSocketAddress = (user, selectedUser) => {
  const joinUsing = "!";
  return `${user.user_id}${joinUsing}${selectedUser.user_id}`;
};

const Inbox = () => {
  const [user, setUser] = useState({});
  const [selectedUser, setSelectedUser] = useState({});
  const [messages, setMessages] = useState([]);
  const [chatUsers, setChatUsers] = useState([]);
  const [inputValue, setInputValue] = useState("");
  const [clearMsg, setClearMsg] = useState(false);
  const ws = useRef(null);

  function handleUserClick(selectedUser, user) {
    setSelectedUser(selectedUser);
    setMessages([]);

    // get messages for the first time
    axios
      .get(`${Constants.API_CHAT}/${user.user_id}/${selectedUser.user_id}`)
      .then((response) => {
        console.log("~~~~~~~~~~~~~~");
        console.log(response.data);
        console.log("~~~~~~~~~~~~~~");
        setMessages(response.data);
      });

    ws.current = new WebSocket(
      `${Constants.SOCKET_CHAT}/${getUniqueSocketAddress(user, selectedUser)}`
    );

    ws.current.onopen = () => {
      console.log("WebSocket Client Connected");
    };

    ws.current.onmessage = (message) => {
      console.log("got message");
      console.log(message);
      const value = JSON.parse(message.data);
      const msg = {
        senderId: value.senderId,
        receiverId: value.receiverId,
        message: value.message,
      };
      setMessages((prevMessages) => [...prevMessages, msg]);
    };

    ws.current.onclose = () => {
      console.log("WebSocket Client Disconnected");
    };

    return () => {
      ws.current.close();
    };
  }

  useEffect(() => {
    // Get user info from token
    axios.get(Constants.API_USER_INFO_FROM_TOKEN).then((response) => {
      const user = response.data;
      console.log(user);
      setUser(user);

      // Get chats for current user
      axios.get(`${Constants.API_CHAT}/${user.user_id}`).then((res) => {
        console.log("~~~~~~~~~~~~~~");
        console.log(res.data);
        console.log("~~~~~~~~~~~~~~");
        setChatUsers(res.data);
        if (res.data.length > 0) {
          handleUserClick(res.data[0], user);
        }
      });
    });
  }, []);

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleSendMessage = () => {
    if (inputValue !== "") {
      axios
        .post(Constants.API_CHAT, {
          senderId: user.user_id,
          receiverId: selectedUser.user_id,
          message: inputValue,
        })
        .then(() => {
          ws.current.send(
            JSON.stringify({
              senderId: user.user_id,
              receiverId: selectedUser.user_id,
              message: inputValue,
            })
          );
          setInputValue("");
        });
    }

  };

  const handleClearMessages = () => {
    setClearMsg(true);
    axios
      .delete(`${Constants.API_CHAT}/all/${user.user_id}/${selectedUser.user_id}`)
      .then((res) => {
        console.log(res);
        setMessages([]);
      });
  };


  const handleKeyDown = (event) => {
    if (event.key === "Enter") {
      handleSendMessage();
    }
  };

  return (
    <div className="inboxArea">
      <div className="userArea">
        <Typography
          sx={{ fontSize: "24px", fontWeight: "450" }}
          className="titleSidebar"
        >
          Messages
        </Typography>

        <div className="userList">
          {chatUsers.map((currUser, index) => (
            <div
              className="users"
              key={index}
              onClick={() => handleUserClick(currUser, user)}
            >
              <div className="user-picture">
                {/* <img style={{ width: 15, height: 15 }} alt="pfp chat user" src={chatProfileImg}></img>  */}
                {
                  currUser.first_name[0] + currUser.last_name[0]
                }

              </div>
              <div className="user-name">
                {selectedUser && selectedUser.user_id === currUser.user_id ? (
                  <b style={{ color: "#3f0744" }}>{currUser.first_name + " " + currUser.last_name}</b>
                ) : (
                  currUser.first_name + " " + currUser.last_name
                )}
              </div>

              <DeleteIcon className="trashIcon" onClick={handleClearMessages}></DeleteIcon>

            </div>

          ))}
        </div>
      </div>
      <div className="chatWindow">
        <div className="messageContainer">
          {
            (clearMsg === true /*&& selectedUser === clearMsgUser*/) ? <div>Messages cleared</div> :

              messages.map((message, index) => (
                <div key={index}>
                  {
                    <div
                      className={
                        message.senderId === user.user_id
                          ? "myMessage"
                          : "otherMessage"
                      }
                    >
                      <b>
                        {message.senderId === user.user_id
                          ? "You"
                          : selectedUser.first_name}
                      </b>
                      : {message.message}
                    </div>
                  }
                </div>
              ))}
        </div>
        <div className="inputArea">
          <input
            className="chatInput"
            placeholder="Type your message here..."
            value={inputValue}
            onKeyDown={handleKeyDown}
            onChange={handleInputChange}
          />
          <Button
            sx={{ margin: "0rem 1rem", height: "36px", borderRadius: "18px", backgroundColor: "#3f0744" }}
            variant="contained"
            className="btnSend"
            onClick={handleSendMessage}
          >
            Send&nbsp;
            <SendIcon
              sx={{
                transform: "rotate(-52deg)",
                fontSize: "17px",
                marginTop: "-4px",
              }}
            ></SendIcon>
          </Button>
        </div>
      </div>
    </div>
  );
};

export default Inbox;
