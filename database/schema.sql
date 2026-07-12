-- Career Assessment Platform Database Schema
-- Similar to Mindler's 5-dimensional career assessment

CREATE DATABASE IF NOT EXISTS career_assessment_db;
USE career_assessment_db;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    date_of_birth DATE,
    education_level ENUM('CLASS_8_9', 'CLASS_10_12', 'GRADUATE', 'PROFESSIONAL') NOT NULL DEFAULT 'CLASS_10_12',
    school_name VARCHAR(255),
    city VARCHAR(100),
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Assessment categories (5 dimensions like Mindler)
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    code VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    icon VARCHAR(50),
    display_order INT NOT NULL DEFAULT 0,
    time_limit_minutes INT DEFAULT 15,
    total_questions INT DEFAULT 20,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Questions for each category
CREATE TABLE IF NOT EXISTS questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NOT NULL,
    question_text TEXT NOT NULL,
    question_type ENUM('SINGLE_CHOICE', 'LIKERT_SCALE', 'SCENARIO') NOT NULL DEFAULT 'SINGLE_CHOICE',
    difficulty_level ENUM('EASY', 'MEDIUM', 'HARD') DEFAULT 'MEDIUM',
    display_order INT NOT NULL DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- Answer options for each question
CREATE TABLE IF NOT EXISTS answer_options (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_id BIGINT NOT NULL,
    option_text TEXT NOT NULL,
    option_label VARCHAR(1) NOT NULL,
    score_value INT NOT NULL DEFAULT 0,
    trait_code VARCHAR(50),
    display_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

-- Assessment sessions
CREATE TABLE IF NOT EXISTS assessment_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    session_code VARCHAR(50) NOT NULL UNIQUE,
    status ENUM('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED', 'EXPIRED') DEFAULT 'NOT_STARTED',
    started_at TIMESTAMP NULL,
    completed_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- User responses (answers selected by users)
CREATE TABLE IF NOT EXISTS user_responses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    selected_option_id BIGINT NOT NULL,
    answered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES assessment_sessions(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
    FOREIGN KEY (selected_option_id) REFERENCES answer_options(id) ON DELETE CASCADE,
    UNIQUE KEY unique_response (session_id, question_id)
);

-- Category scores per session
CREATE TABLE IF NOT EXISTS category_scores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    raw_score INT NOT NULL DEFAULT 0,
    max_score INT NOT NULL DEFAULT 0,
    percentage DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    trait_summary TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES assessment_sessions(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
    UNIQUE KEY unique_category_score (session_id, category_id)
);

-- Career recommendations
CREATE TABLE IF NOT EXISTS career_recommendations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    career_title VARCHAR(255) NOT NULL,
    career_description TEXT,
    match_percentage DECIMAL(5,2) NOT NULL,
    career_field VARCHAR(100),
    required_education TEXT,
    salary_range VARCHAR(100),
    growth_outlook VARCHAR(50),
    rank_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES assessment_sessions(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- MBTI questions (managed by admin)
CREATE TABLE IF NOT EXISTS mbti_questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    dimension VARCHAR(2) NOT NULL,
    question_text TEXT NOT NULL,
    display_order INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- MBTI answer options (two per question, each carrying a letter)
CREATE TABLE IF NOT EXISTS mbti_options (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_id BIGINT NOT NULL,
    option_label VARCHAR(1) NOT NULL,
    option_text TEXT NOT NULL,
    letter VARCHAR(1) NOT NULL,
    display_order INT NOT NULL DEFAULT 0,
    FOREIGN KEY (question_id) REFERENCES mbti_questions(id) ON DELETE CASCADE
);

-- MBTI type profiles (report content, managed by admin). List fields are newline-separated.
CREATE TABLE IF NOT EXISTS mbti_type_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type_code VARCHAR(4) NOT NULL UNIQUE,
    nickname VARCHAR(100) NOT NULL,
    summary TEXT,
    overview TEXT,
    strengths TEXT,
    weaknesses TEXT,
    careers TEXT,
    relationships TEXT,
    growth_tips TEXT
);

-- Persisted MBTI results (part of an assessment session). dimensions stored as JSON.
CREATE TABLE IF NOT EXISTS mbti_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    session_id BIGINT,
    type_code VARCHAR(4) NOT NULL,
    nickname VARCHAR(100),
    dimensions_json TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (session_id) REFERENCES assessment_sessions(id) ON DELETE CASCADE
);
