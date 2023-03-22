import { BrowserRouter, Routes, Route } from "react-router-dom";
import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";
import Login from "./pages/Login";
import Registration from "./pages/Registration";
import RegistrationForm from "./pages/RegistrationForm";
import RegSuccessful from "./pages/RegSuccessful";
import CourierForm from "./pages/CourierForm";
import UserDashboard from "./pages/UserDashboard";
import EditProfile from "./pages/EditProfile";
import SendItems from "./pages/SendItems";
import Inbox from "./pages/Inbox/Inbox";
import OrderDetails from "./components/OrderDetails";
import Orders from "./pages/Orders";
import Payment from "./pages/payment";

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<App />}>
        <Route path="/login" element={<Login />} />
        <Route path="/registration" element={<Registration />} />
        <Route path="/courier/search" element={<CourierForm path={1} />} />
        <Route path="/courier/offer" element={<CourierForm key={2} />} />
        <Route path="/registration/form" element={<RegistrationForm />} />
        <Route path="/registration/success" element={<RegSuccessful />} />
        <Route path="/userdash" element={<UserDashboard />} />
        <Route path="/user/editprofile" element={<EditProfile />} />
        <Route path="/userdash/send" element={<SendItems />} />
        <Route path="/inbox" element={<Inbox></Inbox>}></Route>

        <Route path="/orders" element={<Orders></Orders>}></Route>
        <Route
          path="/orders/details/:orderId"
          element={<OrderDetails></OrderDetails>}
        ></Route>
        <Route
          path="/courier/payment/:id"
          element={<Payment></Payment>}
        ></Route>
      </Route>
    </Routes>
  </BrowserRouter>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
