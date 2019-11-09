USE `partyrdb`;
DROP procedure IF EXISTS `get_user_by_email`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_user_by_email` (
	IN i_email VARCHAR(64)
)
BEGIN
  SELECT * FROM `partyrdb`.`partyr_users` WHERE email = i_email;
END$$

DELIMITER ;
