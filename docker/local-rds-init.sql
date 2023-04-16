-- CREATE DATABASE chaterview DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
--
-- USE chaterview;

CREATE TABLE IF NOT EXISTS chaterview.tbl_prompt(
    prompt_id    INT(11)  unsigned auto_increment COMMENT '일련번호',
    prompt_Type  ENUM('INTERVIEW_ANSWER') NOT NULL COMMENT '타입',
    command VARCHAR(2000) NOT NULL COMMENT '명령어',
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    created_by  VARCHAR(100) NOT NULL DEFAULT 'SYSTEM' COMMENT '등록자',
    last_modified_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시',
    last_modified_by  VARCHAR(100) NOT NULL DEFAULT 'SYSTEM' COMMENT '수정자',
    PRIMARY KEY (prompt_id),
    UNIQUE (prompt_Type)
);

CREATE TABLE IF NOT EXISTS chaterview.tbl_job(
    job_id    INT(11)  unsigned auto_increment COMMENT '일련번호',
    job_type  ENUM('BACKEND_ENGINEER', 'FRONTEND_ENGINEER', 'CLOUD_ENGINEER') NOT NULL COMMENT '타입',
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    created_by  VARCHAR(100) NOT NULL DEFAULT 'SYSTEM' COMMENT '등록자',
    last_modified_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시',
    last_modified_by  VARCHAR(100) NOT NULL DEFAULT 'SYSTEM' COMMENT '수정자',
    PRIMARY KEY (job_id),
    UNIQUE (job_type)
);

CREATE TABLE IF NOT EXISTS chaterview.tbl_subject(
    subject_id    INT(11)  unsigned auto_increment COMMENT '일련번호',
    subject_type  ENUM('SPRING', 'JAVA', 'NETWORK', 'OS') NOT NULL COMMENT '타입',
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    created_by  VARCHAR(100) NOT NULL DEFAULT 'SYSTEM' COMMENT '등록자',
    last_modified_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시',
    last_modified_by  VARCHAR(100) NOT NULL DEFAULT 'SYSTEM' COMMENT '수정자',
    PRIMARY KEY (subject_id),
    UNIQUE (subject_type)
);

CREATE TABLE IF NOT EXISTS chaterview.tbl_member(
    member_id    INT(11)  unsigned auto_increment COMMENT '일련번호',
    member_name        VARCHAR(255) NOT NULL COMMENT '이름',
    job_id      INT(11) NOT NULL COMMENT '직업 일련변호',
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    created_by  VARCHAR(100) NOT NULL DEFAULT 'SYSTEM' COMMENT '등록자',
    last_modified_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시',
    last_modified_by  VARCHAR(100) NOT NULL DEFAULT 'SYSTEM' COMMENT '수정자',
    PRIMARY KEY (member_id)
);

CREATE TABLE IF NOT EXISTS chaterview.tbl_quiz(
    quiz_id    INT(11)  unsigned auto_increment COMMENT '일련번호',
    question    TEXT NOT NULL COMMENT '질문',
    quiz_level ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT') NOT NULL COMMENT '퀴즈 레벨',
    active     TINYINT(1) NOT NULL COMMENT '활성화 여부',
    job_id      INT(11) NOT NULL COMMENT '직업 일련변호',
    subject_id  INT(11) NOT NULL COMMENT '주제 일련변호',
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    created_by  VARCHAR(100) NOT NULL DEFAULT 'SYSTEM' COMMENT '등록자',
    last_modified_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시',
    last_modified_by  VARCHAR(100) NOT NULL DEFAULT 'SYSTEM' COMMENT '수정자',
    PRIMARY KEY (quiz_id)
);

CREATE TABLE IF NOT EXISTS chaterview.tbl_member_quiz_answer(
    member_quiz_answer_id    INT(11)  unsigned auto_increment COMMENT '일련번호',
    correct     TINYINT(1) NOT NULL COMMENT '정답 여부',
    answer    TEXT NOT NULL COMMENT '답변',
    feedback    TEXT NOT NULL COMMENT '피드백',
    member_id  INT(11) NOT NULL COMMENT '회원 일련변호',
    quiz_id  INT(11) NOT NULL COMMENT '퀴즈 일련변호',
    created_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '등록일시',
    created_by  VARCHAR(100) NOT NULL DEFAULT 'SYSTEM' COMMENT '등록자',
    last_modified_at  DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수정일시',
    last_modified_by  VARCHAR(100) NOT NULL DEFAULT 'SYSTEM' COMMENT '수정자',
    PRIMARY KEY (member_quiz_answer_id)
);