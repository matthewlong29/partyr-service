USE `partyrdb`;
DROP procedure IF EXISTS `get_online_friends`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_online_friends` (
  IN i_email VARCHAR(64)   
)
BEGIN
	SELECT
	  `partyr_users`.`user_id`,
	  `partyr_users`.`user_hash`,
	  `partyr_users`.`user_name`,
	  `partyr_users`.`first_name`,
	  `partyr_users`.`last_name`,
	  `partyr_users`.`email`,
	  `partyr_users`.`profile_image_url`,
	  `partyr_users`.`joined_date`,
	  `partyr_users`.`online_status`,
	  `partyr_users`.`ready_to_play_status`,
	  `partyr_users`.`theme_id`,
	  `partyr_users`.`age`,
	  `partyr_users`.`country`
	FROM partyr_users 
	LEFT JOIN
	  relationships ON (
	    relationships.related_email = partyr_users.email
	  ) WHERE relating_email = i_email 
	      AND relationship_type = 'FRIEND' 
        AND online_status = 'ONLINE'
        ORDER BY first_name;
END$$

DELIMITER ;
