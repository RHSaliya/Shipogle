//places api key AIzaSyBPtYm-CJPPW4yO9njM-e9YBWyp-DwIODM
import React, { useState, useEffect } from "react";
import "./courierListing.css";
import NavBar from "../components/NavBar";

import TextField from "@mui/material/TextField";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import dayjs from "dayjs";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import PostAddIcon from "@mui/icons-material/PostAdd";
import Button from "@mui/material/Button";
import Autocomplete from "@mui/material/Autocomplete";
import { debounce } from "@mui/material/utils";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";

import IconButton from "@mui/material/IconButton";
import FormatListBulletedIcon from "@mui/icons-material/FormatListBulleted";
import MapIcon from "@mui/icons-material/Map";
import WhereToVoteIcon from "@mui/icons-material/WhereToVote";
import ArrowForwardIcon from "@mui/icons-material/ArrowForward";
import SearchIcon from "@mui/icons-material/Search";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import MapView from "./mapView";

function loadScript(src, position, id) {
  if (!position) {
    return;
  }

  const script = document.createElement("script");
  script.setAttribute("async", "");
  script.setAttribute("id", id);
  script.src = src;
  position.appendChild(script);
}

const autocompleteService = { current: null };

function CourierListing() {
  let presentDate = new Date();
  presentDate = `${
    presentDate.getMonth() + 1
  }/${presentDate.getDate()}/${presentDate.getFullYear()}`;

  //new component data start
  const [showMapView, setShowMapView] = React.useState(false);
  const listings = [
    {
      pickupData: {
        date: presentDate,
        description: "Halifax, NS, Canada",
        place_id: "ChIJwfrbBxQhWksR3C2LQ6bja2Y",
      },
      dropoffData: {
        date: presentDate,
        description: "Montreal, QC, Canada ",
        place_id: "ChIJDbdkHFQayUwR7-8fITgxTmU",
      },
      name: "Test courier 1",
      avatar: "https://material.angular.io/assets/img/examples/shiba1.jpg",
      max_packages: "2",
      package_dimensions: { l: "100", h: "50", w: "40" },
    },
    {
      pickupData: {
        date: presentDate,
        description: "Halifax, NS, Canada",
        place_id: "ChIJwfrbBxQhWksR3C2LQ6bja2Y",
      },
      dropoffData: {
        date: presentDate,
        description: "Montreal, QC, Canada ",
        place_id: "ChIJDbdkHFQayUwR7-8fITgxTmU",
      },
      name: "Test courier 2",
      avatar: "https://material.angular.io/assets/img/examples/shiba1.jpg",
      max_packages: "2",
      package_dimensions: { l: "100", h: "50", w: "40" },
    },
    {
      pickupData: {
        date: presentDate,
        description: "South Street, Halifax, NS, Canada",
        place_id:
          "EiFTb3V0aCBTdHJlZXQsIEhhbGlmYXgsIE5TLCBDYW5hZGEiLiosChQKEglHUqlaOiJaSxHJCn-MzHmqQxIUChIJwfrbBxQhWksR3C2LQ6bja2Y",
      },
      dropoffData: {
        date: presentDate,
        description: "Montreal, QC, Canada ",
        place_id: "ChIJDbdkHFQayUwR7-8fITgxTmU",
      },
      name: "Test courier 3",
      avatar: "https://material.angular.io/assets/img/examples/shiba1.jpg",
      max_packages: "2",
      package_dimensions: { l: "100", h: "50", w: "40" },
    },
  ];
  const [listingCards, setListingCards] = React.useState([]);
  const [listingsLoaded, setListingsLoaded] = React.useState(true);
  const [center, setCenter] = useState({ lat: 0, lng: 0 });
  const createlistingcards = () => {
    let cards = [];
    listings.forEach((listing) => {
      console.log(listing);
      cards.push(
        <div className="listing-card">
          <img
            className="listing-card-avatar"
            src={listing?.avatar}
            alt="avatar"
          ></img>
          <span
            style={{
              width: "1px",
              borderLeft: "1px solid #b2b2b2a1",
              marginLeft: "8px",
              marginRight: "8px",
            }}
          ></span>
          <div className="lisitng-card-header">
            <p className="listing-card-heading">{listing?.name}</p>
            <hr></hr>
            <div className="listing-card-subheading">
              <LocationOnIcon></LocationOnIcon> &nbsp;
              <p style={{ margin: "0px", marginTop: "3px", color: "#191919" }}>
                {listing?.pickupData?.description}
              </p>{" "}
              &nbsp;
              <ArrowForwardIcon></ArrowForwardIcon> &nbsp;{" "}
              <WhereToVoteIcon></WhereToVoteIcon>
              <p style={{ margin: "0px", marginTop: "3px", color: "#191919" }}>
                {listing?.dropoffData?.description}
              </p>{" "}
            </div>
          </div>
        </div>
      );
    });
    setListingsLoaded(true);
    setListingCards(cards);
    getUserLocation();
    console.log(listingCards, listingsLoaded, "function called");
  };

  const getUserLocation = () => {
    if (navigator.geolocation) {
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
    }
  };
  //new component data end;

  const [pickupLocation, setPickupLocation] = React.useState("");
  const [pickupLocationValue, setPickupLocationValue] = React.useState(null);
  const [dropoffLocation, setDropoffLocation] = React.useState("");
  const [dropoffLocationValue, setDropoffLocationValue] = React.useState(null);
  const [options, setOptions] = React.useState([]);
  const placesAutocompleteloading = React.useState(false);
  const [packages, setPackages] = React.useState(0);
  const [length, setLength] = React.useState(0);
  const [height, setHeight] = React.useState(0);
  const [width, setWidth] = React.useState(0);
  const [startDate, setValueStartDate] = React.useState(dayjs(presentDate));
  const [dropoffDate, setValueDropoffDate] = React.useState(null);
  const [showFields, setShowFields] = React.useState(false);
  const handlePackagesChange = (event) => {
    const value = event.target.value;
    setPackages(value);
    setShowFields(value > 0);
  };
  if (typeof window !== "undefined" && !placesAutocompleteloading.current) {
    if (!document.querySelector("#google-maps")) {
      loadScript(
        `https://maps.googleapis.com/maps/api/js?key=AIzaSyBPtYm-CJPPW4yO9njM-e9YBWyp-DwIODM&libraries=places`,
        document.querySelector("head"),
        "google-maps"
      );
    }

    placesAutocompleteloading.current = true;
  }
  const fetch = React.useMemo(
    () =>
      debounce((request, callback) => {
        autocompleteService.current.getPlacePredictions(request, callback);
      }, 500),
    []
  );
  React.useEffect(() => {
    let active = true;
    setOptions([]);
    if (!autocompleteService.current && window.google) {
      autocompleteService.current =
        new window.google.maps.places.AutocompleteService();
    }
    if (!autocompleteService.current) {
      return undefined;
    }

    if (pickupLocation === "") {
      setOptions(pickupLocationValue ? [pickupLocationValue] : []);
      return undefined;
    } else {
      fetch({ input: pickupLocation }, (results) => {
        if (active) {
          let newOptions = [];

          if (pickupLocationValue) {
            newOptions = [pickupLocationValue];
          }

          if (results) {
            newOptions = [...newOptions, ...results];
          }

          setOptions(newOptions);
        }
      });
    }

    if (dropoffLocation === "") {
      setOptions(dropoffLocationValue ? [dropoffLocationValue] : []);
      return undefined;
    } else {
      fetch({ input: dropoffLocation }, (results) => {
        if (active) {
          let newOptions = [];

          if (dropoffLocationValue) {
            newOptions = [dropoffLocationValue];
          }

          if (results) {
            newOptions = [...newOptions, ...results];
          }

          setOptions(newOptions);
        }
      });
    }

    return () => {
      active = false;
    };
  }, [
    pickupLocationValue,
    pickupLocation,
    dropoffLocationValue,
    dropoffLocation,
    fetch,
  ]);

  return (
    <div>
      <NavBar></NavBar>
      <form className="form-container" id="courierRegistrationForm">
        <div className="input-fields-container">
          <Autocomplete
            id="from"
            className="courier-form-input"
            getOptionLabel={(option) =>
              typeof option === "string" ? option : option.description
            }
            filterOptions={(x) => x}
            options={options}
            autoComplete
            includeInputInList
            filterSelectedOptions
            value={pickupLocationValue}
            noOptionsText="No locations"
            onChange={(event, newValue) => {
              setOptions(newValue ? [newValue, ...options] : options);
              setPickupLocationValue({
                description: newValue.description,
                place_id: newValue.place_id,
              });
            }}
            onInputChange={(event, newInputValue) => {
              setPickupLocation(newInputValue);
            }}
            renderInput={(params) => (
              <TextField
                {...params}
                helperText="e.g Halifax"
                label="Add a location"
                fullWidth
              />
            )}
            renderOption={(props, option) => {
              const matches =
                option.structured_formatting.main_text_matched_substrings || [];

              const parts = matches.map((match) => [
                match.offset,
                match.offset + match.length,
              ]);

              return (
                <li {...props}>
                  <Grid container alignItems="center">
                    <Grid item sx={{ display: "flex", width: 44 }}>
                      <LocationOnIcon sx={{ color: "text.secondary" }} />
                    </Grid>
                    <Grid
                      item
                      sx={{
                        width: "calc(100% - 44px)",
                        wordWrap: "break-word",
                      }}
                    >
                      {parts.map((part, index) => (
                        <Box key={index} component="span">
                          {option.structured_formatting.main_text}
                        </Box>
                      ))}

                      <Typography variant="body2" color="text.secondary">
                        {option.structured_formatting.secondary_text}
                      </Typography>
                    </Grid>
                  </Grid>
                </li>
              );
            }}
          />
          <div className="spacer"></div>
          <Autocomplete
            id="to"
            className="courier-form-input"
            getOptionLabel={(option) =>
              typeof option === "string" ? option : option.description
            }
            filterOptions={(x) => x}
            options={options}
            autoComplete
            includeInputInList
            filterSelectedOptions
            value={dropoffLocationValue}
            noOptionsText="No locations"
            onChange={(event, newValue) => {
              setOptions(newValue ? [newValue, ...options] : options);
              setDropoffLocationValue({
                description: newValue.description,
                place_id: newValue.place_id,
              });
            }}
            onInputChange={(event, newInputValue) => {
              setDropoffLocation(newInputValue);
            }}
            renderInput={(params) => (
              <TextField
                {...params}
                helperText="e.g Montreal"
                label="Add a location"
                fullWidth
              />
            )}
            renderOption={(props, option) => {
              const matches =
                option?.structured_formatting?.main_text_matched_substrings ||
                [];

              const parts = matches.map((match) => [
                match.offset,
                match.offset + match.length,
              ]);

              return (
                <li {...props}>
                  <Grid container alignItems="center">
                    <Grid item sx={{ display: "flex", width: 44 }}>
                      <LocationOnIcon sx={{ color: "text.secondary" }} />
                    </Grid>
                    <Grid
                      item
                      sx={{
                        width: "calc(100% - 44px)",
                        wordWrap: "break-word",
                      }}
                    >
                      {parts.map((part, index) => (
                        <Box key={index} component="span">
                          {option.structured_formatting.main_text}
                        </Box>
                      ))}

                      <Typography variant="body2" color="text.secondary">
                        {option?.structured_formatting?.secondary_text}
                      </Typography>
                    </Grid>
                  </Grid>
                </li>
              );
            }}
          />
          <div className="spacer"></div>
          <TextField
            id="numberOfPackages"
            type="Number"
            variant="outlined"
            label="Number of Packages"
            min="0"
            required
            className="courier-form-input"
            value={packages <= 0 ? "" : packages}
            helperText="e.g 1"
            onChange={handlePackagesChange}
          ></TextField>
          <div className="spacer"></div>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker
              label="Pickup Date"
              id="pickupDate"
              className="courier-form-input"
              required
              minDate={dayjs(presentDate)}
              value={startDate}
              onChange={(newValue) => {
                setValueStartDate(newValue);
              }}
              renderInput={(params) => <TextField {...params} />}
            />
          </LocalizationProvider>
          <div className="spacer"></div>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DatePicker
              label="Dropoff Date"
              id="dropOffDate"
              required
              value={dropoffDate}
              minDate={dayjs(presentDate)}
              className="courier-form-input"
              onChange={(newValue) => {
                setValueDropoffDate(newValue);
              }}
              renderInput={(params) => <TextField {...params} />}
            />
          </LocalizationProvider>
        </div>
        {showFields && (
          <div>
            <br></br>
            <p style={{}}>Enter Package dimensions</p>
            <div className="courier-form-sub-field-container">
              <TextField
                type="number"
                id="packageLength"
                min="0"
                label="Length"
                value={length}
                required
                variant="standard"
                className="courier-form-sub-fields"
                onChange={(event) => setLength(event.target.value)}
              />
              <div className="spacer"></div>
              <TextField
                id="packageWidth"
                type="number"
                min="0"
                required
                label="Width"
                value={width}
                variant="standard"
                className="courier-form-sub-fields"
                onChange={(event) => setWidth(event.target.value)}
              />
              <div className="spacer"></div>
              <TextField
                id="packageHeight"
                type="number"
                min="0"
                required
                label="Height"
                variant="standard"
                value={height}
                className="courier-form-sub-fields"
                onChange={(event) => setHeight(event.target.value)}
              />
            </div>
          </div>
        )}
        <div
          style={{
            width: "100%",
            display: "flex",
            justifyContent: "center",
            marginTop: "1.5rem",
          }}
        >
          <Button
            variant="contained"
            endIcon={<SearchIcon />}
            onClick={createlistingcards}
          >
            Search
          </Button>
        </div>
      </form>

      {listingsLoaded && !showMapView && (
        <div className="courier-listing-container">
          <div className="view-buttons-container">
            <p style={{ margin: "0px", flexGrow: "1" }}>
              Tap on a deliverer to book or know more
            </p>
            <IconButton aria-label="list-view">
              <FormatListBulletedIcon />
            </IconButton>
            <IconButton
              aria-label="map-view"
              onClick={() => {
                setShowMapView(!showMapView);
              }}
            >
              <MapIcon />
            </IconButton>
          </div>
          <br></br>
          {listingCards}
        </div>
      )}

      {listingsLoaded && showMapView && (
        <div>
          <MapView
            center={center}
            placeIds={listings.map((listing) => {
              return {
                id: listing.pickupData.place_id,
                name: listing.pickupData.description,
              };
            })}
          ></MapView>
        </div>
      )}
    </div>
  );
}

export default CourierListing;

/*
 <Autocomplete
            disablePortal
            options={options}
            id="courierFrom"
            className="courier-form-input"
            noOptionsText="Please enter a locaiton"
            loading={placesAutocompleteloading}
            getOptionLabel={(option) =>
              typeof option === "string" ? option : option.label
            }
            renderInput={(params) => (
              <TextField
                {...params}
                label="From"
                helperText="e.g halifax"
                required
                onChange={handlePickupLocationChange}
              />
            )}
            renderOption={(props, option) => {
              console.log(props, option, "line 119");
              return <li {...props}>{option}</li>;
            }}
          />

          <Autocomplete
            disablePortal
            options={options}
            loading={placesAutocompleteloading}
            renderInput={(params) => (
              <TextField
                {...params}
                label="To"
                required
                id="courierTo"
                className="courier-form-input"
                helperText="e.g montreal"
                onChange={handleDropoffLocationChange}
              />
            )}
            renderOption={(option) => <li>{option}</li>}
          />
*/
