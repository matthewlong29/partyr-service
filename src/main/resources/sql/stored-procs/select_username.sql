USE `partyrdb`;
DROP procedure IF EXISTS `select_username`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `select_username`(
  IN i_email VARCHAR(64),
  IN i_user_name VARCHAR(32)
)
BEGIN
  update `partyrdb`.`partyr_users` 
  set `user_name` = i_user_name where `email` = i_email;
END$$

DELIMITER ;

