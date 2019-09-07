create table if not exists Achievements (
	achievement_id int auto_increment,
  achievement_name varchar(32) not null,
  primary key (achievement_id)
) ENGINE = INNODB;