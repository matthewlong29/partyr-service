USE `partyrdb`;
DROP procedure IF EXISTS `create_black_hand_lobby`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `create_black_hand_lobby` (
  IN i_lobby_name VARCHAR(32),
  IN i_player_email VARCHAR(32)
)
BEGIN
  INSERT INTO `partyrdb`.`black_hand`(
    `game_instance_name`,
    `host_email`,
	  `number_of_players`
  ) VALUES(
    i_lobby_name,
    i_player_email,
    1
  );
  INSERT INTO `partyrdb`.`black_hand_instances` (
    `game_instance_name`,
    `player_email`
  ) VALUES (
    i_lobby_name,
    i_player_email
  );
END$$

DELIMITER ;

