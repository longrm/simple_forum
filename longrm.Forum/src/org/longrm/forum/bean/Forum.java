package org.longrm.forum.bean;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Forum {

	private String id;
	private int level_index;
	private String name;
	private int rec_no;
	private String descpt;
	private int authority;
	private String rule;
	private String up_forum_id;
	private String symbol;
	private ArrayList<Forum> subForumList;
	private String master;
	private int topics;
	private int notes;
	private String last_title;
	private Timestamp last_time; // Timestamp¾«È·µ½2008/5/18 10:06:00.0
	private String last_replier;
	private String last_topic_id;

	public String getLast_replier() {
		return last_replier;
	}

	public void setLast_replier(String last_replier) {
		this.last_replier = last_replier;
	}

	public String getLast_topic_id() {
		return last_topic_id;
	}

	public void setLast_topic_id(String last_topic_id) {
		this.last_topic_id = last_topic_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLevel_index() {
		return level_index;
	}

	public void setLevel_index(int level_index) {
		this.level_index = level_index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRec_no() {
		return rec_no;
	}

	public void setRec_no(int rec_no) {
		this.rec_no = rec_no;
	}

	public String getDescpt() {
		return descpt;
	}

	public void setDescpt(String descpt) {
		this.descpt = descpt;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getUp_forum_id() {
		return up_forum_id;
	}

	public void setUp_forum_id(String up_forum_id) {
		this.up_forum_id = up_forum_id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public ArrayList<Forum> getSubForumList() {
		return subForumList;
	}

	public void setSubForumList(ArrayList<Forum> subForumList) {
		this.subForumList = subForumList;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public int getTopics() {
		return topics;
	}

	public void setTopics(int topics) {
		this.topics = topics;
	}

	public int getNotes() {
		return notes;
	}

	public void setNotes(int notes) {
		this.notes = notes;
	}

	public String getLast_title() {
		return last_title;
	}

	public void setLast_title(String last_title) {
		this.last_title = last_title;
	}

	public Timestamp getLast_time() {
		return last_time;
	}

	public void setLast_time(Timestamp last_time) {
		this.last_time = last_time;
	}

}