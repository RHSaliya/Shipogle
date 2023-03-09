import "./App.css";
import { Route, Routes } from "react-router-dom";
import Home from './pages/Home';
import Registration from './pages/Registration';
import RegistrationForm from './pages/RegistrationForm';
import SendItems from './pages/SendItems';
import Login from './pages/Login';
import RegSuccessful from './pages/RegSuccessful';
import UserDashboard from './pages/UserDashboard';
import EditProfile from './pages/EditProfile';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/registration" element={<Registration/>} />
        <Route path="/registration/form" element={<RegistrationForm/>} />
        <Route path="/registration/success" element={<RegSuccessful/>} />
        <Route path="/userdash" element={<UserDashboard/>} />
        <Route path="/user/editprofile" element = {<EditProfile />} />
        <Route path="/userdash/send" element={<SendItems/>} />
        <Route path="/offer-courier" element={<CourierRegistration />} />
        <Route path="/search-courier" element={<CourierListing />} />
        <Route path="/search-courier/mapview" element={<MapView />} />
      </Routes>
    </div>
  );
}

export default App;
