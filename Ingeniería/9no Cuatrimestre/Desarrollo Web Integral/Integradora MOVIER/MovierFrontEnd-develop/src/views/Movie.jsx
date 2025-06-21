import React, { useEffect, useState } from "react";
import { Alert, Col, Container, Row } from "react-bootstrap";
import { Spinner } from "@nextui-org/react";
import { useParams } from "react-router-dom";
import "../css/movies/movie.css";
import CommentForm from "../components/forms/CommentForm";
import Comment from "../components/misc/Comment";
import api from "../config/axios/client-gateway";
import endpoints from "../utils/endpoints";
import Loader from "./common/Loader";
import { Chip } from "@nextui-org/react";
import { Button } from "@mui/material";
import MoviesNavbar from "../components/navigation/MoviesNavbar";
import Swal from "sweetalert2";
import VisibilityIcon from "@mui/icons-material/Visibility";
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff";
export default function Movie() {
  const { id } = useParams();
  const user_id = localStorage.getItem("userId");

  const [comments, setComments] = useState([]);
  const [movie, setMovie] = useState({});
  const [isMovieLoading, setIsMovieLoading] = useState(false);
  const [isCommentsLoading, setIsCommentsLoading] = useState(false);
  const [isWatchedLoading, setIsWatchedLoading] = useState(false);
  const [isWatched, setIsWatched] = useState(false);

  useEffect(() => {
    fetchMovie(id);
    fetchComments(id);
  }, [id]);

  const fetchMovie = async (id) => {
    try {
      setIsMovieLoading(true);
      const response = await api.doGet(
        endpoints.GetMovieByIdFunction + id + "/" + user_id
      );
      if (response && response.status === 200) {
        setMovie(response.data.Pelicula);
        setIsWatched(Boolean(response.data.Pelicula.watched));
      }
    } catch (error) {
      console.error("Error fetching movie:", error);
    } finally {
      setIsMovieLoading(false);
    }
  };

  const fetchComments = async (id) => {
    try {
      setIsCommentsLoading(true);
      const response = await api.doGet(endpoints.GetCommentsFunction + id);
      if (response && response.status === 200) {
        setComments(response.data.Comentarios);
      }
    } catch (error) {
      console.error("Error fetching comments:", error);
    } finally {
      setIsCommentsLoading(false);
    }
  };

  const handleClick = async () => {
    setIsWatchedLoading(true);
    try {
      const endpoint = endpoints.WatchedMovieFunction;
      const response = await api.doPost(endpoint, {
        movie_id: parseInt(id),
        user_id: parseInt(localStorage.getItem("userId")),
      });
      if (response && response.status === 200) {
        Swal.fire({
          icon: "success",
          title: "Éxito",
          text: "Estado de vista actualizado correctamente",
          showConfirmButton: false,
          timer: 1500,
        }).then(() => {
          fetchMovie(id);
        });
      }
    } catch (error) {
      console.error(
        "Error actualizar el estado de watched de la pelicula:",
        error
      );
    } finally {
      setIsWatchedLoading(false);
    }
  };

  return (
    <div>
      {isMovieLoading ? (
        <div className="loader-container">
          <Loader />
        </div>
      ) : (
        <div>
          <MoviesNavbar />
          <Container fluid className="mainContainer">
            <Row className="movie-row">
              <Col xl={4} className="movie-image-col">
                <img
                  src={movie.image || ""}
                  className="movie-image"
                  alt={movie.title}
                  title={movie.title}
                />
              </Col>
              <Col xl={5} className="movie-info-col">
                <h1 className="movie-title">{movie.title}</h1>
                <p className="movie-description">{movie.description}</p>
                <h5 className="movie-genre">Género: {movie.genre}</h5>
                <Button
                  onClick={handleClick}
                  className="button-movie"
                  variant="contained"
                  disabled={isWatchedLoading}
                  endIcon={
                    isWatched
                      ? !isWatchedLoading && (
                          <VisibilityOffIcon color="secondary" />
                        )
                      : !isWatchedLoading && (
                          <VisibilityIcon color="secondary" />
                        )
                  }
                >
                  {isWatchedLoading ? (
                    <Spinner color="secondary" />
                  ) : isWatched ? (
                    "Desmarcar como vista"
                  ) : (
                    "Marcar como vista"
                  )}
                </Button>
              </Col>
              <Col xl={1} className="vertical-line-col">
                <div className="vertical-line" />
              </Col>
              <Col xl={2} className="movie-status-col">
                <h3 className="movie-status-title">Estado</h3>
                <Chip color={movie.status === 1 ? "primary" : "error"}>
                  {movie.status === 1 ? "Activa" : "Inactiva"}
                </Chip>
                <Chip
                  className="mx-2"
                  color={movie.watched === 1 ? "primary" : "error"}
                >
                  {movie.watched === 1 ? "Vista" : "No vista"}
                </Chip>
              </Col>
            </Row>
          </Container>
          <hr />
          <Container className="mt-5 commentContainer">
            <CommentForm rows={4} fetchComments={fetchComments} idMovie={id} />
          </Container>
          <hr />
          <Container className="mb-5 comment-list-container" fluid>
            {isCommentsLoading ? (
              <div className="w-100 d-flex justify-content-center">
                <Spinner color="secondary" />
              </div>
            ) : (
              <div>
                <h2 className="text-center mb-3 comment-title">Comentarios</h2>
                {comments && comments.length > 0 ? (
                  comments.map((m) => (
                    <div key={m.comment_id} className="comment">
                      <Comment
                        comment={m.comment}
                        date={m.date}
                        user={m.username}
                      />
                    </div>
                  ))
                ) : (
                  <Alert variant="warning">
                    No hay comentarios disponibles.
                  </Alert>
                )}
              </div>
            )}
          </Container>
        </div>
      )}
    </div>
  );
}
