import React from "react";
import { Link } from "react-router-dom";
import SearchIcon from "@mui/icons-material/Search";
import AddRoadIcon from "@mui/icons-material/AddRoad";
import Avatar from "@mui/material/Avatar";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import "./navBar.css";
import styled from "@emotion/styled";

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
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const handleClickOnExpand = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleCloseOnExpand = () => {
    setAnchorEl(null);
  };

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
          <Link to="/search-courier" style={{ textDecoration: "none" }}>
            Search
          </Link>
        </button>
        <button className="navbar-menu-item">
          <AddRoadIcon></AddRoadIcon> &nbsp;
          <Link to="/offer-courier" style={{ textDecoration: "none" }}>
            Deliver
          </Link>
        </button>
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            height: "64px",
            marginTop: "-8px",
          }}
        >
          <Avatar></Avatar>
          &nbsp;
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
                <Link style={{ textDecoration: "none" }} to="/login">
                  Login
                </Link>
              </MenuItem>
              <MenuItem>
                <Link style={{ textDecoration: "none" }} to="/registration">
                  Register
                </Link>
              </MenuItem>
            </Menu>
          </div>
        </div>
      </div>
    </div>
  );
}
