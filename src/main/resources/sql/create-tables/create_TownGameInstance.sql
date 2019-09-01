create table if not exists TownGamesInstance (
  game_instance_id int not null,
  player varchar(32) not null,
  primary key (player),
  foreign key (player) references Users(user_name),
  foreign key (game_instance_id) references TownGames(game_instance_id)
) ENGINE = INNODB;