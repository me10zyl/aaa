package net.xicp.zyl_me.entity;

public class KeepSessionResponse {
	private String keepSessionResult;
	private String newPublicMessage;
	private String newUserMessage;
	private String errorInfo;
	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	private Response response;
	public KeepSessionResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public KeepSessionResponse(String keepSessionResult, String newPublicMessage, String newUserMessage, Response response,String errorInfo) {
		super();
		this.keepSessionResult = keepSessionResult;
		this.newPublicMessage = newPublicMessage;
		this.newUserMessage = newUserMessage;
		this.response = response;
		this.errorInfo = errorInfo;
	}

	public String getKeepSessionResult() {
		return keepSessionResult;
	}
	public String getNewPublicMessage() {
		return newPublicMessage;
	}
	public String getNewUserMessage() {
		return newUserMessage;
	}
	public Response getResponse() {
		return response;
	}
	public void setKeepSessionResult(String keepSessionResult) {
		this.keepSessionResult = keepSessionResult;
	}
	public void setNewPublicMessage(String newPublicMessage) {
		this.newPublicMessage = newPublicMessage;
	}
	public void setNewUserMessage(String newUserMessage) {
		this.newUserMessage = newUserMessage;
	}
	public void setResponse(Response response) {
		this.response = response;
	}

}
