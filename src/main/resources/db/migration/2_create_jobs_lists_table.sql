create table jobs_lists (users_lists_id integer not null, created datetime, edited datetime, user_name varchar(255), primary key (users_lists_id)) engine=MyISAM
create table jobs_lists_owner (jobs_list_users_lists_id integer not null, owner_user_id integer not null, primary key (jobs_list_users_lists_id, owner_user_id)) engine=MyISAM
create table users_lists (user_id integer not null, users_lists_id integer not null, primary key (user_id, users_lists_id)) engine=MyISAM
alter table jobs_lists_owner add constraint FK2nfosoqqa1t8kcsw18ahf5xvd foreign key (owner_user_id) references users (user_id)
alter table jobs_lists_owner add constraint FK7aygdwojb16nq9tsdymabcko8 foreign key (jobs_list_users_lists_id) references jobs_lists (users_lists_id)
alter table users_lists add constraint FK56qygw4fdun4dxd2loc62ua6 foreign key (users_lists_id) references jobs_lists (users_lists_id)
alter table users_lists add constraint FKjgo95u0dtudqcry0l04rkjmfp foreign key (user_id) references users (user_id)