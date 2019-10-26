USE `partyrdb`;
DROP procedure IF EXISTS `create_relationship`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `create_relationship` (
  IN i_relating_email VARCHAR(64),
  IN i_related_email VARCHAR(64),
  IN i_relationship_type VARCHAR(16)
)
BEGIN
  insert into `partyrdb`.`relationships` (
	  `relating_email`, 
    `related_email`, 
    `relationship_type`
  ) values (
	  i_relating_email,
	  i_related_email,
	  i_relationship_type
  );
END$$

DELIMITER ;

