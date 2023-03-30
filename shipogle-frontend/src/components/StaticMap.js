import React from "react";
import Constants from "../Constants";

const apiKey = Constants.API_KEY;
function StaticMap({ pickup, dropOff, width, height, zoom }) {
  const buildStaticMapUrl = () => {
    const baseApiUrl = "https://maps.googleapis.com/maps/api/staticmap?";

    const path = `path=color:0x0000ff|weight:5|${pickup[0]},${pickup[1]}|${dropOff[0]},${dropOff[1]}`;
    const size = `size=${width}x${height}`;
    const mapZoom = `zoom=${zoom}`;
    const apiKeyParam = `key=${apiKey}`;

    const url = `${baseApiUrl}${size}&${path}&${mapZoom}&${apiKeyParam}`;
    return url;
  };

  return (
    <div>
      <img src={buildStaticMapUrl()} alt="Static map" />
    </div>
  );
}

export default StaticMap;
