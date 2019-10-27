USE `partyrdb`;
DROP procedure IF EXISTS `join_black_hand_lobby`;

DELIMITER $$
USE `partyrdb`$$
CREATE DEFINER=`root`@`%` PROCEDURE `join_black_hand_lobby`(
  IN i_lobby_name VARCHAR(32),
  IN i_player_email VARCHAR(32)
)
BEGIN
  INSERT INTO `partyrdb`.`black_hand_instances` (
    `game_instance_name`,
    `player_email`
  ) VALUES (
    i_lobby_name,
    i_player_email
  );
  UPDATE `partyrdb`.`black_hand` SET `number_of_players` = `number_of_players` + 1 WHERE
    `game_instance_name` = i_lobby_name;
END$$

DELIMITER ;

