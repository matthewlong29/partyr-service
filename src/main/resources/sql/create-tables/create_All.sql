-- ** drop all tables

drop table if exists TownGamesInstance;
drop table if exists TownGames;
drop table if exists Relationships;
drop table if exists Users;
drop table if exists Themes;
drop table if exists Games;

-- ** create Themes table

create table if not exists Themes (
  theme_id int auto_increment,
  theme_name varchar(32) not null,
  primary key (theme_id)
) ENGINE = INNODB;

-- ** create Users table

create table if not exists Users (
  user_id int auto_increment,
  user_name varchar(32) unique not null,
  email varchar(64) unique not null,
  password varchar(64) not null,
  joined_date timestamp default current_timestamp,
  online_status varchar(8) default 'OFFLINE',
  theme_id int default 0,
  age int,
  primary key (user_id),
  foreign key (theme_id) references Themes(theme_id),
  constraint limit_online_status check (online_status in ('ONLINE', 'OFFLINE'))
) ENGINE = INNODB;

-- ** create Relationships table

create table if not exists Relationships (
  relationship_id int auto_increment,
  relating_name varchar(32) not null,
  related_name varchar(32) not null,
  relationship_type varchar(16) not null, -- 'FRIEND' or 'BLOCK'
  primary key (relationship_id),
  foreign key (relating_name) references Users(user_name),
  foreign key (related_name) references Users(user_name),
  constraint limit_relationship_type check (relationship_type in ('FRIEND', 'BLOCK'))
) ENGINE = INNODB;

-- ** create Achievements table

create table if not exists Achievements (
	achievement_id int auto_increment,
    achievement_name varchar(32) not null,
    primary key (achievement_id)
) ENGINE = INNODB;

-- ** create Games table

create table if not exists Games (
  game_id int auto_increment,
  game_name varchar(32) unique not null,
  min_players_num int not null,
  max_players_num int not null,
  min_age int not null,
  average_game_duration int not null,
  primary key (game_id)
) ENGINE = INNODB;

-- ** create TownGames table

create table if not exists TownGames (
  game_instance_id int auto_increment not null,
  number_of_players int not null,
  game_start_time timestamp not null,
  game_end_time timestamp not null,
  primary key (game_instance_id)
) ENGINE = INNODB;

-- ** create TownGameInstance table

create table if not exists TownGamesInstance (
  game_instance_id int not null,
  player varchar(32) not null,
  primary key (player),
  foreign key (player) references Users(user_name),
  foreign key (game_instance_id) references TownGames(game_instance_id)
) ENGINE = INNODB;

