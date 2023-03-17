import React, { useState, useEffect, useRef } from "react";
import axios from "../../utils/MyAxios";
import { w3cwebsocket as WebSocket } from "websocket";
import Constants from "../../Constants";
import "./inbox.css";
import chatProfileImg from "../../assets/profile.png";

const getUniqueSocketAddress = (user, selectedUser) => {
    const joinUsing = "!";
    return `${user.user_id}${joinUsing}${selectedUser.user_id}`;
}

const Inbox = () => {
    const [user, setUser] = useState({});
    const [selectedUser, setSelectedUser] = useState({});
    const [messages, setMessages] = useState([]);
    const [chatUsers, setChatUsers] = useState([]);
    const [inputValue, setInputValue] = useState("");
    const ws = useRef(null);

    function handleUserClick(selectedUser) {
        setSelectedUser(selectedUser);

        // get messages for the first time
        axios.get(`${Constants.API_CHAT}/${user.user_id}/${selectedUser.user_id}`).then((response) => {
            setMessages(response.data);
        });

        console.log(`ws://localhost:8080/chatSocket/${getUniqueSocketAddress(user, selectedUser)}`);
        ws.current = new WebSocket(`ws://localhost:8080/chatSocket/${getUniqueSocketAddress(user, selectedUser)}`);

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
                setChatUsers(res.data);
                console.log("~~~~~~~~~~~~~~");
            });
        });
    }, []);

    const handleInputChange = (event) => {
        setInputValue(event.target.value);
    };

    const handleSendasMeClick = () => {
        axios
            .post(Constants.API_CHAT, {
                senderId: user.user_id,
                receiverId: selectedUser.user_id,
                message: inputValue,
            })
            .then(() => {
                ws.current.send(JSON.stringify({
                    senderId: user.user_id,
                    receiverId: selectedUser.user_id,
                    message: inputValue,
                }));
                setInputValue("");
            });
    };

    const handleSendasOtherClick = () => {
        axios
            .post(Constants.API_CHAT, {
                senderId: selectedUser.user_id,
                receiverId: user.user_id,
                message: inputValue,
            })
            .then(() => {
                ws.current.send(JSON.stringify({
                    senderId: selectedUser.user_id,
                    receiverId: user.user_id,
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
                        <div className="users" key={index} onClick={() => handleUserClick(user)} >
                            <div className="user-picture">
                                {/* <img style={{ width: 15, height: 15 }} alt="pfp chat user" src={chatProfileImg}></img>  */}
                                {index % 2 === 0 ? <img style={{ width: 20, height: 20 }} alt="pfp chat user" src={chatProfileImg}></img> :

                                    user.first_name[0] + user.last_name[0]}
                            </div>
                            <div className="user-name" >
                                <div>
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
                                <div className={message.senderId === user.user_id ? "myMessage" : "otherMessage"}>
                                    <b>{message.senderId === user.user_id ? 'You' : selectedUser.first_name}</b>: {message.message}
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
