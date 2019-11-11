USE `partyrdb`;
DROP procedure IF EXISTS `toggle_ready_status`;

DELIMITER $$
USE `partyrdb`$$
CREATE DEFINER=`root`@`%` PROCEDURE `toggle_ready_status`(
  IN i_room_name VARCHAR(32),
  IN i_email VARCHAR(32)
)
BEGIN
  UPDATE `partyrdb`.`black_hand_rooms`
	  SET `ready_status` = IF(`ready_status` = 'READY', 'NOT_READY', 'READY')
  WHERE `game_room_name` = i_room_name AND `email` = i_email;
END$$

DELIMITER ;

