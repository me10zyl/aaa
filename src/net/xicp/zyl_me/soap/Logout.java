package net.xicp.zyl_me.soap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.xicp.zyl_me.entity.LoginRequest;
import net.xicp.zyl_me.entity.LoginResponse;
import net.xicp.zyl_me.entity.LogoutRequest;
import net.xicp.zyl_me.entity.LogoutResponse;
import net.xicp.zyl_me.entity.Response;
import net.xicp.zyl_me.util.InputStreamUtil;
import net.xicp.zyl_me.util.SOAPRequestUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Logout {
	private LogoutRequest logoutRequest;

	public Logout(LogoutRequest logoutRequest) {
		super();
		this.logoutRequest = logoutRequest;
	}

	private Response logout_() throws UnsupportedEncodingException, IOException {
		Response response = null;
		String message = InputStreamUtil.getString("xml/login.xml");
		message = message.replace("~UserID~", logoutRequest.getUserID());
		message = message.replace("~UserIP~", logoutRequest.getUserIP());
		message = message.replace("~Token~", logoutRequest.getUserIP());
		response = SOAPRequestUtil.request(SOAPRequestUtil.RequestAction.logout, message);
		return response;
	}

	public LogoutResponse logout() throws DocumentException, UnsupportedEncodingException, IOException {
		Response response = logout_();
		LogoutResponse logoutResponse = parseLogoutResponse(response);
		return logoutResponse;
	}

	private LogoutResponse parseLogoutResponse(Response response) throws DocumentException {
		LogoutResponse logoutResponse = new LogoutResponse();
		String responseString = response.getResponseString();
		Document responseDocument = DocumentHelper.parseText(responseString);
		Map<String, String> maps = new HashMap();
		Element root = responseDocument.getRootElement();
		Element loginResult = root.element("Body").element("LogoutResponse").element("LogoutResult");
		for (Iterator<Element> it = loginResult.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			maps.put(element.getName(), element.getText());
		}
		logoutResponse.setLogoutResult(maps.get("LogoutResult"));
		logoutResponse.setResponse(response);
		return logoutResponse;
	}
}
