import * as React from "react";
import CommonFunctions from "../services/CommonFunction";

const containerStyle = {
  width: "80%",
  height: "400px",
};

export default function MapView(props) {
  const defaultCenter = {
    lat: 44.65107,
    lng: -63.582687,
  };
  const commFunc = new CommonFunctions();
  const userLocation = commFunc.getCurrentLocation();
  const locations = [defaultCenter];
  console.log(props);
  return <>Testing</>;
}
