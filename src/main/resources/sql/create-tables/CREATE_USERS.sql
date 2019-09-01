create table if not exists Users (
  user_id int auto_increment,
  user_name varchar(32) unique not null,
  email varchar(64) unique not null,
  password varchar(64) not null, -- TODO: security
  joined_date timestamp default current_timestamp,
  online_status varchar(8) default 'OFFLINE',
  theme_id int default 0,
  age int,
  primary key (user_id),
  foreign key (theme_id) references Themes(theme_id),
  constraint limit_online_status check (online_status in ('ONLINE', 'OFFLINE'))
) ENGINE = INNODB;