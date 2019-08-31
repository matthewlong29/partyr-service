create table if not exists Users (
  user_id int auto_increment,
  user_name varchar(32) not null,
  email varchar(64) not null,
  joined_date date,
  theme_id int not null,
  age int,
  password varchar(64) not null,
  primary key (user_id)
) ENGINE=INNODB;