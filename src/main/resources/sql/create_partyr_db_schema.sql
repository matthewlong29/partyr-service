-- ** drop all tables

SET FOREIGN_KEY_CHECKS=0; -- to disable them

drop table if exists black_hand_instances;
drop table if exists black_hand;
drop table if exists relationships;
drop table if exists achievements;
drop table if exists partyr_users;
drop table if exists themes;
drop table if exists games;

SET FOREIGN_KEY_CHECKS=1; -- to re-enable them

-- ** create themes table

CREATE TABLE `themes` (
  `theme_id` int(11) NOT NULL AUTO_INCREMENT,
  `theme_name` varchar(32) NOT NULL,
  PRIMARY KEY (`theme_id`),
  UNIQUE KEY `unique_theme_name` (`theme_name`)
) ENGINE=InnoDB;

-- ** create partyr_users table

CREATE TABLE `partyr_users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_hash` varchar(254) NOT NULL,
  `user_name` varchar(32) NOT NULL DEFAULT 'New User',
  `first_name` varchar(254) DEFAULT NULL,
  `last_name` varchar(254) DEFAULT NULL,
  `email` varchar(64) NOT NULL,
  `profile_image_url` varchar(254) DEFAULT NULL,
  `joined_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `online_status` varchar(8) DEFAULT 'OFFLINE',
  `ready_to_play_status` varchar(16) DEFAULT 'NOT_READY',
  `theme_id` int(11) DEFAULT '1',
  `age` int(11) DEFAULT NULL,
  `country` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_user_name` (`user_name`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_user_hash` (`user_hash`),
  CONSTRAINT `set_theme_reference` FOREIGN KEY (`theme_id`) REFERENCES `themes` (`theme_id`),
  CONSTRAINT `limit_online_status` CHECK ((`online_status` in ('ONLINE','OFFLINE'))),
  CONSTRAINT `limit_ready_to_play_status` CHECK ((`ready_to_play_status` in ('READY', 'NOT_READY')))
) ENGINE=InnoDB;

-- ** create relationships table

CREATE TABLE `relationships` (
  `relationship_id` int(11) NOT NULL AUTO_INCREMENT,
  `relating_email` varchar(32) NOT NULL,
  `related_email` varchar(32) NOT NULL,
  `relationship_type` varchar(16) NOT NULL,
  PRIMARY KEY (`relationship_id`),
  UNIQUE KEY `unique_relationship` (`relating_email`,`related_email`),
  CONSTRAINT `limit_relationship_type` CHECK ((`relationship_type` in ('FRIEND', 'BLOCK')))
) ENGINE=InnoDB;

-- ** create achievements table

CREATE TABLE `achievements` (
  `achievement_id` int(11) NOT NULL AUTO_INCREMENT,
  `achievement_name` varchar(32) NOT NULL,
  PRIMARY KEY (`achievement_id`)
) ENGINE=InnoDB;

-- ** create games table

CREATE TABLE `games` (
  `game_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_name` varchar(32) NOT NULL,
  `min_players_num` int(11) NOT NULL,
  `max_players_num` int(11) NOT NULL,
  `min_age` int(11) NOT NULL,
  `average_game_duration` int(11) NOT NULL,
  PRIMARY KEY (`game_id`),
  UNIQUE KEY `unique_game_name` (`game_name`)
) ENGINE=InnoDB;

-- ** create black_hand table

CREATE TABLE `black_hand` (
  `game_instance_id` int(11) NOT NULL AUTO_INCREMENT,
  `number_of_players` int(11) NOT NULL,
  `game_start_time` timestamp NOT NULL,
  `game_end_time` timestamp NOT NULL,
  PRIMARY KEY (`game_instance_id`)
) ENGINE=InnoDB;

-- ** create black_hand_instances table

CREATE TABLE `black_hand_instances` (
  `game_instance_id` int(11) NOT NULL,
  `player` varchar(32) NOT NULL,
  PRIMARY KEY (`player`),
  KEY `game_instance_id` (`game_instance_id`),
  CONSTRAINT `set_player_reference` FOREIGN KEY (`player`) REFERENCES `partyr_users` (`email`),
  CONSTRAINT `set_game_instance_reference` FOREIGN KEY (`game_instance_id`) REFERENCES `black_hand` (`game_instance_id`)
) ENGINE=InnoDB;
