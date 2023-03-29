import * as React from "react";
import { Link, useLocation } from "react-router-dom";

import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";

import { Button } from "@mui/material";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";

import Data from "../pages/data";
import customAxios from "../utils/MyAxios";
import Constants from "../Constants";

export default function OrderDetails() {
  const data = new Data();
  const location = useLocation().pathname.split("/");
  const orderId = location[4];
  const pathname = location[3];

  React.useEffect(() => {}, []);

  const cancelOrder = () => {
    const body = {
      package_order_id: orderId,
    };
    customAxios.put(Constants.CANCELORDER, body).then(
      (res) => {
        alert("Order canceled");
      },
      (error) => {
        console.error(error);
      }
    );
  };
  return (
    <div className="order-details-container">
      <Card sx={{ maxWidth: "600px", margin: "2rem auto" }}>
        <CardHeader
          title="Order ID/ Driver Name"
          subheader={`status: ${pathname}`}
        />
        {(pathname === "completed" || pathname === "inprogress") && (
          <CardMedia
            component="img"
            alt="Route view in map"
            height="250"
            style={{ width: "95%", margin: "0.5rem auto" }}
            src={data.courierDetails.routeMap}
          />
        )}
        {pathname === "pending" && (
          <h4 style={{ margin: "auto 2rem" }}>
            Tracking will be available once user Pickups your package
          </h4>
        )}
        <CardContent>
          <ul>
            <li>Order Date</li>
            <li>From: source City || To: destination City</li>
            <li>More details</li>
          </ul>
        </CardContent>
        <CardActions
          sx={{
            display: "flex",
            justifyContent: "center",
            marginTop: "1rem",
            marginBottom: "1rem",
          }}
        >
          {pathname === "completed" && (
            <Button variant="contained">
              <Link
                to="/report/:3urfsqiof90"
                style={{ textDecoration: "none", color: "white" }}
              >
                {" "}
                Raise an Issue
              </Link>
            </Button>
          )}
          {pathname === "pending" && (
            <Button
              variant="contained"
              color="error"
              onClick={() => {
                cancelOrder();
              }}
            >
              Confirm Cancel
            </Button>
          )}
        </CardActions>
      </Card>
    </div>
  );
}
