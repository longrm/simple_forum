prompt PL/SQL Developer import file
prompt Created on 2008年7月2日 by longrm
set feedback off
set define off
prompt Loading ROLE...
insert into ROLE (ID, NAME, AUTHORITY, MINCOUNT, MAXCOUNT)
values ('-1', '负级会员', -9999, -999999999, 0);
insert into ROLE (ID, NAME, AUTHORITY, MINCOUNT, MAXCOUNT)
values ('1', '一级会员', 10, 0, 100);
insert into ROLE (ID, NAME, AUTHORITY, MINCOUNT, MAXCOUNT)
values ('2', '二级会员', 20, 100, 500);
commit;
prompt 3 records loaded
set feedback on
set define on
prompt Done.
