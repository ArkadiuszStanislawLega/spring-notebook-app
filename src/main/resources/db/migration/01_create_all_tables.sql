create table roles (role_id integer not null, role varchar(255), primary key (role_id)) engine=MyISAM
create table user_role (user_id integer not null, role_id integer not null, primary key (user_id, role_id)) engine=MyISAM
create table users (user_id integer not null, active bit, email varchar(255), last_name varchar(255), name varchar(255), password varchar(255), user_name varchar(255), primary key (user_id)) engine=MyISAM
alter table jobs_lists add constraint FK8givekug7hxnsm3xtykkbi3ct foreign key (user_id) references users (user_id)
alter table user_role add constraint FKt7e7djp752sqn6w22i6ocqy6q foreign key (role_id) references roles (role_id)
alter table user_role add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users (user_id)