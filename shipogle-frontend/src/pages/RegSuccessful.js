import React from 'react'
import {Link, useLocation} from 'react-router-dom';
import Header from '../components/Header';
import shipogleLogo from '../assets/shipogleLogo.png';
export default function RegSuccessful() {
  const loc = useLocation();
  return (
    <div classname="regSuccessPage">
         <Header 
      title="S H I P O G L E" 
      info = "tagline" 
      />
      <center>
      <img alt="logo" src={shipogleLogo} width="200px" height="200px">

      </img>
      </center>
      <div className="regSuccessBox">
        <h1>Registration successful!</h1>
        <Link to="/login"><p>Click here to login</p></Link>
        

    </div>
      
    </div>
   
  )
}
