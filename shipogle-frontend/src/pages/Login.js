import React, { useState } from 'react'
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import Header from '../components/Header'
import NavBar from '../components/NavBar';
import shipogleLogo from "../assets/shipogleLogo.png";
import Constants from '../Constants';
import axios from 'axios';

export default function Login(props) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    let path = "/userdash";

    let navigate = useNavigate();
    const submit = (e) => {
        e.preventDefault();
        console.log("Submit");
        //props.handleSubmit();
        //navigate(path);

        axios
            .post(Constants.BASE_URL + Constants.API_LOGIN, {
                "email": email,
                "password": password
            })
            .then((response) => {
                //            navigate(path);
                console.log(response);
                // You will save cookie here and move to next screen.

                // In the next screen when you make a request to the server, you will
                // use the token stored in the cookie.
            })
            .catch((err) => console.log(err));
    };

    return (
        <div className="loginPage">
            <NavBar />
            <Header
                title="S H I P O G L E"
                info="tagline"
            />

            <div className="login-box">
                <img alt="logo" src={shipogleLogo} width="100px" height="100px">
                </img>
                <p>Don't have an account yet? <Link to="/registration" >Register now!</Link></p>
                <form className="login-form" onSubmit={submit}>
                    <input
                        id="email"
                        name="email"
                        placeholder="john@example.com"
                        type="email"
                        onChange={(e) => setEmail(e.target.value)}
                    />

                    <input
                        id="password"
                        name="password"
                        placeholder="******"
                        type="password"
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <p>Forgot Password?</p>
                    <button className="btn" type="submit">
                        Log in
                    </button>
                </form>



            </div>
        </div>

    )
}
