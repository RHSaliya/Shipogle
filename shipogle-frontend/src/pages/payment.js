import React, { useState } from "react";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";

import "./payment.css";
import Data from "./data";
import Constants from "../Constants";
import customAxios from "../utils/MyAxios";
import CommonFunctions from "../services/CommonFunction";
import { useNavigate } from "react-router-dom";
function Payment() {
  const navigate = useNavigate();
  const commFunc = new CommonFunctions();
  const [amount, setAmount] = useState("");
  const [Currency, setCurrency] = useState("");
  const [CardNumber, setCardNumber] = useState("");
  const [CardExpiryMonth, setCardExpiryMonth] = useState("");
  const [CardExpiryYear, setCardExpiryYear] = useState("");
  const [CardCvv, setCardCvv] = useState("");
  const [CardHolderName, setCardHolderName] = useState("");

  const handleFormSubmit = (e) => {
    e.preventDefault();
    const paymentDetails = {
      amount: data.price,
      currency: Currency,
      cardNumber: CardNumber,
      cardExpiryMonth: CardExpiryMonth,
      cardExpiryYear: CardExpiryYear,
      cardCvv: CardCvv,
      cardHolderName: CardHolderName,
    };

    customAxios.post(Constants.PAYMENT_CHARGE, paymentDetails).then(
      (res) => {
        commFunc.showAlertMessage("success", "success", 2000, "bottom");
        alert("Payment Successfull");
        navigate("/orders");
      },
      (error) => {
        console.error(error);
      }
    );
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
        value={Currency}
        onChange={(e) => setCurrency(e.target.value)}
      />

      <TextField
        label="Card Number"
        className="input-field"
        type="text"
        value={CardNumber}
        onChange={(e) => setCardNumber(e.target.value)}
      />

      <TextField
        label="Card Expiry Month"
        className="input-field"
        type="number"
        value={CardExpiryMonth}
        onChange={(e) => setCardExpiryMonth(e.target.value)}
      />

      <TextField
        label="Card Expiry Year"
        className="input-field"
        type="number"
        value={CardExpiryYear}
        onChange={(e) => setCardExpiryYear(e.target.value)}
      />

      <TextField
        label="Card CVV"
        className="input-field"
        type="text"
        value={CardCvv}
        onChange={(e) => setCardCvv(e.target.value)}
      />

      <TextField
        label="Card Holder Name"
        className="input-field"
        type="text"
        value={CardHolderName}
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
