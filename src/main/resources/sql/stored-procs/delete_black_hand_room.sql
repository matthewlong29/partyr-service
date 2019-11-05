USE `partyrdb`;
DROP procedure IF EXISTS `delete_black_hand_room`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `delete_black_hand_room` (
  IN i_room_name VARCHAR(32)
)
BEGIN
  DELETE FROM `partyrdb`.`black_hand_rooms` WHERE `game_room_name` = i_room_name;
  DELETE FROM `partyrdb`.`lobby` WHERE `game_room_name` = i_room_name;
END$$

DELIMITER ;

