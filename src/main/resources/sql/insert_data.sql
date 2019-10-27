-- ** themes mock data

insert into `partyrdb`.`themes` (`theme_name`) values ('light');
insert into `partyrdb`.`themes` (`theme_name`) values ('dark');
insert into `partyrdb`.`themes` (`theme_name`) values ('retro');
insert into `partyrdb`.`themes` (`theme_name`) values ('lemon_in_water');

-- ** partyr_user mock data

insert into `partyrdb`.`partyr_users` ( `user_hash`, `first_name`, `last_name`, `email`, `profile_image_url`, `online_status`, `ready_to_play_status`, `age`, `country`)
  values ('fbcef9848426e3a281d2b8d9e213ca80', 'coty', 'dawson', 'coty.dawson@gmail.com', 'http://www.google.com/image.jpg', 'OFFLINE', 'NOT_READY', 28, 'United States');
insert into `partyrdb`.`partyr_users` ( `user_hash`, `first_name`, `last_name`, `email`, `profile_image_url`, `online_status`, `ready_to_play_status`, `age`, `country`)
  values ('dd1b9ac0f78bb89362ca862b8f5fa340', 'timmy', 'smith', 'timmy7@gmail.com', 'http://www.google.com/image.jpg', 'OFFLINE', 'NOT_READY', 13, 'United States');
insert into `partyrdb`.`partyr_users` ( `user_hash`, `first_name`, `last_name`, `email`, `profile_image_url`, `online_status`, `ready_to_play_status`, `age`, `country`)
  values ('c9c3d90d5c54fe0b3addb2d0f3ffeb20', 'cheese', 'cakeeee', 'cheesecake@gmail.com', 'http://www.google.com/image.jpg', 'ONLINE', 'NOT_READY', 99, 'United States');
insert into `partyrdb`.`partyr_users` ( `user_hash`, `first_name`, `last_name`, `email`, `profile_image_url`, `online_status`, `ready_to_play_status`, `age`, `country`)
  values ('3bc97cd06a7c868b3b38a5ab85f49c4e', 'kathy', 'guy', 'obtrusivemonks@gmail.com', 'http://www.google.com/image.jpg', 'ONLINE', 'READY', 19, 'United States');
insert into `partyrdb`.`partyr_users` ( `user_hash`, `first_name`, `last_name`, `email`, `profile_image_url`, `online_status`, `ready_to_play_status`, `age`, `country`)
  values ('3bc96cd06a7ca682ab38a5ad85f49c4e', 'lana', 'wood', 'lanawood@gmail.com', 'http://www.google.com/image.jpg', 'ONLINE', 'READY', 21, 'United States');
insert into `partyrdb`.`partyr_users` ( `user_hash`, `first_name`, `last_name`, `email`, `profile_image_url`, `online_status`, `ready_to_play_status`, `age`, `country`)
  values ('3bc96cd06a7c86a2a838a58d85f49c4e', 'billy', 'twobyfour', 'twobyfour@gmail.com', 'http://www.google.com/image.jpg', 'ONLINE', 'READY', 69, 'United States');

-- ** relationships mock data

insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('coty.dawson@gmail.com', 'obtrusivemonks@gmail.com', 'FRIEND');
insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('coty.dawson@gmail.com', 'cheesecake@gmail.com', 'FRIEND');
insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('timmy7@gmail.com', 'cheesecake@gmail.com', 'FRIEND');
insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('lanawood@gmail.com', 'cheesecake@gmail.com', 'FRIEND');
insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('cheesecake@gmail.com', 'lanawood@gmail.com', 'FRIEND');
insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('obtrusivemonks@gmail.com', 'timmy7@gmail.com', 'FRIEND');
insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('coty.dawson@gmail.com', 'twobyfour@gmail.com', 'BLOCK');
insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('timmy7@gmail.com', 'twobyfour@gmail.com', 'BLOCK');
insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('cheesecake@gmail.com', 'twobyfour@gmail.com', 'BLOCK');
insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('obtrusivemonks@gmail.com', 'twobyfour@gmail.com', 'BLOCK');
insert into `partyrdb`.`relationships` (`relating_email`, `related_email`, `relationship_type`) 
  values ('lanawood@gmail.com', 'twobyfour@gmail.com', 'BLOCK');

-- ** add chat mock data

insert into `partyrdb`.`chat` (`email`, `message`, `time_of_message`)
  values ('coty.dawson@gmail.com', 'yes?', '2019-10-08 20:04:06');
insert into `partyrdb`.`chat` (`email`, `message`, `time_of_message`)
  values ('timmy7@gmail.com', 'but its dark and bitter blend of nihilism with oddly uplifting existentialism', '2019-10-08 20:04:07');
insert into `partyrdb`.`chat` (`email`, `message`, `time_of_message`)
  values ('coty.dawson@gmail.com', 'It hits hard, Morty, then it slowly fades, leaving you stranded in a failing marriage. I did it.', '2019-10-08 20:04:08');
insert into `partyrdb`.`chat` (`email`, `message`, `time_of_message`)
  values ('coty.dawson@gmail.com', 'woooooo', '2019-10-08 20:04:09');
