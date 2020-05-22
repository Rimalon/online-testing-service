INSERT INTO online_testing.question_type (id, type) VALUES (0, 'choiceOfAnswer');
INSERT INTO online_testing.question_type (id, type) VALUES (1, 'freeEntry');

INSERT INTO online_testing.user (id, username, password) VALUES (1,	'ilya',	'$2a$10$bnxQNavPT8cjZh5NFQJlIusyBuofSXdtWMGRIqOyD3IClb2RFcrjq');

INSERT INTO online_testing.test (id, authorId) VALUES (1, 1);
INSERT INTO online_testing.test (id, authorId) VALUES (2, 1);

INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer, secondOption, thirdOption, fourthOption) VALUES (1, 1, 0, '1?', 1, '1first', '1second','1third', '1fourth');
INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer, secondOption, thirdOption, fourthOption) VALUES (2, 1, 0, '2?', 1, '2first', '2second','2third', '2fourth');
INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer, secondOption, thirdOption, fourthOption) VALUES (3, 1, 1, '3?', 1, '3first', '3second','3third', '3fourth');
INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer) VALUES (4, 1, 1, '4?', 1, '4questAnsw');
INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer) VALUES (5, 1, 1, '5?', 1, '5questAnsw');

INSERT INTO online_testing.question (id, testId, type, title, authorId, correctAnswer, secondOption, thirdOption, fourthOption) VALUES (6, 2, 1, '6?', 1, '6first', '6second','6third', '6fourth');