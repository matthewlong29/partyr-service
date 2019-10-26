USE `partyrdb`;
DROP procedure IF EXISTS `get_black_hand_required_number_of_players`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_black_hand_required_number_of_players` (
  IN i_player_total INT
)
BEGIN
  SELECT
    `black_hand_required_number_of_players`.`monster_total`,
    `black_hand_required_number_of_players`.`black_hand_total`,
    `black_hand_required_number_of_players`.`townie_total`
  FROM black_hand_required_number_of_players WHERE `black_hand_required_number_of_players`.`player_total` = i_player_total;
END$$

DELIMITER ;

