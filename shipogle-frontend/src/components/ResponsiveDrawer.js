import * as React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import Divider from '@mui/material/Divider';
import Drawer from '@mui/material/Drawer';
import axios from "../utils/MyAxios";
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';


import MenuIcon from '@mui/icons-material/Menu';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import AccountBoxIcon from '@mui/icons-material/AccountBox';
import ChatIcon from '@mui/icons-material/Chat';
import PersonSearchIcon from '@mui/icons-material/PersonSearch';
import { purple } from '@mui/material/colors';
import RouteIcon from '@mui/icons-material/Route';
import Inventory2Icon from '@mui/icons-material/Inventory2';
import LocalShippingIcon from '@mui/icons-material/LocalShipping';
import NotificationsMenu from './NotificationsMenu';
import Constants from "../Constants";

const drawerWidth = 240;

function ResponsiveDrawer(props) {
  const { window } = props;
  const [mobileOpen, setMobileOpen] = React.useState(false);
  const [user, setUser] = React.useState({});

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen);
  };


  const [key, setKey] = React.useState(0);

  React.useEffect(() => {
    // Get user info from token
    axios.get(Constants.API_USER_INFO_FROM_TOKEN).then((response) => {
      const user = response.data;
      console.log(user);
      setUser(user);
    });
  }, []);

  const drawer = (
    <div>
      <Toolbar />
      <Divider />
      <List >
        <Link to="/userdash/send">
          <ListItem key="0" disablePadding>
            <ListItemButton onClick={() => setKey(0)}>
              <ListItemIcon>
                <Inventory2Icon sx={{ color: purple[800] }} />
              </ListItemIcon>
              <ListItemText primary="Send Item" />
            </ListItemButton>
          </ListItem>
        </Link>


        <Link to="/userdash/deliver">
          <ListItem key="1" disablePadding>
            <ListItemButton onClick={() => setKey(1)}>
              <ListItemIcon>
                <LocalShippingIcon sx={{ color: purple[800] }} />
              </ListItemIcon>
              <ListItemText primary="Deliver Items" />
            </ListItemButton>
          </ListItem>
        </Link>
      </List>
      <Divider />
      <List>

        <Link to="/inbox">
          <ListItem key="5" disablePadding>
            <ListItemButton onClick={() => setKey(2)}>
              <ListItemIcon>
                <ChatIcon sx={{ color: purple[800] }} />
              </ListItemIcon>
              <ListItemText primary="Messages" />
            </ListItemButton>
          </ListItem>
        </Link>

        <Link to="/user/editProfile">
          <ListItem key="5" disablePadding>
            <ListItemButton onClick={() => setKey(3)}>
              <ListItemIcon>
                <AccountBoxIcon sx={{ color: purple[800] }} />
              </ListItemIcon>
              <ListItemText primary="Edit Profile" />
            </ListItemButton>
          </ListItem>
        </Link>

      </List>
    </div>
  );

  const container = window !== undefined ? () => window().document.body : undefined;

  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar
        position="fixed"
        sx={{
          width: { sm: `calc(100% - ${drawerWidth}px)` },
          ml: { sm: `${drawerWidth}px` },
        }}
      >
        <Toolbar className="dashboardToolbar">
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={handleDrawerToggle}
            sx={{ mr: 2, display: { sm: 'none' } }}
          >
            <MenuIcon />
          </IconButton>
          <div className="dashboard-bar">
            <Typography variant="h6" noWrap component="div">Welcome {user !== undefined ? user.first_name + " " + user.last_name : ""}</Typography>

            <Tooltip title="Notifications">
              <NotificationsMenu />
            </Tooltip>

          </div>
        </Toolbar>
      </AppBar>
      <Box
        component="nav"
        sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}
        aria-label="mailbox folders"
      >
        {/* The implementation can be swapped with js to avoid SEO duplication of links. */}
        <Drawer
          container={container}
          variant="temporary"
          open={mobileOpen}
          onClose={handleDrawerToggle}
          ModalProps={{
            keepMounted: true, // Better open performance on mobile.
          }}
          sx={{
            display: { xs: 'block', sm: 'none' },
            '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
          }}
        >
          {drawer}
        </Drawer>
        <Drawer
          variant="permanent"
          sx={{
            display: { xs: 'none', sm: 'block' },
            '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
          }}
          open
        >
          {drawer}
        </Drawer>
      </Box>
      <Box
        component="main"
        sx={{ flexGrow: 1, p: 3, width: { sm: `calc(100% - ${drawerWidth}px)` } }}
      >
        <Toolbar />
        <Typography paragraph>
          1. Current package
          2. Current routes

        </Typography>



      </Box>
    </Box>
  );
}

ResponsiveDrawer.propTypes = {
  /**
   * Injected by the documentation to work in an iframe.
   * You won't need it on your project.
   */
  window: PropTypes.func,
};

export default ResponsiveDrawer;