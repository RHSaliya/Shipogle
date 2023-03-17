import React from 'react'
import Header from '../components/Header'
import CommonRegForms from '../components/CommonRegForms'
import {useLocation} from 'react-router-dom';
export default function RegistrationSender() {


  const state = useLocation();
  
  console.log(state.state.user_type);
  const titleVar = "Register as a " + state.state.user_type;
  return (
    <div className="regPageForm">
        <Header 
            title= {titleVar}
            info="" />
        <CommonRegForms />
        
    </div>
  )
}
