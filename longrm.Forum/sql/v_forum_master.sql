---------------------------------------------
-- Export file for user FORUM              --
-- Created by longrm on 2008-7-2, 16:45:18 --
---------------------------------------------

spool v_forum_master.log

prompt
prompt Creating view V_FORUM_MASTER
prompt ============================
prompt
create or replace view v_forum_master as
Select forum.*, t.Result Master From forum,
(select Id, substr(max(sys_connect_by_path(Master, ',')), 2) result
from (select Id, Master, row_number() over(order by Id, Master desc) rn, row_number() over(order by Id, Master desc) - 1 rn1,
							--+dense_rank() over(order by a)) rn,
							max(Master) over(partition by Id) bs
			 from forum_master)
start with Master = bs
connect by rn1 = prior rn and Id = prior Id
group by Id) t
Where forum.Id=t.Id(+)
/


spool off
