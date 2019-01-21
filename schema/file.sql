/*
File used to create the file table for Archive Master.
 */
CREATE TABLE file (
  id INT(4) AUTO_INCREMENT PRIMARY KEY, #Max value 2.14 billion (32-bit int), Auto-gen, PK.
  fileName VARCHAR(250) NOT NULL UNIQUE, #250 characters Long, Required, Must be unique.
  file LONGBLOB NOT NULL #4GB file size max, Required.
);