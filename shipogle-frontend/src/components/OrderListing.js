import { Button } from "@mui/material";
import * as React from "react";
import { Link } from "react-router-dom";

import Data from "../pages/data";

import "./OrderListing.css";
export default function OrderListing(props) {
  console.log("logging datain listing");
  const demoData = new Data();
  const [listings, setListing] = React.useState([]);
  React.useEffect(() => {
    const cards = [];
    console.log("allowed view", props);
    props.data.forEach((element) => {
      console.log("listing", element);
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
          <div className="action-button">
            <Button color={props.color}>
              <Link
                style={{ textDecoration: "none", color: "inherit" }}
                to={`/orders/details/${props.status}/${element.order_id}`}
              >
                {props.actionButtonText ? props.actionButtonText : "View"}
              </Link>
            </Button>
          </div>
        </div>
      );
    });
    setListing(cards);
  }, []);
  return <>{listings}</>;
}
