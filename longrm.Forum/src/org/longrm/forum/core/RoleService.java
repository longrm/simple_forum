package org.longrm.forum.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.longrm.forum.bean.Role;
import org.longrm.forum.bean.SysRole;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.BeanUtils;

public class RoleService {
	
	// 保存角色信息
	private static Map<String, Role> roleMap;
	private static Map<String, SysRole> sysRoleMap;
	private static RoleService instance = null;
	
	private RoleService() {
		roleMap = requestRoleMap();
		sysRoleMap = requestSysRoleMap();
	}
	
	/**
	 * 获得RoleService的实例
	 * @return
	 */
	public static synchronized RoleService getInstance() {
		if(instance==null)
			instance = new RoleService();
		return instance;
	}
	
	public void update() {
		roleMap = requestRoleMap();
		sysRoleMap = requestSysRoleMap();
	}
	
	public Map<String, Role> getRoleMap() {
		return roleMap;
	}
	
	public Map<String, SysRole> getSysRoleMap() {
		return sysRoleMap;
	}
	
	/**
	 * 获取普通角色的信息
	 * @return
	 */
	private static Map<String, Role> requestRoleMap() {
		HashMap<String, Role> roleMap = new HashMap<String, Role>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "select * from role";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Role role = new Role();
				BeanUtils.populateRole(role, rs);
				role.setMincount(rs.getInt("mincount"));
				role.setMaxcount(rs.getInt("maxcount"));
				roleMap.put(role.getId(), role);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, stmt, rs);
		}
		return roleMap;
	}

	/**
	 * 获取系统角色的信息
	 * @return
	 */
	private static Map<String, SysRole> requestSysRoleMap() {
		HashMap<String, SysRole> roleMap = new HashMap<String, SysRole>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "select * from sys_role";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				SysRole role = new SysRole();
				BeanUtils.populateRole(role, rs);
				roleMap.put(role.getId(), role);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, stmt, rs);
		}
		return roleMap;
	}

}
