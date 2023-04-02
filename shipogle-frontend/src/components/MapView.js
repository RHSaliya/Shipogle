import React, { useEffect, useRef, useState } from "react";
import CommonFunctions from "../services/CommonFunction.js";
import { CircularProgress } from "@mui/material";

const apiKey = "AIzaSyBPtYm-CJPPW4yO9njM-e9YBWyp-DwIODM";

const MapView = ({ locations }) => {
  console.log(locations);
  const commFunc = new CommonFunctions();
  const mapRef = useRef(null);
  const [googleMaps, setGoogleMaps] = useState(null);
  const [map, setMap] = useState(null);
  const [markers, setMarkers] = useState([]);
  const [center, setCenter] = useState({});
  const [currentLocationMarker, setCurrentLocationMarker] = useState(null);
  const [isLoading, setIsLoading] = useState(true);
  const getCurrentLocation = () => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const userLocation = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };
        console.log("Current location:", userLocation.lat); // Log the userLocation to check if it's valid
        setCenter(userLocation);
      },
      (error) => {
        this.showAlertMessage(
          error.message ? error.message : "Please give Location Access",
          "error",
          3000,
          "bottom"
        );
      }
    );
  };

  useEffect(() => {
    getCurrentLocation();
    if (window.google.maps) {
      console.log("loaded maps");
      setGoogleMaps(window.google.maps);
    } else {
      commFunc.showAlertMessage(
        ("there was an error loading maps", "error", 3000, "bottom")
      );
    }
  }, []);

  useEffect(() => {
    if (googleMaps && center) {
      console.log("now showing map");
      const mapOptions = {
        zoom: 18,
        center: new googleMaps.LatLng(center.lat, center.lng),
      };

      const newMap = new googleMaps.Map(mapRef.current, mapOptions);
      setMap(newMap);

      // Remove existing current location marker
      if (currentLocationMarker) {
        currentLocationMarker.setMap(null);
      }

      // Add a new marker for the current location
      const newCurrentLocationMarker = new googleMaps.Marker({
        position: new googleMaps.LatLng(center.lat, center.lng),
        map: newMap,
        label: "You",
        icon: {
          url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png",
        },
      });

      setCurrentLocationMarker(newCurrentLocationMarker);
    }
  }, [googleMaps, center]);

  useEffect(() => {
    if (map) {
      updateMarkers();
      setIsLoading(false);
    }
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

  return (
    <>
      <div ref={mapRef} style={{ width: "100%", height: "500px" }} />{" "}
      {isLoading && <CircularProgress></CircularProgress>}
    </>
  );
};

export default MapView;
