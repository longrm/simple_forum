package org.longrm.forum.bean;

// 记录用户积分信息
public class UserInfo {

	private String id;
	private int souls;
	private int topics;
	private int notes;
	private int mk1;
	private int mk2;
	private int mk3;
	private int time;
	private String mk1_name;
	private String mk2_name;
	private String mk3_name;
	private String mk1_unit;
	private String mk2_unit;
	private String mk3_unit;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSouls() {
		return souls;
	}

	public void setSouls(int souls) {
		this.souls = souls;
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

	public int getMk1() {
		return mk1;
	}

	public void setMk1(int mk1) {
		this.mk1 = mk1;
	}

	public int getMk2() {
		return mk2;
	}

	public void setMk2(int mk2) {
		this.mk2 = mk2;
	}

	public int getMk3() {
		return mk3;
	}

	public void setMk3(int mk3) {
		this.mk3 = mk3;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getMk1_name() {
		return mk1_name;
	}

	public void setMk1_name(String mk1_name) {
		this.mk1_name = mk1_name;
	}

	public String getMk2_name() {
		return mk2_name;
	}

	public void setMk2_name(String mk2_name) {
		this.mk2_name = mk2_name;
	}

	public String getMk3_name() {
		return mk3_name;
	}

	public void setMk3_name(String mk3_name) {
		this.mk3_name = mk3_name;
	}

	public String getMk1_unit() {
		return mk1_unit;
	}

	public void setMk1_unit(String mk1_unit) {
		this.mk1_unit = mk1_unit;
	}

	public String getMk2_unit() {
		return mk2_unit;
	}

	public void setMk2_unit(String mk2_unit) {
		this.mk2_unit = mk2_unit;
	}

	public String getMk3_unit() {
		return mk3_unit;
	}

	public void setMk3_unit(String mk3_unit) {
		this.mk3_unit = mk3_unit;
	}
}
