import React from 'react'
import {useLocation} from 'react-router-dom';
import ResponsiveDrawer from '../components/ResponsiveDrawer';


export default function UserDashboard() {
    const state = useLocation();
  return (
    <div>UserDashboard
        <ResponsiveDrawer />
    </div>
  )
}
