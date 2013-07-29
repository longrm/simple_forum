package org.longrm.forum.bean;

import java.sql.Timestamp;

public class Friend {

	private String me;
	private String friend;
	private Timestamp time;
	private String description;	

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFriend() {
		return friend;
	}

	public void setFriend(String friend) {
		this.friend = friend;
	}

	public String getMe() {
		return me;
	}

	public void setMe(String me) {
		this.me = me;
	}
}
