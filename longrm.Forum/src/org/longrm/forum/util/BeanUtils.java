package org.longrm.forum.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.longrm.forum.bean.BasicFile;
import org.longrm.forum.bean.BasicRole;
import org.longrm.forum.bean.BasicTopic;
import org.longrm.forum.bean.Forum;
import org.longrm.forum.bean.Friend;
import org.longrm.forum.bean.Message;
import org.longrm.forum.bean.User;
import org.longrm.forum.bean.UserInfo;

public class BeanUtils {

	/**
	 * 映射bean的主方法
	 * 
	 * @param bean
	 * @param request
	 */
	public static void populate(Object bean, HttpServletRequest request) {
		// 取出bean里的所有字段
		Class beanClass = bean.getClass();
		Field[] fields = beanClass.getDeclaredFields();

		// 将map里的值赋给bean
		try {
			Date tmpDate = new Date(0);
			Timestamp tmpTs = new Timestamp(0);
			for (int i = 0; i < fields.length; i++) {
				String fieldname = fields[i].getName();
				Class fieldtype = fields[i].getType();

				// 过滤掉非法字符
				Object fieldvalue = request.getParameter(fieldname);
				if(fieldvalue==null || fieldvalue.equals(""))
					continue;
				// 如果是java.sql.Date类型，则将String转换成Date
				if (fieldtype.isInstance(tmpDate)) {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					long milliseconds = df.parse((String) fieldvalue).getTime();
					fieldvalue = new Date(milliseconds);
				}
				else if(fieldtype.isInstance(tmpTs)) {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					long milliseconds = df.parse((String) fieldvalue).getTime();
					fieldvalue = new Timestamp(milliseconds);
				}

				String methodname = getMethodName(fieldname, "set");
				Method method = beanClass.getMethod(methodname, fieldtype);
				method.invoke(bean, fieldvalue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取字段的get/set方法名
	 * 
	 * @param fieldname
	 * @return
	 */
	public static String getMethodName(String fieldname, String type) {
		char upper = Character.toUpperCase(fieldname.charAt(0));
		return type + upper + fieldname.substring(1);
	}
	
	/**
	 * 将rs里的值set入topic
	 * @param bt
	 * @param rs
	 * @throws SQLException
	 */
	public static void populateTopic(BasicTopic bt, ResultSet rs) throws SQLException {
		bt.setId(rs.getString("id"));
		bt.setTitle(ServletUtils.filter(rs.getString("title")));
		bt.setPoster(rs.getString("poster"));
		bt.setPost_time(rs.getTimestamp("post_time"));
		bt.setPost_ip(rs.getString("post_ip"));
		bt.setModifier(rs.getString("modifier"));
		bt.setModify_time(rs.getTimestamp("modify_time"));
		bt.setModify_ip(rs.getString("modify_ip"));
		bt.setForum_id(rs.getString("forum_id"));
		
		// 用流读取content
		Reader reader = rs.getCharacterStream("content");
		if (reader != null){
			BufferedReader bufReader = new BufferedReader(reader);
			StringBuffer strBuf = new StringBuffer();
			try {
				String info = bufReader.readLine();
				while(info!=null) {
					strBuf.append(info+"\n");
					info = bufReader.readLine();
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			bt.setContent(strBuf.toString());
		}else{
			bt.setContent("");
		}
	}
	
	/**
	 * 将rs里的值set入file
	 * @param file
	 * @param rs
	 * @throws SQLException
	 */
	public static void populateFile(BasicFile file, ResultSet rs) throws SQLException {
		file.setId(rs.getString("id"));
		file.setClick(rs.getInt("click"));
		file.setFilename(rs.getString("filename"));
		file.setFilesize(rs.getInt("filesize"));
	}

	/**
	 * 将rs里的值set入user
	 * @param user
	 * @param rs
	 * @throws SQLException
	 */
	public static void populateUser(User user, ResultSet rs) throws SQLException {
		user.setName(rs.getString("name"));
		user.setSex(rs.getString("sex"));
		user.setBirthday(rs.getDate("birthday"));
		user.setHometown(rs.getString("hometown"));
		user.setQq(rs.getString("qq"));
		user.setEmail(rs.getString("email"));
		user.setIspublic(rs.getString("ispublic"));
		user.setBlog(rs.getString("blog"));
		user.setSelf_sign(rs.getString("self_sign"));
		user.setTopic_sign(rs.getString("topic_sign"));
		user.setHead(rs.getString("head"));
		user.setStatus(rs.getInt("status"));
		user.setRole_id(rs.getString("role_id"));
		user.setSys_role_id(rs.getString("sys_role_id"));
	}

	/**
	 * 将rs里的值set入user_info
	 * @param user_info
	 * @param rs
	 * @throws SQLException
	 */
	public static void populateUserInfo(UserInfo user_info, ResultSet rs) throws SQLException {
		user_info.setSouls(rs.getInt("souls"));
		user_info.setTopics(rs.getInt("topics"));
		user_info.setNotes(rs.getInt("notes"));
		user_info.setTime(rs.getInt("time"));
		user_info.setMk1(rs.getInt("mk1"));
		user_info.setMk1_name(rs.getString("mk1_name"));
		user_info.setMk2(rs.getInt("mk2"));
		user_info.setMk2_name(rs.getString("mk2_name"));
		user_info.setMk3(rs.getInt("mk3"));
		user_info.setMk3_name(rs.getString("mk3_name"));
		user_info.setMk1_unit(rs.getString("mk1_unit"));
		user_info.setMk2_unit(rs.getString("mk2_unit"));
		user_info.setMk3_unit(rs.getString("mk3_unit"));
	}

	/**
	 * 将rs里的值set入user
	 * @param user
	 * @param rs
	 * @throws SQLException
	 */
	public static void populateUserAll(User user, ResultSet rs) throws SQLException {
		populateUser(user, rs);
		user.setId(rs.getString("id"));
		user.setPassword(rs.getString("password"));
		user.setAccess_ip(rs.getString("access_ip"));
		user.setAccess_time(rs.getTimestamp("access_time"));
		user.setHowdoknow(rs.getString("howdoknow"));
		user.setQuestion(rs.getString("question"));
		user.setAnswer(rs.getString("answer"));
		user.setRegister_ip(rs.getString("register_ip"));
		user.setRegister_time(rs.getDate("register_time"));
	}

	/**
	 * 将rs里的值set入forum
	 * @param forum
	 * @param rs
	 * @throws SQLException
	 */
	public static void populateForum(Forum forum, ResultSet rs) throws SQLException {
		forum.setId(rs.getString("id"));
		forum.setName(rs.getString("name"));
		forum.setLevel_index(rs.getInt("level_index"));
		forum.setRec_no(rs.getInt("rec_no"));
		forum.setAuthority(rs.getInt("authority"));
		forum.setDescpt(rs.getString("descpt"));
		forum.setRule(rs.getString("rule"));
		forum.setSymbol(rs.getString("symbol"));
		forum.setUp_forum_id(rs.getString("up_forum_id"));
	}
	
	/**
	 * 将rs里的值set入role
	 * @param role
	 * @param rs
	 * @throws SQLException
	 */
	public static void populateRole(BasicRole role, ResultSet rs) throws SQLException {
		role.setId(rs.getString("id"));
		role.setName(rs.getString("name"));
		role.setAuthority(rs.getInt("authority"));
	}

	/**
	 * 将rs里的值set入friend
	 * @param friend
	 * @param rs
	 * @throws SQLException
	 */
	public static void populateFriend(Friend friend, ResultSet rs) throws SQLException {
		friend.setMe(rs.getString("me"));
		friend.setFriend(rs.getString("friend"));
		friend.setTime(rs.getTimestamp("time"));
		friend.setDescription(rs.getString("description"));
	}
	
	/**
	 * 将rs里的值set入message
	 * @param message
	 * @param rs
	 * @throws SQLException
	 */
	public static void populateMessage(Message message, ResultSet rs) throws SQLException {
		message.setId(rs.getString("id"));
		message.setTitle(rs.getString("title"));
		message.setContent(rs.getString("content"));
		message.setTime(rs.getTimestamp("time"));
		message.setSender(rs.getString("sender"));
		message.setReceiver(rs.getString("receiver"));
	}

}
