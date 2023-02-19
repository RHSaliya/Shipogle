import React from 'react'

export default function RegRollBox(props) {
  return (
    <div className="reg-roll-box">
        <p className="role">{props.role}</p>
        <img className="role-img" alt="Role" src={props.roleimg}></img>
        <p className="role-info">{props.roleinfo}</p>  
        <button className="btn reg">Register</button>
    </div>
  )
}
