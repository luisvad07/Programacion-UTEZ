import React, { useState } from "react";
import "../../css/admin/CreateMovieForm.css";
import { Input, Textarea } from "@nextui-org/react";
import { Button } from "@mui/material";
import { GoBack } from "../common/GoBack";
import endpoints from "../../utils/endpoints";
import api from "../../config/axios/client-gateway";
import genresLabel from "../../utils/genres";
import { Select, SelectItem } from "@nextui-org/react";
import AddIcon from "@mui/icons-material/Add";
import Swal from "sweetalert2";
import CloseIcon from "@mui/icons-material/Close";
export const CreateMovieForm = () => {
  const [title, setTitle] = useState("");
  const [genre, setGenre] = useState("");
  const [image, setImage] = useState("");
  const [description, setDescription] = useState("");
  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const genres = genresLabel;

  const validate = () => {
    const newErrors = {};
    if (title.trim() === "")
      newErrors.title = "El título de la película es requerido";
    if (genre.trim() === "")
      newErrors.genre = "El género de la película es requerido";
    if (image.trim() === "")
      newErrors.image = "La URL de la imagen es requerida";
    if (description.trim() === "")
      newErrors.description = "La descripción de la película es requerida";
    return newErrors;
  };

  const createMovie = async () => {
    setIsLoading(true);
    try {
      const response = await api.doPost(endpoints.CreateMovieFunction, {
        title,
        genre,
        image,
        description,
      });
      if (response && response.status === 200) {
        Swal.fire("Éxito", "La película ha sido creada", "success");
      }
      window.location.href = "/home";
    } catch (error) {
    } finally {
      setIsLoading(false);
    }
  };

  const handleChange = (setter, field) => (event) => {
    setter(event.target.value);
    setErrors((prevErrors) => ({ ...prevErrors, [field]: "" }));
  };

  const onSubmit = (event) => {
    event.preventDefault();
    const newErrors = validate();
    setErrors(newErrors);
    if (Object.keys(newErrors).length === 0) {
      createMovie();
    }
  };
  const onCancel = () => {
    setTitle("");
    setGenre("");
    setImage("");
    setDescription("");
    setErrors({});
  };
  return (
    <>
      <h1 className="title">Crear Película</h1>
      <GoBack />
      <div className="forms">
        <Input
          className="input"
          label="Título de la película"
          placeholder="Ingrese el título de la película"
          labelPlacement="outside"
          isInvalid={!!errors.title}
          errorMessage={errors.title}
          value={title}
          onChange={handleChange(setTitle, "title")}
          color="secondary"
        />

        <Select
          label="Generó"
          placeholder="Seleccione el genero de la película"
          value={genre}
          onChange={handleChange(setGenre, "genre")}
          color="secondary"
          isInvalid={!!errors.genre}
          errorMessage={errors.genre}
          fullWidth
          className="input"
        >
          {genres.map((genre) => (
            <SelectItem key={genre.key} value={genre.value}>
              {genre.value}
            </SelectItem>
          ))}
        </Select>
        <Input
          className="input"
          label="Imagen de la película"
          placeholder="Ingrese la URL de la imagen"
          labelPlacement="outside"
          isInvalid={!!errors.image}
          errorMessage={errors.image}
          value={image}
          onChange={handleChange(setImage, "image")}
          color="secondary"
        />
        <Textarea
          className="textarea"
          label="Descripción de la película"
          placeholder="Ingrese la descripción de la película"
          labelPlacement="outside"
          rows={10}
          isInvalid={!!errors.description}
          errorMessage={errors.description}
          value={description}
          onChange={handleChange(setDescription, "description")}
          color="secondary"
        />
        <div className="buttons">
          <Button
            variant="contained"
            className="save"
            onClick={onSubmit}
            disabled={isLoading}
            endIcon={!isLoading && <AddIcon />}
          >
            {isLoading ? "Creando..." : "Crear Película"}
          </Button>
          <Button
            variant="contained"
            className="cancel"
            onClick={onCancel}
            endIcon={<CloseIcon />}
          >
            Cancelar
          </Button>
        </div>
      </div>
    </>
  );
};
