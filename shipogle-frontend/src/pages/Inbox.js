import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { w3cwebsocket as WebSocket } from "websocket";
import Constants from "../Constants";

const styles = {
    myMessage: {
        backgroundColor: "#f5f5f5",
    },
    otherMessage: {
        backgroundColor: "#f5f5f5",
        justifyContent: "end",
        display: "flex",
    },
}

const Inbox = () => {
    const [messages, setMessages] = useState([]);
    const [inputValue, setInputValue] = useState("");
    const [sessionId, setSessionId] = useState(null);
    const ws = useRef(null);
    const token = "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InJocy55b3BtYWlsLmNvbUB5b3BtYWlsLmNvbSIsInN1YiI6IlJhaHVsIiwiaWF0IjoxNjc4NDE3MjQzLCJleHAiOjE2Nzg0MjA4NDN9.4hlqmh6XdY5jycqmaR8fFcBVNCgjgQ7GLONM9y6ICdcVb4W3rtafdUIlrHO7Jio-";
    const myId = 25;
    const receiverId = 60;

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

        const options = {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        };

        ws.current = new WebSocket("ws://localhost:8080/chatSocket", null, null, options);

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
    }, []);

    const handleInputChange = (event) => {
        setInputValue(event.target.value);
    };

    const handleSendasMeClick = () => {
        axios
            .post(Constants.BASE_URL + "/chat", {
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
            .post(Constants.BASE_URL + "/chat", {
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
        <div>
            Session ID: {sessionId}
            <div>
                {messages.map((message, index) => (
                    <div key={index}>
                        {
                            <div style={message.senderId === myId ? styles.myMessage : styles.otherMessage}>
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
        </div>
    );
};

export default Inbox;
