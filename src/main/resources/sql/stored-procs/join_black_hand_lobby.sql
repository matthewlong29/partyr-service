USE `partyrdb`;
DROP procedure IF EXISTS `join_black_hand_lobby`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `join_black_hand_lobby` (
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
END$$

DELIMITER ;

