/*
File used to create the user table for Archive Master.
 */
CREATE TABLE user (
  id INT(4) AUTO_INCREMENT PRIMARY KEY, #Max value 2.14 billion (32-bit int), Auto-gen, PK.
  firstName VARCHAR(250) NULL, #250 characters long, Not required.
  lastName VARCHAR(250) NULL, #250 characters long, Not required.
  userName VARCHAR(250) NOT NULL UNIQUE, #250 characters long, Required, Must be unique.
  userPermissionId INT(4) NOT NULL, #Max value 2.14 billion (32-bit int), Required, This value will be gotten from an enum and will determine what permissions that user has.
  password VARCHAR(128) NOT NULL #128 characters long, Required, This is the length of a SHA512 hash function and this is how we should 'store' passwords.
);