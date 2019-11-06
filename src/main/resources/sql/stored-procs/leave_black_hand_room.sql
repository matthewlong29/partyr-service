USE `partyrdb`;
DROP procedure IF EXISTS `leave_black_hand_room`;

DELIMITER $$
USE `partyrdb`$$
CREATE DEFINER=`root`@`%` PROCEDURE `leave_black_hand_room`(
  IN i_room_name VARCHAR(32),
  IN i_email VARCHAR(32)
)
BEGIN
  DECLARE hostEmail VARCHAR(32);
  DECLARE newHostEmail VARCHAR(32);
  DECLARE numOfPlayers int;

  DELETE FROM `partyrdb`.`black_hand_rooms` WHERE 
	`game_room_name` = i_room_name AND `email` = i_email;
    
  SELECT `host_email` INTO hostEmail FROM `partyrdb`.`lobby` WHERE `game_room_name` = i_room_name;
  SELECT `number_of_players` INTO numOfPlayers FROM `partyrdb`.`lobby` WHERE `game_room_name` = i_room_name;
  
  IF numOfPlayers = 1 THEN
    DELETE FROM `partyrdb`.`lobby` WHERE `game_room_name` = i_room_name;
  ELSEIF hostEmail = i_email THEN
    SELECT `email` INTO newHostEmail FROM `partyrdb`.`black_hand_rooms` WHERE `game_room_name` = i_room_name limit 1;
	  UPDATE `partyrdb`.`lobby` SET `host_email` = newHostEmail WHERE `game_room_name` = i_room_name;
  END IF;

  SELECT count(*) INTO numOfPlayers FROM `partyrdb`.`black_hand_rooms` WHERE `game_room_name` = i_room_name;
  UPDATE `partyrdb`.`lobby` SET `number_of_players` = numOfPlayers WHERE `game_room_name` = i_room_name;
END$$

DELIMITER ;