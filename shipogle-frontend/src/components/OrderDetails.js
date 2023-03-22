import * as React from "react";
import { Link } from "react-router-dom";

import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";

import { Button } from "@mui/material";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import Data from "../Pages/data";

export default function OrderDetails(orderData) {
  const data = new Data();
  React.useEffect(() => {
    console.log("in courier details");
  }, []);
  return (
    <div className="order-details-container">
      <Card sx={{ maxWidth: "600px", margin: "2rem auto" }}>
        <CardHeader
          title="Order ID/ Driver Name"
          subheader="status: Completed"
        />
        <CardMedia
          component="img"
          alt="Route view in map"
          height="250"
          style={{ width: "95%", margin: "0.5rem auto" }}
          src={data.courierDetails.routeMap}
        />
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
          <Button variant="contained">
            <Link to="/report/:3urfsqiof90" style={{ textDecoration: "none" }}>
              {" "}
              Raise an Issue
            </Link>
          </Button>
        </CardActions>
      </Card>
    </div>
  );
}
