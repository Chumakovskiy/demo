INSERT INTO TEST_TASK.COUNTRY (ID, COUNTRY_CODE) VALUES (1, 'ua'),
  (2, 'ru'),
  (3, 'us'),
  (4, 'lt'),
  (5, 'lv');

INSERT INTO TEST_TASK.RESTRICTED_WORD (ID, WORD) VALUES (1, 'goblin'),
  (2, 'elf'),
  (3, 'looser'),
  (4, 'troya'),
  (5, 'virusPetya');

INSERT INTO TEST_TASK.QUESTION (ID, CONTENT, COUNTRY_ID, CREATED_TIME)
VALUES (1, 'First question?', 1, '2017-10-11 22:54:34.333000000'),
  (2, 'Second question?', 3, '2017-10-13 22:57:37.048000000'),
  (3, 'Third question?!', 5, '2017-10-15 23:58:40.324000000');