package net.xicp.zyl_me;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import net.xicp.zyl_me.exception.DisableException;
import net.xicp.zyl_me.exception.ExpireException;
import net.xicp.zyl_me.exception.HTTPNotOKException;
import net.xicp.zyl_me.exception.IDPWWrongException;
import net.xicp.zyl_me.soap.Client;
import net.xicp.zyl_me.soap.Client.OnNewPublicMessageReceivedListener;
import net.xicp.zyl_me.soap.Client.OnNewUserMessageReceivedListener;

import org.dom4j.DocumentException;

public class MainConsole {
	private static String userID = "12310320304";
	private static String userPW = "uujjmm";
	private static String userIP = "169.254.92.24,10.97.34.26,192.168.160.1,192.168.242.1";
	private static String errInfo = "ED3B41FAD0157C3D8EBCB395426C52E5";
	private static String computerName = "ACCELERATEDWORL";
	private static String mac = "6002B4E55A37,C45444AB85DE,005056C00001,005056C00008";
	private static String isAutoLogin = "false";
	private static String clientVersion = "1.14.10.16";
	private static String osVersion = "Microsoft Windows NT 6.1.7601 Service Pack 1";

	public MainConsole() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws DocumentException {
		try {
			Client client = new Client();
			client.setClientVersion(clientVersion);
			client.setOsVersion(osVersion);
			client.setComputerName(computerName);
			client.setErrInfo(errInfo);
			client.setIsAutoLogin(isAutoLogin);
			client.setMac(mac);
			client.setUserID(userID);
			client.setUserIP(userIP);
			client.setUserPW(userPW);
			client.setOnNewPublicMessageReceivedListener(new OnNewPublicMessageReceivedListener() {
				@Override
				public void onMessageReceived(String message) {
					// TODO Auto-generated method stub
					System.out.println(message);
				}
			});
			client.setOnNewUserMessageReceivedListener(new OnNewUserMessageReceivedListener() {
				@Override
				public void onMessageReceived(String message) {
					// TODO Auto-generated method stub
					System.out.println(message);
				}
			});
			client.work();
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | IOException | IDPWWrongException | ExpireException | HTTPNotOKException | DisableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
