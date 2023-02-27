import "./App.css";
import { Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Registration from "./pages/Registration";
import RegistrationForm from "./pages/RegistrationForm";

import Login from "./pages/Login";
import CourierRegistration from "./pages/courierRegistration";
import CourierListing from "./pages/couriersListing";
import MapView from "./pages/mapView";
function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/registration" element={<Registration />} />
        <Route path="/registration/form" element={<RegistrationForm />} />
        <Route path="/offer-courier" element={<CourierRegistration />} />
        <Route path="/search-courier" element={<CourierListing />} />
        <Route path="/search-courier/mapview" element={<MapView />} />
      </Routes>
    </div>
  );
}

export default App;
