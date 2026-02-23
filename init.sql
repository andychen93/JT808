-- 根目录初始化 SQL（PostgreSQL 18）
-- 用法：psql -U postgres -f init.sql

CREATE DATABASE jt808;
\connect jt808;

-- Flyway 会自动建表/初始化业务数据。
-- 这里预留扩展与基础权限。
CREATE EXTENSION IF NOT EXISTS pgcrypto;

DO $$
BEGIN
    RAISE NOTICE 'Database jt808 is ready. Start backend and Flyway migrations will run.';
END $$;
