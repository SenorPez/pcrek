CREATE TABLE IF NOT EXISTS telemetry_session (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(50),
    created DATETIME DEFAULT NOW()
);

INSERT INTO telemetry_session(id, name)
    VALUES ('ebde23de-db53-45b5-9eec-a4264e5e53f4', 'Telemetry Session - 2073')