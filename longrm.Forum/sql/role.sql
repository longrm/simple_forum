prompt PL/SQL Developer import file
prompt Created on 2008��7��2�� by longrm
set feedback off
set define off
prompt Loading ROLE...
insert into ROLE (ID, NAME, AUTHORITY, MINCOUNT, MAXCOUNT)
values ('-1', '������Ա', -9999, -999999999, 0);
insert into ROLE (ID, NAME, AUTHORITY, MINCOUNT, MAXCOUNT)
values ('1', 'һ����Ա', 10, 0, 100);
insert into ROLE (ID, NAME, AUTHORITY, MINCOUNT, MAXCOUNT)
values ('2', '������Ա', 20, 100, 500);
commit;
prompt 3 records loaded
set feedback on
set define on
prompt Done.
