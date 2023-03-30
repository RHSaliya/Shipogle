import * as React from "react";
import { GoogleMap, LoadScript, Marker } from "@react-google-maps/api";
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
  return (
    <>
      return (
      <LoadScript googleMapsApiKey="YOUR_GOOGLE_MAPS_API_KEY">
        <GoogleMap
          mapContainerStyle={containerStyle}
          center={defaultCenter}
          zoom={12}
        >
          {locations.map((location, index) => (
            <Marker key={index} position={location} />
          ))}
        </GoogleMap>
      </LoadScript>
      );
    </>
  );
}
