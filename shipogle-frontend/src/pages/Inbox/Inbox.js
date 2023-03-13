import React, { useState, useEffect, useRef } from "react";
import axios from "../../utils/MyAxios";
import { w3cwebsocket as WebSocket } from "websocket";
import Constants from "../../Constants";
import "./inbox.css";
import chatProfileImg from "../../assets/profile.png";

const Inbox = () => {
    const [messages, setMessages] = useState([]);
    const [chatUsers, setChatUsers] = useState([]);
    const [inputValue, setInputValue] = useState("");
    const [receiverId, setReceiverId] = useState(60);

   


    const ws = useRef(null);
    const myId = 25;

    function handleSelectUser(user) {
        
        console.log("Selected user is: " + user.first_name);
        setReceiverId(prevReceiverId => user.user_id);
        console.log(receiverId);
    }

    useEffect(() => {
        axios.get(`${Constants.API_CHAT}/${myId}`).then((response) => {
            console.log("~~~~~~~~~~~~~~");
            console.log(response.data);
            setChatUsers(response.data);
            console.log("~~~~~~~~~~~~~~");
        });
    }, []);

    useEffect(() => {
        // get messages for the first time
        axios.get(`${Constants.API_CHAT}/${myId}/${receiverId}`).then((response) => {
            setMessages(response.data);
        });

        ws.current = new WebSocket("ws://localhost:8080/chatSocket");

        ws.current.onopen = () => {
            console.log('WebSocket Client Connected');
        };

        ws.current.onmessage = (message) => {
            console.log("got message");
            console.log(message);
            const value = JSON.parse(message.data);
            const msg = {
                senderId: value.senderId,
                receiverId: value.receiverId,
                message: value.message,
            }
            setMessages((prevMessages) => [...prevMessages, msg]);
        };

        ws.current.onclose = () => {
            console.log('WebSocket Client Disconnected');
        }

        return () => {
            ws.current.close();
        };
    }, [receiverId]);

    const handleInputChange = (event) => {
        setInputValue(event.target.value);
    };

    const handleSendasMeClick = () => {
        axios
            .post(Constants.API_CHAT, {
                senderId: myId,
                receiverId: receiverId,
                message: inputValue,
            })
            .then(() => {
                ws.current.send(JSON.stringify({
                    senderId: myId,
                    receiverId: receiverId,
                    message: inputValue,
                }));
                setInputValue("");
            });
    };

    const handleSendasOtherClick = () => {
        axios
            .post(Constants.API_CHAT, {
                senderId: receiverId,
                receiverId: myId,
                message: inputValue,
            })
            .then(() => {
                ws.current.send(JSON.stringify({
                    senderId: receiverId,
                    receiverId: myId,
                    message: inputValue,
                }));
                setInputValue("");
            });
    };

    return (
        <div className="inboxArea">
            <div className="userArea">
                <p className="titleSidebar">Chats</p>
                <div className="userList">
                    {chatUsers.map((user, index) => (
                        <div className="users" key={index}>
                            <div className="user-picture">
                                {/* <img style={{ width: 15, height: 15 }} alt="pfp chat user" src={chatProfileImg}></img>  */}
                                {index % 2 === 0 ? <img style={{ width: 20, height: 20 }} alt="pfp chat user" src={chatProfileImg}></img> :

                                    user.first_name[0] + user.last_name[0]}
                            </div>
                            <div className="user-name" >
                                <div onClick ={() => handleSelectUser(user)} >
                                {
                                    user.first_name + " " + user.last_name
                                }
                                </div>
                                


                            </div>

                        </div>
                    ))}
                </div>
            </div>
            <div className="chatWindow">
                <div className="messageContainer">
                    {messages.map((message, index) => (
                        <div key={index}>
                            {
                                <div className={message.senderId === myId ? "myMessage" : "otherMessage"}>
                                    <b>{message.senderId === myId ? 'You' : 'Him'}</b>: {message.message}
                                </div>
                            }
                        </div>
                    ))}
                </div>
                <div className="inputArea">
                    <input className="chatInput" placeholder="Type your message here..." value={inputValue} onChange={handleInputChange} />
                    <button className="btnSend" onClick={handleSendasMeClick}>Send</button>
                    <button className="btnSend" style={{
                        marginLeft: "10px",
                    }} onClick={handleSendasOtherClick}>Reply</button>
                </div>
            </div>
        </div >
    );
};

export default Inbox;
