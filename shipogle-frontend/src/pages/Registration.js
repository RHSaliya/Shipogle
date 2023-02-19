import React from 'react'
import Header from '../components/Header'
import RegRollBox from '../components/RegRollBox'
import senderImg from "../assets/sender.png";
import delivererImg from "../assets/deliverer.png";
export default function Registration() {
  return (
    <div className="regPage">
      <Header 
      title="Registration"
      info="Register as a deliverer or sender with us!" />

      <div className="regRole">
        <RegRollBox
        role="Sender"
        roleimg = {senderImg}
        roleinfo="I want to send my package from A to B before a due date!"
         />
        <RegRollBox 
        role="Deliverer"
        roleimg = {delivererImg}
        roleinfo="I am travelling and accepting packages from A to B!"
         />
      </div>
    </div>
  )
}
