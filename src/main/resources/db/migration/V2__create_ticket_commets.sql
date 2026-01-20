CREATE TABLE ticket_comments(
    id BIGSERIAL PRIMARY KEY,
    ticket_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_ticket_comments_ticket
        FOREIGN KEY(ticket_id)
        REFERENCES tickets(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_ticket_comments_ticket_id ON ticket_comments(ticket_id);