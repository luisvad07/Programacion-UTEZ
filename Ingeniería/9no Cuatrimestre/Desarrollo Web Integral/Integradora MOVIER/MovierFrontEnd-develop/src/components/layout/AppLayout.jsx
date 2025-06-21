import React from "react";
import MoviesNavbar from "../navigation/MoviesNavbar";
import "../../css/common/layout.css";
export const AppLayout = ({ children }) => {
  return (
    <>
      <MoviesNavbar />
      <div className="container">{children}</div>
    </>
  );
};
