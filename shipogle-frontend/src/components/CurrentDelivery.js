import {
  Button,
  Card,
  CardContent,
  CardHeader,
  TextField,
} from "@mui/material";
import * as React from "react";
import { Form } from "react-router-dom";
import customAxios from "../utils/MyAxios";
import Constants from "../Constants";

export default function CurrentDelivery(props) {
  React.useEffect(() => {
    customAxios.get(Constants.GETREQUESTS).then(
      (res) => {},
      (error) => {
        console.error(error);
      }
    );
  }, []);
  const submit = (e) => {
    e.preventDefault();
  };
  return (
    <>
      <Card>
        <CardHeader title="Enter Delivery code"></CardHeader>
        <CardContent>
          <Form onSubmit={(event) => submit(event)}>
            <TextField label="Enter Code"></TextField>
            <Button type="submit">Submit</Button>
          </Form>
        </CardContent>
      </Card>
    </>
  );
}
