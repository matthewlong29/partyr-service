USE `partyrdb`;
DROP procedure IF EXISTS `join_black_hand_room`;

DELIMITER $$
USE `partyrdb`$$
CREATE DEFINER=`root`@`%` PROCEDURE `join_black_hand_room`(
  IN i_room_name VARCHAR(32),
  IN i_username VARCHAR(32)
)
BEGIN
  DECLARE numOfPlayers int;
   
  INSERT INTO `partyrdb`.`black_hand_rooms` (
    `game_room_name`,
    `username`
  ) VALUES (
    i_room_name,
    i_username
  );
  
  SELECT count(*) INTO numOfPlayers FROM `partyrdb`.`black_hand_rooms` WHERE `game_room_name` = i_room_name;
  UPDATE `partyrdb`.`lobby` SET `number_of_players` = numOfPlayers WHERE `game_room_name` = i_room_name;
END$$

DELIMITER ;

