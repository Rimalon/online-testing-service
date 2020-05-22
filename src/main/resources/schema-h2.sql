CREATE SCHEMA IF NOT EXISTS online_testing;

CREATE TABLE IF NOT EXISTS online_testing.user
(
    id       INT          NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS online_testing.question_type
(
    id   INT NOT NULL,
    type VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS online_testing.test
(
    id       INT NOT NULL AUTO_INCREMENT,
    authorId INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (authorId) REFERENCES online_testing.user (id)

);

CREATE TABLE IF NOT EXISTS online_testing.question
(
    id           INT          NOT NULL AUTO_INCREMENT,
    testId       INT          NOT NULL,
    type         INT          NOT NULL,
    title     VARCHAR(255) NOT NULL,
    authorId     INT          NOT NULL,
    correctAnswer       VARCHAR(255) NOT NULL,
    secondOption VARCHAR(255),
    thirdOption  VARCHAR(255),
    fourthOption VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (authorId) REFERENCES online_testing.user (id),
    FOREIGN KEY (type) REFERENCES online_testing.question_type (id),
    FOREIGN KEY (testId) REFERENCES online_testing.test (id)
);


CREATE TABLE IF NOT EXISTS online_testing.answer
(
    id         INT     NOT NULL AUTO_INCREMENT,
    userId     INT     NOT NULL,
    questionId INT     NOT NULL,
    answer     VARCHAR(255),
    isCorrect  BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES online_testing.user (id),
    FOREIGN KEY (questionId) REFERENCES online_testing.question (id)
);
