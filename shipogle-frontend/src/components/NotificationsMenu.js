import React, { useState, useEffect, useRef } from "react";

import { IconButton } from "@mui/material";
import Button from "@mui/material/Button";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import NotificationsIcon from "@mui/icons-material/Notifications";
import Notification from "./Notification";
import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import axios from "../utils/MyAxios";
import Constants from "../Constants";
import { w3cwebsocket as WebSocket } from "websocket";

import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";

export default function NotificationsMenu() {
  const [dialogOpen, setDialogOpen] = React.useState(false);
  const [user, setUser] = useState({});
  const [anchorEl, setAnchorEl] = useState(null);
  const [hasNotfication, setHasNotification] = useState(false);
  const [notifications, setNotifications] = useState([]);
  const [emptyNotifications, setEmptyNotifications] = useState(false);
  const ws = useRef(null);

  const open = Boolean(anchorEl);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleCloseDialog = () => {
    setDialogOpen(false);
  };

  const handleNotif = () => {
    setHasNotification(false);
  };
  const handleClearNotif = () => {
    setDialogOpen(true);
  };

  const handleClear = () => {
    setDialogOpen(false);
    axios
      .delete(
        `${Constants.API_NOTIFICATIONS}/all/${localStorage.getItem("user_id")}`
      )
      .then((response) => {
        console.log(response);
        setEmptyNotifications(true);
      });
  };

  useEffect(() => {
    // Get user info from token
    axios
      .get(`${Constants.API_NOTIFICATIONS}/${localStorage.getItem("user_id")}`)
      .then((res) => {
        console.log("~~~~~~~~~~~~~~");
        console.log(res.data);
        setNotifications(res.data);
        if (res.data.length === 0) {
          setEmptyNotifications(true);
        }
        console.log("~~~~~~~~~~~~~~");
      });

    ws.current = new WebSocket(
      `${Constants.SOCKET_NOTIFICATIONS}/${localStorage.getItem("user_id")}`
    );

    ws.current.onmessage = (message) => {
      console.log(message);
      const value = JSON.parse(message.data);
      setEmptyNotifications(false);
      setNotifications((prevNotifications) => [...prevNotifications, value]);
      setHasNotification(true && !open);
    };

    ws.current.onopen = () => {
      console.log("Notification WebSocket Client Connected");
    };

    ws.current.onclose = () => {
      console.log("Notification WebSocket Client Disconnected");
    };

    return () => {
      ws.current.close();
    };
  }, []);

  return (
    <div>
      <Dialog
        open={dialogOpen}
        onClose={handleCloseDialog}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">Please confirm</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Are you sure you want to clear all notifications?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>Cancel</Button>
          <Button onClick={handleClear} autoFocus>
            Clear
          </Button>
        </DialogActions>
      </Dialog>

      <IconButton
        className="icon-buttons"
        id="demo-positioned-button"
        aria-controls={open ? "demo-positioned-menu" : undefined}
        aria-haspopup="true"
        aria-expanded={open ? "true" : undefined}
        onClick={handleClick}
      >
        {hasNotfication ? (
          <NotificationsActiveIcon sx={{ color: "red" }} />
        ) : (
          <NotificationsIcon sx={{ color: "grey" }} />
        )}
      </IconButton>

      <Menu
        className="notifMenu"
        id="demo-positioned-menu"
        aria-labelledby="demo-positioned-button"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "top",
          horizontal: "left",
        }}
        transformOrigin={{
          vertical: "top",
          horizontal: "left",
        }}
      >
        <div className="notification-header">
          <p
            style={{
              padding: "0 1em 0 1em",
              fontSize: "20px",
              fontWeight: "bold",
              textAlign: "center",
            }}
          >
            Notifications
          </p>
          <button className="btn" onClick={handleClearNotif}>
            Clear
          </button>
        </div>

        {emptyNotifications === true ? (
          <p style={{ padding: "0 1em 0 1em", textAlign: "center" }}>
            Empty Notifications
          </p>
        ) : (
          notifications.map((notification, index) => (
            <MenuItem
              style={{ borderBottom: "1px solid black" }}
              sx={{ width: "500px" }}
              onClick={handleClose}
            >
              <Notification
                notificationName={notification.title}
                notificationAction={notification.message}
              />
            </MenuItem>
          ))
        )}
      </Menu>
    </div>
  );
}
