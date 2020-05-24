INSERT INTO online_testing.user (id, username, password)
VALUES (1, 'ilya1', '$2a$10$bnxQNavPT8cjZh5NFQJlIusyBuofSXdtWMGRIqOyD3IClb2RFcrjq'); -- this password is qwerty
INSERT INTO online_testing.user (id, username, password)
VALUES (2, 'ilya2', '$2a$10$bnxQNavPT8cjZh5NFQJlIusyBuofSXdtWMGRIqOyD3IClb2RFcrjq'); -- this password is qwerty
INSERT INTO online_testing.user (id, username, password)
VALUES (3, 'ilya3', '$2a$10$bnxQNavPT8cjZh5NFQJlIusyBuofSXdtWMGRIqOyD3IClb2RFcrjq'); -- this password is qwerty
INSERT INTO online_testing.user (id, username, password)
VALUES (4, 'ilya4', '$2a$10$bnxQNavPT8cjZh5NFQJlIusyBuofSXdtWMGRIqOyD3IClb2RFcrjq'); -- this password is qwerty

INSERT INTO online_testing.test (id, authorId)
VALUES (1, 1);
INSERT INTO online_testing.test (id, authorId)
VALUES (2, 1);

INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer, secondOption, thirdOption, fourthOption)
VALUES (1, 1, 0, '1?', 1, '1first', '1second', '1third', '1fourth');
INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer, secondOption, thirdOption, fourthOption)
VALUES (2, 1, 0, '2?', 1, '2first', '2second', '2third', '2fourth');
INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer, secondOption, thirdOption, fourthOption)
VALUES (3, 1, 1, '3?', 1, '3first', '3second', '3third', '3fourth');
INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer)
VALUES (4, 1, 1, '4?', 1, '4first');
INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer)
VALUES (5, 1, 1, '5?', 1, '5first');

INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer, secondOption, thirdOption, fourthOption)
VALUES (6, 2, 1, '6?', 1, '6first', '6second', '6third', '6fourth');
INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer, secondOption, thirdOption, fourthOption)
VALUES (7, 2, 1, '6?', 1, '6first', '6second', '6third', '6fourth');

INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (1, 1, 1, '1first', true);
INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (2, 1, 2, '2first', true);
INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (3, 1, 3, '3first', true);
INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (4, 1, 4, '4first', true);
INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (5, 1, 5, '5first', true);

INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (6, 2, 1, '1first', true);
INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (7, 2, 2, '2second', false);
INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (8, 2, 3, '2third', false);


INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (9, 3, 1, '1first', true);
INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (10, 3, 2, '2second', false);
INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (11, 3, 3, '3second', false);
INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (12, 3, 4, 'Place for Easter egg', false);
INSERT INTO online_testing.answer (id, userId, questionId, answer, isCorrect)
VALUES (13, 3, 5, '5first', true);