insert into `partyrdb`.`chat` (`email`, `message`, `time_of_message`)
  values ('timmy7@gmail.com', 'Rick has traveled across the universe and a myriad of different dimensions, and his hysterically anxious grandson often gets dragged along.', '2019-10-08 20:04:10');
insert into `partyrdb`.`chat` (`email`, `message`, `time_of_message`)
  values ('coty.dawson@gmail.com', 'chattting and stuff woo', '2019-10-08 20:04:11');
insert into `partyrdb`.`chat` (`email`, `message`, `time_of_message`)
  values ('coty.dawson@gmail.com', 'Listen Morty, I hate to break it to you, but what people calls “love” is just a chemical reaction that compels animals to breed. ', '2019-10-08 20:04:12');
insert into `partyrdb`.`chat` (`email`, `message`, `time_of_message`)
  values ('coty.dawson@gmail.com', 'They’ve both seen some serious stuff, especially considering they survived the destruction of their own universe and lived to bury their own bodies in an alternate reality they went on to live in.', '2019-10-08 20:04:13');
insert into `partyrdb`.`chat` (`email`, `message`, `time_of_message`)
  values ('timmy7@gmail.com', 'get you pumped for the rest', '2019-10-08 20:04:14');
insert into `partyrdb`.`chat` (`email`, `message`, `time_of_message`)
  values ('cheesecake@gmail.com', 'In a time where Adult Swim is straying away from animated', '2019-10-08 20:04:15');

-- ** add black_hand_required_number_of_players mock data

insert into `partyrdb`.`black_hand_required_number_of_players` (`player_total`, `monster_total`, `black_hand_total`, `townie_total`)
  values (5, 1, 1, 3);
insert into `partyrdb`.`black_hand_required_number_of_players` (`player_total`, `monster_total`, `black_hand_total`, `townie_total`)
  values (6, 1, 2, 3);
insert into `partyrdb`.`black_hand_required_number_of_players` (`player_total`, `monster_total`, `black_hand_total`, `townie_total`)
  values (7, 1, 2, 4);
insert into `partyrdb`.`black_hand_required_number_of_players` (`player_total`, `monster_total`, `black_hand_total`, `townie_total`)
  values (8, 1, 2, 5);
insert into `partyrdb`.`black_hand_required_number_of_players` (`player_total`, `monster_total`, `black_hand_total`, `townie_total`)
  values (9, 2, 2, 5);
insert into `partyrdb`.`black_hand_required_number_of_players` (`player_total`, `monster_total`, `black_hand_total`, `townie_total`)
  values (10, 2, 2, 6);
insert into `partyrdb`.`black_hand_required_number_of_players` (`player_total`, `monster_total`, `black_hand_total`, `townie_total`)
  values (11, 2, 2, 7);
insert into `partyrdb`.`black_hand_required_number_of_players` (`player_total`, `monster_total`, `black_hand_total`, `townie_total`)
  values (12, 2, 3, 7);
insert into `partyrdb`.`black_hand_required_number_of_players` (`player_total`, `monster_total`, `black_hand_total`, `townie_total`)
  values (13, 2, 3, 8);
insert into `partyrdb`.`black_hand_required_number_of_players` (`player_total`, `monster_total`, `black_hand_total`, `townie_total`)
  values (14, 2, 4, 8);
insert into `partyrdb`.`black_hand_required_number_of_players` (`player_total`, `monster_total`, `black_hand_total`, `townie_total`)
  values (15, 2, 4, 9);

-- ** add black hand roles data

insert into `partyrdb`.`black_hand_roles` (`faction`, `role_name`, `night_ability_description`, `attribute_description`, `goal_description`, `role_priority`, `day_kill`, `night_kill`, `day_block`, `night_block`)
  values ('Townie', 'Bodyguard', 'Protect one person from death each night.', 'If your target is attacked, both you and your attacker will die instead. If you successfully protect someone, you cant be saved from death. Your counterattack ignores night immunity.', 'Lynch every criminal and evildoer.', 9, 0, 0, 0, 1);
insert into `partyrdb`.`black_hand_roles` (`faction`, `role_name`, `night_ability_description`, `attribute_description`, `goal_description`, `role_priority`, `day_kill`, `night_kill`, `day_block`, `night_block`)
  values ('Townie', 'Doctor', 'Heal one person each night, preventing them from dying.', 'You may only heal yourself once. You will know if your target is attacked.', 'Lynch every criminal and evildoer.', 8, 0, 0, 0, 1);
insert into `partyrdb`.`black_hand_roles` (`faction`, `role_name`, `day_ability_description`, `attribute_description`, `goal_description`, `role_priority`, `day_kill`, `night_kill`, `day_block`, `night_block`)
  values ('Townie', 'Jailor', 'You may choose one person during the day to jail for the night.', 'You may anonymously talk with your prisoner. You may choose to execute your prisoner. The jailed target cannot perform their night ability. While jailed the prisoner is safe from all attacks.', 'Lynch every criminal and evildoer.', 6, 0, 0, 1, 1);
