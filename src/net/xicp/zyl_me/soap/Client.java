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
import javax.swing.JOptionPane;

import org.dom4j.DocumentException;

import net.xicp.zyl_me.entity.KeepSessionRequest;
import net.xicp.zyl_me.entity.KeepSessionResponse;
import net.xicp.zyl_me.entity.LoginRequest;
import net.xicp.zyl_me.entity.LoginResponse;
import net.xicp.zyl_me.entity.LogoutRequest;
import net.xicp.zyl_me.entity.LogoutResponse;
import net.xicp.zyl_me.entity.Response;
import net.xicp.zyl_me.exception.CannotConnectToServerException;
import net.xicp.zyl_me.exception.DisableException;
import net.xicp.zyl_me.exception.ExpireException;
import net.xicp.zyl_me.exception.HTTPNotOKException;
import net.xicp.zyl_me.exception.IDPWWrongException;
import net.xicp.zyl_me.exception.LogoutFailedException;
import net.xicp.zyl_me.soap.KeepSession.OnResponseListener;
import net.xicp.zyl_me.util.EncyptUtil;

public class Client {
	public interface OnNewPublicMessageReceivedListener {
		public void onMessageReceived(String message);
	}

	public interface OnNewUserMessageReceivedListener {
		public void onMessageReceived(String message);
	}
	
	public interface OnErrorListener{
		public void onError(String message);
	}
	
	public interface OnLogoutSuccessListener{
		public void onLogout(String message);
	}
	
	private String publicMessageID = "";
	private String userMessageID = "";
	private LoginRequest loginRequest;
	private LogoutRequest logoutRequest;
	private String userID = "";
	private String userPW = "";
	private String userIP = "";
	private String errInfo = "ED3B41FAD0157C3D8EBCB395426C52E5";
	private String computerName = "ACCELERATEDWORL";
	private String mac = "";
	private String isAutoLogin = "false";
	private String clientVersion = "1.14.10.16";
	private String osVersion = "Microsoft Windows NT 6.1.7601 Service Pack 1";
	private String token;
	private String loginStatus = "non-login";
	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	OnNewPublicMessageReceivedListener onNewPublicMessageReceivedListener;

	OnNewUserMessageReceivedListener onNewUserMessageReceivedListener;
	
	OnErrorListener onErrorListener;
	
	OnLogoutSuccessListener onLogoutListener;
	public OnLogoutSuccessListener getOnLogoutSucessListener() {
		return onLogoutListener;
	}

	public void setOnLogoutSuccessListener(OnLogoutSuccessListener onLogoutListener) {
		this.onLogoutListener = onLogoutListener;
	}

	public OnErrorListener getOnErrorListener() {
		return onErrorListener;
	}

	public void setOnErrorListener(OnErrorListener onErrorListener) {
		this.onErrorListener = onErrorListener;
	}

	private KeepSession keepSession;

	public KeepSession getKeepSession() {
		return keepSession;
	}

	public void setKeepSession(KeepSession keepSession) {
		this.keepSession = keepSession;
	}

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

