import React from "react";

import Alert from "@mui/material/Alert";

class AlertMessage extends React.Component {
  message;
  messageType;
  duration;
  position;
  alertContainerStyle = {
    width: "100%",
    padding: "0.5rem, 1rem",
    position: "absolute",
    zIndex: "99999",
    margin: "0.5rem 0rem",
  };

  render() {
    Object.assign(
      this.alertContainerStyle,
      this.props?.position
        ? this.props?.position === "top"
          ? { top: 0 }
          : { bottom: 0 }
        : { bottom: 0 }
    );
    this.message = this.props?.message ? this.props?.message : "no message";
    this.messageType = this.props?.messageType
      ? this.props.messageType
      : "error";
    this.duration = this.props?.duration ? this.props.duration : 3000;
    this.position = this.props?.position ? this.props.position : "bottom";
    return (
      <div style={this.alertContainerStyle}>
        <Alert
          id="floating-alert-bar"
          style={{ width: "70%", margin: "auto" }}
          severity={this.messageType}
        >
          {this.message}
        </Alert>
      </div>
    );
  }

  componentDidMount() {
    setTimeout(() => {
      const alert = document.getElementById("floating-alert-bar");
      alert.remove();
    }, this.duration);
  }
}

export default AlertMessage;
