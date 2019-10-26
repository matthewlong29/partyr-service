-- ** drop all tables

SET FOREIGN_KEY_CHECKS=0; -- to disable them

drop trigger if exists set_user_name_equal_to_email;
drop trigger if exists verify_valid_number_of_players;

drop table if exists black_hand_instances;
drop table if exists black_hand_roles;
drop table if exists black_hand_required_number_of_players;
drop table if exists black_hand;
drop table if exists relationships;
drop table if exists achievements;
drop table if exists partyr_users;
drop table if exists themes;
drop table if exists games;
drop table if exists chat;

SET FOREIGN_KEY_CHECKS=1; -- to re-enable them

-- ** create themes table

CREATE TABLE `themes` (
  `theme_id` int NOT NULL AUTO_INCREMENT,
  `theme_name` varchar(32) NOT NULL,
  PRIMARY KEY (`theme_id`),
  UNIQUE KEY `unique_theme_name` (`theme_name`)
) ENGINE=InnoDB;

-- ** create partyr_users table

CREATE TABLE `partyr_users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_hash` varchar(254) NOT NULL,
  `user_name` varchar(32),
  `first_name` varchar(254) DEFAULT NULL,
  `last_name` varchar(254) DEFAULT NULL,
  `email` varchar(64) NOT NULL,
  `profile_image_url` varchar(254) DEFAULT NULL,
  `joined_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `online_status` varchar(8) DEFAULT 'OFFLINE',
  `ready_to_play_status` varchar(16) DEFAULT 'NOT_READY',
  `theme_id` int DEFAULT '1',
  `age` int DEFAULT NULL,
  `country` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_user_name` (`user_name`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_user_hash` (`user_hash`),
  CONSTRAINT `set_theme_reference` FOREIGN KEY (`theme_id`) REFERENCES `themes` (`theme_id`),
  CONSTRAINT `limit_online_status` CHECK ((`online_status` in ('ONLINE','OFFLINE'))),
  CONSTRAINT `limit_ready_to_play_status` CHECK ((`ready_to_play_status` in ('READY', 'NOT_READY')))
) ENGINE=InnoDB;

-- CREATE TRIGGER set_user_name_equal_to_email BEFORE INSERT ON partyr_users FOR EACH ROW BEGIN
--   IF (NEW.user_name = null) SET NEW.user_name = NEW.email; END IF;
-- END;

-- ** create relationships table

CREATE TABLE `relationships` (
  `relationship_id` int NOT NULL AUTO_INCREMENT,
  `relating_email` varchar(32) NOT NULL,
  `related_email` varchar(32) NOT NULL,
  `relationship_type` varchar(16) NOT NULL,
  PRIMARY KEY (`relationship_id`),
  UNIQUE KEY `unique_relationship` (`relating_email`,`related_email`),
  CONSTRAINT `limit_relationship_type` CHECK ((`relationship_type` in ('FRIEND', 'BLOCK')))
) ENGINE=InnoDB;

-- ** create achievements table

CREATE TABLE `achievements` (
  `achievement_id` int NOT NULL AUTO_INCREMENT,
  `achievement_name` varchar(32) NOT NULL,
  PRIMARY KEY (`achievement_id`)
) ENGINE=InnoDB;

-- ** create games table

CREATE TABLE `games` (
  `game_id` int NOT NULL AUTO_INCREMENT,
  `game_name` varchar(32) NOT NULL,
  `min_players_num` int NOT NULL,
  `max_players_num` int NOT NULL,
  `min_age` int NOT NULL,
  `average_game_duration` int NOT NULL,
  PRIMARY KEY (`game_id`),
  UNIQUE KEY `unique_game_name` (`game_name`)
) ENGINE=InnoDB;

-- ** create black_hand_required_number_of_players table

CREATE TABLE `black_hand_required_number_of_players` (
  `player_total` int NOT NULL,
  `monster_total` int NOT NULL,
  `black_hand_total` int NOT NULL,
  `townie_total` int NOT NULL,
  PRIMARY KEY (`player_total`)
) ENGINE=InnoDB;

-- CREATE TRIGGER verify_valid_number_of_players AFTER INSERT
-- ON `black_hand_required_number_of_players`
--   FOR EACH ROW UPDATE `black_hand_required_number_of_players` SET NEW.`player_total`=NEW.`monster_total`+NEW.`black_hand_total`+NEW.`townie_total`

-- ** create black_hand table

CREATE TABLE `black_hand` (
  `game_instance_id` int NOT NULL AUTO_INCREMENT,
  `number_of_players` int NOT NULL,
  `game_start_time` timestamp NOT NULL,
  `game_end_time` timestamp NOT NULL,
  PRIMARY KEY (`game_instance_id`),
  CONSTRAINT `verify_valid_player_total` FOREIGN KEY (`number_of_players`) REFERENCES `black_hand_required_number_of_players` (`player_total`)
) ENGINE=InnoDB;

-- ** create black_hand_roles table

CREATE TABLE `black_hand_roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `faction` varchar(32) NOT NULL,
  `role_name` varchar(32) NOT NULL,
  `day_ability_description` varchar(1024),
  `night_ability_description` varchar(1024),
  `attribute_description` varchar(1024) NOT NULL,
  `goal_description` varchar(1024) NOT NULL,
  `role_priority` int NOT NULL,
  `day_kill` BOOLEAN NOT NULL,
  `night_kill` BOOLEAN NOT NULL,
  `day_block` BOOLEAN NOT NULL,
  `night_block` BOOLEAN NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `unique_role_name` (`role_name`),
  UNIQUE KEY `unique_role_priority` (`role_priority`),
  CONSTRAINT `limit_faction` CHECK ((`faction` in ('BlackHand', 'Monster', 'Townie')))
) ENGINE=InnoDB;

-- ** create black_hand_instances table

CREATE TABLE `black_hand_instances` (
  `game_instance_id` int NOT NULL,
  `player` varchar(32) NOT NULL,
  PRIMARY KEY (`player`),
  KEY `game_instance_id` (`game_instance_id`),
  CONSTRAINT `set_player_reference` FOREIGN KEY (`player`) REFERENCES `partyr_users` (`email`),
  CONSTRAINT `set_game_instance_reference` FOREIGN KEY (`game_instance_id`) REFERENCES `black_hand` (`game_instance_id`)
) ENGINE=InnoDB;

-- ** create chat table

CREATE TABLE `chat` (
  `chat_id` int NOT NULL AUTO_INCREMENT,
  `author` varchar(32) NOT NULL,
  `message` varchar(512) NOT NULL,
  `time_of_message` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`chat_id`),
  CONSTRAINT `set_author_reference` FOREIGN KEY (`author`) REFERENCES `partyr_users` (`email`)
) ENGINE=InnoDB;