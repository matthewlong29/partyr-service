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

-- ** add black_hand mock data

insert into `partyrdb`.`black_hand` (`number_of_players`, `game_start_time`, `game_end_time`) 
  values (8, now(), now());

-- ** add black_hand_instances mock data

insert into `partyrdb`.`black_hand_instances` (`game_instance_id`, `player`) 
  values (1, 'coty.dawson@gmail.com');

-- ** add chat mock data

insert into `partyrdb`.`chat` (`author`, `message`, `time_of_message`)
  values ('long.matthew29@gmail.com', 'sup dudes?', '2019-10-08 20:04:04');

insert into `partyrdb`.`chat` (`author`, `message`, `time_of_message`)
  values ('long.matthew29@gmail.com', 'ready to have a total fake conversation?', '2019-10-08 20:04:05');

insert into `partyrdb`.`chat` (`author`, `message`, `time_of_message`)
  values ('coty.dawson@gmail.com', 'yes?', '2019-10-08 20:04:06');

insert into `partyrdb`.`chat` (`author`, `message`, `time_of_message`)
  values ('timmy7@gmail.com', 'but its dark and bitter blend of nihilism with oddly uplifting existentialism', '2019-10-08 20:04:07');

insert into `partyrdb`.`chat` (`author`, `message`, `time_of_message`)
  values ('coty.dawson@gmail.com', 'It hits hard, Morty, then it slowly fades, leaving you stranded in a failing marriage. I did it.', '2019-10-08 20:04:08');

insert into `partyrdb`.`chat` (`author`, `message`, `time_of_message`)
  values ('coty.dawson@gmail.com', 'woooooo', '2019-10-08 20:04:09');

insert into `partyrdb`.`chat` (`author`, `message`, `time_of_message`)
  values ('timmy7@gmail.com', 'Rick has traveled across the universe and a myriad of different dimensions, and his hysterically anxious grandson often gets dragged along.', '2019-10-08 20:04:10');

insert into `partyrdb`.`chat` (`author`, `message`, `time_of_message`)
  values ('coty.dawson@gmail.com', 'chattting and stuff woo', '2019-10-08 20:04:11');

insert into `partyrdb`.`chat` (`author`, `message`, `time_of_message`)
  values ('coty.dawson@gmail.com', 'Listen Morty, I hate to break it to you, but what people calls “love” is just a chemical reaction that compels animals to breed. ', '2019-10-08 20:04:12');

insert into `partyrdb`.`chat` (`author`, `message`, `time_of_message`)
  values ('coty.dawson@gmail.com', 'They’ve both seen some serious stuff, especially considering they survived the destruction of their own universe and lived to bury their own bodies in an alternate reality they went on to live in.', '2019-10-08 20:04:13');

insert into `partyrdb`.`chat` (`author`, `message`, `time_of_message`)
  values ('timmy7@gmail.com', 'get you pumped for the rest', '2019-10-08 20:04:14');

insert into `partyrdb`.`chat` (`author`, `message`, `time_of_message`)
  values ('cheesecake@gmail.com', 'In a time where Adult Swim is straying away from animated', '2019-10-08 20:04:15');

  -- ** add game mock data

insert into `partyrdb`.`games` (`game_name`, `min_players_num`, `max_players_num`, `min_age`, `average_game_duration`)
  values ("Black Hand", 5, 10, 13, 30);
