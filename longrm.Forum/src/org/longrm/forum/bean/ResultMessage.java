package org.longrm.forum.bean;

public class ResultMessage {

	private String location = "信息提示";
	private String title = "信息提示";
	private String message = "";
	private String url;
	boolean success = false;

	public ResultMessage(String location, String message, boolean success) {
		setParams(location, null, message, null, success);
	}

	public ResultMessage(String location, String message, String url, boolean success) {
		setParams(location, null, message, url, success);
	}

	public ResultMessage(String location, String title, String message, String url, boolean success) {
		setParams(location, title, message, url, success);
	}

	/**
	 * 设置参数
	 * @param location
	 * @param title
	 * @param message
	 * @param url
	 * @param success
	 */
	public void setParams(String location, String title, String message, String url, boolean success) {
		if(location!=null)
			setLocation(location);
		if(title!=null)
			setTitle(title);
		if(message!=null)
			setMessage(message);
		if(success && url!=null)
			setUrl(url);
		setSuccess(success);		
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
