
import './App.css';
import { Route, Routes } from "react-router-dom";
import Home from './pages/Home';
import Registration from './pages/Registration';
import RegistrationForm from './pages/RegistrationForm';
import SendItems from './pages/SendItems';
import Login from './pages/Login';
import Inbox from './pages/Inbox';
import RegSuccessful from './pages/RegSuccessful';
import UserDashboard from './pages/UserDashboard';
import EditProfile from './pages/EditProfile';
import DeliverItems from './pages/DeliverItems';
import ForgotPwd from './pages/ForgotPwd';
import ResetPwd from './pages/ResetPwd';
import Feedback from './pages/FeedbackIssues/Feedback';
import Issues from './pages/FeedbackIssues/Issues';
function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/forgotPwd" element={<ForgotPwd />} />
        <Route path="/userdash/deliver" element={<DeliverItems />} />
        <Route path="/resetPassword" element={<ResetPwd />} />
        <Route path="/inbox" element={<Inbox />} />
        <Route path="/registration" element={<Registration />} />
        <Route path="/registration/form" element={<RegistrationForm />} />
        <Route path="/registration/success" element={<RegSuccessful />} />
        <Route path="/userdash" element={<UserDashboard />} />
        <Route path="/user/editprofile" element={<EditProfile />} />
        <Route path="/userdash/send" element={<SendItems />} />
        <Route path="/feedback" element={<Feedback />} />
        <Route path="/issues" element={<Issues />} />
      </Routes>
    </div>
  );
}

export default App;
