CREATE TABLE IF NOT EXISTS telemetry_session (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(50),
    created DATETIME
);