package org.longrm.forum.bean;

import java.sql.Timestamp;

import org.longrm.forum.util.Constant;

public class Topic extends BasicTopic {
	
	private int pageCount;
	
	private int authority;
	private int top;
	private int soul;
	private String color;
	private String bold;
	private String kind;
	private int click;
	private int re_topics;
	private Timestamp last_time;
	private String last_replier;
	private int status;  // 帖子状态（0正常、-1锁定、-2关闭...）

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getSoul() {
		return soul;
	}

	public void setSoul(int soul) {
		this.soul = soul;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBold() {
		return bold;
	}

	public void setBold(String bold) {
		this.bold = bold;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public int getClick() {
		return click;
	}

	public void setClick(int click) {
		this.click = click;
	}

	public int getRe_topics() {
		return re_topics;
	}

	public void setRe_topics(int re_topics) {
		this.re_topics = re_topics;
		
		// 计算页面数
		int pageCount = (re_topics+1)/Constant.TOPIC_PAGESIZE;
		if((re_topics+1)%Constant.TOPIC_PAGESIZE!=0)
			pageCount++;
		setPageCount(pageCount);
	}

	public Timestamp getLast_time() {
		return last_time;
	}

	public void setLast_time(Timestamp last_time) {
		this.last_time = last_time;
	}

	public String getLast_replier() {
		return last_replier;
	}

	public void setLast_replier(String last_replier) {
		this.last_replier = last_replier;
	}

	public int getPageCount() {
		return pageCount;
	}

	// 函数设为protected，只在setRe_topics里自动调用，外面不得修改
	protected void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
