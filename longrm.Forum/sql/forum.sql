---------------------------------------------
-- Export file for user FORUM              --
-- Created by longrm on 2008/7/1, 23:31:16 --
---------------------------------------------

spool forum.log

prompt
prompt Creating table ROLE
prompt ===================
prompt
create table ROLE
(
  ID        VARCHAR2(20) not null,
  NAME      VARCHAR2(20) not null,
  AUTHORITY INTEGER not null,
  MINCOUNT  INTEGER,
  MAXCOUNT  INTEGER
)
;
comment on column ROLE.ID
  is '��ɫid';
comment on column ROLE.NAME
  is '����';
comment on column ROLE.AUTHORITY
  is 'Ȩ��';
comment on column ROLE.MINCOUNT
  is '��С����';
comment on column ROLE.MAXCOUNT
  is '������';
alter table ROLE
  add constraint ��ɫID primary key (ID);

prompt
prompt Creating table SYS_ROLE
prompt =======================
prompt
create table SYS_ROLE
(
  ID        VARCHAR2(20) not null,
  NAME      VARCHAR2(20) not null,
  AUTHORITY INTEGER not null
)
;
comment on column SYS_ROLE.ID
  is 'ϵͳ��ɫid';
comment on column SYS_ROLE.NAME
  is '����';
comment on column SYS_ROLE.AUTHORITY
  is 'Ȩ��';
alter table SYS_ROLE
  add constraint ϵͳ��ɫID primary key (ID);

prompt
prompt Creating table FM_USER
prompt ======================
prompt
create table FM_USER
(
  ID            VARCHAR2(20) not null,
  NAME          VARCHAR2(20) not null,
  PASSWORD      VARCHAR2(50) not null,
  EMAIL         VARCHAR2(50) not null,
  ISPUBLIC      VARCHAR2(10),
  SEX           VARCHAR2(10),
  BIRTHDAY      DATE,
  HOMETOWN      VARCHAR2(20),
  QQ            VARCHAR2(12),
  REGISTER_TIME DATE,
  ACCESS_TIME   DATE,
  BLOG          VARCHAR2(50),
  SELF_SIGN     VARCHAR2(100),
  TOPIC_SIGN    VARCHAR2(1000),
  HEAD          VARCHAR2(50),
  QUESTION      VARCHAR2(50),
  ANSWER        VARCHAR2(50),
  ROLE_ID       VARCHAR2(20),
  SYS_ROLE_ID   VARCHAR2(20),
  REGISTER_IP   VARCHAR2(20),
  ACCESS_IP     VARCHAR2(20),
  STATUS        INTEGER default 0 not null,
  HOWDOKNOW     VARCHAR2(20),
  NOTES         INTEGER default 0 not null
)
;
comment on column FM_USER.ID
  is '�û�id';
comment on column FM_USER.NAME
  is '����';
comment on column FM_USER.PASSWORD
  is '����';
comment on column FM_USER.EMAIL
  is 'e-mail';
comment on column FM_USER.ISPUBLIC
  is '�Ƿ񹫿�';
comment on column FM_USER.SEX
  is '�Ա�';
comment on column FM_USER.BIRTHDAY
  is '����';
comment on column FM_USER.HOMETOWN
  is '����';
comment on column FM_USER.QQ
  is 'qq';
comment on column FM_USER.REGISTER_TIME
  is 'ע��ʱ��';
comment on column FM_USER.ACCESS_TIME
  is '������ʱ��';
comment on column FM_USER.BLOG
  is 'blog��������վ';
comment on column FM_USER.SELF_SIGN
  is '����ǩ��';
comment on column FM_USER.TOPIC_SIGN
  is '����ǩ��';
comment on column FM_USER.HEAD
  is 'ͷ��';
comment on column FM_USER.QUESTION
  is '��ȫ����';
comment on column FM_USER.ANSWER
  is '��';
comment on column FM_USER.ROLE_ID
  is '��Ա��ɫ';
