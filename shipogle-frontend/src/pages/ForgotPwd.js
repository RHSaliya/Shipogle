import React, { useState } from 'react';
import Header from '../components/Header';
import shipogleLogo from '../assets/shipogleLogo.png';



export default function ForgotPwd() {
    const [email, setEmail] = useState("");
    return (
        <div classname="regSuccessPage">
            <Header
                title="S H I P O G L E"
                info="tagline"
            />
            <center>
                <img alt="logo" src={shipogleLogo} width="200px" height="200px">

                </img>
            </center>
            <div className="regSuccessBox">
                <div>
                    <h1>Forgot Password</h1>
                  
                    <p>Please enter your email ID.</p>
                    <input
                        className="forgotPwdMailField"
                        id="email"
                        name="email"
                        placeholder="john@example.com"
                        type="email"
                        onChange={(e) => setEmail(e.target.value)}
                    />
                 
                   
                </div>
                <br></br>
                <div>
                    <button className="btn" type="submit">
                        Reset password
                    </button>
                </div>





            </div>

        </div>

    )
}
