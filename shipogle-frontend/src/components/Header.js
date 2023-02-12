import React from 'react'

export default function Header(props) {
  return (
    <div className="Header">
        <div>{props.title}</div>
        <div>{props.info}</div>
    </div>
    
  )
}
Header.defaultProps = {
    title: "Title",
    info: "Information for the title",
}
