import React from "react";
import { Card, Container } from "react-bootstrap";
import "../../css/misc/comment.css";

export default function Comment(props) {
  const { comment, date, user } = props;

  return (
    <Container className="comment-container">
      <Card className="comment-card">
        <Card.Body>
          <div className="comment-header">
            <Card.Title className="comment-user">
              {user.split("@")[0]}
            </Card.Title>{" "}
            <div className="comment-date-container">
              <small className="text-muted comment-date">
                {new Date(date).toLocaleString()}
              </small>
            </div>
          </div>
          <Card.Text className="comment-text">{comment}</Card.Text>
        </Card.Body>
      </Card>
    </Container>
  );
}
