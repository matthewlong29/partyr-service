USE `partyrdb`;
DROP procedure IF EXISTS `create_relationship`; -- TODO change to insert

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `create_relationship` (
  IN i_relating_username VARCHAR(64),
  IN i_related_username VARCHAR(64),
  IN i_relationship_type VARCHAR(16)
)
BEGIN
  insert into `partyrdb`.`relationships` (
	  `relating_username`, 
    `related_username`, 
    `relationship_type`
  ) values (
	  i_relating_username,
	  i_related_username,
	  i_relationship_type
  );
END$$

DELIMITER ;

