import React from 'react'
import {useLocation} from 'react-router-dom';
import ResponsiveDrawer from '../components/ResponsiveDrawer';


export default function UserDashboard() {
  const location = useLocation();
  const userState= location.state;
  console.log("My email:" + userState.email);
  console.log("My password:" + userState.password);
  return (
    <div>UserDashboard
        <ResponsiveDrawer />
    </div>
  )
}
