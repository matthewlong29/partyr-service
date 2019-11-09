USE `partyrdb`;
DROP procedure IF EXISTS `get_black_hand_required_number_of_players`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_black_hand_required_number_of_players` (
  IN i_player_total INT
)
BEGIN
  SELECT * FROM black_hand_required_number_of_players 
    WHERE `black_hand_required_number_of_players`.`player_total` = i_player_total;
END$$

DELIMITER ;

