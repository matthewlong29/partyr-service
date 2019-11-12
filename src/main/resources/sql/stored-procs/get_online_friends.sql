USE `partyrdb`;
DROP procedure IF EXISTS `get_online_friends`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_online_friends` (
  IN i_username VARCHAR(64)   
)
BEGIN
  SELECT * FROM partyr_users 
	LEFT JOIN
	  relationships ON (
	    relationships.related_username = partyr_users.username
	  ) WHERE relating_username = i_username 
	    AND relationship_type = 'FRIEND'
      AND online_status = 'ONLINE'
    ORDER BY username;
END$$

DELIMITER ;

