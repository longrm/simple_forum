prompt PL/SQL Developer import file
prompt Created on 2008年7月2日 by longrm
set feedback off
set define off
prompt Loading OPTIONS...
insert into OPTIONS (ID, VALUE)
values ('mk1_name', '威望');
insert into OPTIONS (ID, VALUE)
values ('mk2_name', '贡献值');
insert into OPTIONS (ID, VALUE)
values ('mk3_name', '金钱');
insert into OPTIONS (ID, VALUE)
values ('mk1_unit', '点');
insert into OPTIONS (ID, VALUE)
values ('mk2_unit', '点');
insert into OPTIONS (ID, VALUE)
values ('mk3_unit', 'RMB');
commit;
prompt 6 records loaded
set feedback on
set define on
prompt Done.
