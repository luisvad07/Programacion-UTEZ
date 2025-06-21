// index.js o tu componente principal
import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ThemeProvider } from '../src/shared/components/ThemeContext';
import { FontSizeProvider } from '../src/shared/components/FontSizeContext';
import { CarritoProvider } from '../src/modules/shoppingCart/CarritoContext'; // Ajusta la ruta según tu estructura de archivos

ReactDOM.render(
  <React.StrictMode>
    <ThemeProvider>
      <FontSizeProvider>
        <CarritoProvider>
          <App />
        </CarritoProvider>
      </FontSizeProvider>
    </ThemeProvider>
  </React.StrictMode>,
  document.getElementById('root')
);