comment on column FM_USER.SYS_ROLE_ID
  is 'ϵͳ��ɫ';
comment on column FM_USER.REGISTER_IP
  is 'ע��ip';
comment on column FM_USER.ACCESS_IP
  is '����ip';
comment on column FM_USER.STATUS
  is '״̬(0������-1��ֹ���ԣ�-2��ֹ����)';
comment on column FM_USER.HOWDOKNOW
  is '���֪����վ��';
comment on column FM_USER.NOTES
  is '������';
alter table FM_USER
  add constraint �û�ID primary key (ID);
alter table FM_USER
  add constraint ϵͳͷ�� foreign key (SYS_ROLE_ID)
  references SYS_ROLE (ID);
alter table FM_USER
  add constraint �û�ͷ�� foreign key (ROLE_ID)
  references ROLE (ID);

prompt
prompt Creating table FORUM
prompt ====================
prompt
create table FORUM
(
  ID          VARCHAR2(20) not null,
  NAME        VARCHAR2(20) not null,
  REC_NO      INTEGER not null,
  DESCPT      VARCHAR2(200),
  AUTHORITY   INTEGER default 0 not null,
  RULE        VARCHAR2(1000),
  UP_FORUM_ID VARCHAR2(20),
  SYMBOL      VARCHAR2(50),
  LEVEL_INDEX INTEGER
)
;
comment on column FORUM.ID
  is '���id';
comment on column FORUM.NAME
  is '����';
comment on column FORUM.REC_NO
  is '��ţ���������';
comment on column FORUM.DESCPT
  is '����';
comment on column FORUM.AUTHORITY
  is 'Ȩ��';
comment on column FORUM.RULE
  is '���';
comment on column FORUM.UP_FORUM_ID
  is '�ϼ����id';
comment on column FORUM.SYMBOL
  is 'ͼ��';
comment on column FORUM.LEVEL_INDEX
  is '����';
alter table FORUM
  add constraint ���ID primary key (ID);
alter table FORUM
  add constraint �ϼ����ID foreign key (UP_FORUM_ID)
  references FORUM (ID);

prompt
prompt Creating table FORUM_MASTER
prompt ===========================
prompt
create table FORUM_MASTER
(
  ID     VARCHAR2(20) not null,
  MASTER VARCHAR2(20) not null
)
;
alter table FORUM_MASTER
  add constraint ���_����ID primary key (ID, MASTER);
alter table FORUM_MASTER
  add constraint ��� foreign key (ID)
  references FORUM (ID);

prompt
prompt Creating table MANAGE_TOPIC_LOG
prompt ===============================
prompt
create table MANAGE_TOPIC_LOG
(
  ID       VARCHAR2(20) not null,
  MANAGER  VARCHAR2(20) not null,
  TOPIC_ID VARCHAR2(20) not null,
  TYPE     VARCHAR2(20) not null,
  FORUM_ID VARCHAR2(20) not null,
  TIME     TIMESTAMP(6) not null
)
;
comment on column MANAGE_TOPIC_LOG.ID
  is 'id';
comment on column MANAGE_TOPIC_LOG.MANAGER
  is '������';
comment on column MANAGE_TOPIC_LOG.TOPIC_ID
  is '����id';
comment on column MANAGE_TOPIC_LOG.TYPE
  is '�������ͣ�top,soul,color,lock,close,move,push,down,delete��';
comment on column MANAGE_TOPIC_LOG.FORUM_ID
  is '�������id';
comment on column MANAGE_TOPIC_LOG.TIME
  is '����ʱ��';
alter table MANAGE_TOPIC_LOG
  add constraint ID primary key (ID);

prompt
prompt Creating table MESSAGE
prompt ======================
prompt
create table MESSAGE
(
  ID       VARCHAR2(20) not null,
  TITLE    VARCHAR2(20) not null,
  CONTENT  VARCHAR2(200) not null,
  SENDER   VARCHAR2(20) not null,
  RECEIVER VARCHAR2(20) not null,
  TIME     TIMESTAMP(6) not null,
  ISNEW    INTEGER default 1 not null
)
;
comment on column MESSAGE.ID
  is '����Ϣid';
