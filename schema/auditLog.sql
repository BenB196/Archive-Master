/*
File used to create the auditLog table for Archive Master.
 */
CREATE TABLE auditLog (
  id BIGINT(8) AUTO_INCREMENT PRIMARY KEY, #Max value 2^63-1 (64-bit int), Auto-gen, PK.
  userId INT(4) NOT NULL, #Max value 2.14 billion (32-bit int), Required.
  action VARCHAR(250) NOT NULL, #250 characters long, Required.
  timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP #Timestamp YYYY-MM-DD HH:MM:SS, Required, Automatically adds the timestamp when row inserted.
);