USE `partyrdb`;

DROP PROCEDURE IF EXISTS `create_relationship`;
DROP PROCEDURE IF EXISTS `create_room`;
DROP PROCEDURE IF EXISTS `create_user`;
DROP PROCEDURE IF EXISTS `delete_black_hand_room`;
DROP PROCEDURE IF EXISTS `get_all_chat_messages`;
DROP PROCEDURE IF EXISTS `get_black_hand_game`;
DROP PROCEDURE IF EXISTS `get_black_hand_required_number_of_players`;
DROP PROCEDURE IF EXISTS `get_black_hand_roles`;
DROP PROCEDURE IF EXISTS `get_game`;
DROP PROCEDURE IF EXISTS `get_games`;
DROP PROCEDURE IF EXISTS `get_lobby`;
DROP PROCEDURE IF EXISTS `get_online_friends`;
DROP PROCEDURE IF EXISTS `get_online_users`;
DROP PROCEDURE IF EXISTS `get_relationships`;
DROP PROCEDURE IF EXISTS `get_themes`;
DROP PROCEDURE IF EXISTS `get_user_by_email`;
DROP PROCEDURE IF EXISTS `get_users`;
DROP PROCEDURE IF EXISTS `join_black_hand_room`;
DROP PROCEDURE IF EXISTS `leave_black_hand_room`;
DROP PROCEDURE IF EXISTS `save_chat_message`;
DROP PROCEDURE IF EXISTS `select_theme`;
DROP PROCEDURE IF EXISTS `select_username`;
DROP PROCEDURE IF EXISTS `toggle_ready_status`;
DROP PROCEDURE IF EXISTS `set_black_hand_preferred_faction`;
DROP PROCEDURE IF EXISTS `set_black_hand_display_name`;
DROP PROCEDURE IF EXISTS `submit_black_hand_player_turn`;

DELIMITER $$
USE `partyrdb`$$

-- ** create_relationship

CREATE PROCEDURE `create_relationship`(
  IN i_relating_username VARCHAR(64),
  IN i_related_username VARCHAR(64),
  IN i_relationship_type VARCHAR(16)
)
BEGIN
  INSERT INTO `partyrdb`.`relationships`(
	  `relating_username`, 
    `related_username`, 
    `relationship_type`
  ) VALUES (
	  i_relating_username,
	  i_related_username,
	  i_relationship_type
  );
END$$


-- ** create_room

CREATE PROCEDURE `create_room`(
  IN i_room_name VARCHAR(32),
  IN i_username VARCHAR(32),
  IN i_game_name VARCHAR(32)
)
BEGIN
  INSERT INTO `partyrdb`.`lobby`(
    `room_name`,
    `game_name`,
    `host_username`,
	  `number_of_players`
  ) VALUES (
    i_room_name,
    i_game_name,
    i_username,
    1
  );
  INSERT INTO `partyrdb`.`black_hand_games`(
    `room_name`,
    `username`
  ) VALUES (
    i_room_name,
    i_username
  );
END$$


-- ** create_user

CREATE PROCEDURE `create_user`(
	IN i_user_hash VARCHAR(254),
  IN i_email VARCHAR(64),
  IN i_first_name VARCHAR(254),
  IN i_last_name VARCHAR(254),
  IN i_profile_image_url VARCHAR(254)
)
BEGIN
	INSERT INTO `partyrdb`.`partyr_users`( 
		`user_hash`, 
    `first_name`, 
    `last_name`, 
    `email`, 
    `profile_image_url`
	) VALUES (
		i_user_hash,
    i_first_name, 
    i_last_name, 
    i_email, 
    i_profile_image_url
	);
END$$

-- ** delete_black_hand_room

CREATE PROCEDURE `delete_black_hand_room`(
  IN i_room_name VARCHAR(32)
)
BEGIN
  DELETE FROM `partyrdb`.`black_hand_games` WHERE `room_name` = i_room_name;
  DELETE FROM `partyrdb`.`lobby` WHERE `room_name` = i_room_name;
END$$

-- ** get_all_chat_messages

CREATE PROCEDURE `get_all_chat_messages`()
BEGIN
	SELECT * FROM `partyrdb`.`chat` ORDER BY `chat`.`time_of_chat_message`;
END$$

-- ** get_black_hand_game

CREATE PROCEDURE `get_black_hand_game`(
  IN i_room_name VARCHAR(32)   
)
BEGIN
  SELECT * FROM `partyrdb`.`black_hand_games` WHERE `black_hand_games`.`room_name` = i_room_name;
END$$

-- ** get_black_hand_required_number_of_players

CREATE PROCEDURE `get_black_hand_required_number_of_players`(
  IN i_player_total INT
)
BEGIN
  SELECT * FROM black_hand_required_number_of_players 
    WHERE `black_hand_required_number_of_players`.`player_total` = i_player_total;
END$$

-- ** get_black_hand_roles

CREATE PROCEDURE `get_black_hand_roles`()
BEGIN
  SELECT * FROM black_hand_roles;
END$$

-- ** get_game

