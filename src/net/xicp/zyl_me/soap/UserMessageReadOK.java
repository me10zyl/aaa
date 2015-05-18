package net.xicp.zyl_me.soap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.xicp.zyl_me.entity.LoginRequest;
import net.xicp.zyl_me.entity.LoginResponse;
import net.xicp.zyl_me.entity.LogoutRequest;
import net.xicp.zyl_me.entity.LogoutResponse;
import net.xicp.zyl_me.entity.MessageReadOKResponse;
import net.xicp.zyl_me.entity.Response;
import net.xicp.zyl_me.entity.UserMessageReadOKRequest;
import net.xicp.zyl_me.exception.CannotConnectToServerException;
import net.xicp.zyl_me.util.EncyptUtil;
import net.xicp.zyl_me.util.InputStreamUtil;
import net.xicp.zyl_me.util.SOAPRequestUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class UserMessageReadOK {
	private UserMessageReadOKRequest userMessageReadOKRequest;

	public UserMessageReadOK(UserMessageReadOKRequest userMessageReadOKRequest) {
		super();
		this.userMessageReadOKRequest = userMessageReadOKRequest;
	}

	private Response userMessageReadOK_() throws UnsupportedEncodingException, IOException, NoSuchAlgorithmException, CannotConnectToServerException {
		Response response = null;
		String message = InputStreamUtil.getString("xml/userMessageReadOK.xml");
		message = message.replace("~UserID~", userMessageReadOKRequest.getUid());
		message = message.replace("~UserPW~", userMessageReadOKRequest.getPw());
		message = message.replace("~UserMessageID~", userMessageReadOKRequest.getUserMessageID());
		System.out.println(message);
		response = SOAPRequestUtil.request(SOAPRequestUtil.RequestAction.messageReadOK, message);
		return response;
	}

	public MessageReadOKResponse userMessageReadOK() throws DocumentException, UnsupportedEncodingException, IOException, NoSuchAlgorithmException, CannotConnectToServerException {
		Response response = userMessageReadOK_();
		MessageReadOKResponse messageReadOKResponse = parseLogoutResponse(response);
		return messageReadOKResponse;
	}

	private MessageReadOKResponse parseLogoutResponse(Response response) throws DocumentException {
		MessageReadOKResponse messageReadOKResponse = new MessageReadOKResponse();
		String responseString = response.getResponseString();
		Document responseDocument = DocumentHelper.parseText(responseString);
		Map<String, String> maps = new HashMap();
		Element root = responseDocument.getRootElement();
		Element logoutResponseElement = root.element("Body").element("MessageReadOKResponse");
		for (Iterator<Element> it = logoutResponseElement.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			maps.put(element.getName(), element.getText());
		}
		messageReadOKResponse.setMessageReadOKResult(maps.get("MessageReadOKResult"));
		messageReadOKResponse.setErrInfo(maps.get("ErrInfo"));
		messageReadOKResponse.setResponse(response);
		return messageReadOKResponse;
	}
}
