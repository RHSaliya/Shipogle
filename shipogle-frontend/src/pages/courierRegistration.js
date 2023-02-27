//places api key AIzaSyBPtYm-CJPPW4yO9njM-e9YBWyp-DwIODM
import React, { useState, useEffect } from "react";
import "./courierRegistration.css";
import TextField from "@mui/material/TextField";
import NavBar from "../components/NavBar";
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
import LocationOnIcon from "@mui/icons-material/LocationOn";
import Grid from "@mui/material/Grid";

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

function CourierRegistration() {
  let presentDate = new Date();
  presentDate = `${
    presentDate.getMonth() + 1
  }/${presentDate.getDate()}/${presentDate.getFullYear()}`;

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
      console.log("dropoffloc");
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
          console.log("dropoff");
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

  /*
  useEffect(() => {
    let pickupLocationTimer = null;
    let dropoffLocationTimer = null;

    

    return () => {
      clearTimeout(pickupLocationTimer);
      clearTimeout(dropoffLocationTimer);
    };
  }, [pickupLocation, dropoffLocation, options]);

  

  const handlePickupLocationChange = (event) => {
    setPickupLocation(event.target.value);
  };

  const handleDropoffLocationChange = (event) => {
    setDropoffLocation(event.target.value);
  };

  const fetchPlaces = async (inputValue) => {
    const response = await fetch(
      `https://cors-anywhere.herokuapp.com/https://maps.googleapis.com/maps/api/place/autocomplete/json?input=${inputValue}&key=AIzaSyBPtYm-CJPPW4yO9njM-e9YBWyp-DwIODM`
    );
    const data = await response.json();
    let predictions = [];
    data.predictions.forEach((prediction) => {
      predictions.push({
        label: prediction.description,
        value: prediction.place_id,
      });
      predictions.push(prediction.description);
    });
    return predictions;
  };*/

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
                label="Pickup Location"
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
                label="Dropoff location"
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
          <Button variant="contained" endIcon={<PostAddIcon />}>
            Post
          </Button>
        </div>
      </form>
    </div>
  );
}

export default CourierRegistration;

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
