create table if not exists Themes (
  theme_id int auto_increment,
  theme_name varchar(32) not null,
  primary key (theme_id)
) ENGINE = INNODB;