	public String logout() throws NoSuchAlgorithmException, DocumentException, IOException, HTTPNotOKException, LogoutFailedException, CannotConnectToServerException
	{
		System.out.println("注销中...");
		String message = "";
		cancelKeepSession("logout");
		logoutRequest = new LogoutRequest(userID,userIP,token);
		Logout logout = new Logout(logoutRequest);
		LogoutResponse logoutResponse = logout.logout();
		if(logoutResponse.getResponse().getResponseCode() == HttpURLConnection.HTTP_OK)
		{
			if("true".equals(logoutResponse.getLogoutResult()))
			{
				System.out.println("注销成功!");
				message += "注销成功!";
				onLogoutListener.onLogout(message);
			}else
			{
				cancelKeepSession("logoutFailed");
				throw new LogoutFailedException("注销失败" + (logoutResponse.getErrorInfo() != null ? ":"+logoutResponse.getErrorInfo() : ""));
			}
		}else
		{
			throw new HTTPNotOKException("注销失败！");
		}
		return message;
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
	
	public String work() throws UnsupportedEncodingException, DocumentException, IOException, IDPWWrongException, ExpireException, HTTPNotOKException, DisableException, NoSuchAlgorithmException, CannotConnectToServerException {
		String message = "";
		loginRequest = new LoginRequest(errInfo, userID, userPW, userIP, computerName, mac, isAutoLogin, clientVersion, osVersion);
		Login login = new Login(loginRequest);
		LoginResponse loginResponse = login.login();
		int responseCode = loginResponse.getResponse().getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			if ("true".equals(loginResponse.getIsIPInvalid())) {
				loginStatus = "login-failed";
				throw new IDPWWrongException("ip无效");
			} else if ("true".equals(loginResponse.getIsIDPWWrong())) {
				loginStatus = "login-failed";
				throw new IDPWWrongException("用户名或密码错误");
			} else if ("true".equals(loginResponse.getIsExpire())) {
				loginStatus = "login-failed";
				throw new ExpireException("用户已过期");
			} else if ("true".equals(loginResponse.getIsDisable())) {
				loginStatus = "login-failed";
				throw new DisableException("什么鬼, 账户被禁用了");
			}
			if ("true".equals(loginResponse.getIsLogin())) {
				loginStatus = "login";
				System.out.println("登陆成功!!");
				message += "登陆成功!\n账户可用日期为:\n" + loginResponse.getExpireTime() + "\n宽带组：" + loginResponse.getNetGroup();
				token = loginResponse.getToken();
				token = EncyptUtil.encyptMD5(token);
				keepSession = new KeepSession(new KeepSessionRequest(userID, userIP, token));
				keepSession.keepSession(new OnResponseListener() {
					@Override
					public void onResponse(KeepSessionResponse keepSessionResponse) {
						// TODO Auto-generated method stub
						Response response = keepSessionResponse.getResponse();
						if (response.getResponseCode() == KeepSession.EXCEPTION_OCCURED) {
							loginStatus = "keepSession-failed";
							System.out.println("发生了错误！");
							System.out.println(response.getResponseString());
							onErrorListener.onError(response.getResponseString());
						} else if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
							System.out.println("KeepSession HTTP OK!");
							if ("true".equals(keepSessionResponse.getKeepSessionResult())) {
								System.out.println("保持会话成功...");
							}else
							{//TODO keepSession failed
								System.out.println("保持会话失败...");
								cancelKeepSession("keepSessionFailed");
								onErrorListener.onError("你已断开连接"+ (keepSessionResponse.getErrorInfo() != null ? ":" + keepSessionResponse.getErrorInfo() : ""));
							}
							if (keepSessionResponse.getNewPublicMessage() != null && !"".equals(keepSessionResponse.getNewPublicMessage()) && !keepSessionResponse.getNewPublicMessage().startsWith(publicMessageID)) {
								publicMessageID = keepSessionResponse.getNewPublicMessage().split("|")[0];
								System.out.println("你有新的公共消息...");
								System.out.println(keepSessionResponse.getNewPublicMessage());
								if (onNewPublicMessageReceivedListener != null) {
									onNewPublicMessageReceivedListener.onMessageReceived(keepSessionResponse.getNewPublicMessage().split("|")[keepSessionResponse.getNewPublicMessage().length() - 1]);
								}
							}
							if (keepSessionResponse.getNewUserMessage() != null && !"".equals(keepSessionResponse.getNewUserMessage()) && !keepSessionResponse.getNewUserMessage().startsWith(userMessageID)) {
								userMessageID = keepSessionResponse.getNewUserMessage().split("|")[0];
								System.out.println("你有新的用户消息...");
								System.out.println(keepSessionResponse.getNewUserMessage());
								if (onNewUserMessageReceivedListener != null) {
									onNewUserMessageReceivedListener.onMessageReceived(keepSessionResponse.getNewUserMessage().split("|")[keepSessionResponse.getNewUserMessage().length() - 1]);
								}
							}
						}else
						{
							loginStatus = "keepSession-failed";
							System.out.println("居然不是200 OK?!连接服务器超时");
							onErrorListener.onError("居然不是200 OK?!连接服务器超时");
						}
						System.out.println(response.toString());
					}
				});
			}
		} else {
			loginStatus = "login-failed";
			System.out.println("居然不是200 OK?!");
			System.out.println(loginResponse.getResponse());
			throw new HTTPNotOKException("居然不是200 OK?!登陆失败");
		}
		return message;
	}
	
	public void cancelKeepSession(String newLoginStatus) {
		keepSession.cancel();
		loginStatus = newLoginStatus;
	}
	
}
