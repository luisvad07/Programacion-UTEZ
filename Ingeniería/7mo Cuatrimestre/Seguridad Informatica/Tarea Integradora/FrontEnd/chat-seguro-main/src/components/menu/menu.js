import React, { useState, useEffect } from "react";
import { Container, Row, Col, Button, Card, Form } from "react-bootstrap";
import { AuthContext } from "../../services/auth/context/AuthContext";
import "./menu.css";
import { API_URL_WS, cifradoCesar } from "../../utils/constants";
import { fetchClient } from "../../utils/fetchClient";
import { Send } from "feather-icons-react/build/IconComponents";

const MainMenu = () => {
  const { userInfo, isLoading, logout } = React.useContext(AuthContext);
  const [chats, setChats] = useState([]);
  const [activeChat, setActiveChat] = useState(null);
  const [mensaje, setMensaje] = useState("");
  const [loading, setLoading] = useState(false);
  const [NoChats, setNoChats] = useState([]);

  async function WebsocketAPI() {
    let webSocket = new WebSocket(`${API_URL_WS}/ws/chat/`);
    webSocket.onopen = function () {
      if (webSocket.readyState === webSocket.OPEN) {
        webSocket.send(userInfo.access_token);
      }
    };

    webSocket.onmessage = function (event) {
      setChats(JSON.parse(event.data));
    };
    webSocket.onclose = function (event) {
      alert("Conexión cerrada");
    };

    const sendRequest = () => {
      if (webSocket.readyState === webSocket.OPEN) {
        webSocket.send(userInfo.access_token);
      }
    };

    const sendConstante = () => {
      // Función para enviar una petición al servidor de forma constante
      setInterval(sendRequest, 1000);
    };
    // se ejecuta la función para enviar constantemente peticiones al servidor
    sendConstante();
  }

  const handleSendMessage = async (chatId, message, key) => {
    try {
      const messageToSend = {
        message: cifradoCesar(key, message),
      };
      const response = await fetchClient(
        `/chats/${chatId}/messages/`,
        "POST",
        messageToSend
      );
      if (response) {
        setMensaje("");
      }
    } catch (error) {
      console.error(error);
    }
  };
  const getNoChats = async () => {
    const noChat = await fetchClient("/users/noChat/", "GET", null);
    if (noChat) {
      setNoChats(noChat);
    }
  };
  const createChat = async (username) => {
    const chat = {
      usernameTo: username,
    };
    const response = await fetchClient("/chats/", "POST", chat);
    if (response) {
      getNoChats();
      setActiveChat(response);
    } else {
      getNoChats();
      alert("Error al crear el chat");
    }
  };

  useEffect(() => {
    const getChats = async () => {
      const response = await fetchClient("/chats/", "GET", null);
      if (response) {
        setChats(response);
      }
    };
    const getNoChats = async () => {
      const noChat = await fetchClient("/users/noChat/", "GET", null);
      if (noChat) {
        setNoChats(noChat);
      }
    };
    getNoChats();
    getChats();
    WebsocketAPI();
  }, []);

  useEffect(() => {
    if (activeChat) {
      chats.find((chat) => {
        if (chat._id === activeChat._id) {
          setActiveChat(chat);
        }
      });
    }
  }, [chats]);

  return (
    <div className="bg-light mb-5">
      <section className="d-flex align-items-center">
        <Container fluid className="position-relative">
          <Card className="shadow-lg border-0 rounded-lg mt-5 chat-container">
            <Card.Body className="p-5">
              <h1 className="fs-4 mb-4 text-center">Chats</h1>
              <Row>
                <Col xs={12} md={4}>
                  <div className="chat-list">
                    {chats ? (
                      chats.map((chat) => (
                        <div
                          key={chat._id}
                          className={`chat-item ${
                            activeChat && activeChat._id === chat._id
                              ? "active"
                              : ""
                          }`}
                          onClick={() => setActiveChat(chat)}
                        >
                          {chat.usernameFrom === userInfo.username
                            ? `Chat con ${chat.usernameTo}`
                            : `Chat con ${chat.usernameFrom}`}
                        </div>
                      ))
                    ) : (
                      <div className="text-center">Cargando...</div>
                    )}
                    <div>
                      <hr />
                      <h6 className="text-center">Usuarios sin chat</h6>
                    </div>
                    {NoChats ? (
                      NoChats.map((noChat) => (
                        <div
                          key={noChat._id}
                          className={`chat-item ${
                            activeChat && activeChat._id === noChat._id
                              ? "active"
                              : ""
                          }`}
                          onClick={() => createChat(noChat.username)}
                        >
                          {`Iniciar chat con ${noChat.username}`}
                        </div>
                      ))
                    ) : (
                      <div className="text-center">Cargando...</div>
                    )}
                  </div>
                </Col>
                <Col xs={12} md={8}>
                  {activeChat && (
                    <div className="chat-box">
                      {activeChat.messages ? (
                        activeChat.messages.map((message, index) => (
                          <div
                            key={index}
                            className={`message ${
                              message.username === userInfo.username
                                ? "own-message"
                                : "other-message"
                            }`}
                          >
                            <strong>{message.username}:</strong>{" "}
                            <small className="text-muted ms-2">
                              {new Date(message.timestamp).toLocaleString()}
                            </small>
                            <br />
                            {message.message}
                          </div>
                        ))
                      ) : (
                        <div className="text-center">No hay mensajes</div>
                      )}
                    </div>
                  )}

                  {activeChat && (
                    <Form
                      className="mt-3"
                      onSubmit={(e) => {
                        e.preventDefault();
                      }}
                    >
                      <Form.Group controlId="newMessage">
                        <Form.Control
                          type="text"
                          placeholder="Escribe tu mensaje..."
                          value={mensaje}
                          onChange={(e) => setMensaje(e.target.value)}
                        />
                      </Form.Group>
                      <Button
                        type="submit"
                        variant="primary m-2"
                        onClick={async () =>
                          await handleSendMessage(
                            activeChat._id,
                            mensaje,
                            activeChat.key
                          )
                        }
                        disabled={isLoading}
                        className="send-button"
                      >
                        Enviar <Send />
                      </Button>
                    </Form>
                  )}
                </Col>
              </Row>
            </Card.Body>
          </Card>
        </Container>
      </section>
    </div>
  );
};

export default MainMenu;
