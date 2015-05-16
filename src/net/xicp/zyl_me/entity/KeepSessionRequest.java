package net.xicp.zyl_me.entity;

public class KeepSessionRequest {
	private String userID;
	private String userIP;
	private String token;
	public KeepSessionRequest(String userID, String userIP, String token) {
		super();
		this.userID = userID;
		this.userIP = userIP;
		this.token = token;
	}
	public String getToken() {
		return token;
	}
	public String getUserID() {
		return userID;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
}