comment on column MESSAGE.TITLE
  is '����';
comment on column MESSAGE.CONTENT
  is '����';
comment on column MESSAGE.SENDER
  is '������';
comment on column MESSAGE.RECEIVER
  is '������';
comment on column MESSAGE.TIME
  is '����ʱ��';
comment on column MESSAGE.ISNEW
  is '�Ƿ��Ѷ���1δ����0�Ѷ���';
alter table MESSAGE
  add constraint ����ϢKEY primary key (ID);

prompt
prompt Creating table OPTIONS
prompt ======================
prompt
create table OPTIONS
(
  ID    VARCHAR2(20) not null,
  VALUE VARCHAR2(20) not null
)
;
comment on column OPTIONS.ID
  is '��';
comment on column OPTIONS.VALUE
  is 'ֵ';
alter table OPTIONS
  add constraint �� primary key (ID);

prompt
prompt Creating table RE_TOPIC
prompt =======================
prompt
create table RE_TOPIC
(
  ID          VARCHAR2(20) not null,
  TOPIC_ID    VARCHAR2(20) not null,
  TITLE       VARCHAR2(100) not null,
  CONTENT     CLOB not null,
  POSTER      VARCHAR2(20) not null,
  POST_TIME   TIMESTAMP(6) not null,
  MODIFIER    VARCHAR2(20),
  MODIFY_TIME TIMESTAMP(6),
  POST_IP     VARCHAR2(20) not null,
  FORUM_ID    VARCHAR2(20) not null,
  MODIFY_IP   VARCHAR2(20)
)
;
comment on column RE_TOPIC.ID
  is '����id';
comment on column RE_TOPIC.TOPIC_ID
  is '����id';
comment on column RE_TOPIC.TITLE
  is '����';
comment on column RE_TOPIC.CONTENT
  is '����';
comment on column RE_TOPIC.POSTER
  is '����';
comment on column RE_TOPIC.POST_TIME
  is '��������';
comment on column RE_TOPIC.MODIFIER
  is '�޸���';
comment on column RE_TOPIC.MODIFY_TIME
  is '����޸�����';
comment on column RE_TOPIC.POST_IP
  is '����ip';
comment on column RE_TOPIC.FORUM_ID
  is '�������id';
comment on column RE_TOPIC.MODIFY_IP
  is '����޸�ip';
alter table RE_TOPIC
  add constraint ����ID primary key (ID);
alter table RE_TOPIC
  add constraint ����_���ID foreign key (FORUM_ID)
  references FORUM (ID);

prompt
prompt Creating table TOPIC
prompt ====================
prompt
create table TOPIC
(
  ID           VARCHAR2(20) not null,
  TITLE        VARCHAR2(100) not null,
  CONTENT      CLOB not null,
  POSTER       VARCHAR2(20) not null,
  POST_TIME    TIMESTAMP(6) not null,
  MODIFIER     VARCHAR2(20),
  MODIFY_TIME  TIMESTAMP(6),
  FORUM_ID     VARCHAR2(20) not null,
  AUTHORITY    INTEGER default 0 not null,
  POST_IP      VARCHAR2(20),
  MODIFY_IP    VARCHAR2(20),
  TOP          INTEGER default 0 not null,
  SOUL         INTEGER default 0 not null,
  COLOR        VARCHAR2(20),
  BOLD         VARCHAR2(10),
  KIND         VARCHAR2(20),
  CLICK        INTEGER default 0 not null,
  RE_TOPICS    INTEGER default 0 not null,
  LAST_TIME    TIMESTAMP(6),
  LAST_REPLIER VARCHAR2(20),
  STATUS       INTEGER default 0 not null
)
;
comment on column TOPIC.ID
  is '����id';
comment on column TOPIC.TITLE
  is '����';
