import {
  Button,
  Card,
  CardContent,
  CardHeader,
  TextField,
} from "@mui/material";
import * as React from "react";

import { Form } from "react-router-dom";
import customAxios from "../utils/MyAxios";
import Constants from "../Constants";
import Listings from "./Listings";

export default function CurrentDelivery(props) {
  const [order_id, setOrderID] = React.useState("qeyv9c3q78r62389qryfqhi");
  const [order_code, setOrderCode] = React.useState("");
  const [MyRides, setMyRides] = React.useState([]);
  const [isLoading, setIsLoading] = React.useState(true);
  React.useEffect(() => {
    const driverId = window.localStorage.getItem("user_id");
    customAxios.get(Constants.GETDRIVERROUTES + "?driverId=" + driverId).then(
      (res) => {
        console.log(res);

        setMyRides(res.data);
        setIsLoading(false);
      },
      (error) => {
        console.error(error);
      }
    );
  }, []);
  const submit = (e) => {
    e.preventDefault();
  };
  return (
    /*<>
      <Card sx={{ margin: "2rem auto", display: "block", maxWidth: "400px" }}>
        <CardHeader title="Enter Delivery code"></CardHeader>
        <CardContent>
          <form onSubmit={submit} style={{ textAlign: "center" }}>
            <TextField
              sx={{ width: "100%" }}
              label="OrderID"
              value={order_id}
              disabled
              type="text"
              className="input-field"
            />

            <TextField
              sx={{ marginTop: "1.5rem", marginBottom: "1rem", width: "100%" }}
              label="Delivery code"
              className="input-field"
              value={order_code}
              type="text"
              onChange={(event) => {
                setOrderCode(event.target.value);
              }}
            />

            <Button
              style={{
                margin: "auto",
                marginTop: "0.5rem",
                gridColumnEnd: "span 2",
                minWidth: "180px",
                maxWidth: "300px",
                width: "100%",
              }}
              type="submit"
              variant="contained"
              color="primary"
            >
              Start
            </Button>
          </form>
        </CardContent>
      </Card>
    </>*/
    <>
      <div className="listing-container">
        {!isLoading && <Listings data={MyRides}></Listings>}
      </div>
    </>
  );
}
