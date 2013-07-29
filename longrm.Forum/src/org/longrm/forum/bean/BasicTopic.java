package org.longrm.forum.bean;

import java.sql.Timestamp;

public class BasicTopic {
	
	// 发表者信息，显示帖子时展示
	private User author;
	private UserInfo author_info;
	private String id;
	private String title;
	private String content;
	private String poster;
	private Timestamp post_time;
	private String post_ip;
	private String modifier;
	private Timestamp modify_time;
	private String modify_ip;
	private String forum_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Timestamp getPost_time() {
		return post_time;
	}

	public void setPost_time(Timestamp post_time) {
		this.post_time = post_time;
	}

	public String getPost_ip() {
		return post_ip;
	}

	public void setPost_ip(String post_ip) {
		this.post_ip = post_ip;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Timestamp getModify_time() {
		return modify_time;
	}

	public void setModify_time(Timestamp modify_time) {
		this.modify_time = modify_time;
	}

	public String getModify_ip() {
		return modify_ip;
	}

	public void setModify_ip(String modify_ip) {
		this.modify_ip = modify_ip;
	}

	public String getForum_id() {
		return forum_id;
	}

	public void setForum_id(String forum_id) {
		this.forum_id = forum_id;
	}

	public UserInfo getAuthor_info() {
		return author_info;
	}

	public void setAuthor_info(UserInfo author_info) {
		this.author_info = author_info;
	}

}
