create table if not exists Games (
  game_id int auto_increment,
  game_name varchar(32) unique not null,
  min_players_num int not null,
  max_players_num int not null,
  min_age int not null,
  average_game_duration int not null,
  primary key (game_id)
) ENGINE = INNODB;