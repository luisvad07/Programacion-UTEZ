import React from "react"
import { Col, Container, Row } from "react-bootstrap";

export const Loading = () => {
    return(
        <Container>
            <Row className="row position-absolute top-50 start-50 translate-middle">
                <Col className="col-4">
                    <div className="spinner-grow" role="status" style={{backgroundColor:"#3D9982"}}>
                        <span className="visually-hidden"></span>
                    </div>
                </Col>
                <Col className="col-4">
                    <div className="spinner-grow" role="status" style={{backgroundColor:"#ccc"}}>
                        <span className="visually-hidden"></span>
                    </div>
                </Col>
                <Col className="col-4">
                    <div className="spinner-grow" role="status" style={{backgroundColor:"#375689"}}>
                        <span className="visually-hidden"></span>
                    </div>
                </Col>
            </Row>
        </Container>
    )
}
export default Loading