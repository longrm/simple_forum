package org.longrm.forum.util;

import java.util.Map;

import org.longrm.forum.bean.Forum;
import org.longrm.forum.bean.Role;
import org.longrm.forum.bean.SysRole;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.RoleService;

public class AuthorityUtils {
	

	/**
	 * 板块权限控制
	 * @param authority
	 * @param user
	 * @return
	 */
	public static boolean checkAuthority(int authority, User user) {
		// 无权限限制
		if(authority<=0)
			return true;
		// 用户未登陆
		if(user==null)
			return false;

		// 取出角色信息
		RoleService rs = RoleService.getInstance();
		Map<String, Role> roleMap = rs.getRoleMap();
		Map<String, SysRole> sysRoleMap = rs.getSysRoleMap();
		int userAuthority;
		if(user.getSys_role_id().equals(Constant.GENERAL_USER))
			userAuthority = ((Role)roleMap.get(user.getRole_id())).getAuthority();
		else
			userAuthority = ((SysRole)sysRoleMap.get(user.getSys_role_id())).getAuthority();
		// 没有足够的权限
		if(userAuthority<authority) {
			return false;
		}
		return true;
	}
	
	/**
	 * 板块管理权限
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
