-- database.sql
-- -------------
-- Run this file in MySQL (Workbench, phpMyAdmin, or the mysql command line)
-- BEFORE running the Java backend for the first time.

CREATE DATABASE IF NOT EXISTS ai_design_agent;
USE ai_design_agent;

CREATE TABLE IF NOT EXISTS designs (
    id                    INT AUTO_INCREMENT PRIMARY KEY,
    user_id               VARCHAR(50)  NOT NULL,
    design_type           VARCHAR(100) NOT NULL,   -- e.g. Bedroom, Kitchen, Office
    requirements          TEXT,                    -- full requirement summary text
    dimensions            VARCHAR(50),              -- e.g. "10x12"
    design_prompt         TEXT,                    -- the final AI prompt that was used
    generated_image_path  VARCHAR(255),             -- path/URL of the generated image
    created_at            DATETIME
);

-- Example query to view all saved designs, newest first:
-- SELECT * FROM designs ORDER BY created_at DESC;
