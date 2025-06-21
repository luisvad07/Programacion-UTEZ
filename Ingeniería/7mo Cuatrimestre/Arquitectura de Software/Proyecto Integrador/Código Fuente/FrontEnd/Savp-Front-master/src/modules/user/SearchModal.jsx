import React, { useState } from 'react';
import { Modal, Button, Card, Row, Col } from 'react-bootstrap';

const SearchModal = ({ isOpen, onClose, products, onProductSelect }) => {
  const [searchTerm, setSearchTerm] = useState('');

  const filteredProducts = products.filter((product) =>
    product.titulo.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <Modal show={isOpen} onHide={onClose} size='xl'>
      <Modal.Header closeButton>
        <Modal.Title>Buscar Productos</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div>
          <input
            type="text"
            placeholder="Buscar producto por título"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <Row className="card-container">
          {filteredProducts.length > 0 ? (
            filteredProducts.map((product, index) => (
              <Col key={index} xs={12} md={4} lg={3}>
                <Card className="product-card" onClick={() => onProductSelect(product)}>
                  <Card.Img variant="top" src={product.imageUrl} alt={`Imagen de ${product.titulo}`} style={{ height: "150px", objectFit: "cover" }} />
                  <Card.Body>
                    <Card.Title>{product.titulo}</Card.Title>
                    <Card.Text>{product.descripcion}</Card.Text>
                  </Card.Body>
                </Card>
              </Col>
            ))
          ) : (
            <p>No se encontraron resultados</p>
          )}
        </Row>
      </Modal.Body>
    </Modal>
  );
};

export default SearchModal;
