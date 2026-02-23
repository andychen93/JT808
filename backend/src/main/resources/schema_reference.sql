-- 车联网核心表设计（结合 JT/T 808、JT/T 1078、GB/T 32960 常见业务字段）
CREATE TABLE IF NOT EXISTS vehicle_terminal (
    id BIGSERIAL PRIMARY KEY,
    sim_no VARCHAR(20) UNIQUE NOT NULL,
    plate_no VARCHAR(20),
    device_model VARCHAR(64),
    manufacturer_id VARCHAR(16),
    protocol_type VARCHAR(16),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS vehicle_location (
    id BIGSERIAL PRIMARY KEY,
    sim_no VARCHAR(20) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    speed_kmh INT,
    direction_deg INT,
    gps_time TIMESTAMPTZ,
    alarm_flags VARCHAR(64),
    status_flags VARCHAR(64),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS raw_message (
    id BIGSERIAL PRIMARY KEY,
    protocol VARCHAR(16) NOT NULL,
    message_id VARCHAR(16),
    sim_no VARCHAR(20),
    hex_payload TEXT NOT NULL,
    json_payload TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

-- RBAC
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(64) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);