insert into `partyrdb`.`black_hand_roles` (`faction`, `role_name`, `day_ability_description`, `attribute_description`, `goal_description`, `role_priority`, `day_kill`, `night_kill`, `day_block`, `night_block`)
  values ('Townie', 'Spy', 'You may bug a players house to see what happens to them that night.', 'You will know who the Black Hand and Monsters visit each night.', 'Lynch every criminal and evildoer.', 5, 0, 0, 0, 0);
insert into `partyrdb`.`black_hand_roles` (`faction`, `role_name`, `night_ability_description`, `attribute_description`, `goal_description`, `role_priority`, `day_kill`, `night_kill`, `day_block`, `night_block`)
  values ('Townie', 'Vampire Hunter', 'Check for Vampires each night.', 'If you find a Vampire you will stake them in the heart. If a Vampire visits you they will be staked. You can hear Vampires at night. If you kill all Vampires you will become a Vigilante.', 'Lynch every criminal and evildoer.', 7, 1, 1, 0, 0);
insert into `partyrdb`.`black_hand_roles` (`faction`, `role_name`, `day_ability_description`, `attribute_description`, `goal_description`, `role_priority`, `day_kill`, `night_kill`, `day_block`, `night_block`)
  values ('BlackHand', 'Forget', 'Choose a person and rewrite their last will at night.', 'If your target dies their last will is replaced with your forgery. You may only perform 3 forgeries. If there are no kill capable Black Hand roles left you will become a Mafioso. You can talk with the other Black Hand at night.', 'Kill anyone that will not submit to the Black Hand.', 4, 0, 0, 0, 0);
insert into `partyrdb`.`black_hand_roles` (`faction`, `role_name`, `night_ability_description`, `attribute_description`, `goal_description`, `role_priority`, `day_kill`, `night_kill`, `day_block`, `night_block`)
  values ('BlackHand', 'Godfather', 'Kill someone each night.', 'You cant be killed at night. If there is a Mafioso he will attack the target instead of you. You will appear to be a Town member to the Sheriff. You can talk with the other Black Hand at night.', 'Kill anyone that will not submit to the Black Hand.', 1, 0, 1, 0, 0);
insert into `partyrdb`.`black_hand_roles` (`faction`, `role_name`, `night_ability_description`, `attribute_description`, `goal_description`, `role_priority`, `day_kill`, `night_kill`, `day_block`, `night_block`)
  values ('Monster', 'Vampire', 'Convert others to Vampires at night.', 'Vampires vote at night to bite a target. The youngest Vampire will visit the target at night. You must wait 1 night between conversions.', 'Convert everyone who would oppose you.', 2, 0, 1, 0, 0);
insert into `partyrdb`.`black_hand_roles` (`faction`, `role_name`, `night_ability_description`, `attribute_description`, `goal_description`, `role_priority`, `day_kill`, `night_kill`, `day_block`, `night_block`)
  values ('Monster', 'Werewolf', 'Transform into a Werewolf during the full moon.', 'As a Werewolf you can not be killed at night. As a Werewolf you will attack your victim and anyone that visits them. Your attack goes through night immunity. As a Werewolf you may choose to stay home and attack anyone who visits you.', 'Kill everyone who would oppose you.', 3, 0, 0, 0, 0);

-- ** add games mock data

insert into `partyrdb`.`games` (`game_name`, `min_players_num`, `max_players_num`, `min_age`, `average_game_duration`) 
  values ('Black Hand', 5, 15, 18, 30);

-- ** add lobby mock data

insert into `partyrdb`.`lobby` (`game_room_name`, `game_name`, `host_email`, `number_of_players`) 
  values ('ziploc bags box tablet stand', 'Black Hand', 'coty.dawson@gmail.com', 5);

-- ** add black_hand_rooms mock data

insert into `partyrdb`.`black_hand_rooms` (`game_room_name`, `email`, `preferred_faction`, `role_name`, `player_status`) 
  values ('ziploc bags box tablet stand', 'coty.dawson@gmail.com', 'Townie', 'Bodyguard', 'ALIVE');
insert into `partyrdb`.`black_hand_rooms` (`game_room_name`, `email`, `preferred_faction`, `role_name`, `player_status`) 
  values ('ziploc bags box tablet stand', 'lanawood@gmail.com', 'Monster', 'Werewolf', 'ALIVE');
insert into `partyrdb`.`black_hand_rooms` (`game_room_name`, `email`, `preferred_faction`, `role_name`, `player_status`) 
  values ('ziploc bags box tablet stand', 'cheesecake@gmail.com', 'BlackHand', 'Forget', 'ALIVE');
insert into `partyrdb`.`black_hand_rooms` (`game_room_name`, `email`, `preferred_faction`, `role_name`, `player_status`) 
  values ('ziploc bags box tablet stand', 'timmy7@gmail.com', 'Monster', 'Doctor', 'ALIVE');
insert into `partyrdb`.`black_hand_rooms` (`game_room_name`, `email`, `preferred_faction`, `role_name`, `player_status`) 
  values ('ziploc bags box tablet stand', 'obtrusivemonks@gmail.com', 'Monster', 'Jailor', 'ALIVE');
