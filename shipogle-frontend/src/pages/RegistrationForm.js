import React from 'react'
import Header from '../components/Header'
import CommonRegForms from '../components/CommonRegForms'

export default function RegistrationSender() {
  return (
    <div className="regPageForm">
        <Header 
            title="Become a [type]!"
            info="" />
        <CommonRegForms />
        
    </div>
  )
}
