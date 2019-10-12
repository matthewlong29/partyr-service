USE `partyrdb`;
DROP procedure IF EXISTS `get_users_ready_to_play`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_users_ready_to_play`(IN queryString VARCHAR(255))
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
    FROM partyr_users WHERE 
      ready_to_play_status = 'READY' AND (
        user_name LIKE concat('%', queryString, '%') or 
        first_name LIKE concat('%', queryString, '%') or 
        last_name LIKE concat('%', queryString, '%') or 
        email LIKE concat('%', queryString, '%')
      )
    ORDER BY first_name;
END$$

DELIMITER ;
