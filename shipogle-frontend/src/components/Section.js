import React from 'react'

export default function Section(props) {
  return (
    <div className="Section">
        <div className="section-title">
            {props.title}
        </div>
        <div className="section-info">
            {props.info}
        </div>
    </div>
  )
}
