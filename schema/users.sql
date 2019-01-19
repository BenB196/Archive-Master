/**
File used to create the users for Archive Master.
NOTE: This is just a temporary thing, and the username/password should be configurable during the install/setup
NOTE: the '%' allows for connections from anywhere, this should be configurable during the install/setup, and it should default to 'localhost' for simple setups.
 */
CREATE USER 'archive_manager'@'%' IDENTIFIED BY 'superStrongPassword';
GRANT ALL PRIVILEGES ON archive_master.* TO 'archive_manager'@'%';