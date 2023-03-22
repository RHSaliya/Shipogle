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

import Data from "./data";
import OrderListing from "../components/OrderListing";

export default function Orders() {
  const data = new Data();
  const [pending, setPending] = React.useState(data.listings);
  const [inProgress, setInProgress] = React.useState(data.listings);
  const [completed, setCompleted] = React.useState(data.listings);
  const [expanded, setExpand] = React.useState(true);
  const expand = () => {
    setExpand(!expanded);
  };
  return (
    <>
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
              <WhereToVoteIcon sx={{ position: "absolute" }}></WhereToVoteIcon>
              <span style={{ marginLeft: "2rem" }}>Completed Orders</span>
            </Typography>
          </AccordionSummary>
          <AccordionDetails>
            <OrderListing data={completed}></OrderListing>
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
            <OrderListing data={inProgress}></OrderListing>
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
            <OrderListing data={pending}></OrderListing>
          </AccordionDetails>
        </Accordion>
        <Accordion>
          <AccordionSummary
            sx={{ position: "relative" }}
            expandIcon={<ExpandMoreIcon />}
          >
            <Typography>
              <RemoveRoadIcon sx={{ position: "absolute" }}></RemoveRoadIcon>{" "}
              <span style={{ marginLeft: "2rem" }}>Canceled Orders</span>
            </Typography>
          </AccordionSummary>
          <AccordionDetails>
            <OrderListing data={pending}></OrderListing>
          </AccordionDetails>
        </Accordion>
      </div>
    </>
  );
}
