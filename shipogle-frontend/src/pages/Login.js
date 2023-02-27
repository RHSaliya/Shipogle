import React, { useState } from "react";
import { Link } from "react-router-dom";
import Header from "../components/Header";
import NavBar from "../components/NavBar";
import shipogleLogo from "../assets/shipogleLogo.png";
export default function Login(props) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const submit = (e) => {
    props.handleSubmit();
  };

  return (
    <div className="loginPage">
      <NavBar />
      <Header title="S H I P O G L E" info="tagline" />
      <div className="login-box">
        <img alt="logo" src={shipogleLogo} width="100px" height="100px"></img>
        <p>
          Don't have an account yet?{" "}
          <Link to="/registration">Register now!</Link>
        </p>
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
  );
}
