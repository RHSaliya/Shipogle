import {
  Button,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  CircularProgress,
} from "@mui/material";
import * as React from "react";
import customAxios from "../utils/MyAxios";
import Constants from "../Constants";
import Data from "../pages/data";
import CommonFunctions from "../services/CommonFunction";

export default function PackageRequests() {
  const demoData = new Data();
  const [requests, setRequests] = React.useState([]);
  const [isLoading, setIsLoading] = React.useState(true);
  const [noRequest, setNoRequest] = React.useState(false);
  const commFunc = new CommonFunctions();
  const handleRequest = (request, action) => {
    const body = { package_request_id: request.package_request_id };
    customAxios.post(Constants[action], body).then(
      (res) => {
        if (action === "REJECTREQUEST") {
          commFunc.showAlertMessage("Request Rejected", "warning", 3000, "top");
        } else {
          commFunc.showAlertMessage("Request Accepted", "success", 3000, "top");
        }

        setRequests(requests.splice(requests.indexOf(request), 1));
      },
      (error) => {
        console.error(error);
        commFunc.showAlertMessage(
          "Error while performing action!!",
          "error",
          3000,
          "bottom"
        );
      }
    );
  };

  const renderRequestCard = (request) => (
    <div key={request._package.id}>
      <Card sx={{ maxWidth: "500px", width: "100%", margin: "2rem auto" }}>
        <CardHeader
          title="Delivery Request for"
          subheader={`Ride ID: ${request.driverRoute.driverRouteId}, From: ${request.driverRoute.sourceCity} To: ${request.driverRoute.destinationCity}`}
        />

        <hr
          style={{
            marginTop: "-0.5rem",
            borderTop: "1px solid rgba(0,0,0,0.4)",
            width: "95%",
            margin: "-0.5rem auto 0.5rem auto",
          }}
        />

        <CardContent>
          <h3 style={{ margin: "0rem auto" }}>Details</h3>
          <ul style={{ color: "rgba(0,0,0,0.6)" }}>
            <li>Sender's Source city: {request.driverRoute.sourceCity}</li>
            <li>
              Sender's Destination City: {request.driverRoute.destinationCity}
            </li>
            <li>
              Pickup Date:{" "}
              {new Date(request.driverRoute.pickupDate).toLocaleDateString()}
            </li>
          </ul>
          <h4 style={{ margin: "0rem auto" }}>Request By</h4>
          <ul style={{ color: "rgba(0,0,0,0.6)" }}>
            <li>
              Username:{" "}
              {request.sender.first_name + " " + request.sender.last_name}
            </li>
            <li>Email: {request.sender.email}</li>
          </ul>
          <h4 style={{ margin: "0rem auto", fontWeight: 400 }}>
            Your Price : {request.driverRoute.price} CAD
          </h4>
          <br></br>
          <h4 style={{ margin: "0rem auto" }}>
            Requested Price : {request.aksPrice} CAD
          </h4>
        </CardContent>
        <CardActions>
          <Button onClick={() => handleRequest(request, "ACCEPTREQUEST")}>
            Accept
          </Button>
          <Button
            color="error"
            onClick={() => handleRequest(request, "REJECTREQUEST")}
          >
            Reject
          </Button>
        </CardActions>
      </Card>
    </div>
  );

  React.useEffect(() => {
    customAxios.get(Constants.GETREQUESTS).then(
      (res) => {
        const requestCards = res.data.map((request) => {
          if (request.status === "requested") return renderRequestCard(request);
          else return <></>;
        });
        setRequests(requestCards);
        setIsLoading(false);
      },
      (error) => {
        console.error(error);
      }
    );
  }, []);

  return (
    <>
      {isLoading && (
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            flexFlow: "column",
            height: "300px",
            width: "100%",
          }}
        >
          <CircularProgress />
          <p>Loading Requests</p>
        </div>
      )}
      {!isLoading && (
        <>
          {requests.length > 0 ? (
            requests
          ) : (
            <h4 style={{ textAlign: "center", marginTop: "2rem" }}>
              No new Requests
            </h4>
          )}
        </>
      )}
    </>
  );
}