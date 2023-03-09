import React, { useState } from 'react'
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import Header from '../components/Header'
import NavBar from '../components/NavBar';
import shipogleLogo from "../assets/shipogleLogo.png";
import Constants from '../Constants';
import axios from 'axios';
import Cookies from 'js-cookie';

export default function Login(props) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    let path = "/userdash";

    const COOKIE_EXPIRATION_TIME = 1 / 24;

    let navigate = useNavigate();
    const submit = (e) => {
        e.preventDefault();
        console.log("Submit");
        //props.handleSubmit();
        //navigate(path);
        console.log(Constants.BASE_URL + Constants.API_LOGIN)

        axios
            .post(Constants.BASE_URL + Constants.API_LOGIN, {
                "email": email,
                "password": password
            })
            .then((response) => {
                //            navigate(path);
                console.log(response);
                console.log(response.data);
                //Set the token as cookie
                const token = response.data;
                Cookies.set("authToken", token, { expires: COOKIE_EXPIRATION_TIME });
                console.log(Cookies.get('authToken'));
                 navigate(path);
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
