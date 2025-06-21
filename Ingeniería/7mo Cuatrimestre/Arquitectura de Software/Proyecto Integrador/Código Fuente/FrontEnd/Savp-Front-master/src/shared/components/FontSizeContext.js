// FontSizeContext.js
import React, { createContext, useContext, useState, useEffect } from 'react';

const FontSizeContext = createContext();

export const useFontSize = () => useContext(FontSizeContext);

export const FontSizeProvider = ({ children }) => {
  const storedFontSize = localStorage.getItem('fontSize') || '16px';
  const [fontSize, setFontSize] = useState(storedFontSize);

  const changeFontSize = (newSize) => {
    setFontSize(newSize);
    localStorage.setItem('fontSize', newSize);
  };

  useEffect(() => {
    setFontSize(storedFontSize);
  }, [storedFontSize]);

  return (
    <FontSizeContext.Provider value={{ fontSize, changeFontSize }}>
      {children}
    </FontSizeContext.Provider>
  );
};
