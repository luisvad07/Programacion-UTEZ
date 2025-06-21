import React, { useState, useEffect } from "react";
import { Modal, Container, Form } from "react-bootstrap";
import api from "../../config/axios/client-gateway";
import { Input } from "@nextui-org/react";
import SearchIcon from "@mui/icons-material/Search";
import Loader from "../../views/common/Loader";
import endpoints from "../../utils/endpoints";
import { useNavigate } from "react-router-dom";

const SearchModal = ({ initialSearchQuery = '', show, handleClose }) => {
  const [searchQuery, setSearchQuery] = useState(initialSearchQuery);
  const [movies, setMovies] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [typingTimeout, setTypingTimeout] = useState(null);
  const navigate = useNavigate();
  useEffect(() => {
    if (!show) {
      setSearchQuery("");
      setMovies([]);
    }
  }, [show]);

  useEffect(() => {
    if (searchQuery) {
      if (typingTimeout) {
        clearTimeout(typingTimeout);
      }
      setTypingTimeout(
        setTimeout(() => {
          searchMovies(searchQuery);
        }, 500)
      );
    }
  }, [searchQuery]);

  const searchMovies = async (query) => {
    setIsLoading(true);
    try {
      console.log(`${endpoints.SearchMoviesFunction}${query}`)
      const response = await api.doGet(`${endpoints.SearchMoviesFunction}${query}`);
      if (response && response.status === 200) {
        setMovies(response.data.Peliculas);
      }
    } catch (error) {
      console.error('Error fetching movies:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleClick = (id) => {
    navigate(`/movies/m/${id}`);
    handleClose(); 
  };

  const styles = {
    listItem: {
      display: "flex",
      alignItems: "center",
      padding: "10px",
      cursor: "pointer",
      borderBottom: "1px solid #ccc",
    },
    thumbnail: {
      width: "50px",
      height: "50px",
      marginRight: "10px",
      objectFit: "cover",
      borderRadius: "5px",
    },
    title: {
      fontSize: "16px",
      fontWeight: "bold",
    },
    description: {
      fontSize: "14px",
      color: "#666",
    },
  };

  return (
    <Modal show={show} onHide={handleClose} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>Resultados de la búsqueda</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form className="form">
          <Input
            value={searchQuery}
            onChange={handleSearchChange}
            placeholder="Busca películas"
            startContent={
              <div>
                <SearchIcon className="search" />
              </div>
            }
          />
        </Form>
        <Container fluid>
          {isLoading ? (
            <Loader />
          ) : movies.length > 0 ? (
            movies.map((movie) => (
              <div
                key={movie.id}
                style={styles.listItem}
                onClick={() => handleClick(movie.id)}
              >
                <img
                  src={movie.image}
                  alt={movie.title}
                  style={styles.thumbnail}
                />
                <div>
                  <div style={styles.title}>
                    {movie.title.length > 24
                      ? `${movie.title.substring(0, 24)}...`
                      : movie.title}
                  </div>
                  <div style={styles.description}>
                    {movie.description.substring(0, 50)}...
                  </div>
                </div>
              </div>
            ))
          ) : (
            <p>No se encontraron resultados.</p>
          )}
        </Container>
      </Modal.Body>
    </Modal>
  );
};

export default SearchModal;
