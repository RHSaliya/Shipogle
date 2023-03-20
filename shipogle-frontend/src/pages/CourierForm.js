import React from "react";
import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";

import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import OutlinedInput from "@mui/material/OutlinedInput";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import ListItemText from "@mui/material/ListItemText";
import Select from "@mui/material/Select";
import Checkbox from "@mui/material/Checkbox";
import SearchIcon from "@mui/icons-material/Search";
import PostAddIcon from "@mui/icons-material/PostAdd";

import LocationAutoComplete from "../components/LocationAutoComplete";
import AlertMessage from "../components/AlertMessage";
import "./courierForm.css";

const API_KEY = "AIzaSyBPtYm-CJPPW4yO9njM-e9YBWyp-DwIODM";
const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

function CourierForm() {
  const date = new Date();
  const location = useLocation();
  const navigate = useNavigate();
  const [path, setLocationPath] = useState("");
  const presentDate = `${date.getFullYear()}-${
    date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1
  }-${date.getDate()}`;
  const [sourceCityName, setSourceCityName] = useState("");
  const [sourceCityReferenceId, setSourceCityReferenceId] = useState("");
  const [destinationsCityName, setDestinationsCityName] = useState("");
  const [destinationsCityReferenceId, setDestinationsCityReferenceId] =
    useState("");
  const [maxPackages, setMaxPackages] = useState(1);
  const [maxLength, setMaxLength] = useState(1);
  const [maxWidth, setMaxWidth] = useState(1);
  const [maxHeight, setMaxHeight] = useState(1);
  const [pickupDate, setPickupDate] = useState(presentDate);
  const [dropoffDate, setDropoffDate] = useState(presentDate);
  const [allowedCategory, setAllowedCategory] = useState([]);
  const [radius, setRadius] = useState(2);
  const [price, setPrice] = useState(1);
  const allowedCategoryLabels = ["Documents", "Fragile", "Liquids", "General"];
  const [showAlert, setAlert] = useState(false);

  const [alertMessage, setAlertMessage] = useState("");
  const [alertType, setAlertType] = useState("");
  const [alertDuration, setAlertDuration] = useState(2000);
  const [alertPosition, setAlertPosition] = useState("bottom");
  const [pickupLocationCoords, setPickupLocationCoords] = useState([]);
  const [dropoffLocationCoords, setDropoffLocationCoords] = useState([]);
  useEffect(() => {
    if (location.pathname.includes("search")) {
      setLocationPath("search");
    } else {
      setLocationPath("post");
    }
    console.log("there is a change in component");
    console.log(location.pathname);
    // do any side-effect operation here that is equivalent to componentDidMount in class component
  }, [location]);

  const onLocationChange = (key, value) => {
    if (!value || value === "") {
      return;
    }
    const data = { place: value.description, place_id: value.place_id };
    const latLng = [];
    if (path !== "search") {
      fetch(
        `https://maps.googleapis.com/maps/api/place/details/json?place_id=${data.place_id}&fields=geometry&key=${API_KEY}`,
        { mode: "no-cors" }
      ).then(
        (res) => {
          if (res) {
            latLng = [
              res.results.geometry.location.lat,
              res.results.geometry.location.lng,
            ];
          }
        },
        (error) => {
          console.error(error);
        }
      );
    }
    if (key === "sourceCity") {
      setSourceCityName(data.place);
      setSourceCityReferenceId(data.place_id);
      setPickupLocationCoords(latLng);
    } else if (key === "destinations") {
      setDestinationsCityName(data.place);
      setDestinationsCityReferenceId(data.place_id);
      setDropoffLocationCoords(latLng);
    }
  };

  const onSubmit = (event) => {
    event.preventDefault();
    // submit form data
    const data = {};
    data["sourceCity"] = sourceCityName;
    data["sourceCityReferenceId"] = sourceCityReferenceId;
    data["destinationsCityName"] = destinationsCityName;
    data["destinationsCityReferenceId"] = destinationsCityReferenceId;
    data["maxPackages"] = maxPackages;
    data["maxLength"] = maxLength;
    data["maxWidth"] = maxWidth;
    data["maxHeight"] = maxHeight;
    data["pickupDate"] = pickupDate;
    data["dropoffDate"] = dropoffDate;
    data["daysToDeliver"] = "";
    data["pickupLocationCoords"] = "";
    data["dropoffLocationCoords"] = "";
    data["allowedCategory"] = allowedCategory;
    data["driverId"] = localStorage.getItem("userId");
    data["price"] = price;
    if (path === "search") {
      data["radius"] = radius;
    }

    if (path !== "search") {
      navigate("/courier/search");
    }
    console.log(data);
  };

  const setAlertSettings = (message, type, duration, position) => {
    setAlert(true);
    setAlertMessage(message);
    setAlertType(type);
    setAlertDuration(duration);
    setAlertPosition(position ? position : "bottom");

    setTimeout(() => {
      setAlert(false);
    }, duration + 50);
  };

  return (
    <>
      {showAlert && (
        <AlertMessage
          message={alertMessage}
          messageType={alertType}
          duration={alertDuration}
          position={alertPosition}
        ></AlertMessage>
      )}

      <div className="form-container">
        <h3 style={{ margin: "0px 0px 16px 6px" }}>
          {path === "search" ? "Search Couriers" : "Post a delivery"}
        </h3>
        <form
          id="courierForm"
          onSubmit={(event) => {
            onSubmit(event);
          }}
        >
          <LocationAutoComplete
            className="courier-form-input"
            id={"sourceCity"}
            setSelectedLocation={onLocationChange}
            fieldType={"outlined"}
            label={"From"}
            placeholder={"e.g Halifax"}
            required={true}
          ></LocationAutoComplete>

          <LocationAutoComplete
            className="courier-form-input"
            id={"destinations"}
            setSelectedLocation={onLocationChange}
            fieldType={"outlined"}
            label={"To"}
            placeholder={"e.g Montreal"}
            required={true}
          ></LocationAutoComplete>

          <TextField
            id="pickup-date"
            className="courier-form-input"
            variant="filled"
            type="date"
            min={pickupDate}
            required
            pattern="\d{4}-\d{2}-\d{2}"
            defaultValue={pickupDate}
            onChange={(event) => {
              const value = event.target.value;
              setPickupDate(value);
            }}
            label="Pickup Date"
            placeholder="Pickup Date"
          ></TextField>

          <TextField
            id="dropoff-date"
            className="courier-form-input"
            variant="filled"
            type="date"
            required
            pattern="\d{4}-\d{2}-\d{2}"
            defaultValue={dropoffDate}
            onChange={(event) => {
              const value = event.target.value;
              setDropoffDate(value);
            }}
            label="Dropoff Date"
            placeholder="Dropoff Date"
          ></TextField>

          <TextField
            className="courier-form-input"
            id="number-of-packages"
            type="number"
            label="Packages"
            min="1"
            required
            placeholder="1"
            value={maxPackages}
            onChange={(event) => {
              const value = event.target.value;
              setMaxPackages(value);
            }}
          ></TextField>
          <div
            style={{
              display: "flex",
              minWidth: 200,
              maxWidth: 300,
              width: "100%",
            }}
          >
            <TextField
              id="package-length"
              variant="filled"
              type="number"
              label="Length (in cm)"
              placeholder="10"
              min="1"
              required
              sx={{ flexGrow: 1 }}
              value={maxLength}
              onChange={(event) => {
                const value = event.target.value;
                setMaxLength(value);
              }}
            ></TextField>
            &nbsp;
            <TextField
              id="package-width"
              variant="filled"
              type="number"
              min="1"
              required
              label="Width (in cm)"
              placeholder="10"
              sx={{ flexGrow: 1 }}
              value={maxWidth}
              onChange={(event) => {
                const value = event.target.value;
                setMaxWidth(value);
              }}
            ></TextField>
            &nbsp;
            <TextField
              id="package-height"
              variant="filled"
              min="1"
              required
              type="number"
              label="Height (in cm)"
              placeholder="10"
              sx={{ flexGrow: 1 }}
              value={maxHeight}
              onChange={(event) => {
                const value = event.target.value;
                setMaxHeight(value);
              }}
            ></TextField>
          </div>
          <div
            style={{
              display: "flex",
              maxWidth: "300px",
              width: "100%",
              padding: "0px 16px",
            }}
          >
            <TextField
              id="price"
              sx={{
                flexGrow: 1,
              }}
              label="Price (CAD)"
              required
              type="number"
              min="1"
              value={price}
              onChange={(event) => {
                const value = event.target.value;
                setPrice(value);
              }}
            ></TextField>
            &nbsp;
            {path === "search" ? (
              <TextField
                id="radius"
                type="number"
                min="1"
                label="Radius (in km)"
                value={radius}
                required
                sx={{
                  flexGrow: 1,
                }}
                onChange={(event) => {
                  setRadius(event.target.value);
                }}
              ></TextField>
            ) : (
              ""
            )}
          </div>
          <FormControl sx={{ m: 1, maxWidth: "300px", width: "100%" }}>
            <InputLabel id="allowedCategoryLabel">
              {path === "search" ? "Product Category" : "Allowed Category"}
            </InputLabel>
            <Select
              required
              labelId="allowedCategoryLabel"
              id="allowedCategory"
              multiple
              value={allowedCategory}
              onChange={(event) => {
                const {
                  target: { value },
                } = event;
                setAllowedCategory(
                  typeof value === "string" ? value.split(",") : value
                );
              }}
              input={<OutlinedInput required label="Category" />}
              renderValue={(selected) => selected.join(", ")}
              MenuProps={MenuProps}
            >
              {allowedCategoryLabels.map((cateogory) => (
                <MenuItem key={cateogory} value={cateogory}>
                  <Checkbox checked={allowedCategory.indexOf(cateogory) > -1} />
                  <ListItemText primary={cateogory} />
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <Button
            type="submit"
            variant="contained"
            style={{
              margin: "auto",
              marginTop: "0.5rem",
              gridColumnEnd: "span 2",
              minWidth: "180px",
              maxWidth: "300px",
              width: "100%",
            }}
            endIcon={path === "search" ? <SearchIcon /> : <PostAddIcon />}
          >
            {path === "search" ? "Search" : "Post"}
          </Button>
        </form>
      </div>
      <div className="listing-container"></div>
    </>
  );
}
export default CourierForm;
