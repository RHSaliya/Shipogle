import React, { useState } from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import "./payments.css";
import Data from "./data";
function Payment() {
  const [amount, setAmount] = useState("");
  const [currency, setCurrency] = useState("");
  const [cardNumber, setCardNumber] = useState("");
  const [cardExpiryMonth, setCardExpiryMonth] = useState("");
  const [cardExpiryYear, setCardExpiryYear] = useState("");
  const [cardCvv, setCardCvv] = useState("");
  const [cardHolderName, setCardHolderName] = useState("");

  const handleFormSubmit = (e) => {
    e.preventDefault();
    // handle form submission logic here
  };
  const data = new Data().courierDetails;
  return (
    <form className="payment-form" onSubmit={handleFormSubmit}>
      <p
        style={{
          textAlign: "left",
          gridColumnEnd: "span 2",
        }}
      >
        Please enter payment Details
      </p>
      <TextField
        label="Amount (CAD)"
        type="number"
        className="input-field"
        value={data.price}
        disabled
        onChange={(e) => setAmount(e.target.value)}
      />

      <TextField
        label="Currency"
        className="input-field"
        type="text"
        value={currency}
        onChange={(e) => setCurrency(e.target.value)}
      />

      <TextField
        label="Card Number"
        className="input-field"
        type="text"
        value={cardNumber}
        onChange={(e) => setCardNumber(e.target.value)}
      />

      <TextField
        label="Card Expiry Month"
        className="input-field"
        type="number"
        value={cardExpiryMonth}
        onChange={(e) => setCardExpiryMonth(e.target.value)}
      />

      <TextField
        label="Card Expiry Year"
        className="input-field"
        type="number"
        value={cardExpiryYear}
        onChange={(e) => setCardExpiryYear(e.target.value)}
      />

      <TextField
        label="Card CVV"
        className="input-field"
        type="text"
        value={cardCvv}
        onChange={(e) => setCardCvv(e.target.value)}
      />

      <TextField
        label="Card Holder Name"
        className="input-field"
        type="text"
        value={cardHolderName}
        onChange={(e) => setCardHolderName(e.target.value)}
      />

      <Button
        style={{
          margin: "auto",
          marginTop: "0.5rem",
          gridColumnEnd: "span 2",
          minWidth: "180px",
          maxWidth: "300px",
          width: "100%",
        }}
        type="submit"
        variant="contained"
        color="primary"
      >
        Pay
      </Button>
    </form>
  );
}

export default Payment;
