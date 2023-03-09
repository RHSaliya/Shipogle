import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { w3cwebsocket as WebSocket } from "websocket";
import Constants from "../Constants";

const Inbox = () => {
    const [messages, setMessages] = useState([]);
    const [inputValue, setInputValue] = useState("");
    const ws = useRef(null);
    const token = "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InJocy55b3BtYWlsLmNvbUB5b3BtYWlsLmNvbSIsInN1YiI6IlJhaHVsIiwiaWF0IjoxNjc4MzM3MTA1LCJleHAiOjE2NzgzNDA3MDV9.Lq_5Ytqp8Ygx_ogyatoWfTzLTJ6X0OxPT0B0jszoOSMP3i5P8TuHSdnIcsW8ZHAG";

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

        ws.current = new WebSocket("ws://localhost:8080/socket");

        ws.current.onopen = () => {
            console.log('WebSocket Client Connected');
        };

        ws.current.onmessage = (message) => {
            setMessages((prevMessages) => [...prevMessages, JSON.parse(message.data)]);
        };

        // return () => {
        // ws.current.close();
        // };
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
