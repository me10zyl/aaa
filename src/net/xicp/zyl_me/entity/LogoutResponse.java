package net.xicp.zyl_me.entity;

public class LogoutResponse {
	private String logoutResult;
	private Response response;
	
	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public void setLogoutResult(String logoutResult) {
		this.logoutResult = logoutResult;
	}

	public String getLogoutResult() {
		return logoutResult;
	}

	public LogoutResponse(String logoutResult, Response response) {
		this.logoutResult = logoutResult;
		this.response = response;
	}

	public LogoutResponse() {
		// TODO Auto-generated constructor stub
	}
	
}
