import React from "react";
import Constants from "../Constants";

const apiKey = Constants.API_KEY;
function StaticMap({ pickup, dropOff, width, height, zoom }) {
  console.log(pickup);
  const buildMarkers = () => {
    const markerA = `markers=color:0xFF0000|label:${
      pickup[2] ? pickup[2] : "No label"
    }|${pickup[0]},${pickup[1]}`;
    const markerB = `markers=color:0xFF00|label:${
      dropOff[2] ? dropOff[2] : "No label"
    }|${dropOff[0]},${dropOff[1]}`;
    return `${markerA}&${markerB}`;
  };

  const buildStaticMapUrl = () => {
    const baseApiUrl = "https://maps.googleapis.com/maps/api/staticmap?";
    const path = `path=color:0x0000ff|weight:5|${pickup[0]},${pickup[1]}|${dropOff[0]},${dropOff[1]}`;
    const markers = buildMarkers();
    console.log(markers);
    const size = `size=${width}x${height}`;
    const mapZoom = `zoom=${zoom}`;
    const apiKeyParam = `key=${apiKey}`;

    const url = `${baseApiUrl}${size}&${path}&${markers}&${mapZoom}&${apiKeyParam}`;
    return url;
  };

  return (
    <div>
      <img src={buildStaticMapUrl()} alt="Static map" />
    </div>
  );
}

export default StaticMap;