CREATE PROCEDURE `get_game`(
  IN i_game_name VARCHAR(255)
)
BEGIN
  SELECT * FROM games where game_name = i_game_name;
END$$

-- ** get_games

CREATE PROCEDURE `get_games`()
BEGIN
  SELECT 
    `games`.`game_id`,
    `games`.`game_name`,
    `games`.`min_players_num`,
    `games`.`max_players_num`,
    `games`.`min_age`,
    `games`.`average_game_duration`
  FROM games;
END$$

-- ** get_lobby

CREATE PROCEDURE `get_lobby`()
BEGIN
  SELECT 
    T1.`room_name`,
    T1.`game_name`,
    T1.`host_username`,
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
      `black_hand_games`.`room_name`, 
      GROUP_CONCAT(`black_hand_games`.`username`) AS 'players_ready' 
      from `black_hand_games` 
        where `black_hand_games`.`ready_status` = 'READY'
      group by `black_hand_games`.`room_name`
    ) T2 ON T1.`room_name` = T2.`room_name`
    LEFT OUTER JOIN (
    SELECT 
      `black_hand_games`.`room_name`, 
      GROUP_CONCAT(`black_hand_games`.`username`) AS 'players_not_ready'
      from `black_hand_games` 
        where `black_hand_games`.`ready_status` = 'NOT_READY' 
      group by `black_hand_games`.`room_name`
    ) T3 ON T1.`room_name` = T3.`room_name`
  ORDER BY `game_name`, `room_name`;
END$$

-- ** get_online_friends

CREATE PROCEDURE `get_online_friends`(
  IN i_username VARCHAR(64)   
)
BEGIN
  SELECT * FROM partyr_users 
	LEFT JOIN
	  relationships ON (
	    relationships.related_username = partyr_users.username
	  ) WHERE relating_username = i_username 
	    AND relationship_type = 'FRIEND'
      AND online_status = 'ONLINE'
    ORDER BY username;
END$$

-- ** get_online_users

CREATE PROCEDURE `get_online_users`(
  IN i_query_string VARCHAR(255)
)
BEGIN
	SELECT * FROM partyr_users WHERE 
    online_status = 'ONLINE' AND (
      username LIKE concat('%', i_query_string, '%') or 
      first_name LIKE concat('%', i_query_string, '%') or 
      last_name LIKE concat('%', i_query_string, '%') or 
      email LIKE concat('%', i_query_string, '%')
    )
  ORDER BY first_name;
END$$

-- ** get_relationships

CREATE PROCEDURE `get_relationships`(
	IN i_relationship_type VARCHAR(32),
  IN i_username VARCHAR(64)    
)
BEGIN
	SELECT * FROM partyr_users 
	LEFT JOIN
		relationships ON (
			relationships.related_username = partyr_users.username
		) WHERE relating_username = i_username AND relationship_type = i_relationship_type ORDER BY username;
END$$

-- ** get_themes

CREATE PROCEDURE `get_themes`()
BEGIN
  SELECT `theme_name` FROM `partyrdb`.`themes`;
END$$

-- ** get_user_by_email

CREATE PROCEDURE `get_user_by_email`(
	IN i_email VARCHAR(64)
)
BEGIN
  SELECT * FROM `partyrdb`.`partyr_users` WHERE email = i_email;
END$$

-- ** get_users

CREATE PROCEDURE `get_users`(
  IN i_query_string VARCHAR(255)
)
BEGIN
	SELECT * FROM `partyrdb`.`partyr_users` WHERE
    username LIKE concat('%', i_query_string, '%') or 
    first_name LIKE concat('%', i_query_string, '%') or 
    last_name LIKE concat('%', i_query_string, '%') or 
    email LIKE concat('%', i_query_string, '%')
  ORDER BY online_status desc, first_name;
END$$

-- ** join_black_hand_room

CREATE PROCEDURE `join_black_hand_room`(
  IN i_room_name VARCHAR(32),
  IN i_username VARCHAR(32)
)
BEGIN
  DECLARE numOfPlayers int;
   
  INSERT INTO `partyrdb`.`black_hand_games`(
    `room_name`,
    `username`
  ) VALUES (
    i_room_name,
    i_username
  );
  
  SELECT count(*) INTO numOfPlayers FROM `partyrdb`.`black_hand_games` WHERE `room_name` = i_room_name;
  UPDATE `partyrdb`.`lobby` SET `number_of_players` = numOfPlayers WHERE `room_name` = i_room_name;
END$$

-- ** leave_black_hand_room

