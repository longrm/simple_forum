prompt PL/SQL Developer import file
prompt Created on 2008��7��2�� by longrm
set feedback off
set define off
prompt Loading SYS_ROLE...
insert into SYS_ROLE (ID, NAME, AUTHORITY)
values ('Administrator', '����Ա', 500);
insert into SYS_ROLE (ID, NAME, AUTHORITY)
values ('SuperMaster', '��������', 400);
insert into SYS_ROLE (ID, NAME, AUTHORITY)
values ('SingleMaster', '����', 200);
insert into SYS_ROLE (ID, NAME, AUTHORITY)
values ('GeneralUser', '��ͨ��Ա', 0);
insert into SYS_ROLE (ID, NAME, AUTHORITY)
values ('HonorUser', '������Ա', 200);
commit;
prompt 5 records loaded
set feedback on
set define on
prompt Done.
