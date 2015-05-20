package net.xicp.zyl_me.entity;

public class LoginResponse {
	private Response response;
	private String NetGroup;
	private String ExpireTime;
	private String Name;
	private String Token;
	private String IsLogin;
	private String IsExpire;
	private String IsIDPWWrong;
	private String IsNeedUpdate;
	private String IsIPInvalid;
	private String IsDisable;
	private String PublicMessageID;
	private String LoginPublicMessage;

	public String getLoginPublicMessage() {
		return LoginPublicMessage;
	}

	public void setLoginPublicMessage(String loginPublicMessage) {
		LoginPublicMessage = loginPublicMessage;
	}

	public LoginResponse() {
		// TODO Auto-generated constructor stub
	}

	public LoginResponse(String netGroup, String expireTime, String name, String token, String isLogin, String isExpire, String isIDPWWrong, String isNeedUpdate, String isIPInvalid, String isDisable, String publicMessageID, String loginPublicMessage, Response response) {
		this.NetGroup = netGroup;
		this.ExpireTime = expireTime;
		this.Name = name;
		this.Token = token;
		this.IsLogin = isLogin;
		this.IsExpire = isExpire;
		this.IsIDPWWrong = isIDPWWrong;
		this.IsNeedUpdate = isNeedUpdate;
		this.IsIPInvalid = isIPInvalid;
		this.IsDisable = isDisable;
		this.PublicMessageID = publicMessageID;
		this.LoginPublicMessage = loginPublicMessage;
		this.response = response;
	}

	public String getExpireTime() {
		return this.ExpireTime;
	}

	public String getIsDisable() {
		return this.IsDisable;
	}

	public String getIsExpire() {
		return this.IsExpire;
	}

	public String getIsIDPWWrong() {
		return this.IsIDPWWrong;
	}

	public String getIsIPInvalid() {
		return this.IsIPInvalid;
	}

	public String getIsLogin() {
		return this.IsLogin;
	}

	public String getIsNeedUpdate() {
		return this.IsNeedUpdate;
	}

	public String getName() {
		return this.Name;
	}

	public String getNetGroup() {
		return this.NetGroup;
	}

	public String getPublicMessageID() {
		return this.PublicMessageID;
	}

	public Response getResponse() {
		return response;
	}

	public String getToken() {
		return this.Token;
	}

	public void setExpireTime(String expireTime) {
		this.ExpireTime = expireTime;
	}

	public void setIsDisable(String isDisable) {
		this.IsDisable = isDisable;
	}

	public void setIsExpire(String isExpire) {
		this.IsExpire = isExpire;
	}

	public void setIsIDPWWrong(String isIDPWWrong) {
		this.IsIDPWWrong = isIDPWWrong;
	}

	public void setIsIPInvalid(String isIPInvalid) {
		this.IsIPInvalid = isIPInvalid;
	}

	public void setIsLogin(String isLogin) {
		this.IsLogin = isLogin;
	}

	public void setIsNeedUpdate(String isNeedUpdate) {
		this.IsNeedUpdate = isNeedUpdate;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public void setNetGroup(String netGroup) {
		this.NetGroup = netGroup;
	}

	public void setPublicMessageID(String publicMessageID) {
		this.PublicMessageID = publicMessageID;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public void setToken(String token) {
		this.Token = token;
	}
}
