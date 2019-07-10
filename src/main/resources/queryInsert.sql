--USERS--
insert into users(user_id,username, full_name) values (nextval('user_id_seq'), 'userA', 'kojiro sasaki');
insert into users(user_id,username, full_name) values (nextval('user_id_seq'), 'userB', 'sasaki');
insert into users(user_id,username, full_name) values (nextval('user_id_seq'), 'userC', 'kenshin');

--LOOKUP--
insert into lookup(lookup_id, name, category_type) values (nextval('lookup_id_seq'), 'PromoCard', 'menu_type');
insert into lookup(lookup_id, name, category_type) values (nextval('lookup_id_seq'), 'CategoryCard', 'menu_type');
insert into lookup(lookup_id, name, category_type) values (nextval('lookup_id_seq'), 'FlashSaleCard', 'menu_type');
insert into lookup(lookup_id, name, category_type) values (nextval('lookup_id_seq'), 'HistoryCard', 'menu_type');
insert into lookup(lookup_id, name, category_type) values (nextval('lookup_id_seq'), 'NewsCard', 'menu_type');

--USER_GROUP--
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),1,1,1);
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),2,1,2);
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),3,1,3);
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),4,1,4);
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),5,1,5);

insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),1,2,1);
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),2,2,2);
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),3,2,3);
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),4,2,4);
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),5,2,5);

insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),1,3,1);
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),2,3,2);
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),3,3,3);
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),4,3,4);
insert into user_group(user_group_id, lookup_id, user_id, view_seq) values (nextval('user_group_id_seq'),5,3,5);
