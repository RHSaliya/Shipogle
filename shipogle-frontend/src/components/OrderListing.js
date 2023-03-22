import { Button } from "@mui/material";
import * as React from "react";
import { Link } from "react-router-dom";

import Data from "../pages/data";

import "./OrderListing.css";
export default function OrderListing(data, hideView) {
  console.log("logging datain listing");
  const demoData = new Data();
  const [listings, setListing] = React.useState([]);
  React.useEffect(() => {
    const cards = [];
    console.log("logging data", demoData.listings, hideView);
    demoData.listings.forEach((element) => {
      console.log("listing");
      cards.push(
        <div className="order-listing-container">
          <div className="order-details">
            <div className="order-listing-heading">
              <span style={{ flexGrow: 1 }}>Order name/ driver name</span>
              <span>Oder Date</span>
            </div>
            <hr></hr>
            <div className="order-summary">
              <p style={{ flexGrow: 1, margin: "8px" }}>
                From: Source City || To: Destination City
              </p>
            </div>
          </div>
          {!hideView && (
            <div className="action-button">
              <Button>
                <Link
                  style={{ textDecoration: "none" }}
                  to="/orders/details/df341rfew5213rfe"
                >
                  View
                </Link>
              </Button>
            </div>
          )}
        </div>
      );
    });
    setListing(cards);
  }, []);
  return <>{listings}</>;
}
