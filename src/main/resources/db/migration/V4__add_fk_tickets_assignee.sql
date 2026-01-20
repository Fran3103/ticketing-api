ALTER TABLE tickets
    ADD CONSTRAINT fk_tickets_assigned_to
        FOREIGN KEY (asigned_to)
            REFERENCES users(id);
