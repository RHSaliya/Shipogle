import * as React from 'react';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import NotificationsIcon from '@mui/icons-material/Notifications';
import Notification from './Notification';


export default function NotificationsMenu() {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };


  return (
    <div>
      <Button
        id="demo-positioned-button"
        aria-controls={open ? 'demo-positioned-menu' : undefined}
        aria-haspopup="true"
        aria-expanded={open ? 'true' : undefined}
        onClick={handleClick}
      >
         <NotificationsIcon sx={{ color: "white" }} />
      </Button>
      <Menu
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
        <p style={{padding:"0 1em 0 1em"}}>Notifications</p>
        <MenuItem sx ={{width:"500px"}} onClick={handleClose}><Notification notificationName="Notification1" notificationAction="Matched with a sender." /></MenuItem>
        <MenuItem sx ={{width:"500px"}} onClick={handleClose}><Notification notificationName="Notification2" notificationAction="You have a new message." /></MenuItem>
        <MenuItem sx ={{width:"500px"}} onClick={handleClose}><Notification notificationName="Notification3" notificationAction="We found some possible matches." /></MenuItem>
      </Menu>
    </div>
  );
}
