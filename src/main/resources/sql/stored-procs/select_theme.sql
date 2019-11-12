USE `partyrdb`;
DROP procedure IF EXISTS `select_theme`; -- TODO change to update

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `select_theme`(
  IN i_username VARCHAR(32),
  IN i_theme_name VARCHAR(32)
)
BEGIN
 update `partyrdb`.`partyr_users` 
 set `theme_name` = i_theme_name where `username` = i_username;
END$$

DELIMITER ;

