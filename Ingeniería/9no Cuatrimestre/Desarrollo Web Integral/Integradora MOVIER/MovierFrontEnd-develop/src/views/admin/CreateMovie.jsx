import React from "react";
import { AppLayout } from "../../components/layout/AppLayout";
import { CreateMovieForm } from "../../components/forms/CreateMovieForm";

export const CreateMovie = () => {
  return (
    <AppLayout>
      <CreateMovieForm />
    </AppLayout>
  );
};
