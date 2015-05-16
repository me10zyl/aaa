package net.xicp.zyl_me.entity;

public class LogoutRequest {
	private String userID;
	private String userIP;
	private String token;

	public LogoutRequest(String userID, String userIP, String token) {
		super();
		this.userID = userID;
		this.userIP = userIP;
		this.token = token;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
