import React from "react";

export default function Header(props) {
  return (
    <div className="Header" style={{ backgroundColor: props.bgColor }}>
      <div className="title" style={{ backgroundColor: props.bgColor }}>
        {props.title}
      </div>
      <div className="info" style={{ backgroundColor: props.bgColor }}>
        {props.info}
      </div>
    </div>
  );
}
Header.defaultProps = {
  title: "Title",
  info: "Information for the title",
};
