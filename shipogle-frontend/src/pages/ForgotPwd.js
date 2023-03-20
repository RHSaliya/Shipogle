import React, { useState } from 'react';
import Header from '../components/Header';
import shipogleLogo from '../assets/shipogleLogo.png';
import Constants from '../Constants';
import axios from 'axios';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';


export default function ForgotPwd() {
    const [email, setEmail] = useState("");
    const [showMsg, setShowMsg] = useState(0);



    const submit = (e) => {
        setShowMsg(prevShowMsg => 1);
        e.preventDefault();
        console.log("Submit");
        //props.handleSubmit();
        //navigate(path);
        console.log(Constants.API_FORGOT_PWD)

        axios
            .post(Constants.API_FORGOT_PWD, {
                "email": email,
            })
            .then((response) => {
                console.log(response);

            })
            .catch((err) => console.log(err));


    };
    return (
        <div className="regSuccessPage">
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
                    {showMsg === 1 ? <p>Reset password email has been sent.</p> : <p></p>}
                </div>





            </div>

        </div>

    )
}
