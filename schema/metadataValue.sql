/*
File used to create the metadataValue table for Archive Master.
 */
CREATE TABLE metadataValue (
  id INT(4) AUTO_INCREMENT PRIMARY KEY, #Max value 2.14 billion (32-bit int), Auto-gen, PK.
  value TEXT NOT NULL #65535 characters long, Not required, Must be unique.
);