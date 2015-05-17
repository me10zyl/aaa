import static org.junit.Assert.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import net.xicp.zyl_me.entity.LoginRequest;
import net.xicp.zyl_me.entity.LoginResponse;
import net.xicp.zyl_me.soap.Login;
import net.xicp.zyl_me.util.EncyptUtil;
import net.xicp.zyl_me.util.SystemUtil;

import org.dom4j.DocumentException;
import org.junit.Test;


public class LoginTest {
	private static String userID = "";
	private static String userPW = "";
	private static String userIP = "169.254.92.24,10.97.34.26,192.168.160.1,192.168.242.1";
	private static String errInfo = "ED3B41FAD0157C3D8EBCB395426C52E5";
	private static String computerName = "ACCELERATEDWORL";
	private static String mac = "6002B4E55A37,C45444AB85DE,005056C00001,005056C00008";
	private static String isAutoLogin = "false";
	private static String clientVersion = "1.14.10.16";
	private static String osVersion = "Microsoft Windows NT 6.1.7601 Service Pack 1";
	@Test
	public void testLogin() throws UnsupportedEncodingException, DocumentException, IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		computerName = SystemUtil.getComputerName();
		userIP = SystemUtil.getIPAddress();
		mac = SystemUtil.getMACAddress();
		Login login = new Login(new LoginRequest(errInfo, userID, EncyptUtil.encyptPassword(userID, userPW), userIP, computerName, mac, isAutoLogin, clientVersion, osVersion));
		LoginResponse loginResponse = login.login();
		System.out.println(loginResponse.getResponse());
		System.out.println(loginResponse.getExpireTime());
	}
}
