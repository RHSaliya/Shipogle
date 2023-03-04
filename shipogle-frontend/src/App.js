
import './App.css';
import { Route, Routes } from "react-router-dom";
import Home from './pages/Home';
import Registration from './pages/Registration';
import RegistrationForm from './pages/RegistrationForm';

import Login from './pages/Login';
import RegSuccessful from './pages/RegSuccessful';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/registration" element={<Registration/>} />
        <Route path="/registration/form" element={<RegistrationForm/>} />
        <Route path="/registration/success" element={<RegSuccessful/>} />
      </Routes>
    </div>
  );
}

export default App;
