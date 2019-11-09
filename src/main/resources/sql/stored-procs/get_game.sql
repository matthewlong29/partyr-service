USE `partyrdb`;
DROP procedure IF EXISTS `get_game`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_game` (
  IN i_game_name VARCHAR(255)
)
BEGIN
  SELECT * FROM games where game_name = i_game_name;
END$$

DELIMITER ;

