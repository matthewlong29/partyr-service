USE `partyrdb`;
DROP procedure IF EXISTS `get_lobby`;

DELIMITER $$
USE `partyrdb`$$
CREATE PROCEDURE `get_lobby` ()
BEGIN
  SELECT 
    T1.`game_room_name`,
    T1.`game_name`,
    T1.`host_email`,
    T2.`players_ready`,
    T3.`players_not_ready`,
    T1.`number_of_players`,
    T1.`game_started`,
    T1.`game_start_time`,
    T1.`game_end_time`
  FROM
    (select * from `lobby`) T1
    LEFT OUTER JOIN (
    SELECT 
      `black_hand_rooms`.`game_room_name`, 
      GROUP_CONCAT(`black_hand_rooms`.`email`) AS 'players_ready' 
      from `black_hand_rooms` 
        where `black_hand_rooms`.`ready_status` = 'READY'
      group by `black_hand_rooms`.`game_room_name`
    ) T2 ON T1.`game_room_name` = T2.`game_room_name`
    LEFT OUTER JOIN (
    SELECT 
      `black_hand_rooms`.`game_room_name`, 
      GROUP_CONCAT(`black_hand_rooms`.`email`) AS 'players_not_ready'
      from `black_hand_rooms` 
        where `black_hand_rooms`.`ready_status` = 'NOT_READY' 
      group by `black_hand_rooms`.`game_room_name`
    ) T3 ON T1.`game_room_name` = T3.`game_room_name`
  ORDER BY `game_name`, `game_room_name`;
END$$

DELIMITER ;

