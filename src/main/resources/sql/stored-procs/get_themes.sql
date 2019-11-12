USE `partyrdb`;
DROP procedure IF EXISTS `get_themes`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_themes` ()
BEGIN
  SELECT `theme_name` FROM `partyrdb`.`themes`;
END$$

DELIMITER ;
