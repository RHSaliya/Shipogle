import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { w3cwebsocket as WebSocket } from "websocket";
import Constants from "../Constants";

const Inbox = () => {
    const [messages, setMessages] = useState([]);
    const [inputValue, setInputValue] = useState("");
    const ws = useRef(null);
    const token = "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InJocy55b3BtYWlsLmNvbUB5b3BtYWlsLmNvbSIsInN1YiI6IlJhaHVsIiwiaWF0IjoxNjc4MzQ0MDY2LCJleHAiOjE2NzgzNDc2NjZ9.5kn0zsF9DDXsuKRlrAwiwfxg3bcs0yr5h5COWP4dEvutxNMA-p7_cDZqeX9Kf5e9";

    useEffect(() => {
        axios.get(Constants.BASE_URL + "/chat/25/60", {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then((response) => {
            setMessages(response.data);
            console.log("~~~~~~~~~~~~~~");
            console.log(response.data);
            console.log("~~~~~~~~~~~~~~");
        });

        ws.current = new WebSocket("ws://localhost:8080/chatSocket", null, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

        ws.current.onopen = () => {
            console.log('WebSocket Client Connected');
        };

        ws.current.onmessage = (message) => {
            console.log(message);
            setMessages((prevMessages) => [...prevMessages, JSON.parse(message.data)]);
        };

        ws.current.onclose = () => {
            console.log('WebSocket Client Disconnected');
        }

        return () => {
            ws.current.close();
        };
    }, []);

    const handleInputChange = (event) => {
        setInputValue(event.target.value);
    };

    const handleSendClick = () => {
        axios
            .post(Constants.BASE_URL + "/chat", {
                senderId: 25,
                receiverId: 60,
                message: inputValue,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
            .then(() => {
                setInputValue("");
            });
    };

    return (
        <div>
            <div>
                {messages.map((message, index) => (
                    <div key={index}>
                        {message.senderId}: {message.message}
                    </div>
                ))}
            </div>
            <div>
                <input value={inputValue} onChange={handleInputChange} />
                <button onClick={handleSendClick}>Send</button>
            </div>
        </div>
    );
};

export default Inbox;
