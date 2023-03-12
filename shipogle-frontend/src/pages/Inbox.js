import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { w3cwebsocket as WebSocket } from "websocket";
import Constants from "../Constants";


const styles = {
    inboxArea: {
        backgroundColor: "#ead4ec",
    },
    messageArea: {
        padding: "1rem",
        width: "60%",
        margin: "0 0 0 auto",
        backgroundColor: "white",

    
    },
    otherMessage: {
        color: "#3F0744",
        backgroundColor : "white",
        border: "1px solid black",
        padding: "1em",
        marginRight: "20rem",
        marginBottom: "1em",
        display: "inline-block",
        borderRadius: '15px',
        borderBottomLeftRadius:'0',
    },
    myMessage: {
        backgroundColor: "#3F0744",
        color: "white",
        border: "1px solid black",
        padding: "1em",
        marginLeft: "20rem",
        marginBottom: "1em",
        display:"inline-block",
        float: "right",
        borderRadius:'15px',
        borderBottomRightRadius:'0',
    },
}

const Inbox = () => {
    const [messages, setMessages] = useState([]);
    const [inputValue, setInputValue] = useState("");
    const ws = useRef(null);
    const token = "eyJhbGciOiJIUzM4NCJ9.eyJlbWFpbCI6InJocy55b3BtYWlsLmNvbUB5b3BtYWlsLmNvbSIsInN1YiI6IlJhaHVsIiwiaWF0IjoxNjc4NTgxNzY1LCJleHAiOjE2ODM3NjU3NjV9.KUFvR1GNYpQQxiVKe_zQwyS3mvfGUWIRJ04nlNT4wTysIgQd94Z4fi95rGSRXYSy";
    const myId = 25;
    const receiverId = 60;
    // const receiverId = 58;

    useEffect(() => {
        axios.get(Constants.BASE_URL + `/chat/${myId}/${receiverId}`, {
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
        <div style={styles.inboxArea}>
            <div style={styles.messageArea}>
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
