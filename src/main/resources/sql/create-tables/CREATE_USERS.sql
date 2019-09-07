create table if not exists Users (
  user_id int auto_increment,
  user_name varchar(32) unique not null,
  email varchar(64) unique not null,
  password varchar(64) not null, -- TODO: security..
  joined_date timestamp default current_timestamp,
  online_status varchar(8) default 'OFFLINE',
  ready_to_play_status varchar(16) default 'NOT_READY', -- create trigger cant be ready if offline
  theme_id int default 1,
  age int,
  country varchar(32) not null,
  primary key (user_id),
  foreign key (theme_id) references Themes(theme_id),
  unique key (user_name),
  unique key (email),
  constraint limit_online_status check (online_status in ('ONLINE', 'OFFLINE')),
  constraint limit_ready_to_play_status check (ready_to_play_status in ('READY', 'NOT_READY'))
) ENGINE = INNODB;