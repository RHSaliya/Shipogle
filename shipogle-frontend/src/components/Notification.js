import React from 'react'
import { Link } from "react-router-dom";


export default function Notification(props) {
  const [notifType, setNotifType] = React.useState(0);
  // notifType 0 -> You have a chat message
  // notifType 1 -> You have a match (either with a sender or deliverer).




  return (
    <div>
        <h4> {props.notificationName}</h4>

        {notifType === 0 ? <Link to="/inbox">  <p> {props.notificationAction}</p></Link> :  <Link to="/userdash">  <p> {props.notificationAction}</p></Link>}
      
    </div>
  )
}
