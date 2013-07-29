package org.longrm.forum.util;

public class Constant {
	
	// 系统角色
	public final static String GENERAL_USER = "GeneralUser";
	public final static String HONOR_USER = "HonorUser";
	public final static String SINGLE_MASTER = "SingleMaster";
	public final static String SUPER_MASTER = "SuperMaster";
	public final static String ADMIN = "Administrator";
	
	// 默认头像
	public final static String DEFAUTL_HEAD = "images/head/default.jpg";
	
	// 默认文件上传路径
	public final static String DEFAULT_UPLOAD = "/upload/";
	
	// 分页控制
	public final static int FORUM_PAGESIZE = 20;
	public final static int TOPIC_PAGESIZE = 10;
	public final static int SEARCH_PAGESIZE = 20;
	
	// 角色权限
	public final static int NOT_LOGINED = 0;
	
	// 积分系统
	public final static int TOPIC_MK1 = 0;
	public final static int TOPIC_MK2 = 0;
	public final static int TOPIC_MK3 = 10;
	public final static int RETOPIC_MK1 = 0;
	public final static int RETOPIC_MK2 = 0;
	public final static int RETOPIC_MK3 = 5;
	public final static int SOUL_MK1 = 1;
	public final static int SOUL_MK2 = 0;
	public final static int SOUL_MK3 = 10;
	
	// 升级制度，总分计算规则
	public final static int getTotalMk(int mk1, int mk2, int mk3) {
		return mk3;
	}

}
