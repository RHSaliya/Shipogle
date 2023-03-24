import React, { useContext, useEffect, useState } from "react";
import Cookies from "js-cookie";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

import Button from "@mui/material/Button";
import Card from "@mui/material/Card";
import TextField from "@mui/material/TextField";

import Constants from "../Constants";
import CommonFunctions from "../services/CommonFunction";
import Header from "../components/Header";
import { AuthContext } from "../utils/Auth";

export default function Login() {
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();
  const commFunc = new CommonFunctions();
  const COOKIE_EXPIRATION_TIME = 1 / 24;
  const [showPage, setShowPage] = useState(false);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [containerStyle, setContainerStyle] = useState({});

  useEffect(() => {
    setBackgroundImage(window.localStorage.getItem("backgroundUrlLogin"));
  }, []);

  const setBackgroundImage = (url) => {
    if (url === "") {
      const containerStyle = {
        background:
          "linear-gradient(180deg, rgba(245,245,245,1) 0%, rgba(255,255,255,0.7805716036414566) 100%)",
        height: "calc(100vh - 58px)",
        width: "100%",
        margin: "0px",
      };
      setShowPage(true);
      setContainerStyle(containerStyle);
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
      setShowPage(true);
      setContainerStyle(containerStyle);
    }
  };

  const submit = (e) => {
    e.preventDefault();

    axios
      .post(Constants.API_LOGIN, {
        email: email,
        password: password,
      })
      .then((response) => {
        const token = response.data;
        Cookies.set("authToken", token, {
          expires: COOKIE_EXPIRATION_TIME,
        });
        window.localStorage.setItem("authToken", token);
        login();
        navigate("/courier/search");
      })
      .catch((err) => console.error(err));
  };
  return (
    <>
      {showPage && (
        <div style={containerStyle} className="container">
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
              <form onSubmit={submit}>
                <TextField
                  sx={{ width: "100%", marginBottom: "1rem" }}
                  required
                  id="username"
                  label="Username"
                  variant="outlined"
                  value={email}
                  type="email"
                  placeholder="e.g : Johndoe@email.com"
                  onChange={(event) => {
                    setEmail(event.target.value);
                  }}
                />
                <br></br>
                <TextField
                  sx={{ width: "100%", marginBottom: "0rem" }}
                  required
                  id="password"
                  value={password}
                  type="password"
                  label="Password"
                  variant="outlined"
                  placeholder="password***"
                  onChange={(event) => {
                    setPassword(event.target.value);
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
