package net.xicp.zyl_me.entity;

public class UserMessageReadOKRequest {
	private String uid;
	private String pw;
	private String userMessageID;
	public String getUid() {
		return uid;
	}
	public UserMessageReadOKRequest(String uid, String pw, String userMessageID) {
		super();
		this.uid = uid;
		this.pw = pw;
		this.userMessageID = userMessageID;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getUserMessageID() {
		return userMessageID;
	}
	public void setUserMessageID(String userMessageID) {
		this.userMessageID = userMessageID;
	}
}
