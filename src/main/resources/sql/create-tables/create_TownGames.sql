create table if not exists TownGames (
  game_instance_id int auto_increment not null,
  number_of_players int not null,
  game_start_time timestamp not null,
  game_end_time timestamp not null,
  primary key (game_instance_id)
) ENGINE = INNODB;