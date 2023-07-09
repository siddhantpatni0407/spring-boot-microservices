CREATE SCHEMA IF NOT EXISTS ${schema};

CREATE TABLE IF NOT EXISTS ${schema}.email_transactions (
  id SERIAL PRIMARY KEY,
  correlation_id VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  to_address TEXT NOT NULL,
  status VARCHAR(255),
  failure_description VARCHAR(255)
);