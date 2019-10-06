-- ** themes mock data

INSERT INTO `partyrdb`.`themes` (`theme_name`) VALUES ('light');
INSERT INTO `partyrdb`.`themes` (`theme_name`) VALUES ('dark');
INSERT INTO `partyrdb`.`themes` (`theme_name`) VALUES ('retro');
INSERT INTO `partyrdb`.`themes` (`theme_name`) VALUES ('lemon_in_water');

-- ** partyr_user mock data

INSERT INTO `partyrdb`.`partyr_users` ( `user_hash`, `first_name`, `last_name`, `email`, `profile_image_url`, `online_status`, `ready_to_play_status`, `age`, `country`)
VALUES ('fbcef9848426e3a281d2b8d9e213ca80', 'coty', 'dawson', 'coty.dawson@gmail.com', 'http://www.google.com/image.jpg', 'OFFLINE', 'NOT_READY', 28, 'United States');
INSERT INTO `partyrdb`.`partyr_users` ( `user_hash`, `first_name`, `last_name`, `email`, `profile_image_url`, `online_status`, `ready_to_play_status`, `age`, `country`)
VALUES ('dd1b9ac0f78bb89362ca862b8f5fa340', 'timmy', 'smith', 'timmy7@gmail.com', 'http://www.google.com/image.jpg', 'OFFLINE', 'NOT_READY', 13, 'United States');
INSERT INTO `partyrdb`.`partyr_users` ( `user_hash`, `first_name`, `last_name`, `email`, `profile_image_url`, `online_status`, `ready_to_play_status`, `age`, `country`)
VALUES ('c9c3d90d5c54fe0b3addb2d0f3ffeb20', 'cheese', 'cakeeee', 'cheesecake@gmail.com', 'http://www.google.com/image.jpg', 'ONLINE', 'NOT_READY', 99, 'United States');
INSERT INTO `partyrdb`.`partyr_users` ( `user_hash`, `first_name`, `last_name`, `email`, `profile_image_url`, `online_status`, `ready_to_play_status`, `age`, `country`)
VALUES ('3bc97cd06a7c868b3b38a5ab85f49c4e', 'kathy', 'guy', 'obtrusivemonks@gmail.com', 'http://www.google.com/image.jpg', 'ONLINE', 'READY', 19, 'United States');

-- ** relationships mock data

insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('coty.dawson@gmail.com', 'obtrusivemonks@gmail.com', 'FRIEND');
insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('coty.dawson@gmail.com', 'cheesecake@gmail.com', 'BLOCK');
insert into `partyrdb`.`Relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('timmy7@gmail.com', 'cheesecake@gmail.com', 'BLOCK');

-- ** add games mock data

insert into `partyrdb`.`games` (`game_name`, `min_players_num`, `max_players_num`, `min_age`, `average_game_duration`) 
  values ('BlackHand', 2, 10, 13, 45);

-- ** add BlackHand

insert into `partyrdb`.`black_hand` (`number_of_players`, `game_start_time`, `game_end_time`) 
  values (8, now(), now());

-- ** add black_hand_instances

insert into `partyrdb`.`black_hand_instances` (`game_instance_id`, `player`) 
  values (1, 'coty.dawson@gmail.com');