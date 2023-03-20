import React from 'react'


export default function Notification(props) {
  return (
    <div>
        <h4> {props.notificationName}</h4>
        <p> {props.notificationAction}</p>
    </div>
  )
}
