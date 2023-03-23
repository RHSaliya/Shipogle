import React from 'react'

export default function Header(props) {
  return (
    <div className="Header">
      <div className="title">{props.title}</div>
      <div className="info">{props.info}</div>
    </div>

  )
}
Header.defaultProps = {
  title: "Title",
  info: "Information for the title",
}
