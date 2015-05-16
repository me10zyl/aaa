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
import net.xicp.zyl_me.util.InputStreamUtil;
import net.xicp.zyl_me.util.SOAPRequestUtil;

public class Login {
	private LoginRequest loginData = new LoginRequest();

	public Login(LoginRequest loginData) {
		super();
		this.loginData.setErrInfo(loginData.getErrInfo());
		this.loginData.setUserID(loginData.getUserID());
		this.loginData.setUserPW(loginData.getUserPW());
		this.loginData.setUserIP(loginData.getUserIP());
		this.loginData.setComputerName(loginData.getComputerName());
		this.loginData.setMac(loginData.getMac());
		this.loginData.setIsAutoLogin(loginData.getIsAutoLogin());
		this.loginData.setClientVersion(loginData.getClientVersion());
		this.loginData.setOsVersion(loginData.getOsVersion());
	}

	private Response login_() throws UnsupportedEncodingException, IOException {
		Response response = null;
		String message = InputStreamUtil.getString("xml/login.xml");
		message = message.replace("~ErrInfo~", loginData.getErrInfo());
		message = message.replace("~UserID~", loginData.getUserID());
		message = message.replace("~UserPW~", loginData.getUserPW());
		message = message.replace("~UserIP~", loginData.getUserIP());
		message = message.replace("~ComputerName~", loginData.getComputerName());
		message = message.replace("~MAC~", loginData.getMac());
		message = message.replace("~IsAutoLogin~", loginData.getIsAutoLogin());
		message = message.replace("~ClientVersion~", loginData.getClientVersion());
		message = message.replace("~OSVersion~", loginData.getOsVersion());
		response = SOAPRequestUtil.request(SOAPRequestUtil.RequestAction.login, message);
		return response;
	}

	public LoginResponse login() throws DocumentException, UnsupportedEncodingException, IOException {
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
		for (Iterator<Element> it = loginResult.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			maps.put(element.getName(), element.getText());
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
		loginResponse.setResponse(response);
		return loginResponse;
	}
}
