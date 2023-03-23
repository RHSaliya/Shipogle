import {
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
} from "@mui/material";
import * as React from "react";

export default function MyRides() {
  return (
    <div>
      <Card sx={{ maxWidth: "500px", width: "100%", margin: "2rem auto" }}>
        <CardHeader
          title="Courier Requests"
          subheader={"RideID: " + "1234"}
        ></CardHeader>
        <hr
          style={{
            marginTop: "-0.5rem",
            borderTop: "1px solid rgba(0,0,0,0.4)",
            width: "95%",
            margin: "-0.5rem auto 0.5rem auto",
          }}
        ></hr>
        <CardContent>
          <h3 style={{ margin: "0rem auto" }}>Details</h3>
          <ul style={{ color: "rgba(0,0,0,0.6)" }}>
            <li>Source city: {"Halifax"}</li>
            <li>Destination City: {"Toronto"}</li>
            <li>Pickup Date: {"2023-03-"}</li>
          </ul>
          <h4 style={{ margin: "0rem auto" }}>Request By</h4>
          <ul style={{ color: "rgba(0,0,0,0.6)" }}>
            <li>Driver Name</li>
          </ul>
        </CardContent>
        <CardActions>
          <Button>Accept</Button>
          <Button color="error">Reject</Button>
        </CardActions>
      </Card>
    </div>
  );
}
