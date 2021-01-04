create table hibernate_sequence (next_val bigint) engine=MyISAM;
insert into hibernate_sequence values ( 1 );
create table jobs (job_id integer not null, content varchar(255), created datetime, edited datetime, title varchar(255), jobs_list_id integer, primary key (job_id)) engine=MyISAM;
create table jobs_lists (jobs_list_id integer not null, created datetime, edited datetime, name varchar(255), user_id integer not null, primary key (jobs_list_id)) engine=MyISAM;
create table roles (role_id integer not null, role varchar(255), primary key (role_id)) engine=MyISAM;
create table user_role (user_id integer not null, role_id integer not null, primary key (user_id, role_id)) engine=MyISAM;
create table users (user_id integer not null, active bit, email varchar(255), last_name varchar(255), name varchar(255), password varchar(255), user_name varchar(255), primary key (user_id)) engine=MyISAM;
INSERT INTO `roles` (`role_id`, `role`) VALUES ('1', 'ADMIN'), ('2', 'USER');