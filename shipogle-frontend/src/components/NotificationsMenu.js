import React, { useState, useEffect, useRef } from "react";

import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import NotificationsIcon from '@mui/icons-material/Notifications';
import Notification from './Notification';
import NotificationsActiveIcon from '@mui/icons-material/NotificationsActive';
import axios from "../utils/MyAxios";
import Constants from "../Constants";
import { w3cwebsocket as WebSocket } from "websocket";

import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';


export default function NotificationsMenu() {
  const [dialogOpen, setDialogOpen] = React.useState(false);
  const [user, setUser] = useState({});
  const [anchorEl, setAnchorEl] = useState(null);
  const [hasNotfication, setHasNotification] = useState(false);
  const [notifications, setNotifications] = useState([]);
  const [count, setCount] = useState(0);
  const [clearNotif, setClearNotif] = useState(false);
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
  }
  const handleClearNotif = () => {
    setDialogOpen(true);
  }

  const handleClear = () => {
    setDialogOpen(false);
    axios.delete(`${Constants.API_NOTIFICATIONS}/all/${user.user_id}`).then((response) => {
      console.log(response);
      setClearNotif(prevClearNotif => true);
    });
  };


  useEffect(() => {
    // Get user info from token
    axios.get(Constants.API_USER_INFO_FROM_TOKEN).then((response) => {
      console.log("~~~~~~~~~~~~~~");
      const user = response.data;
      setUser(user);
      console.log(user);

      // Get notifications for current user
      axios.get(`${Constants.API_NOTIFICATIONS}/${user.user_id}`).then((res) => {
        console.log("~~~~~~~~~~~~~~");
        console.log(res.data);
        setCount(res.data.length);
        setNotifications(res.data.reverse());
        console.log("~~~~~~~~~~~~~~");
      });

      ws.current = new WebSocket(`${Constants.SOCKET_NOTIFICATIONS}/${user.user_id}`);

      ws.current.onmessage = (message) => {
        console.log(message);
        const value = JSON.parse(message.data);
        setCount(notifications.length + 1);
        setNotifications((prevNotifications) => [value, ...prevNotifications]);
        setHasNotification(true && !open);
      };


      ws.current.onopen = () => {
        console.log('Notification WebSocket Client Connected');
      };

      ws.current.onclose = () => {
        console.log('Notification WebSocket Client Disconnected');
      }

      return () => {
        ws.current.close();
      };
    });
  }, []);

  return (
    <div>
      <Dialog
        open={dialogOpen}
        onClose={handleCloseDialog}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          Please confirm
        </DialogTitle>
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

      <Button
        id="demo-positioned-button"
        aria-controls={open ? 'demo-positioned-menu' : undefined}
        aria-haspopup="true"
        aria-expanded={open ? 'true' : undefined}
        onClick={handleClick}
      >
        <div onClick={handleNotif}>
          {hasNotfication ? <NotificationsActiveIcon sx={{ color: "red" }} /> : <NotificationsIcon sx={{ color: "white" }} />}
        </div>

      </Button>
      <Menu
        className="notifMenu"
        id="demo-positioned-menu"
        aria-labelledby="demo-positioned-button"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'left',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'left',
        }}
      >
        <div className="notification-header">
          <p style={{ padding: "0 1em 0 1em", fontSize: "20px", fontWeight: "bold", textAlign: "center" }}>Notifications</p>
          <button className="btn" onClick={handleClearNotif}>Clear</button>

        </div>

        {
          (clearNotif === true ? <p style={{ padding: "0 1em 0 1em", textAlign: "center" }}>Empty Notifications</p> :
            notifications.map((notification, index) => (
              <MenuItem style={{ borderBottom: "1px solid black" }} sx={{ width: "500px" }} onClick={handleClose}><Notification notificationName={notification.title} notificationAction={notification.message} /></MenuItem>)

            ))}
      </Menu>
    </div>
  );
}
