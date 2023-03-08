import React from 'react'
import { Link } from "react-router-dom";

export default function NavBar() {
  return (
    <div className="navbar">
        <Link to="/">Home</Link>
        <Link to="/registration">Register</Link>
        <Link to="/login">Login</Link>
        <Link to="/aboutUs">About Us</Link>
    </div>
  )
}
