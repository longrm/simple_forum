prompt PL/SQL Developer import file
prompt Created on 2008��7��2�� by longrm
set feedback off
set define off
prompt Loading OPTIONS...
insert into OPTIONS (ID, VALUE)
values ('mk1_name', '����');
insert into OPTIONS (ID, VALUE)
values ('mk2_name', '����ֵ');
insert into OPTIONS (ID, VALUE)
values ('mk3_name', '��Ǯ');
insert into OPTIONS (ID, VALUE)
values ('mk1_unit', '��');
insert into OPTIONS (ID, VALUE)
values ('mk2_unit', '��');
insert into OPTIONS (ID, VALUE)
values ('mk3_unit', 'RMB');
commit;
prompt 6 records loaded
set feedback on
set define on
prompt Done.
