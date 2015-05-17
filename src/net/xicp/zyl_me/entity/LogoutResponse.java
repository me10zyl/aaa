package net.xicp.zyl_me.entity;

public class LogoutResponse {
	private String logoutResult;
	private Response response;
	private String errorInfo;
	
	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

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

	public LogoutResponse(String logoutResult, Response response,String errorInfo) {
		this.logoutResult = logoutResult;
		this.response = response;
		this.errorInfo = errorInfo;
	}

	public LogoutResponse() {
		// TODO Auto-generated constructor stub
	}
	
}
