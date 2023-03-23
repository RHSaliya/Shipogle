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
      console.log(listing);
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

    console.log(listingCards, listingsLoaded, "function called");
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
            <MapView
              center={center}
              placeIds={listings.map((listing) => {
                return {
                  id: listing.pickupData.place_id,
                  name: listing.pickupData.description,
                };
              })}
            ></MapView>
          </div>
        )}
      </div>
    </>
  );
}
