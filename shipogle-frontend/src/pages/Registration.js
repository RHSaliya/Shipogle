import React, { useState } from 'react';
import { useNavigate } from "react-router-dom";
import Header from '../components/Header'
import RegRollBox from '../components/RegRollBox'
import senderImg from "../assets/sender.png";
import delivererImg from "../assets/deliverer.png";
export default function Registration() {

  const[type, setType] = useState("sender");
  console.log(type);

  let navigate = useNavigate();
  let userIsSender = () => {
    setType("sender");
    let path = "/registration/form";
    navigate(path);
  }
  let userIsDeliverer = () => {
    setType("deliverer");
    let path = "/registration/form";
    navigate(path);
  }




  return (
    <div className="regPage">
      <Header 
      title="Registration"
      info="Register as a deliverer or sender with us!" />

      <div className="regRole">
        <div className="one">
          <RegRollBox
          role="Sender"
          roleimg = {senderImg}
          roleinfo="I want to send my package from A to B before a due date!"
          />
          <center><div><button className="btn reg" onClick={() => userIsSender()} >Register</button></div></center>
        </div>
        <div className="two">
          <RegRollBox 
          role="Deliverer"
          roleimg = {delivererImg}
          roleinfo="I am travelling and accepting packages from A to B!"
          />
          <center><button className="btn reg" onClick={() => userIsDeliverer()}>Register</button></center>
        </div>
        
      </div>
    </div>
  )
}
