import * as React from "react";

import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import RemoveRoadIcon from "@mui/icons-material/RemoveRoad";
import WhereToVoteIcon from "@mui/icons-material/WhereToVote";
import RouteIcon from "@mui/icons-material/Route";
import PendingActionsIcon from "@mui/icons-material/PendingActions";
import CircularProgress from "@mui/material/CircularProgress";

import Data from "./data";
import OrderListing from "../components/OrderListing";
import Constants from "../Constants";
import customAxios from "../utils/MyAxios";

export default function Orders() {
  const data = new Data();
  const [pending, setPending] = React.useState([]);
  const [inProgress, setInProgress] = React.useState([]);
  const [completed, setCompleted] = React.useState([]);
  const [canceled, setCanceled] = React.useState([]);
  const [expanded, setExpand] = React.useState(true);
  const [isLoading, setIsLoading] = React.useState(true);
  const expand = () => {
    setExpand(!expanded);
  };
  React.useEffect(() => {
    setIsLoading(true);
    customAxios.get(Constants.ORDERS).then(
      (res) => {
        setOrders(res);
      },
      (error) => {
        console.error(error);
      }
    );
  }, []);

  const setOrders = (res) => {
    const resData = data.orders;
    const inprogressOrders = [];
    const pendingOrders = [];
    const completedOrders = [];
    const canceledOrders = [];
    resData.forEach((order) => {
      if (order.is_delivered) completedOrders.push(order);
      else if (order.is_started) inprogressOrders.push(order);
      else if (order.is_canceled) canceledOrders.push(order);
      else pendingOrders.push(order);
    });
    setCompleted(completedOrders);
    setCanceled(canceledOrders);
    setInProgress(inprogressOrders);
    setPending(pendingOrders);
    setIsLoading(false);
  };
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
          <CircularProgress></CircularProgress>
          <p>Loading Orders</p>
        </div>
      )}
      {!isLoading && (
        <div style={{ width: "80%", margin: "2rem auto 2rem auto" }}>
          <h3>My Orders</h3>
          <Accordion expanded={expanded}>
            <AccordionSummary
              sx={{ position: "relative" }}
              onClick={() => {
                expand(expanded);
              }}
              expandIcon={<ExpandMoreIcon />}
            >
              <Typography>
                <WhereToVoteIcon
                  sx={{ position: "absolute" }}
                ></WhereToVoteIcon>
                <span style={{ marginLeft: "2rem" }}>Completed Orders</span>
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              <OrderListing
                data={completed}
                actionButtonText={"View"}
                color={"success"}
                status={"completed"}
              ></OrderListing>
            </AccordionDetails>
          </Accordion>
          <Accordion>
            <AccordionSummary
              sx={{ position: "relative" }}
              expandIcon={<ExpandMoreIcon />}
            >
              <Typography>
                <RouteIcon sx={{ position: "absolute" }}></RouteIcon>
                <span style={{ marginLeft: "2rem" }}>In-Progress Orders</span>
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              <OrderListing
                data={inProgress}
                actionButtonText={"Track"}
                color={"primary"}
                status={"inprogress"}
              ></OrderListing>
            </AccordionDetails>
          </Accordion>
          <Accordion>
            <AccordionSummary
              sx={{ position: "relative" }}
              expandIcon={<ExpandMoreIcon />}
            >
              <Typography>
                <PendingActionsIcon
                  sx={{ position: "absolute" }}
                ></PendingActionsIcon>
                <span style={{ marginLeft: "2rem" }}>Pending Requests</span>
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              <OrderListing
                data={pending}
                actionButtonText={"Cancel"}
                color={"error"}
                status={"pending"}
              ></OrderListing>
            </AccordionDetails>
          </Accordion>
          <Accordion>
            <AccordionSummary
              sx={{ position: "relative" }}
              expandIcon={<ExpandMoreIcon />}
            >
              <Typography>
                <RemoveRoadIcon sx={{ position: "absolute" }}></RemoveRoadIcon>
                <span style={{ marginLeft: "2rem" }}>Canceled Orders</span>
              </Typography>
            </AccordionSummary>
            <AccordionDetails>
              <OrderListing
                data={canceled}
                actionButtonText={"View"}
                color={"secondary"}
                status={"canceled"}
              ></OrderListing>
            </AccordionDetails>
          </Accordion>
        </div>
      )}
    </>
  );
}