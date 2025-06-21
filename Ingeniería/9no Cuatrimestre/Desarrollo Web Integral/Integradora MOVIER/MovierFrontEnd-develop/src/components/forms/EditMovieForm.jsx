import React, { useEffect, useState } from "react";
import "../../css/admin/CreateMovieForm.css";
import { Input, Spinner, Textarea } from "@nextui-org/react";
import { Button } from "@mui/material";
import { GoBack } from "../common/GoBack";
import api from "../../config/axios/client-gateway";
import endpoints from "../../utils/endpoints";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import CloseIcon from "@mui/icons-material/Close";
export const EditMovieForm = () => {
  const navigate = useNavigate();
  const [title, setTitle] = useState("");
  const [genre, setGenre] = useState("");
  const [image, setImage] = useState("");
  const [description, setDescription] = useState("");
  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    getMovie();
  }, []);

  const getMovie = async () => {
    setIsLoading(true);
    let id = localStorage.getItem("idMovie");
    let user_id = localStorage.getItem("userId");
    try {
      const response = await api.doGet(
        endpoints.GetMovieByIdFunction + id + "/" + user_id
      );
      if (response && response.status === 200) {
        setTitle(response.data.Pelicula.title);
        setGenre(response.data.Pelicula.genre);
        setImage(response.data.Pelicula.image);
        setDescription(response.data.Pelicula.description);
      }
    } catch (error) {
    } finally {
      setIsLoading(false);
    }
  };

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

  const UpdateMovie = async () => {
    setIsLoading(true);
    let id = localStorage.getItem("idMovie");
    try {
      const response = await api.doPut(endpoints.UpdateMovieFunction + id, {
        title,
        description,
        genre,
        image,
      });
      if (response && response.status === 200) {
        Swal.fire("Éxito", "La película ha sido actualizada", "success");
        window.location.href = "/home";
        localStorage.removeItem("idMovie");
      }
    } catch (error) {
      Swal.fire("Error", error.data.message, "error");
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
      UpdateMovie();
    }
  };

  const onCancel = () => {
    setTitle("");
    setGenre("");
    setImage("");
    setDescription("");
    setErrors({});
    navigate("/admin/list");
    localStorage.removeItem("idMovie");
  };

  return (
    <>
      <h1 className="title">Actualizar Película</h1>
      <GoBack />
      {isLoading ? (
        <div className="loading">
          <Spinner color="secondary" label="Cargando información..." />
        </div>
      ) : (
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
          />
          <Input
            className="input"
            label="Género de la película"
            placeholder="Ingrese el género de la película"
            labelPlacement="outside"
            isInvalid={!!errors.genre}
            errorMessage={errors.genre}
            value={genre}
            onChange={handleChange(setGenre, "genre")}
          />
          <Input
            className="input"
            label="Imagen de la película"
            placeholder="Ingrese la URL de la imagen"
            labelPlacement="outside"
            isInvalid={!!errors.image}
            errorMessage={errors.image}
            value={image}
            onChange={handleChange(setImage, "image")}
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
          />
          <div className="buttons">
            <Button
              variant="contained"
              className="save"
              onClick={onSubmit}
              disabled={isLoading}
              endIcon={!isLoading && <EditIcon />}
            >
              {isLoading ? "Actualizando..." : "Actualizar Película"}
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
      )}
    </>
  );
};
