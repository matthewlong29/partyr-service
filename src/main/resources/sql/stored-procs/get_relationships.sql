USE `partyrdb`;
DROP procedure IF EXISTS `get_users_online`;

DELIMITER $$
USE `partyrdb`$$

CREATE PROCEDURE `get_relationships` (
	IN i_relationship_type VARCHAR(32),
  IN i_username VARCHAR(64)    
)
BEGIN
	SELECT * FROM partyr_users 
	LEFT JOIN
		relationships ON (
			relationships.related_username = partyr_users.username
		) WHERE relating_username = i_username AND relationship_type = i_relationship_type ORDER BY username;
END$$

DELIMITER ;