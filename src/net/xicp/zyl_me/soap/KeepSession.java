package net.xicp.zyl_me.soap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import net.xicp.zyl_me.entity.KeepSessionRequest;
import net.xicp.zyl_me.entity.KeepSessionResponse;
import net.xicp.zyl_me.entity.KeepSessionResponse;
import net.xicp.zyl_me.entity.Response;
import net.xicp.zyl_me.util.EncyptUtil;
import net.xicp.zyl_me.util.InputStreamUtil;
import net.xicp.zyl_me.util.SOAPRequestUtil;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class KeepSession {
	private KeepSessionRequest keepSessionData;
	public static final int EXCEPTION_OCCURED = 12345;
	public KeepSession(KeepSessionRequest keepSessionRequest) {
		super();
		this.keepSessionData = keepSessionRequest;
	}

	private boolean isFirstTimeToLogin = true;

	interface OnResponseListener {
		public void onResponse(KeepSessionResponse response);
	};

	public void keepSession(final OnResponseListener onRespnseListener) throws UnsupportedEncodingException, IOException {
		final String messageStub = InputStreamUtil.getString("xml/login.xml");
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					String message = messageStub;
					if (isFirstTimeToLogin) {
						keepSessionData.setToken(EncyptUtil.encyptMD5(keepSessionData.getToken()));
						keepSessionData.setToken(EncyptUtil.encyptMD5(keepSessionData.getToken()));
					} else {
						keepSessionData.setToken(EncyptUtil.encyptMD5(keepSessionData.getToken()));
					}
					message = message.replace("~UserID~", keepSessionData.getUserID());
					message = message.replace("~UserIP~", keepSessionData.getUserIP());
					message = message.replace("~Token~", keepSessionData.getToken());
					Response response = SOAPRequestUtil.request(SOAPRequestUtil.RequestAction.keepSession, message);
					KeepSessionResponse keepSessionResponse = parseKeepSessionResponse(response);
					onRespnseListener.onResponse(keepSessionResponse);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					KeepSessionResponse errorResponse = new KeepSessionResponse();
					errorResponse.setResponse(new Response(EXCEPTION_OCCURED, e.getMessage()));
					onRespnseListener.onResponse(errorResponse);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					KeepSessionResponse errorResponse = new KeepSessionResponse();
					errorResponse.setResponse(new Response(EXCEPTION_OCCURED, e.getMessage()));
					onRespnseListener.onResponse(errorResponse);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					KeepSessionResponse errorResponse = new KeepSessionResponse();
					errorResponse.setResponse(new Response(EXCEPTION_OCCURED, e.getMessage()));
					onRespnseListener.onResponse(errorResponse);
				}
			}
		}, 10000, 20000);
	}

	private KeepSessionResponse parseKeepSessionResponse(Response response) throws DocumentException {
		Map<String, String> maps = new HashMap();
		Document document = DocumentHelper.parseText(response.toString());
		Element root = document.getRootElement();
		Element target = root.element("Body").element("KeepSessionResponse");
		for (Iterator<Element> it = target.elementIterator(); it.hasNext();) {
			Element element = (Element) it.next();
			maps.put(element.getName(), element.getText());
		}
		KeepSessionResponse keepSessionResponse = new KeepSessionResponse();
		keepSessionResponse.setNewPublicMessage((String) maps.get("NewPublicMessage"));
		keepSessionResponse.setNewUserMessage(((String) maps.get("NewUserMessage")));
		keepSessionResponse.setKeepSessionResult((String) maps.get("KeepSessionResult"));
		keepSessionResponse.setResponse(response);
		return keepSessionResponse;
	}
}
