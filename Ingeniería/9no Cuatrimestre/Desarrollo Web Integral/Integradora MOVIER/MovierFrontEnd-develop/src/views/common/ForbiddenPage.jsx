import React from "react";
import { Button } from "@mui/material";

export default function ForbiddenPage() {
  return (
    <div className="error-container">
      <h1 className="code-error">403</h1>
      <p>No tienes acceso a esta página</p>
      <Button variant="contained" href="/home">
        Ir a la página de inicio
      </Button>
    </div>
  );
}
