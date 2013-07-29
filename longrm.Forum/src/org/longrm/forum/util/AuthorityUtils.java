package org.longrm.forum.util;

import java.util.Map;

import org.longrm.forum.bean.Forum;
import org.longrm.forum.bean.Role;
import org.longrm.forum.bean.SysRole;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.RoleService;

public class AuthorityUtils {
	

	/**
	 * ���Ȩ�޿���
	 * @param authority
	 * @param user
	 * @return
	 */
	public static boolean checkAuthority(int authority, User user) {
		// ��Ȩ������
		if(authority<=0)
			return true;
		// �û�δ��½
		if(user==null)
			return false;

		// ȡ����ɫ��Ϣ
		RoleService rs = RoleService.getInstance();
		Map<String, Role> roleMap = rs.getRoleMap();
		Map<String, SysRole> sysRoleMap = rs.getSysRoleMap();
		int userAuthority;
		if(user.getSys_role_id().equals(Constant.GENERAL_USER))
			userAuthority = ((Role)roleMap.get(user.getRole_id())).getAuthority();
		else
			userAuthority = ((SysRole)sysRoleMap.get(user.getSys_role_id())).getAuthority();
		// û���㹻��Ȩ��
		if(userAuthority<authority) {
			return false;
		}
		return true;
	}
	
	/**
	 * ������Ȩ��
	 * @param forum
	 * @param user
	 * @return
	 */
	public static boolean isMaster(String master, User user) {
		if(user==null || user.getSys_role_id().equals(Constant.GENERAL_USER))
			return false;
		if (user.getSys_role_id().equals(Constant.ADMIN) 
				|| user.getSys_role_id().equals(Constant.SUPER_MASTER))
			return true;
		if(master != null && user.getSys_role_id().equals(Constant.SINGLE_MASTER) 
				&& master.indexOf(user.getName()) != -1)
			return true;
		return false;
	}
	
}
