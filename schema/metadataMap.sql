/*
File used to create the metadataMap table for Archive Master.
 */
CREATE TABLE metadataMap(
  id INT(4) AUTO_INCREMENT PRIMARY KEY, #Max value 2.14 billion (32-bit int), Auto-gen, PK.
  collectionItemId INT(4) NOT NULL, #Max value 2.14 billion (32-bit int), Required, This value will either be a collectionId or an itemId.
  collectionItemIdType INT(4) NOT NULL, #Max value 2.14 billion (32-bit int), Required, This value will be gotten from an enum, and will specify whether collectionItemId is either a collectionId or an itemId.
  metadataNameInheritance TINYINT(1) NOT NULL, #Same thing as Boolean 0/1, specifies whether the linking metadataName will be inherited by all sub collections/items, 1 means YES inherit/0 means no don't inherit.
  metadataNameId INT(4) NOT NULL, #Max value 2.14 billion (32-bit int), Required.
  metadataValueInheritance TINYINT(1) NOT NULL, #Same thing as Boolean 0/1, specifies whether the linking metadataValue will be inherited by all sub collections/items, 1 mean YES inherit/0 means no don't inherit.
  metadataValueId INT(4) NULL #Max value 2.14 billion (32-bit int), Required only if the map is linking directly to the item, or if metadataValueInheritance is equal to 1.
);