package net.xicp.zyl_me.entity;

public class KeepSessionResponse {
	private String KeepSessionResult;
	private String newPublicMessage;
	private String newUserMessage;
	private Response response;
	public KeepSessionResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public KeepSessionResponse(String keepSessionResult, String newPublicMessage, String newUserMessage, Response response) {
		super();
		KeepSessionResult = keepSessionResult;
		this.newPublicMessage = newPublicMessage;
		this.newUserMessage = newUserMessage;
		this.response = response;
	}

	public String getKeepSessionResult() {
		return KeepSessionResult;
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
		KeepSessionResult = keepSessionResult;
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
