package net.xicp.zyl_me.soap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import net.xicp.zyl_me.entity.LoginRequest;
import net.xicp.zyl_me.entity.LoginResponse;
import net.xicp.zyl_me.entity.Response;
import net.xicp.zyl_me.exception.CannotConnectToServerException;
import net.xicp.zyl_me.util.InputStreamUtil;
import net.xicp.zyl_me.util.SOAPRequestUtil;

public class Login {
	private LoginRequest loginRequest = new LoginRequest();

	public Login(LoginRequest loginRequest) {
		super();
		this.loginRequest.setErrInfo(loginRequest.getErrInfo());
		this.loginRequest.setUserID(loginRequest.getUserID());
		this.loginRequest.setUserPW(loginRequest.getUserPW());
		this.loginRequest.setUserIP(loginRequest.getUserIP());
		this.loginRequest.setComputerName(loginRequest.getComputerName());
		this.loginRequest.setMac(loginRequest.getMac());
		this.loginRequest.setIsAutoLogin(loginRequest.getIsAutoLogin());
		this.loginRequest.setClientVersion(loginRequest.getClientVersion());
		this.loginRequest.setOsVersion(loginRequest.getOsVersion());
	}

	private Response login_() throws UnsupportedEncodingException, IOException, CannotConnectToServerException {
		Response response = null;
		String message = InputStreamUtil.getString("xml/login.xml");
		message = message.replace("~ErrInfo~", loginRequest.getErrInfo());
		message = message.replace("~UserID~", loginRequest.getUserID());
		message = message.replace("~UserPW~", loginRequest.getUserPW());
		message = message.replace("~UserIP~", loginRequest.getUserIP());
		message = message.replace("~ComputerName~", loginRequest.getComputerName());
		message = message.replace("~MAC~", loginRequest.getMac());
		message = message.replace("~IsAutoLogin~", loginRequest.getIsAutoLogin());
		message = message.replace("~ClientVersion~", loginRequest.getClientVersion());
		message = message.replace("~OSVersion~", loginRequest.getOsVersion());
		response = SOAPRequestUtil.request(SOAPRequestUtil.RequestAction.login, message);
		return response;
	}

	public LoginResponse login() throws DocumentException, UnsupportedEncodingException, IOException, CannotConnectToServerException {
		Response response = login_();
		LoginResponse loginResponse = parseLoginResponse(response);
		return loginResponse;
	}

	private LoginResponse parseLoginResponse(Response response) throws DocumentException {
		LoginResponse loginResponse = new LoginResponse();
		String responseString = response.getResponseString();
		Document responseDocument = DocumentHelper.parseText(responseString);
		Map<String, String> maps = new HashMap();
		Element root = responseDocument.getRootElement();
		Element loginResult = root.element("Body").element("LoginResponse").element("LoginResult");
		Element loginPublicMessage = null;
		for (Iterator<Element> it = loginResult.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			maps.put(element.getName(), element.getText());
			if(element.getName().equals("LoginPublicMessage"))
				loginPublicMessage = element;
		}
		loginResponse.setName((String) maps.get("Name"));
		loginResponse.setNetGroup((String) maps.get("NetGroup"));
		loginResponse.setExpireTime((String) maps.get("ExpireTime"));
		loginResponse.setToken((String) maps.get("Token"));
		loginResponse.setIsLogin((String) maps.get("IsLogin"));
		loginResponse.setIsExpire((String) maps.get("IsExpire"));
		loginResponse.setIsIDPWWrong((String) maps.get("IsIDPWWrong"));
		loginResponse.setIsNeedUpdate((String) maps.get("IsNeedUpdate"));
		loginResponse.setIsIPInvalid((String) maps.get("IsIPInvalid"));
		loginResponse.setIsDisable((String) maps.get("IsDisable"));
		loginResponse.setPublicMessageID((String) maps.get("PublicMessageID"));
		if(loginPublicMessage != null)
		{
			Element stringElement = loginPublicMessage.element("string");
			if(stringElement != null)
				loginResponse.setLoginPublicMessage(stringElement.getText());
		}
		loginResponse.setResponse(response);
		return loginResponse;
	}
}
