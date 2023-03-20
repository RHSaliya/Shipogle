import React from "react";
import { Outlet } from "react-router-dom";

import "./App.css";
import AlertMessage from "./components/AlertMessage";
import CommonFunctions from "../src/services/CommonFunction";
import NavBar from "./Components/Navbar";

const API_KEY = "AIzaSyBPtYm-CJPPW4yO9njM-e9YBWyp-DwIODM";
let userLocation = { latitude: "", longitude: "" };

window.initMap = function () {
  const comfunc = new CommonFunctions();
  comfunc.googleObjectDefinedStatus(true);
};
class App extends React.Component {
  alertSettings = {
    alertMessage: "",
    alertType: "",
    alertDuration: 0,
    position: "bottom",
  };

  commFunc = new CommonFunctions();
  constructor(props) {
    super(props);
    this.state = {
      showAlert: false,
    };
  }

  componentDidMount() {
    const head = document.querySelector("head");
    if (!document.getElementById("font-style-link")) {
      const link = document.createElement("link");
      link.rel = "stylesheet";
      link.setAttribute("id", "font-style-link");
      link.href =
        "https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap";
      head.append(link);
    }
    if (!document.getElementById("google-maps-script")) {
      const script = document.createElement("script");
      script.setAttribute("async", "");
      script.setAttribute("defer", "");
      script.setAttribute("id", "google-maps-script");
      script.src = `https://maps.googleapis.com/maps/api/js?key=${API_KEY}&libraries=places,maps,marker&callback=initMap`;
      head.append(script);
    }
    navigator.geolocation.getCurrentPosition(
      (position) => {
        userLocation.latitude = position.coords.latitude;
        userLocation.longitude = position.coords.longitude;
      },
      (error) => {
        this.setAlert(
          error.message ? error.message : "Please give Location Access",
          "error",
          3000,
          "bottom"
        );
      }
    );
  }

  render() {
    return (
      <>
        <NavBar></NavBar>
        <Outlet></Outlet>
        {this.state.showAlert && (
          <AlertMessage
            message={this.alertSettings.alertMessage}
            messageType={this.alertSettings.alertType}
            duration={this.alertSettings.alertDuration}
            position={this.alertSettings.position}
          ></AlertMessage>
        )}
      </>
    );
  }

  setAlert(message, type, duration, position) {
    this.setState({ showAlert: true });
    this.alertSettings.alertMessage = message;
    this.alertSettings.alertType = type;
    this.alertSettings.alertDuration = duration;
    this.alertSettings.position = position ? position : "bottom";
    setTimeout(() => {
      this.setState({ showAlert: false });
    }, duration + 50);
  }
}

export default App;
