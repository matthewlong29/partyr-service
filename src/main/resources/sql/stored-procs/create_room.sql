USE `partyrdb`;
DROP procedure IF EXISTS `create_room`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `create_room`(
  IN i_room_name VARCHAR(32),
  IN i_partyr_email VARCHAR(32),
  IN i_game_name VARCHAR(32)
)
BEGIN
  INSERT INTO `partyrdb`.`lobby`(
    `game_room_name`,
    `game_name`,
    `host_email`,
	`number_of_players`
  ) VALUES(
    i_room_name,
    i_game_name,
    i_partyr_email,
    1
  );
  INSERT INTO `partyrdb`.`black_hand_rooms` (
    `game_room_name`,
    `partyr_email`
  ) VALUES (
    i_room_name,
    i_email
  );
END$$

DELIMITER ;

