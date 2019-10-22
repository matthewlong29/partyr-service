USE `partyrdb`;
DROP procedure IF EXISTS `get_game`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_game` (
  IN i_game_name VARCHAR(255)
)
BEGIN
  SELECT 
    `games`.`game_id`,
    `games`.`game_name`,
    `games`.`min_players_num`,
    `games`.`max_players_num`,
    `games`.`min_age`,
    `games`.`average_game_duration`
  FROM games where game_name = i_game_name;
END$$

DELIMITER ;

