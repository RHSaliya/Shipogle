import {
  Button,
  Checkbox,
  FormControl,
  InputLabel,
  ListItemText,
  MenuItem,
  OutlinedInput,
  Select,
  TextField,
} from "@mui/material";
import * as React from "react";

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

export default function CreatePackage({ routeDetails, packageCreated }) {
  const [packages, setPackages] = React.useState(1);
  const [maxLength, setMaxLength] = React.useState(1);
  const [maxWidth, setMaxWidth] = React.useState(1);
  const [maxHeight, setMaxHeight] = React.useState(1);
  const [askPrice, setAskPrice] = React.useState(routeDetails.price);
  const allowedCategoryLabels = routeDetails.allowedCategory;
  const [category, setCategory] = React.useState([]);
  const onSubmit = (event) => {
    event.preventDefault();
  };
  return (
    <>
      <div className="form-container">
        <h3 style={{ margin: "0px 0px 16px 6px" }}>
          Enter your Package Details
        </h3>
        <form
          id="courierForm"
          onSubmit={(event) => {
            onSubmit(event);
            packageCreated();
          }}
        >
          <TextField
            className="courier-form-input"
            id="number-of-packages"
            type="number"
            label="Packages"
            min="1"
            required
            placeholder="1"
            value={packages}
            onChange={(event) => {
              const value = event.target.value;
              setPackages(value);
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
              value={askPrice}
              onChange={(event) => {
                const value = event.target.value;
                setAskPrice(value);
              }}
            ></TextField>
            &nbsp;
          </div>
          <FormControl sx={{ m: 1, maxWidth: "300px", width: "100%" }}>
            <InputLabel id="allowedCategoryLabel">Type of Package</InputLabel>
            <Select
              required
              labelId="allowedCategoryLabel"
              id="allowedCategory"
              multiple
              value={category}
              onChange={(event) => {
                const {
                  target: { value },
                } = event;
                setCategory(
                  typeof value === "string" ? value.split(",") : value
                );
              }}
              input={<OutlinedInput required label="Category" />}
              renderValue={(selected) => selected.join(", ")}
              MenuProps={MenuProps}
            >
              {allowedCategoryLabels.map((cateogory) => (
                <MenuItem key={cateogory} value={cateogory}>
                  <Checkbox checked={cateogory.indexOf(cateogory) > -1} />
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
          >
            Submit
          </Button>
        </form>
      </div>
    </>
  );
}
