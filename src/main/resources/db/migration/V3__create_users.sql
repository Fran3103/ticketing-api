CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),



    CONSTRAINT chk_users_role
        CHECK ( role IN ('AGENT', 'CUSTOMER', 'ADMIN') )

);


CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);