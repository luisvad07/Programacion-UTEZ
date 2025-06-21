import React, { useEffect, useState } from "react";
import { Container } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import api from "../../config/axios/client-gateway";
import endpoints from "../../utils/endpoints";

import Loader from "../../views/common/Loader";
import {
  Card,
  CardHeader,
  CardBody,
  Image,
  CardFooter,
} from "@nextui-org/react";
import { Button, CardMedia } from "@mui/material";
import "../../css/movies/movies.css";
import VisibilityIcon from "@mui/icons-material/Visibility";

export default function MoviesCards() {
  const [movies, setMovies] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const imgSize = 300;
  const navigate = useNavigate();
  const user_id = localStorage.getItem("userId");

  useEffect(() => {
    getMovies();
  }, []);

  const handleClick = (id) => {
    navigate(`/movies/m/${id}`);
  };

  const getMovies = async () => {
    setIsLoading(true);
    try {
      const response = await api.doGet(endpoints.GetMovieFunction);
      if (response && response.status === 200) {
        setMovies(response.data.Peliculas);
      }
    } catch (error) {
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <h1 className="title">Catalogo de películas</h1>

      <Container fluid className="movies-container">
        {isLoading ? (
          <Loader />
        ) : movies.length > 0 ? (
          movies.map((movie) => (
            <Card key={movie.id} className="card">
              <CardHeader className="pb-0 pt-2 px-4 flex-col items-start">
                <h4 className="font-bold text-large title-text">
                  {movie.title.length > 24
                    ? movie.title.substring(0, 24) + "..."
                    : movie.title}
                </h4>
              </CardHeader>
              <CardBody className="overflow-visible py-2">
                <CardMedia
                  sx={{ height: 440 }}
                  image={movie.image}
                  title="Movie Image"
                />
                <p className="description-text">
                  {movie.description.substring(0, 50)}
                  <span>...</span>
                </p>
              </CardBody>
              <CardFooter className="flex-col items-center">
                <Button
                  variant="contained"
                  auto
                  onClick={() => handleClick(movie.id)}
                  className="w-full"
                  endIcon={!isLoading && <VisibilityIcon />}
                >
                  Ver detalles
                </Button>
              </CardFooter>
            </Card>
          ))
        ) : (
          <p className="no-peliculas">No hay peliculas disponibles</p>
        )}
      </Container>
    </>
  );
}
