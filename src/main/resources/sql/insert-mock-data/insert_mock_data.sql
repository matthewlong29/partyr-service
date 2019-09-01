-- ** add Themes data

insert into `PartyGamesDatabase`.`Themes` (`theme_name`) values ('dark');
insert into `PartyGamesDatabase`.`Themes` (`theme_name`) values ('light');

-- ** add Users data

insert into `PartyGamesDatabase`.`Users` (`user_name`, `email`, `theme_id`, `age`, `password`) values ('matthewlong29', 'long.matthew29@gmail.com', 1, 28, 'password123');
insert into `PartyGamesDatabase`.`Users` (`user_name`, `email`, `theme_id`, `age`, `password`) values ('timmy7', 'timmy@gmail.com', 1, 28, '321drowssap');

-- ** add Relationships

insert into `PartyGamesDatabase`.`Relationships` (`relating_name`, `related_name`, `relationship_type`) values ('matthewlong29', 'timmy7', 'FRIEND');

-- ** add Games

insert into `PartyGamesDatabase`.`Games` (`game_name`, `min_players_num`, `max_players_num`, `min_age`, `average_game_duration`) values ('TownGames', 2, 10, 13, 45);

-- ** add TownGames

insert into `PartyGamesDatabase`.`TownGames` (`number_of_players`, `game_start_time`, `game_end_time`) values (8, now(), now());

-- ** add TownGamesInstance

insert into `PartyGamesDatabase`.`TownGamesInstance` (`game_instance_id`, `player`) values (1, 'matthewlong29');