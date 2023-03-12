import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { w3cwebsocket as WebSocket } from "websocket";
import Constants from "../../Constants";
import "./inbox.css";

const Inbox = () => {
    const [messages, setMessages] = useState([]);
    const [chatUsers, setChatUsers] = useState([]);
    const [inputValue, setInputValue] = useState("");
    const [receiverId, setReceiverId] = useState(60);
    const ws = useRef(null);
    const token = "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InJocy55b3BtYWlsLmNvbUB5b3BtYWlsLmNvbSIsInN1YiI6IlJhaHVsIiwiaWF0IjoxNjc4NTgxNzY1LCJleHAiOjE2ODM3NjU3NjV9.KUFvR1GNYpQQxiVKe_zQwyS3mvfGUWIRJ04nlNT4wTysIgQd94Z4fi95rGSRXYSy";
    const myId = 25;

    useEffect(() => {
    }, []);

    useEffect(() => {
        axios.get(`${Constants.API_CHAT}/${myId}/${receiverId}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then((response) => {
            setMessages(response.data);
            console.log("~~~~~~~~~~~~~~");
            console.log(response.data);
            console.log("~~~~~~~~~~~~~~");
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
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
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
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
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

            </div>
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
            <div>
                <input value={inputValue} onChange={handleInputChange} />
                <button onClick={handleSendasMeClick}>Send as Me</button>
                <button onClick={handleSendasOtherClick}>Send as Other</button>
            </div>
        </div >
    );
};

export default Inbox;
