drop table if exists phones;
drop table if exists users;
drop sequence if exists phones_seq;

create table users (
  id varchar(36) primary key,
  name varchar(100) not null,
  password varchar(100) not null,
  email varchar(100) not null,
  active boolean not null,
  token varchar(500) not null,
  created timestamp not null,
  last_login timestamp not null,
  modified timestamp null
);

create table phones (
  id int primary key auto_increment,
  user_id varchar(36) not null,
  number varchar(10) not null,
  city_code varchar(3) not null,
  country_code varchar(3) not null
);

create sequence phones_seq start with 100 increment by 1;
create unique index user_email_unique_idx on users(email);
alter table phones add constraint phones_users_fk foreign key (user_id) references users(id);
