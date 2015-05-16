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
	OnNewPublicMessageReceivedListener onNewPublicMessageReceivedListener;

	OnNewUserMessageReceivedListener onNewUserMessageReceivedListener;
	private KeepSession keepSession;

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

	public String logout() throws NoSuchAlgorithmException, DocumentException, IOException, HTTPNotOKException, LogoutFailedException
	{
		String message = "";
		keepSession.setLoginStatus("logout");
		token = EncyptUtil.encyptMD5(token);
		logoutRequest = new LogoutRequest(userID,userIP,token);
		Logout logout = new Logout(logoutRequest);
		LogoutResponse logoutResponse = logout.logout();
		if(logoutResponse.getResponse().getResponseCode() == HttpURLConnection.HTTP_OK)
		{
			if("true".equals(logoutResponse.getLogoutResult()))
			{
				System.out.println("ע���ɹ�!");
				message += "ע���ɹ�!";
			}else
			{
				throw new LogoutFailedException("ע��ʧ�ܣ�");
			}
		}else
		{
			throw new HTTPNotOKException("ע��ʧ�ܣ�");
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
	
	public String work() throws UnsupportedEncodingException, DocumentException, IOException, IDPWWrongException, ExpireException, HTTPNotOKException, DisableException {
		String message = "";
		loginRequest = new LoginRequest(errInfo, userID, userPW, userIP, computerName, mac, isAutoLogin, clientVersion, osVersion);
		Login login = new Login(loginRequest);
		LoginResponse loginResponse = login.login();
		int responseCode = loginResponse.getResponse().getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			if ("true".equals(loginResponse.getIsIPInvalid())) {
				throw new IDPWWrongException("ip��Ч");
			} else if ("true".equals(loginResponse.getIsIDPWWrong())) {
				throw new IDPWWrongException("�û������������");
			} else if ("true".equals(loginResponse.getIsExpire())) {
				throw new ExpireException("�û��ѹ���");
			} else if ("true".equals(loginResponse.getIsDisable())) {
				throw new DisableException("ʲô��, �˻���������");
			}
			token = loginResponse.getToken();
			if ("true".equals(loginResponse.getIsLogin())) {
				System.out.println("��½�ɹ�!!");
				message += "��½�ɹ�!\n�˻���������Ϊ " + loginResponse.getExpireTime() + "\n����飺" + loginResponse.getNetGroup();
				keepSession = new KeepSession(new KeepSessionRequest(userID, userIP, token));
				keepSession.keepSession(new OnResponseListener() {
					@Override
					public void onResponse(KeepSessionResponse keepSessionResponse) {
						// TODO Auto-generated method stub
						Response response = keepSessionResponse.getResponse();
						if (response.getResponseCode() == KeepSession.EXCEPTION_OCCURED) {
							System.out.println("�����˴���");
							System.out.println(response.getResponseString());
						} else if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
							System.out.println("KeepSession HTTP OK!");
							if ("true".equals(keepSessionResponse.getKeepSessionResult())) {
								System.out.println("���ֻỰ�ɹ�...");
							}
							if (keepSessionResponse.getNewPublicMessage() != null && !"".equals(keepSessionResponse.getNewPublicMessage())) {
								System.out.println("�����µĹ�����Ϣ...");
								System.out.println(keepSessionResponse.getNewPublicMessage());
								if (onNewPublicMessageReceivedListener != null) {
									onNewPublicMessageReceivedListener.onMessageReceived(keepSessionResponse.getNewPublicMessage());
								}
							}
							if (keepSessionResponse.getNewUserMessage() != null && !"".equals(keepSessionResponse.getNewUserMessage())) {
								System.out.println("�����µ��û���Ϣ...");
								System.out.println(keepSessionResponse.getNewUserMessage());
								if (onNewUserMessageReceivedListener != null) {
									onNewUserMessageReceivedListener.onMessageReceived(keepSessionResponse.getNewUserMessage());
								}
							}
						}else
						{
							System.out.println("��Ȼ����200 OK?!���ӷ�������ʱ");
							JOptionPane.showMessageDialog(null, "��Ȼ����200 OK?!���ӷ�������ʱ");
						}
						System.out.println(response.toString());
					}
				});
			}
		} else {
			System.out.println("��Ȼ����200 OK?!");
			System.out.println(loginResponse.getResponse());
			throw new HTTPNotOKException("��Ȼ����200 OK?!��½ʧ��");
		}
		return message;
	}
	
}
