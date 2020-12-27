create table jobs (job_id integer not null, created datetime, edited datetime, name varchar(255), user_id integer not null, jobs_list_id integer not null, primary key (job_id)) engine=MyISAM
alter table jobs_lists add column jobs_list_id integer not null
alter table jobs add constraint FKra3g6pshf0p0hv5aisuh3weg8 foreign key (user_id) references users (user_id)
alter table jobs add constraint FKkt5o3lswp0vpwwbbune04rred foreign key (jobs_list_id) references jobs_lists (jobs_list_id)
alter table jobs_lists add constraint FK8givekug7hxnsm3xtykkbi3ct foreign key (user_id) references users (user_id)
alter table user_role add constraint FKt7e7djp752sqn6w22i6ocqy6q foreign key (role_id) references roles (role_id)
alter table user_role add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users (user_id)