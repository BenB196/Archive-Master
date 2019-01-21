/*
File used to create the itemFileMap table for Archive Master.
 */
CREATE TABLE itemFileMap (
  id INT(4) AUTO_INCREMENT PRIMARY KEY, #Max value 2.14 billion (32-bit int), Auto-gen, PK.
  itemId INT(4) NOT NULL, #Max value 2.14 billion (32-bit int), Required.
  fileId INT(4) NOT NULL #Max value 2.14 billion (32-bit int), Required.
);