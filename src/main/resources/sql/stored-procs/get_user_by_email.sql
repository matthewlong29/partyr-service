USE `partyrdb`;
DROP procedure IF EXISTS `get_user_by_email`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_user_by_email` (
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
    `partyr_users`.`theme_id`,
    `partyr_users`.`age`,
    `partyr_users`.`country`
  FROM `partyrdb`.`partyr_users` WHERE email = i_email;
END$$

DELIMITER ;
