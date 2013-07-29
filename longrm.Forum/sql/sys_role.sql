prompt PL/SQL Developer import file
prompt Created on 2008年7月2日 by longrm
set feedback off
set define off
prompt Loading SYS_ROLE...
insert into SYS_ROLE (ID, NAME, AUTHORITY)
values ('Administrator', '管理员', 500);
insert into SYS_ROLE (ID, NAME, AUTHORITY)
values ('SuperMaster', '超级斑竹', 400);
insert into SYS_ROLE (ID, NAME, AUTHORITY)
values ('SingleMaster', '斑竹', 200);
insert into SYS_ROLE (ID, NAME, AUTHORITY)
values ('GeneralUser', '普通会员', 0);
insert into SYS_ROLE (ID, NAME, AUTHORITY)
values ('HonorUser', '荣誉会员', 200);
commit;
prompt 5 records loaded
set feedback on
set define on
prompt Done.
