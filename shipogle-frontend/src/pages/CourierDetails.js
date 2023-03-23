import * as React from "react";

import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Avatar from "@mui/material/Avatar";
import CardHeader from "@mui/material/CardHeader";
import CardActions from "@mui/material/CardActions";

import Data from "./data";
import { useNavigate } from "react-router-dom";

function CourierDetails() {
  const navigate = new useNavigate();
  const [viewDetails, setViewDetails] = React.useState(true);
  const [confirmBooking, setConfirmBooking] = React.useState(false);
  const [requestSent, setRequestSent] = React.useState(false);
  const data = new Data().courierDetails;

  const confirmBookingFunction = (bool) => {
    setViewDetails(!bool);
    setConfirmBooking(bool);
    console.log(confirmBooking);
  };

  const requestDriver = (bool) => {
    setViewDetails(false);
    setConfirmBooking(false);
    setRequestSent(true);
  };
  return (
    <>
      <Card
        sx={{ maxWidth: "80%", margin: " 2rem auto ", borderRadius: "4px" }}
      >
        {viewDetails && (
          <>
            <CardHeader
              sx={{
                boxShadow: "0px 0px 1px 1px rgba(0,0,0,0.2)",
                fontSize: "16px",
              }}
              avatar={
                <Avatar sx={{ bgcolor: "blue" }} aria-label="recipe">
                  <img src={data.avatar}></img>
                </Avatar>
              }
              title="Test courier 4"
              subheader="sub"
            />
            <CardMedia
              component="img"
              alt="Route view in map"
              height="250"
              style={{ width: "80%", margin: "0.5rem auto" }}
              src={data.routeMap}
            />
            <CardContent>
              <Typography gutterBottom variant="h6" component="div">
                Pickup Location: {data.sourceCityName}
                <br></br>
                Dropoff Location: {data.destinationsCityName}
              </Typography>
              <Typography
                variant="body2"
                color="text.secondary"
                style={{ fontSize: "16px" }}
              >
                <li>{"Pickup: " + data.pickupDate}</li>
                <li>{"Dropoff: " + data.dropoffDate}</li>
                <li>{"Price: " + data.price + " CAD"}</li>
                <li>
                  {"Max package dimensions (l*b*h cm): " +
                    data.maxLength +
                    ", " +
                    data.maxWidth +
                    ", " +
                    data.maxHeight}
                </li>
                <li>{"Allowed Categories: " + data.allowedCategory}</li>
              </Typography>
            </CardContent>
            <CardActions sx={{ display: "flex", justifyContent: "center" }}>
              <Button
                variant="contained"
                onClick={() => {
                  confirmBookingFunction(true);
                }}
              >
                Book Now
              </Button>
            </CardActions>
          </>
        )}

        {confirmBooking && (
          <div style={{ width: "100%", textAlign: "center", padding: "1rem" }}>
            <h4>
              Are you sure you want to confirm Booking with{" "}
              <b>Test Courier 4</b>
            </h4>
            <div style={{ display: "flex", justifyContent: "center" }}>
              <Button
                onClick={() => {
                  requestDriver(true);
                }}
              >
                Confirm
              </Button>
              &nbsp;&nbsp;
              <Button
                color="error"
                onClick={() => {
                  confirmBookingFunction(false);
                }}
              >
                Cancel
              </Button>
            </div>
          </div>
        )}

        {requestSent && (
          <div style={{ textAlign: "center" }}>
            <h5>Request Sent Successfully.</h5>
          </div>
        )}
      </Card>
    </>
  );
}

export default CourierDetails;
