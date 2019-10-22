USE `partyrdb`;
DROP procedure IF EXISTS `get_games`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_games` ()
BEGIN
  SELECT 
    `games`.`game_id`,
    `games`.`game_name`,
    `games`.`min_players_num`,
    `games`.`max_players_num`,
    `games`.`min_age`,
    `games`.`average_game_duration`
  FROM games;
END$$

DELIMITER ;