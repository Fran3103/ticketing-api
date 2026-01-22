ALTER TABLE ticket_comments
   ADD COLUMN updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
   ADD COLUMN author_id BIGINT NOT NULL;


ALTER  TABLE ticket_comments
    ADD CONSTRAINT fk_ticket_comments_author
        FOREIGN KEY (author_id)
        REFERENCES users(id);



CREATE INDEX IF NOT EXISTS idx_ticket_comments_ticket_id ON ticket_comments(ticket_id);
CREATE INDEX IF NOT EXISTS idx_ticket_comments_author_id ON ticket_comments(author_id);
CREATE INDEX IF NOT EXISTS idx_ticket_comments_ticket_created_at ON ticket_comments(ticket_id, created_at);


