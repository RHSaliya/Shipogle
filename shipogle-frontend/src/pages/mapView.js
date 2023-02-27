import React, { useState, useEffect } from "react";
import GoogleMapReact from "google-map-react";
import LocationOnIcon from "@mui/icons-material/LocationOn";

const Marker = ({ name }) => (
  <div title={name}>
    <h3>{name}</h3>
    <LocationOnIcon></LocationOnIcon>
  </div>
);

function MapView(props) {
  const [places, setPlaces] = useState([]);
  const [center, setCenter] = useState(props.center);

  useEffect(() => {
    /*if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          // Update the center state with the user's current position
          setCenter({
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          });
        },
        (error) => {
          console.error(error);
        },
        {
          enableHighAccuracy: true,
        }
      );
    } else {
      console.error("Geolocation is not supported by this browser.");
    }*/
    console.log(props.center, center);
    //setCenter(props.center);
    // Use the Places API to get the details for each place_id
    const service = new window.google.maps.places.PlacesService(
      document.createElement("div")
    );
    console.log(props);
    Promise.all(
      props.placeIds.map(
        (place) =>
          new Promise((resolve) => {
            const placeId = place.id;
            const request = { placeId, fields: ["geometry"] };
            service.getDetails(request, (place, status) => {
              if (status === window.google.maps.places.PlacesServiceStatus.OK) {
                console.log(place, "line 49");
                resolve(place);
              } else {
                console.error("Could not get place details:", status);
                resolve(null);
              }
            });
          })
      )
    ).then((places) => {
      setPlaces(
        places.filter((place, index) => {
          Object.assign(place, { description: props.placeIds[index].name });
          return place !== null;
        })
      );
      console.log(places, "places");
      //setCenter(places[0].geometry.location);
    });
  }, [props]);

  return (
    <div
      style={{ height: "500px", width: "80%", margin: "auto auto 1rem auto" }}
    >
      {center ? (
        <GoogleMapReact
          bootstrapURLKeys={{ key: "AIzaSyBPtYm-CJPPW4yO9njM-e9YBWyp-DwIODM" }}
          defaultCenter={center}
          defaultZoom={15}
        >
          {places.map((place) => (
            <Marker
              key={place.place_id}
              lat={place.geometry.location.lat()}
              lng={place.geometry.location.lng()}
              name={place.description}
            />
          ))}
        </GoogleMapReact>
      ) : (
        <p>Loading map...</p>
      )}
    </div>
  );
}

export default MapView;
