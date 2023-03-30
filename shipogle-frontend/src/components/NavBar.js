import { Link, useNavigate } from "react-router-dom";
import React, { useContext, useEffect } from "react";

import { IconButton } from "@mui/material";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import styled from "@emotion/styled";

import AddRoadIcon from "@mui/icons-material/AddRoad";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import ForumIcon from "@mui/icons-material/Forum";
import NotificationsNoneIcon from "@mui/icons-material/NotificationsNone";
import SearchIcon from "@mui/icons-material/Search";
import NotificationsMenu from "./NotificationsMenu";
import Tooltip from "@mui/material/Tooltip";
import "./navBar.css";
import { AuthContext } from "../utils/Auth";
import CommonFunctions from "../services/CommonFunction";

const ExpandButton = styled(Button)({
  minWidth: "18px",
  minHeight: "32px",
  width: "18px",
  height: "32px",

  marginRight: "4px",
  marginTop: "4px",
  color: "black",
});

export default function NavBar() {
  const { isAuthenticated, login, logout } = useContext(AuthContext);
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const commFunc = new CommonFunctions();
  const navigate = useNavigate();

  const handleClickOnExpand = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleCloseOnExpand = () => {
    setAnchorEl(null);
  };

  const route = (url) => {
    navigate(url);
  };
  useEffect(() => {
    console.log(isAuthenticated);
  }, [isAuthenticated]);
  return (
    <div className="navbar-container">
      <div className="navbar-logo">
        <img
          src="../assets/shipogleLogo.png"
          alt="Shipogle"
          className="navbar-logo-img"
        ></img>
      </div>
      <div className="navbar-secondary-menu"></div>
      <div className="navbar-menu">
        <button className="navbar-menu-item">
          <SearchIcon></SearchIcon> &nbsp;
          <Link style={{ textDecoration: "none" }} to="/courier/search">
            Search
          </Link>
        </button>
        <button
          className="navbar-menu-item"
          onClick={() => {
            if (!isAuthenticated)
              commFunc.showAlertMessage(
                "Please Login before posting an ad.",
                "info",
                3000,
                "bottom"
              );
          }}
        >
          <AddRoadIcon></AddRoadIcon> &nbsp;
          <Link
            to={isAuthenticated ? "/courier/offer" : "/login"}
            style={{ textDecoration: "none" }}
          >
            Deliver
          </Link>
        </button>
        &nbsp;
        {isAuthenticated && (
          <>
            {" "}
            <NotificationsMenu />
            <IconButton
              className="icon-buttons"
              onClick={() => {
                route("/inbox");
              }}
            >
              <ForumIcon></ForumIcon>
            </IconButton>
          </>
        )}
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            height: "64px",
            marginTop: "-8px",
          }}
        >
          <Avatar></Avatar>

          <div>
            <ExpandButton
              aria-label="expand"
              size="small"
              id="expandButton"
              aria-controls={open ? "basic-menu" : undefined}
              aria-haspopup="true"
              aria-expanded={open ? "true" : undefined}
              onClick={handleClickOnExpand}
            >
              <ExpandMoreIcon />
            </ExpandButton>
            {!isAuthenticated && (
              <>
                <Menu
                  id="basic-menu"
                  anchorEl={anchorEl}
                  open={open}
                  onClose={handleCloseOnExpand}
                  MenuListProps={{
                    "aria-labelledby": "basic-button",
                  }}
                >
                  <MenuItem>
                    <Link
                      style={{ textDecoration: "none", color: "black" }}
                      to="/login"
                    >
                      Login
                    </Link>
                  </MenuItem>
                  <MenuItem>
                    <Link style={{ textDecoration: "none" }} to="/registration">
                      Register
                    </Link>
                  </MenuItem>
                </Menu>
              </>
            )}
            {isAuthenticated && (
              <Menu
                id="basic-menu"
                anchorEl={anchorEl}
                open={open}
                onClose={handleCloseOnExpand}
                MenuListProps={{
                  "aria-labelledby": "basic-button",
                }}
              >
                <MenuItem>
                  <Link style={{ textDecoration: "none" }} to="/orders">
                    Orders
                  </Link>
                </MenuItem>
                <MenuItem>
                  <Link style={{ textDecoration: "none" }} to="/myrides">
                    Delivery Requests
                  </Link>
                </MenuItem>
                <MenuItem>
                  <Link style={{ textDecoration: "none" }} to="/deliveries">
                    Current Delivery
                  </Link>
                </MenuItem>
                <MenuItem
                  onClick={() => {
                    logout();
                  }}
                >
                  <Link style={{ textDecoration: "none" }} to="/login">
                    Logout
                  </Link>
                </MenuItem>
              </Menu>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
