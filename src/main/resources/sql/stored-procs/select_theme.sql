USE `partyrdb`;
DROP procedure IF EXISTS `select_theme`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `select_theme` (
	IN i_theme_id int,
    IN i_email VARCHAR(64)
)
BEGIN
 update `partyrdb`.`partyr_users` 
 set `theme_id` = i_theme_id where `email` = i_email;
END$$

DELIMITER ;

