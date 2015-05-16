package net.xicp.zyl_me.soap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.dom4j.DocumentException;

import net.xicp.zyl_me.entity.KeepSessionRequest;
import net.xicp.zyl_me.entity.KeepSessionResponse;
import net.xicp.zyl_me.entity.LoginRequest;
import net.xicp.zyl_me.entity.LoginResponse;
import net.xicp.zyl_me.entity.Response;
import net.xicp.zyl_me.exception.DisableException;
import net.xicp.zyl_me.exception.ExpireException;
import net.xicp.zyl_me.exception.HTTPNotOKException;
import net.xicp.zyl_me.exception.IDPWWrongException;
import net.xicp.zyl_me.soap.KeepSession.OnResponseListener;
import net.xicp.zyl_me.util.EncyptUtil;

public class Client {
	public interface OnNewPublicMessageReceivedListener {
		public void onMessageReceived(String message);
	}

	public interface OnNewUserMessageReceivedListener {
		public void onMessageReceived(String message);
	}

	private LoginRequest loginRequest;
	private String userID = "";
	private String userPW = "";
	private String userIP = "";
	private String errInfo = "ED3B41FAD0157C3D8EBCB395426C52E5";
	private String computerName = "ACCELERATEDWORL";
	private String mac = "";
	private String isAutoLogin = "false";
	private String clientVersion = "1.14.10.16";
	private String osVersion = "Microsoft Windows NT 6.1.7601 Service Pack 1";

	OnNewPublicMessageReceivedListener onNewPublicMessageReceivedListener;

	OnNewUserMessageReceivedListener onNewUserMessageReceivedListener;

	public String getClientVersion() {
		return clientVersion;
	}

	public String getComputerName() {
		return computerName;
	}

	public String getErrInfo() {
		return errInfo;
	}

	public String getIsAutoLogin() {
		return isAutoLogin;
	}


	public String getMac() {
		return mac;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public String getUserID() {
		return userID;
	}

	public String getUserIP() {
		return userIP;
	}

	public String getUserPW() {
		return userPW;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}

	public void setIsAutoLogin(String isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public void setOnNewPublicMessageReceivedListener(OnNewPublicMessageReceivedListener onNewPublicMessageReceivedListener) {
		this.onNewPublicMessageReceivedListener = onNewPublicMessageReceivedListener;
	}

	public void setOnNewUserMessageReceivedListener(OnNewUserMessageReceivedListener onNewUserMessageReceivedListener) {
		this.onNewUserMessageReceivedListener = onNewUserMessageReceivedListener;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public void setUserPW(String userPW) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		this.userPW = EncyptUtil.encyptPassword(userID, userPW);
	}

	public String work() throws UnsupportedEncodingException, DocumentException, IOException, IDPWWrongException, ExpireException, HTTPNotOKException, DisableException {
		String message = "";
		loginRequest = new LoginRequest(errInfo, userID, userPW, userIP, computerName, mac, isAutoLogin, clientVersion, osVersion);
		Login login = new Login(loginRequest);
		KeepSession keepSession = null;
		LoginResponse loginResponse = login.login();
		int responseCode = loginResponse.getResponse().getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			if ("true".equals(loginResponse.getIsIPInvalid())) {
				throw new IDPWWrongException("ip无效");
			} else if ("true".equals(loginResponse.getIsIDPWWrong())) {
				throw new IDPWWrongException("用户名或密码错误");
			} else if ("true".equals(loginResponse.getIsExpire())) {
				throw new ExpireException("用户已过期");
			} else if ("true".equals(loginResponse.getIsDisable())) {
				throw new DisableException("什么鬼, 账户被禁用了");
			}
			String token = loginResponse.getToken();
			if ("true".equals(loginResponse.getIsLogin())) {
				System.out.println("登陆成功!!");
				message += "登陆成功!\n账户可用日期为 " + loginResponse.getExpireTime() + "\n宽带组：" + loginResponse.getNetGroup();
				keepSession = new KeepSession(new KeepSessionRequest(userID, userIP, token));
				keepSession.keepSession(new OnResponseListener() {
					@Override
					public void onResponse(KeepSessionResponse keepSessionResponse) {
						// TODO Auto-generated method stub
						Response response = keepSessionResponse.getResponse();
						if (response.getResponseCode() == KeepSession.EXCEPTION_OCCURED) {
							System.out.println("发生了错误！");
							System.out.println(response.getResponseString());
						} else if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
							System.out.println("KeepSession HTTP OK!");
							if ("true".equals(keepSessionResponse.getKeepSessionResult())) {
								System.out.println("保持会话成功...");
							}
							if (keepSessionResponse.getNewPublicMessage() != null && !"".equals(keepSessionResponse.getNewPublicMessage())) {
								System.out.println("你有新的公共消息...");
								System.out.println(keepSessionResponse.getNewPublicMessage());
								if (onNewPublicMessageReceivedListener != null) {
									onNewPublicMessageReceivedListener.onMessageReceived(keepSessionResponse.getNewPublicMessage());
								}
							}
							if (keepSessionResponse.getNewUserMessage() != null && !"".equals(keepSessionResponse.getNewUserMessage())) {
								System.out.println("你有新的用户消息...");
								System.out.println(keepSessionResponse.getNewUserMessage());
								if (onNewUserMessageReceivedListener != null) {
									onNewUserMessageReceivedListener.onMessageReceived(keepSessionResponse.getNewUserMessage());
								}
							}
						}
						System.out.println(response.toString());
					}
				});
			}
		} else {
			System.out.println("居然不是200 OK?!");
			System.out.println(loginResponse.getResponse());
			throw new HTTPNotOKException("居然不是200 OK?!");
		}
		return message;
	}
}
