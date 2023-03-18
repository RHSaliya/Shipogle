import React, { useState } from 'react';
import Header from '../components/Header';
import shipogleLogo from '../assets/shipogleLogo.png';
import Constants from '../Constants';
import axios from 'axios';
import Cookies from 'js-cookie';


export default function ForgotPwd() {
    const [email, setEmail] = useState("");

    const submit = (e) => {
        e.preventDefault();
        console.log("Submit");
        //props.handleSubmit();
        //navigate(path);
        console.log(Constants.BASE_URL + Constants.API_FORGOT_PWD)

        axios
            .post(Constants.BASE_URL + Constants.API_FORGOT_PWD, {
                "email": email,
            })
            .then((response) => {
                console.log(response);
            
            })
            .catch((err) => console.log(err));
    };
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
                    <button className="btn" type="submit" onClick={submit}>
                        Reset password
                    </button>
                </div>





            </div>

        </div>

    )
}
