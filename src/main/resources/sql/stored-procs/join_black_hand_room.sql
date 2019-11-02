USE `partyrdb`;
DROP procedure IF EXISTS `join_black_hand_room`;

DELIMITER $$
USE `partyrdb`$$
CREATE DEFINER=`root`@`%` PROCEDURE `join_black_hand_room`(
  IN i_room_name VARCHAR(32),
  IN i_email VARCHAR(32)
)
BEGIN
  INSERT INTO `partyrdb`.`black_hand_rooms` (
    `game_room_name`,
    `email`
  ) VALUES (
    i_room_name,
    i_email
  );
  UPDATE `partyrdb`.`lobby` SET `number_of_players` = `number_of_players` + 1 WHERE
    `game_room_name` = i_room_name;
END$$

DELIMITER ;

