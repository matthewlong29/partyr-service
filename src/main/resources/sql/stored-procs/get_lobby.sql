USE `partyrdb`;
DROP procedure IF EXISTS `get_lobby`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_lobby` ()
BEGIN
  SELECT 
    `lobby`.`game_room_name`,
    `lobby`.`game_name`,
    `lobby`.`host_email`,
    GROUP_CONCAT(`black_hand_rooms`.`email`, ':', `preferred_faction`) AS 'partyr_users',
    `lobby`.`number_of_players`,
    `lobby`.`game_started`,
    `lobby`.`game_start_time`,
    `lobby`.`game_end_time`
  FROM `partyrdb`.`lobby` INNER JOIN `partyrdb`.`black_hand_rooms` ON 
	`lobby`.`game_room_name` = `black_hand_rooms`.`game_room_name` 
	GROUP BY `lobby`.`game_room_name`;
END$$

DELIMITER ;

