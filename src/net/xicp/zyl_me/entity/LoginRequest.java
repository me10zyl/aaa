package net.xicp.zyl_me.entity;

public class LoginRequest {
	private String errInfo;
	private String userID;
	private String userPW;
	private String userIP;
	private String computerName;
	private String mac;
	private String isAutoLogin;
	private String clientVersion;
	public LoginRequest(String errInfo, String userID, String userPW, String userIP, String computerName, String mac, String isAutoLogin, String clientVersion, String osVersion) {
		super();
		this.errInfo = errInfo;
		this.userID = userID;
		this.userPW = userPW;
		this.userIP = userIP;
		this.computerName = computerName;
		this.mac = mac;
		this.isAutoLogin = isAutoLogin;
		this.clientVersion = clientVersion;
		this.osVersion = osVersion;
	}

	private String osVersion;

	public LoginRequest() {
	}

	public String getErrInfo() {
		return errInfo;
	}

	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserPW() {
		return userPW;
	}

	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getIsAutoLogin() {
		return isAutoLogin;
	}

	public void setIsAutoLogin(String isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
}