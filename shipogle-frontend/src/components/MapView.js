import React, { useEffect, useRef, useState } from "react";
import { loadGoogleMapsAPI } from "../utils/LoadGoogleMapsAPI.js";

const apiKey = "AIzaSyBPtYm-CJPPW4yO9njM-e9YBWyp-DwIODM";

const MapView = ({ center, locations }) => {
  console.log(center, locations);
  const mapRef = useRef(null);
  const [googleMaps, setGoogleMaps] = useState(null);
  const [map, setMap] = useState(null);
  const [markers, setMarkers] = useState([]);

  useEffect(() => {
    if (!center) return;

    loadGoogleMapsAPI(apiKey)
      .then((googleMaps) => {
        setGoogleMaps(googleMaps);
      })
      .catch((error) => {
        console.error("Failed to load Google Maps API:", error);
      });
  }, [center]);

  useEffect(() => {
    if (!googleMaps || !center || isNaN(center.lat) || isNaN(center.lng))
      return;

    const mapOptions = {
      zoom: 12,
      center: new googleMaps.LatLng(center.lat, center.lng),
    };

    const newMap = new googleMaps.Map(mapRef.current, mapOptions);
    setMap(newMap);
  }, [googleMaps, center]);

  useEffect(() => {
    if (!map || !locations) return;

    updateMarkers();
  }, [map, locations]);

  const updateMarkers = () => {
    markers.forEach((marker) => marker.setMap(null));

    const newMarkers = locations.map((location) => {
      const marker = new googleMaps.Marker({
        position: new googleMaps.LatLng(
          location.pickupLocationCoords[0],
          location.pickupLocationCoords[1]
        ),
        map,
        label: location.sourceCity,
      });

      return marker;
    });

    setMarkers(newMarkers);
  };

  if (!center) {
    return <div>Loading...</div>;
  }

  return <div ref={mapRef} style={{ width: "100%", height: "100%" }} />;
};

export default MapView;
