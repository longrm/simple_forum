package org.longrm.forum.bean;

public class Role extends BasicRole {
	
	private int mincount;
	private int maxcount;
	
	public int getMaxcount() {
		return maxcount;
	}
	public void setMaxcount(int maxcount) {
		this.maxcount = maxcount;
	}
	public int getMincount() {
		return mincount;
	}
	public void setMincount(int mincount) {
		this.mincount = mincount;
	}

}
