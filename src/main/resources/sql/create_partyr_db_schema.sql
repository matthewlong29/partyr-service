-- ** drop all tables

SET FOREIGN_KEY_CHECKS=0; -- to disable them

DROP TRIGGER IF EXISTS set_username_equal_to_email;
DROP TRIGGER IF EXISTS verify_valid_number_of_players;

DROP TABLE IF EXISTS black_hand_games;
DROP TABLE IF EXISTS black_hand_roles;
DROP TABLE IF EXISTS black_hand_required_number_of_players;
DROP TABLE IF EXISTS lobby;
DROP TABLE IF EXISTS relationships;
DROP TABLE IF EXISTS achievements;
DROP TABLE IF EXISTS partyr_users;
DROP TABLE IF EXISTS themes;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS chat;

SET FOREIGN_KEY_CHECKS=1; -- to re-enable them

-- ** create themes table

CREATE TABLE `themes` (
  `theme_id` INT NOT NULL AUTO_INCREMENT,
  `theme_name` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`theme_id`),
  UNIQUE KEY `unique_theme_name` (`theme_name`)
) ENGINE=InnoDB;

-- ** create partyr_users table

CREATE TABLE `partyr_users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_hash` VARCHAR(254) NOT NULL,
  `username` VARCHAR(32),
  `first_name` VARCHAR(254) DEFAULT NULL,
  `last_name` VARCHAR(254) DEFAULT NULL,
  `email` VARCHAR(64) NOT NULL,
  `profile_image_url` VARCHAR(254) DEFAULT NULL,
  `joined_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `online_status` VARCHAR(8) DEFAULT 'OFFLINE',
  `theme_name` VARCHAR(32) DEFAULT 'light',
  `age` INT DEFAULT NULL,
  `country` VARCHAR(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_username` (`username`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_user_hash` (`user_hash`),
  CONSTRAINT `set_theme_reference` FOREIGN KEY (`theme_name`) REFERENCES `themes` (`theme_name`),
  CONSTRAINT `limit_online_status` CHECK ((`online_status` IN ('ONLINE','OFFLINE')))
) ENGINE=InnoDB;

-- CREATE TRIGGER set_username_equal_to_email BEFORE INSERT ON partyr_users FOR EACH ROW BEGIN
--   IF (NEW.username = null) SET NEW.username = NEW.email; END IF;
-- END;

-- ** create relationships table

CREATE TABLE `relationships` (
  `relationship_id` INT NOT NULL AUTO_INCREMENT,
  `relating_username` VARCHAR(32) NOT NULL,
  `related_username` VARCHAR(32) NOT NULL,
  `relationship_type` VARCHAR(16) NOT NULL,
  PRIMARY KEY (`relationship_id`),
  UNIQUE KEY `unique_relationship` (`relating_username`,`related_username`),
  CONSTRAINT `limit_relationship_type` CHECK ((`relationship_type` IN ('FRIEND', 'BLOCK')))
) ENGINE=InnoDB;

-- ** create achievements table

CREATE TABLE `achievements` (
  `achievement_id` INT NOT NULL AUTO_INCREMENT,
  `achievement_name` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`achievement_id`)
) ENGINE=InnoDB;

-- ** create games table

CREATE TABLE `games` (
  `game_id` INT NOT NULL AUTO_INCREMENT,
  `game_name` VARCHAR(32) NOT NULL,
  `min_players_num` INT NOT NULL,
  `max_players_num` INT NOT NULL,
  `min_age` INT NOT NULL,
  `average_game_duration` INT NOT NULL,
  PRIMARY KEY (`game_id`),
  UNIQUE KEY `unique_game_name` (`game_name`)
) ENGINE=InnoDB;

-- ** create black_hand_required_number_of_players table

CREATE TABLE `black_hand_required_number_of_players` (
  `player_total` INT NOT NULL,
  `monster_total` INT NOT NULL,
  `black_hand_total` INT NOT NULL,
  `townie_total` INT NOT NULL,
  PRIMARY KEY (`player_total`)
) ENGINE=InnoDB;

-- CREATE TRIGGER verify_valid_number_of_players AFTER INSERT
-- ON `black_hand_required_number_of_players`
--   FOR EACH ROW UPDATE `black_hand_required_number_of_players` SET NEW.`player_total`=NEW.`monster_total`+NEW.`black_hand_total`+NEW.`townie_total`

-- ** create lobby table

CREATE TABLE `lobby` (
  `game_room_name` VARCHAR(32) NOT NULL,
  `game_name` VARCHAR(32) NOT NULL,
  `host_username` VARCHAR(32) NOT NULL,
  `number_of_players` INT NOT NULL,
  `game_started` BOOLEAN NOT NULL DEFAULT 0,
  `game_start_time` TIMESTAMP,
  `game_end_time` TIMESTAMP, -- TODO: maybe use this as indicatror for when to purge data?
  PRIMARY KEY (`game_room_name`),
  CONSTRAINT `set_player_reference` FOREIGN KEY (`host_username`) REFERENCES `partyr_users` (`username`),
  CONSTRAINT `set_game_name_reference` FOREIGN KEY (`game_name`) REFERENCES `games` (`game_name`)
) ENGINE=InnoDB;

-- ** create black_hand_roles table

CREATE TABLE `black_hand_roles` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `faction` VARCHAR(32) NOT NULL,
  `role_name` VARCHAR(32) NOT NULL,
  `day_ability_description` VARCHAR(1024),
  `night_ability_description` VARCHAR(1024),
  `attribute_description` VARCHAR(1024) NOT NULL,
  `goal_description` VARCHAR(1024) NOT NULL,
  `sprite_path` VARCHAR(1024) DEFAULT '/',
  `role_priority` INT NOT NULL,
  `day_kill` BOOLEAN NOT NULL, -- 1 true else false
  `night_kill` BOOLEAN NOT NULL,
  `day_block` BOOLEAN NOT NULL,
  `night_block` BOOLEAN NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `unique_role_name` (`role_name`),
  UNIQUE KEY `unique_role_priority` (`role_priority`),
  CONSTRAINT `limit_faction` CHECK ((`faction` IN ('BlackHand', 'Monster', 'Townie')))
) ENGINE=InnoDB;

-- ** create black_hand_games table

CREATE TABLE `black_hand_games` (
  `game_room_name` VARCHAR(32) NOT NULL,
  `username` VARCHAR(32) NOT NULL,
  `display_name` VARCHAR(32),
  `preferred_faction` VARCHAR(32), 
  `role_name` VARCHAR(32),
  `number_of_blocks_against` INT DEFAULT 0,
  `number_of_kill_strikes_against` INT DEFAULT 0,
  `turn_priority` INT DEFAULT 0,
  `player_status` VARCHAR(5) DEFAULT 'ALIVE',
  `ready_status` VARCHAR(16) DEFAULT 'NOT_READY',
  `note` VARCHAR(1024),
  PRIMARY KEY (`game_room_name`, `username`),
  UNIQUE KEY `unique_role_per_game` (`username`,`role_name`),
  UNIQUE KEY `unique_display_name_per_game` (`display_name`,`game_room_name`),
  CONSTRAINT `set_player_username_reference` FOREIGN KEY (`username`) REFERENCES `partyr_users` (`username`),
  CONSTRAINT `set_role_reference` FOREIGN KEY (`role_name`) REFERENCES `black_hand_roles` (`role_name`),
  CONSTRAINT `set_game_instance_reference` FOREIGN KEY (`game_room_name`) REFERENCES `lobby` (`game_room_name`),
  CONSTRAINT `limit_player_status` CHECK ((`player_status` IN ('ALIVE', 'DEAD'))),
  CONSTRAINT `limit_ready_status` CHECK ((`ready_status` IN ('READY', 'NOT_READY'))),
  CONSTRAINT `limit_preferred_faction` CHECK ((`preferred_faction`) IN ('BlackHand', 'Monster', 'Townie'))
) ENGINE=InnoDB;

-- ** create chat table

CREATE TABLE `chat` (
  `chat_message__id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(32) NOT NULL,
  `chat_message` VARCHAR(512) NOT NULL,
  `time_of_chat_message` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`chat_message__id`),
  CONSTRAINT `set_author_reference` FOREIGN KEY (`username`) REFERENCES `partyr_users` (`username`)
) ENGINE=InnoDB;