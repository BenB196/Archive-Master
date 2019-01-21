/*
File used to create the metadataName table for Archive Master.
 */
CREATE TABLE metadataName (
  id INT(4) AUTO_INCREMENT PRIMARY KEY, #Max value 2.14 billion (32-bit int), Auto-gen, PK.
  name VARCHAR(250) NOT NULL UNIQUE, #250 characters long, Required, Must be unique.
  metadataTypeId INT(4) NOT NULL #Max value 2.14 billion (32-bit int), Required, This value will be gotten from an enum and will be used to validate the metadataValue value.
);