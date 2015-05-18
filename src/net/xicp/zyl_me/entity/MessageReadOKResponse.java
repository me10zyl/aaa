package net.xicp.zyl_me.entity;

public class MessageReadOKResponse {
	private String messageReadOKResult;
	private String errInfo;
	private Response response;
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	public String getMessageReadOKResult() {
		return messageReadOKResult;
	}
	public void setMessageReadOKResult(String messageReadOKResult) {
		this.messageReadOKResult = messageReadOKResult;
	}
	public String getErrInfo() {
		return errInfo;
	}
	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}
	
}
