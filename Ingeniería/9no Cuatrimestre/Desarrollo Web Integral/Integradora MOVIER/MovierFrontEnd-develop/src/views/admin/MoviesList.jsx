import React from "react";
import { MoviesTable } from "../../components/tables/MoviesTable";
import { AppLayout } from "../../components/layout/AppLayout";

export const MoviesList = () => {
  return (
    <AppLayout>
      <MoviesTable />
    </AppLayout>
  );
};
