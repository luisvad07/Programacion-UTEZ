import React from 'react'
import { Col, Container, Row } from 'react-bootstrap'
export const Error = () => {

  return (
    <Container className='pageContainer'>
      <br />
      <Row >
        <Col className='col-lg-12'>
          <div className="col-lg-10 col-sm-offset-1 text-center">
            <div className="four_zero_four_bg">
              <h1 className="text-center">404</h1>
            </div>
            <div className="content_box_404">
              <h3 className="h2">Looks Like You're Lost</h3>
              <p>The page you are looking for not available</p>
            </div>
          </div>
        </Col>
      </Row>
    </Container>

  )
}
