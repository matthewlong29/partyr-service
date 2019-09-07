create table if not exists Relationships (
  relationship_id int auto_increment,
  relating_name varchar(32) not null,
  related_name varchar(32) not null,
  relationship_type varchar(16) not null, -- 'FRIEND' or 'BLOCK'
  primary key (relationship_id),
  foreign key (relating_name) references Users(user_name),
  foreign key (related_name) references Users(user_name),
  unique key `unique_relationship` (`relating_name`,`related_name`),
  constraint limit_relationship_type check (relationship_type in ('FRIEND', 'BLOCK'))
) ENGINE = INNODB;