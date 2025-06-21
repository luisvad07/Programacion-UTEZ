import React from "react";
import { AppLayout } from "../../components/layout/AppLayout";
import "../../css/common/PageNotFound.css";
import { Button } from "@mui/material";

export const PageNotFund = () => {
  return (
    <AppLayout>
      <div className="error-container">
        <h1 className="code-error">404</h1>
        <p>Página no encontrada</p>
        <Button variant="contained" href="/home">
          Ir a la página de inicio
        </Button>
      </div>
    </AppLayout>
  );
};