comment on column TOPIC.CONTENT
  is '����';
comment on column TOPIC.POSTER
  is '����';
comment on column TOPIC.POST_TIME
  is '��������';
comment on column TOPIC.MODIFIER
  is '�޸���';
comment on column TOPIC.MODIFY_TIME
  is '����޸�����';
comment on column TOPIC.FORUM_ID
  is '�������id';
comment on column TOPIC.AUTHORITY
  is '�Ķ�Ȩ��';
comment on column TOPIC.POST_IP
  is '����ip';
comment on column TOPIC.MODIFY_IP
  is '����޸�ip';
comment on column TOPIC.TOP
  is '�ö�';
comment on column TOPIC.SOUL
  is '����';
comment on column TOPIC.COLOR
  is '��������ɫ��';
comment on column TOPIC.BOLD
  is '���������壩';
comment on column TOPIC.KIND
  is '�����ͨ��ͶƱ������......��';
comment on column TOPIC.CLICK
  is '�����';
comment on column TOPIC.RE_TOPICS
  is '�ظ���';
comment on column TOPIC.LAST_TIME
  is '���ظ�����';
comment on column TOPIC.LAST_REPLIER
  is '���ظ���';
comment on column TOPIC.STATUS
  is '����״̬��0������-1������-2�ر�...��';
alter table TOPIC
  add constraint ����ID primary key (ID);
alter table TOPIC
  add constraint ����_���ID foreign key (FORUM_ID)
  references FORUM (ID);

prompt
prompt Creating table USER_INFO
prompt ========================
prompt
create table USER_INFO
(
  ID     VARCHAR2(20) not null,
  SOULS  INTEGER default 0 not null,
  TOPICS INTEGER default 0 not null,
  NOTES  INTEGER default 0 not null,
  MK1    INTEGER default 0 not null,
  MK2    INTEGER default 0 not null,
  MK3    INTEGER default 0 not null,
  TIME   INTEGER default 0 not null
)
;
comment on column USER_INFO.ID
  is '����id';
comment on column USER_INFO.SOULS
  is '������';
comment on column USER_INFO.TOPICS
  is '����';
comment on column USER_INFO.NOTES
  is '����';
comment on column USER_INFO.MK1
  is '����1';
comment on column USER_INFO.MK2
  is '����2';
comment on column USER_INFO.MK3
  is '����3';
comment on column USER_INFO.TIME
  is '����ʱ�䣨��λ���֣�';
alter table USER_INFO
  add constraint ����ID primary key (ID);

prompt
prompt Creating view V_FORUM1
prompt ======================
prompt
create or replace view v_forum1 as
Select forum."ID",forum."NAME",forum."REC_NO",forum."DESCPT",forum."AUTHORITY",forum."RULE",forum."UP_FORUM_ID",forum."SYMBOL",forum."LEVEL_INDEX", t.Result Master From forum,
(select Id, substr(max(sys_connect_by_path(Master, ',')), 2) result
from (select Id, Master, row_number() over(order by Id, Master desc) rn, row_number() over(order by Id, Master desc) - 1 rn1,
              --+dense_rank() over(order by a)) rn,
              max(Master) over(partition by Id) bs
       from forum_master)
start with Master = bs
connect by rn1 = prior rn and Id = prior Id
group by Id) t
Where forum.Id=t.Id(+);

prompt
prompt Creating view V_FORUM2
prompt ======================
prompt
create or replace view v_forum2 as
Select forum."ID",forum."NAME",forum."REC_NO",forum."DESCPT",forum."AUTHORITY",forum."RULE",forum."UP_FORUM_ID",forum."SYMBOL",forum."LEVEL_INDEX", t.Result Master From forum,
(select Id, substr(max(sys_connect_by_path(Master, ',')), 2) result
from (select Id, Master,
              (row_number() over(order by Id, Master desc)
               --row_number()-1 over(order by a,b desc) rn1
                + dense_rank() over(order by Id)) rn, max(Master) over(partition by Id) bs
       from forum_master)
start with Master = bs
connect by rn - 1 = prior rn
group by Id) t
Where forum.Id=t.Id(+);

