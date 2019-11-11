USE `partyrdb`;
DROP procedure IF EXISTS `get_users`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_users` (
  IN i_query_string VARCHAR(255)
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
    `partyr_users`.`theme_id`,
    `partyr_users`.`age`,
    `partyr_users`.`country`
  FROM `partyrdb`.`partyr_users` WHERE
    user_name LIKE concat('%', i_query_string, '%') or 
    first_name LIKE concat('%', i_query_string, '%') or 
    last_name LIKE concat('%', i_query_string, '%') or 
    email LIKE concat('%', i_query_string, '%')
  ORDER BY online_status desc, first_name;
END$$

DELIMITER ;