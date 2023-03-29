import React, { Component } from "react";
import ReactDOM from "react-dom";
import AlertMessage from "../components/AlertMessage";

class CommonFunctions extends Component {
  static backgroundUrl = "";
  static googleMapObjectStatus = false;

  showAlertMessage(message, type, duration, position) {
    const alertMessage = (
      <AlertMessage
        message={message}
        messageType={type}
        duration={duration}
        position={position}
      />
    );
    const bodyElement = document.querySelector("body");

    if (!document.getElementById("alert-container")) {
      const alertContainer = document.createElement("div");
      alertContainer.id = "alert-container";
      bodyElement.appendChild(alertContainer);
    }

    const alertContainer = document.getElementById("alert-container");

    ReactDOM.render(alertMessage, alertContainer);

    setTimeout(() => {
      ReactDOM.unmountComponentAtNode(alertContainer);
    }, duration + 50);
  }

  async fetchUrl() {
    const randomNumber = Math.floor(Math.random() * 1300);
    try {
      const response = await fetch(
        `https://source.unsplash.com/collection/11649432/800x600/?sig=${randomNumber}`
      );
      CommonFunctions.backgroundUrl = response.url;
      window.localStorage.setItem(
        "backgroundUrlLogin",
        CommonFunctions.backgroundUrl
      );
    } catch (error) {
      CommonFunctions.backgroundUrl = "";
      window.localStorage.setItem(
        "backgroundUrlLogin",
        CommonFunctions.backgroundUrl
      );
      console.error(error?.message);
    }
  }

  getUrl() {
    return CommonFunctions.backgroundUrl;
  }

  googleObjectDefinedStatus(status) {
    CommonFunctions.googleMapObjectStatus = status;
  }

  getCurrentLocation() {
    let userLocation = { latitude: 0, longitude: 0 };
    navigator.geolocation.getCurrentPosition(
      (position) => {
        userLocation.latitude = position.coords.latitude;
        userLocation.longitude = position.coords.longitude;
      },
      (error) => {
        this.showAlertMessage(
          error.message ? error.message : "Please give Location Access",
          "error",
          3000,
          "bottom"
        );
      }
    );
    return userLocation;
  }
}

export default CommonFunctions;
