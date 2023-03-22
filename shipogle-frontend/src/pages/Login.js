/*
import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import Header from "../components/Header";

import shipogleLogo from "../assets/shipogleLogo.png";
import Constants from "../Constants";
import axios from "axios";
import Cookies from "js-cookie";

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
    console.log(Constants.API_LOGIN);

        axios
            .post(Constants.API_LOGIN, {
                "email": email,
                "password": password
            })
            .then((response) => {
                //            navigate(path);
                console.log("response value: " + response.value);
                console.log("response.data :::: " +response.data);
             
                //Set the token as cookie
                const token = response.data;

                Cookies.set("authToken", token, { expires: COOKIE_EXPIRATION_TIME });
                console.log(Cookies.get('authToken'));
                navigate(path, {
                    state : {
                        email: email,
                        password: password
                    }
                });
            })
            .catch((err) => console.log(err));
    };

  return (
    <div className="loginPage">
      <Header title="S H I P O G L E" info="tagline" />

      <div className="login-box">
        <img alt="logo" src={shipogleLogo} width="100px" height="100px"></img>
        <p>
          Don't have an account yet?{" "}
          <Link to="/registration">Register now!</Link>
        </p>
        <form className="login-form">
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
          <Link to="/forgotPwd">
            <p>Forgot Password?</p>
          </Link>
          <button className="btn" type="submit" onClick={submit}>
            Log in
          </button>
        </form>
      </div>
    </div>
  );
}
*/

import React from "react";
import Cookies from "js-cookie";
import axios from "axios";
import { Link } from "react-router-dom";

import Button from "@mui/material/Button";
import Card from "@mui/material/Card";
import TextField from "@mui/material/TextField";

import Constants from "../Constants";
import CommonFunctions from "../services/CommonFunction";
import Header from "../components/Header";

class Login extends React.Component {
  commFunc = new CommonFunctions();
  COOKIE_EXPIRATION_TIME = 1 / 24;
  constructor() {
    super();
    this.state = {
      showPage: false,
      email: "",
      password: "",
      containerStyle: {},
    };
  }

  componentDidMount() {
    console.log(window.localStorage);
    this.setBackgroundImage(window.localStorage.getItem("backgroundUrlLogin"));
  }
  render() {
    return (
      <>
        {this.state.showPage && (
          <div style={this.state.containerStyle} className="container">
            <div
              style={{
                height: "100%",
                width: "100%",
                backdropFilter: "blur(1px)",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              <Card
                sx={{
                  maxWidth: "420px",
                  width: "100%",
                  height: "430px",
                  padding: "0rem 1rem 0rem 1rem",
                  display: "flex",
                  flexFlow: "column",
                  justifyContent: "center",
                  background:
                    "linear-gradient(180deg, rgba(255,255,255,0.9514399509803921) 0%, rgba(242,242,242,0.8954175420168067) 50%, rgba(230,230,230,0.6041010154061625) 100%);",
                }}
              >
                <div style={{ marginTop: "-2rem", marginBottom: "1rem" }}>
                  <Header
                    title="S H I P O G L E"
                    info=""
                    bgColor="transparent"
                  ></Header>
                </div>

                <h2 style={{ marginTop: "-2rem" }}>Login</h2>
                <form onSubmit={this.submit}>
                  <TextField
                    sx={{ width: "100%", marginBottom: "1rem" }}
                    required
                    id="username"
                    label="Username"
                    variant="outlined"
                    type="email"
                    placeholder="e.g : Johndoe@email.com"
                    onChange={(event) => {
                      this.setState({ email: event.target.value });
                    }}
                  />
                  <br></br>
                  <TextField
                    sx={{ width: "100%", marginBottom: "0rem" }}
                    required
                    id="password"
                    type="password"
                    label="Password"
                    variant="outlined"
                    placeholder="password***"
                    onChange={(event) => {
                      this.setState({ password: event.target.value });
                    }}
                  />
                  <p style={{ fontSize: "12px" }}>
                    Don't have a shipogle account?{" "}
                    <Link
                      style={{ textDecoration: "none", color: "blue" }}
                      to="/"
                    >
                      Signup
                    </Link>
                  </p>
                  <Link to="/forgotPwd">
                    <p style={{ fontSize: "14px" }}>Forgot Password?</p>
                  </Link>
                  <div style={{ textAlign: "center", marginTop: "2rem" }}>
                    <Button
                      sx={{
                        minWidth: "94px",
                        maxWidth: "240px",
                        width: "100%",
                      }}
                      variant="contained"
                      type="submit"
                    >
                      Login
                    </Button>
                  </div>
                </form>
              </Card>
            </div>
          </div>
        )}
      </>
    );
  }

  login(event) {
    event.preventDefault();
    console.log("in login");
    window.location.href = "http://localhost:3000";
  }

  submit = (e) => {
    e.preventDefault();
    console.log("Submit", e);
    //props.handleSubmit();
    //navigate(path);
    console.log(Constants.API_LOGIN);

    axios
      .post(Constants.API_LOGIN, {
        email: this.state.email,
        password: this.state.password,
      })
      .then((response) => {
        //            navigate(path);
        console.log(response.data);
        //Set the token as cookie
        const token = response.data;
        Cookies.set("authToken", token, {
          expires: this.COOKIE_EXPIRATION_TIME,
        });
        console.log(Cookies.get("authToken"));
        window.location.href = window.location.origin;
      })
      .catch((err) => console.log(err));
  };

  setBackgroundImage(url) {
    console.log(url, "this is the url");
    if (url === "") {
      const containerStyle = {
        background:
          "linear-gradient(180deg, rgba(245,245,245,1) 0%, rgba(255,255,255,0.7805716036414566) 100%)",
        height: "calc(100vh - 58px)",
        width: "100%",
        margin: "0px",
      };
      this.setState({ showPage: true, containerStyle: containerStyle });
    } else {
      const containerStyle = {
        backgroundImage: "url(" + url + ")",
        height: "calc(100vh - 58px)",
        width: "100%",
        margin: "0px",
        backgroundPosition: "center center",
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover",
      };
      this.setState({ showPage: true, containerStyle: containerStyle });
    }
  }
}

export default Login;
