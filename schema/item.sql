/*
File used to create the item table for Archive Master.
 */
CREATE TABLE item (
  id INT(4) AUTO_INCREMENT PRIMARY KEY, #Max value 2.14 billion (32-bit int), Auto-gen, PK.
  collectionId INT(4) NOT NULL, #Max value 2.14 billion (32-bit int), Required.
  name VARCHAR(250) NOT NULL UNIQUE, #250 characters long, Required, Must be unique.
  description TEXT NULL, #65535 characters long, Not required, Must be unique.
  parentItemId INT(4) NULL #Max value 2.14 billion (32-bit int), Not required.
);