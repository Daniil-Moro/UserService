CREATE TABLE IF NOT EXISTS users (
    password VARCHAR(120) NOT NULL,
    username VARCHAR(45) NOT NULL,
    first_name VARCHAR(45) NOT NULL,
    last_name VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    role_id INT NOT NULL default(2),

    CONSTRAINT constraint_name UNIQUE ( username )
);