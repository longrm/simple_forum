package org.longrm.forum.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class User {
	private String id;
	private String name;
	private String password;
	private String email;
	private String ispublic;
	private String sex;
	private Date birthday;
	private String hometown;
	private String qq;
	private String blog;
	private String self_sign;
	private String topic_sign;
	private String head;
	private Date register_time;
	private Timestamp access_time;
	private String question;
	private String answer;
	private int status;  // ×´Ì¬(0Õý³££»-1½ûÖ¹·¢ÑÔ£»-2½ûÖ¹·ÃÎÊ)
	private String role_id;
	private String sys_role_id;
	private String register_ip;
	private String access_ip;
	private String howdoknow;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIspublic() {
		return ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getSelf_sign() {
		return self_sign;
	}

	public void setSelf_sign(String self_sign) {
		this.self_sign = self_sign;
	}

	public String getTopic_sign() {
		return topic_sign;
	}

	public void setTopic_sign(String topic_sign) {
		this.topic_sign = topic_sign;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public Date getRegister_time() {
		return register_time;
	}

	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}

	public Timestamp getAccess_time() {
		return access_time;
	}

	public void setAccess_time(Timestamp access_time) {
		this.access_time = access_time;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getSys_role_id() {
		return sys_role_id;
	}

	public void setSys_role_id(String sys_role_id) {
		this.sys_role_id = sys_role_id;
	}

	public String getRegister_ip() {
		return register_ip;
	}

	public void setRegister_ip(String register_ip) {
		this.register_ip = register_ip;
	}

	public String getAccess_ip() {
		return access_ip;
	}

	public void setAccess_ip(String access_ip) {
		this.access_ip = access_ip;
	}

	public String getHowdoknow() {
		return howdoknow;
	}

	public void setHowdoknow(String howdoknow) {
		this.howdoknow = howdoknow;
	}
}
