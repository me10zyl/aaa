package net.xicp.zyl_me.entity;

public class Response {
	private int responseCode;
	private String responseString;
	public Response(int responseCode, String responseString) {
		super();
		this.responseCode = responseCode;
		this.responseString = responseString;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public String getResponseString() {
		return responseString;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return responseString;
	}
}