CREATE PROCEDURE `leave_black_hand_room`(
  IN i_room_name VARCHAR(32),
  IN i_username VARCHAR(32)
)
BEGIN
  DECLARE hostUsername VARCHAR(32);
  DECLARE newHostUsername VARCHAR(32);
  DECLARE numOfPlayers int;

  DELETE FROM `partyrdb`.`black_hand_games` WHERE 
	`room_name` = i_room_name AND `username` = i_username;
    
  SELECT `host_username` INTO hostUsername FROM `partyrdb`.`lobby` WHERE `room_name` = i_room_name;
  SELECT `number_of_players` INTO numOfPlayers FROM `partyrdb`.`lobby` WHERE `room_name` = i_room_name;
  
  IF numOfPlayers = 1 THEN
    DELETE FROM `partyrdb`.`lobby` WHERE `room_name` = i_room_name;
  ELSEIF hostUsername = i_username THEN
    SELECT `username` INTO newHostUsername FROM `partyrdb`.`black_hand_games` WHERE `room_name` = i_room_name limit 1;
	  UPDATE `partyrdb`.`lobby` SET `host_username` = newHostUsername WHERE `room_name` = i_room_name;
  END IF;

  SELECT count(*) INTO numOfPlayers FROM `partyrdb`.`black_hand_games` WHERE `room_name` = i_room_name;
  UPDATE `partyrdb`.`lobby` SET `number_of_players` = numOfPlayers WHERE `room_name` = i_room_name;
END$$

-- ** save_chat_message

CREATE PROCEDURE `save_chat_message`(
  IN i_username VARCHAR(32),
  IN i_chat_message VARCHAR(512),
  IN i_time_of_chat_message TIMESTAMP
)
BEGIN
  INSERT INTO `partyrdb`.`chat`(
    `username`,
    `chat_message`,
    `time_of_chat_message`
  ) VALUES (
    i_username,
    i_chat_message,
    i_time_of_chat_message
  );
END$$

-- ** select_theme

CREATE PROCEDURE `select_theme`(
  IN i_username VARCHAR(32),
  IN i_theme_name VARCHAR(32)
)
BEGIN
 UPDATE `partyrdb`.`partyr_users` 
 SET `theme_name` = i_theme_name WHERE `username` = i_username;
END$$

-- ** select_username

CREATE PROCEDURE `select_username`(
  IN i_email VARCHAR(64),
  IN i_username VARCHAR(32)
)
BEGIN
  UPDATE `partyrdb`.`partyr_users` 
  SET `username` = i_username WHERE `email` = i_email;
END$$

-- ** toggle_ready_status

CREATE PROCEDURE `toggle_ready_status`(
  IN i_room_name VARCHAR(32),
  IN i_username VARCHAR(32)
)
BEGIN
  UPDATE `partyrdb`.`black_hand_games`
	  SET `ready_status` = IF(`ready_status` = 'READY', 'NOT_READY', 'READY')
  WHERE `room_name` = i_room_name AND `username` = i_username;
END$$

-- ** set_black_hand_preferred_faction

CREATE PROCEDURE `set_black_hand_preferred_faction`(
  IN i_room_name VARCHAR(32),
  IN i_username VARCHAR(32),
  IN i_preferred_faction VARCHAR(32)
)
BEGIN
  UPDATE `partyrdb`.`black_hand_games`
	  SET `preferred_faction` = i_preferred_faction
  WHERE `room_name` = i_room_name AND `username` = i_username;
END$$

-- ** set_black_hand_display_name

CREATE PROCEDURE `set_black_hand_display_name`(
  IN i_room_name VARCHAR(32),
  IN i_username VARCHAR(32),
  IN i_display_name VARCHAR(32)
)
BEGIN
  UPDATE `partyrdb`.`black_hand_games`
	  SET `display_name` = i_display_name
  WHERE `room_name` = i_room_name AND `username` = i_username;
END$$

-- ** submit_black_hand_player_turn

CREATE PROCEDURE `submit_black_hand_player_turn`(
  IN i_room_name VARCHAR(32),
  IN i_username VARCHAR(32),
  IN i_attacking_player VARCHAR(32),
  IN i_blocking_player VARCHAR(32),
  IN i_note VARCHAR(1024)
)
BEGIN
  DECLARE attackingPlayerExists INT;
  DECLARE blockingPlayerExists INT;

  SELECT EXISTS(SELECT * FROM `partyrdb`.`black_hand_games` WHERE `room_name` = i_room_name and `username` = i_attacking_player) INTO attackingPlayerExists;
  SELECT EXISTS(SELECT * FROM `partyrdb`.`black_hand_games` WHERE `room_name` = i_room_name and `username` = i_blocking_player) INTO blockingPlayerExists;

  IF (attackingPlayerExists = 1 AND blockingPlayerExists = 1) THEN
    UPDATE `partyrdb`.`black_hand_games`
      SET `attacking_player` = i_attacking_player, `blocking_player` = i_blocking_player, `note` = i_note
    WHERE `room_name` = i_room_name AND `username` = i_username;
  ELSEIF (attackingPlayerExists <> 1 AND blockingPlayerExists = 1) THEN
    UPDATE `partyrdb`.`black_hand_games`
      SET `blocking_player` = i_blocking_player, `note` = i_note
    WHERE `room_name` = i_room_name AND `username` = i_username;
  ELSEIF (attackingPlayerExists = 1 AND blockingPlayerExists <> 1) THEN
    UPDATE `partyrdb`.`black_hand_games`
      SET `attacking_player` = i_attacking_player, `note` = i_note
    WHERE `room_name` = i_room_name AND `username` = i_username;
  END IF;
END$$

DELIMITER ;