CREATE TABLE  refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token_hash VARCHAR(255) UNIQUE NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    replaced_by BIGINT NULL,
    user_agent VARCHAR(255) NULL,
    ip VARCHAR(50)  NULL,

    CONSTRAINT fk_refresh_tokens_user
            FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE ,

    CONSTRAINT fk_refresh_tokens_replaced_by
            FOREIGN KEY (replaced_by)
            REFERENCES refresh_tokens(id)
            ON DELETE SET NULL
);


CREATE INDEX  IF NOT EXISTS   idx_refresh_tokens_user_id ON refresh_tokens(user_id);
CREATE INDEX IF NOT EXISTS    idx_refresh_tokens_token_hash ON refresh_tokens(token_hash);
CREATE INDEX IF NOT EXISTS idx_refresh_tokens_user_revoked ON refresh_tokens(user_id, revoked);