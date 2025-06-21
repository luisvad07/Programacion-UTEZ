import React from 'react'
import './Main.css'
const Error = () => {
  return (
    <div className="error-page" style={{ marginTop: '-70px' }}>
      <h1 className="error-title">Error</h1>
      <p className="error-message">Oops! Something went wrong.</p>
      <div className="custom-loader"></div>
    </div>
  )
}

export default Error
