import React from "react";
import "../../css/common/Common.css";
import { Button } from "@mui/material";
import ArrowBackIosIcon from "@mui/icons-material/ArrowBackIos";

export const GoBack = () => {
  const goBack = () => {
    window.history.back();
  };
  return (
    <div className="header-back">
      <Button
        className="back-button"
        onClick={goBack}
        startIcon={<ArrowBackIosIcon className="back-icon" />}
      >
        Volver
      </Button>
    </div>
  );
};
