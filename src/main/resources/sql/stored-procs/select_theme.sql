USE `partyrdb`;
DROP procedure IF EXISTS `select_theme`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `select_theme`(
  IN i_email VARCHAR(32),
  IN i_theme_name VARCHAR(32)
)
BEGIN
 update `partyrdb`.`partyr_users` 
 set `theme_name` = i_theme_name where `email` = i_email;
END$$

DELIMITER ;

