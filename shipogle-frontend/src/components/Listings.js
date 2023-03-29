/*
import * as React from "react";
import { useLocation, useNavigate } from "react-router-dom";

import ArrowForwardIcon from "@mui/icons-material/ArrowForward";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import WhereToVoteIcon from "@mui/icons-material/WhereToVote";
import IconButton from "@mui/material/IconButton";
import FormatListBulletedIcon from "@mui/icons-material/FormatListBulleted";
import MapIcon from "@mui/icons-material/Map";

import "./Listings.css";
import MapView from "./MapView";
import Data from "../pages/data";

export default function Listings(props) {
  const navigate = useNavigate();
  const [listingCards, setListingCards] = React.useState([]);
  const [listingsLoaded, setListingsLoaded] = React.useState(false);
  const [center, setCenter] = React.useState({ lat: 0, lng: 0 });
  const [showMapView, setShowMapView] = React.useState(false);
  const [showListView, setShowListView] = React.useState(true);
  const dataComponent = new Data();
  const listings = props.data;
  const createlistingcards = () => {
    let cards = [];
    listings.forEach((listing) => {
      cards.push(
        <div
          className="listing-card"
          onClick={() => {
            navigate("/courier/details/" + listing.postId);
          }}
        >
          <img
            className="listing-card-avatar"
            src={listing?.avatar}
            alt="avatar"
          ></img>
          <span
            style={{
              width: "1px",
              borderLeft: "1px solid #b2b2b2a1",
              marginLeft: "8px",
              marginRight: "8px",
            }}
          ></span>
          <div className="lisitng-card-header">
            <p className="listing-card-heading">{listing?.name}</p>
            <hr></hr>
            <div className="listing-card-subheading">
              <LocationOnIcon></LocationOnIcon> &nbsp;
              <p style={{ margin: "0px", marginTop: "3px", color: "#191919" }}>
                {listing?.pickupData?.description}
              </p>{" "}
              &nbsp;
              <ArrowForwardIcon></ArrowForwardIcon> &nbsp;{" "}
              <WhereToVoteIcon></WhereToVoteIcon>
              <p style={{ margin: "0px", marginTop: "3px", color: "#191919" }}>
                {listing?.dropoffData?.description}
              </p>{" "}
            </div>
          </div>
        </div>
      );
    });
    setListingsLoaded(true);
    setListingCards(cards);
    setCenter({
      lat: localStorage.getItem("userLocation")?.latitude,
      lng: localStorage.getItem("userLocation")?.longitude,
    });
  };
  React.useEffect(() => {
    createlistingcards();
  });
  return (
    <>
      <div
        className="courier-listing-container"
        style={{ marginBottom: "1rem" }}
      >
        <div className="view-buttons-container">
          <p style={{ margin: "0px", flexGrow: "1" }}>
            Tap on a deliverer to book or know more
          </p>
          <IconButton
            aria-label="list-view"
            onClick={() => {
              setShowListView(true);
              setShowMapView(false);
            }}
          >
            <FormatListBulletedIcon />
          </IconButton>
          <IconButton
            aria-label="map-view"
            onClick={() => {
              setShowListView(false);
              setShowMapView(true);
            }}
          >
            <MapIcon />
          </IconButton>
        </div>
        <br></br>
        {listingsLoaded && !showMapView && showListView && <>{listingCards}</>}

        {listingsLoaded && showMapView && !showMapView && (
          <div>
            <MapView listing={props.data}></MapView>
          </div>
        )}
      </div>
    </>
  );
}
*/

import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import ArrowForwardIcon from "@mui/icons-material/ArrowForward";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import WhereToVoteIcon from "@mui/icons-material/WhereToVote";
import IconButton from "@mui/material/IconButton";
import FormatListBulletedIcon from "@mui/icons-material/FormatListBulleted";
import MapIcon from "@mui/icons-material/Map";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

import "./Listings.css";
import MapView from "./MapView";
import { Typography } from "@mui/material";

function Listings({ data }) {
  const navigate = useNavigate();
  const [listingCards, setListingCards] = useState([]);
  const [showMapView, setShowMapView] = useState(false);

  const createListingCards = () => {
    const cards = data.map((listing) => (
      <div
        className="listing-card"
        onClick={() => navigate(`/courier/details/${listing?.driverRouteId}`)}
        key={listing?.driverRouteId}
      >
        {listing.avatar}
        <img
          className="listing-card-avatar"
          src={listing?.avatar}
          alt="avatar"
        ></img>
        <span className="listing-card-divider"></span>
        <div className="lisitng-card-header">
          <p className="listing-card-heading">
            {listing?.driveName ? listing.driveName : "Name not found"}
          </p>
          <hr style={{ marginBottom: "0px", flexGrow: 1 }} />
          <div className="listing-card-subheading">
            <LocationOnIcon /> &nbsp;
            <p className="listing-card-location">{listing?.sourceCity}</p>&nbsp;
            <ArrowForwardIcon />
            &nbsp;
            <WhereToVoteIcon />
            &nbsp;
            <p className="listing-card-location">{listing?.destinationCity}</p>
          </div>
        </div>
      </div>
    ));
    setListingCards(cards);
  };

  useEffect(() => {
    createListingCards();
  }, [data]);

  return (
    <>
      <div
        className="courier-listing-container"
        style={{ marginBottom: "1rem" }}
      >
        <div className="view-buttons-container">
          <p className="view-buttons-text">
            <Typography>Tap on a deliverer to book or know more</Typography>
          </p>
          <IconButton
            sx={{ height: "32px", width: "32px" }}
            aria-label="list-view"
            onClick={() => setShowMapView(false)}
          >
            <FormatListBulletedIcon />
          </IconButton>
          <IconButton
            sx={{ height: "42px", width: "42px" }}
            aria-label="map-view"
            onClick={() => setShowMapView(true)}
          >
            <MapIcon />
          </IconButton>
        </div>
        <br />
        {!showMapView && <>{listingCards}</>}
        {showMapView && <MapView listing={data} />}
      </div>
    </>
  );
}

export default Listings;
