USE `partyrdb`;
DROP procedure IF EXISTS `create_room`; -- TODO change to insert

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `create_room`(
  IN i_room_name VARCHAR(32),
  IN i_username VARCHAR(32),
  IN i_game_name VARCHAR(32)
)
BEGIN
  INSERT INTO `partyrdb`.`lobby`(
    `game_room_name`,
    `game_name`,
    `host_username`,
	  `number_of_players`
  ) VALUES(
    i_room_name,
    i_game_name,
    i_username,
    1
  );
  INSERT INTO `partyrdb`.`black_hand_rooms` (
    `game_room_name`,
    `username`
  ) VALUES (
    i_room_name,
    i_username
  );
END$$

DELIMITER ;

