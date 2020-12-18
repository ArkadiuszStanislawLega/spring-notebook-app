alter table jobs_lists_owner add constraint FK2nfosoqqa1t8kcsw18ahf5xvd foreign key (owner_user_id) references users (user_id)
alter table jobs_lists_owner add constraint FK7aygdwojb16nq9tsdymabcko8 foreign key (jobs_list_users_lists_id) references jobs_lists (users_lists_id)
alter table users_lists add constraint FK56qygw4fdun4dxd2loc62ua6 foreign key (users_lists_id) references jobs_lists (users_lists_id)
alter table users_lists add constraint FKjgo95u0dtudqcry0l04rkjmfp foreign key (user_id) references users (user_id)