prompt
prompt Creating view V_FORUM_MASTER
prompt ============================
prompt
create or replace view v_forum_master as
select forum."ID",forum."NAME",forum."REC_NO",forum."DESCPT",forum."AUTHORITY",forum."RULE",forum."UP_FORUM_ID",forum."SYMBOL",forum."LEVEL_INDEX", fm.master
  from forum,
       (select id, wm_concat(master) master from forum_master group by id) fm
 where forum.id = fm.id(+);

prompt
prompt Creating view V_FORUM_SUM_TOPICS
prompt ================================
prompt
create or replace view v_forum_sum_topics as
select forum_id, count(*) topics, sum(re_topics) re_topics, max(last_time) last_time from topic group by forum_id;

prompt
prompt Creating view V_USER_INFO_TRANS
prompt ===============================
prompt
create or replace view v_user_info_trans as
select f."ID",
       f."SOULS",
       f."TOPICS",
       f."NOTES",
       f."MK1",
       f."MK2",
       f."MK3",
       f."TIME",
       (select value from options where id='mk1_name') mk1_name,
       (select value from options where id='mk2_name') mk2_name,
       (select value from options where id='mk3_name') mk3_name,
       (select value from options where id='mk1_unit') mk1_unit,
       (select value from options where id='mk2_unit') mk2_unit,
       (select value from options where id='mk3_unit') mk3_unit
  from user_info f;

prompt
prompt Creating view V_RE_TOPIC_AUTHOR
prompt ===============================
prompt
create or replace view v_re_topic_author as
select r."ID",r."TOPIC_ID",r."TITLE",r."CONTENT",r."POSTER",r."POST_TIME",r."MODIFIER",r."MODIFY_TIME",r."POST_IP",r."FORUM_ID",r."MODIFY_IP",
       u.id user_id,
       u.name,
       u.sex,
       u.birthday,
       u.hometown,
       u.qq,
       u.email,
       u.ispublic,
       u.blog,
       u.self_sign,
       u.topic_sign,
       u.head,
       u.status,
       u.role_id,
       u.sys_role_id,
         f.souls,
         f.topics,
         f.notes,
         f.mk1,
         f.mk2,
         f.mk3,
         f.mk1_name,
         f.mk2_name,
         f.mk3_name,
         f.mk1_unit,
         f.mk2_unit,
         f.mk3_unit,
         f.TIME
    from re_topic r, fm_user u, v_user_info_trans f
   where r.poster = u.name(+) and u.id=f.id(+);

prompt
prompt Creating view V_TOPIC_AUTHOR
prompt ============================
prompt
create or replace view v_topic_author as
select t."ID",t."TITLE",t."CONTENT",t."POSTER",t."POST_TIME",t."MODIFIER",t."MODIFY_TIME",t."FORUM_ID",t."AUTHORITY",t."POST_IP",t."MODIFY_IP",t."TOP",t."SOUL",t."COLOR",t."BOLD",t."KIND",t."CLICK",t."RE_TOPICS",t."LAST_TIME",t."LAST_REPLIER",t."STATUS" topic_status,
         u.id user_id,
         u.name,
         u.sex,
         u.birthday,
         u.hometown,
         u.qq,
         u.blog,
         u.email,
         u.ispublic,
         u.self_sign,
         u.topic_sign,
         u.head,
         u.status status,
         u.role_id,
         u.sys_role_id,
         f.souls,
         f.topics,
         f.notes,
         f.mk1,
         f.mk2,
         f.mk3,
         f.mk1_name,
         f.mk2_name,
         f.mk3_name,
         f.mk1_unit,
         f.mk2_unit,
         f.mk3_unit,
         f.TIME
    from topic t, fm_user u, v_user_info_trans f
   where t.poster = u.name(+) and u.id=f.id(+);


spool off
