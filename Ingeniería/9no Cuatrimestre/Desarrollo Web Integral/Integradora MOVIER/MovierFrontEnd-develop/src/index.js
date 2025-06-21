import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { BrowserRouter } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { NextUIProvider } from "@nextui-org/react";


ReactDOM.createRoot(document.getElementById('root')).render(
  <NextUIProvider>

    <React.StrictMode>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </React.StrictMode>
  </NextUIProvider>
)
