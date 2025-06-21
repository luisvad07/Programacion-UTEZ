import React from "react";
import { Spinner } from "@nextui-org/react";

export default function Loader() {
  const styles = {
    container: {
      position: "fixed",
      top: "50%",
      left: "50%",
      transform: "translate(-50%, -50%)",
      backgroundColor: "rgba(0,0,0,0.5)",
      width: "100%",
      height: "100%",
      display: "flex",
      flexDirection: "column",
      justifyContent: "center",
      alignItems: "center",
      zIndex: 1000,
    },
  };
  return (
    <div style={styles.container}>
      <Spinner color="secondary" label="Cargando..."/>
    </div>
  );
}
