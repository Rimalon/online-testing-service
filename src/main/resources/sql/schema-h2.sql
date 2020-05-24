CREATE SCHEMA IF NOT EXISTS online_testing;

CREATE TABLE IF NOT EXISTS online_testing.user
(
    id       INT          NOT NULL IDENTITY (1,1),
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

INSERT INTO online_testing.question_type (id, type)
VALUES (0, 'choiceOfAnswer');
INSERT INTO online_testing.question_type (id, type)
VALUES (1, 'freeEntry');

CREATE TABLE IF NOT EXISTS online_testing.test
(
    id       INT NOT NULL IDENTITY (1,1),
    authorId INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (authorId) REFERENCES online_testing.user (id)

);

CREATE TABLE IF NOT EXISTS online_testing.question
(
    id            INT          NOT NULL IDENTITY (1,1),
    testId        INT          NOT NULL,
    type          INT          NOT NULL,
    title         VARCHAR(255) NOT NULL,
    authorId      INT          NOT NULL,
    correctAnswer VARCHAR(255) NOT NULL,
    secondOption  VARCHAR(255),
    thirdOption   VARCHAR(255),
    fourthOption  VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (authorId) REFERENCES online_testing.user (id),
    FOREIGN KEY (type) REFERENCES online_testing.question_type (id),
    FOREIGN KEY (testId) REFERENCES online_testing.test (id),
    CONSTRAINT check_not_null_options_for_choiceOfAnswer_question
        CHECK ((question.type != 0) OR (secondOption IS NOT NULL AND thirdOption IS NOT NULL AND fourthOption IS NOT NULL)),
    CONSTRAINT check_null_options_for_freeEntry_question
        CHECK ((question.type != 1) OR (secondOption IS NULL AND thirdOption IS NULL AND fourthOption IS NULL))
);

CREATE TRIGGER only_five_questions_for_one_test_trigger
    AFTER INSERT
    ON online_testing.question
    FOR EACH ROW
CALL "com.rimalon.onlinetesting.database.triggers.OnlyFiveQuestionsInTestTrigger";

CREATE TABLE IF NOT EXISTS online_testing.answer
(
    id         INT     NOT NULL IDENTITY (1,1),
    userId     INT     NOT NULL,
    questionId INT     NOT NULL,
    answer     VARCHAR(255),
    isCorrect  BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES online_testing.user (id),
    FOREIGN KEY (questionId) REFERENCES online_testing.question (id),
    CONSTRAINT check_only_one_answer UNIQUE (userId, questionId),
    CONSTRAINT check_isCorrect
        CHECK (isCorrect = (SELECT question.correctAnswer = answer
                            FROM online_testing.question
                            WHERE question.id = questionId)),
    CONSTRAINT check_choiceOfAnswer_answer
        CHECK (SELECT (question.type != 0) OR answer IN (question.correctAnswer, question.secondOption, question.thirdOption, question.fourthOption)
               FROM online_testing.question
               WHERE question.id = questionId)
);
